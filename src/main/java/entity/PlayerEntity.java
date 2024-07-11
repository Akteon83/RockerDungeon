package main.java.entity;

import main.java.GamePanel;
import main.java.instrument.Instrument;
import main.java.Sound;
import main.java.level.Level;
import main.java.tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;

public class PlayerEntity extends LivingEntity {

    private int shield;
    private final int maxShield;
    private int shieldDelay;
    public int fireDelay;
    public int fireCharge;
    private int counter;
    private boolean imageAlt;
    public Instrument instrument;

    Image imageStanding = (new ImageIcon("res/player_sprite.png")).getImage();
    Image imageMoving = (new ImageIcon("res/player_sprite_moving.png")).getImage();

    public PlayerEntity(double x, double y, Level level) {
        super(x, y, 100, level);
        width = 32 * GamePanel.SIZE;
        height = 32 * GamePanel.SIZE;
        updateHitBox();
        maxShield = 100;
        shield = maxShield;
        velocity = 4;
    }

    @Override
    public void draw(Graphics2D g2, PlayerEntity player, GamePanel panel) {
        super.draw(g2, player, panel);
        if (instrument != null) {
            Point centerDrawPosition = new Point(getCenterX() + panel.screenCenter.x - player.getCenterX(),
                    getCenterY() + panel.screenCenter.y - player.getCenterY());
            g2.rotate(angle, centerDrawPosition.x, centerDrawPosition.y);
            if (Math.abs(angle) < Math.PI / 2) {
                g2.drawImage(instrument.image, centerDrawPosition.x + 2 * GamePanel.SIZE, centerDrawPosition.y - 16 * GamePanel.SIZE,
                        16 * GamePanel.SIZE, 32 * GamePanel.SIZE, null);
            } else {
                g2.drawImage(instrument.image, centerDrawPosition.x + 2 * GamePanel.SIZE, centerDrawPosition.y + 16 * GamePanel.SIZE,
                        16 * GamePanel.SIZE, -32 * GamePanel.SIZE, null);
            }
            g2.rotate(-angle, centerDrawPosition.x, centerDrawPosition.y);
        }
    }

    @Override
    public void hurt(int damage) {
        if (shield > 0) {
            shield -= damage;
            if (shield < 0) shield = 0;
        } else {
            super.hurt(damage);
        }
        shieldDelay = 300;
    }

    public void update(Point mousePosition) {
        
        move();
        updateHitBox();

        angle = Math.acos((mousePosition.x - level.panel.screenCenter.x) / level.panel.screenCenter.distance(mousePosition)) *
                Integer.signum(mousePosition.y - level.panel.screenCenter.y);
        if (angle == 0 && Integer.signum(mousePosition.x - level.panel.screenCenter.x) == -1) angle = Math.PI;

        if (shieldDelay > 0) --shieldDelay;

        if (fireDelay > 0) --fireDelay;

        if (shield < maxShield && shieldDelay == 0) ++shield;

        if (imageAlt) {
            image = imageMoving;
        } else {
            image = imageStanding;
        }
    }

    public void fire() {
        int projectileCount = instrument.projectileCount;
        if (projectileCount == 1) {
            level.projectiles.add(new ProjectileEntity(getCenterX() + (24 * Math.cos(angle) - 4) * GamePanel.SIZE, getCenterY() + (24 * Math.sin(angle) - 4) * GamePanel.SIZE,
                    angle, false, instrument.projectileDamage, instrument.projectileVelocity, instrument.projectileImage, level));
        } else {
            double baseAngle = Math.toDegrees(angle) - 5 * (projectileCount - 1);
            for (int i = 0; i < projectileCount; ++i) {
                double currentAngle = Math.toRadians(baseAngle + 10 * i);
                level.projectiles.add(new ProjectileEntity(getCenterX() + (24 * Math.cos(currentAngle) - 4) * GamePanel.SIZE, getCenterY() + (24 * Math.sin(currentAngle) - 4) * GamePanel.SIZE,
                        currentAngle, false, instrument.projectileDamage, instrument.projectileVelocity, instrument.projectileImage, level));
            }
        }
    }

    public void pickInstrument(InstrumentEntity instrumentEntity) {
        fireCharge = 0;
        if (instrument != null) level.instruments.set(level.instruments.indexOf(instrumentEntity),
                new InstrumentEntity(instrumentEntity.x, instrumentEntity.y, instrument, level));
        else level.instruments.remove(instrumentEntity);
        instrument = instrumentEntity.instrument;
    }

    public void throwInstrument() {
        fireCharge = 0;
        level.instruments.add(new InstrumentEntity(getCenterX() + (32 * Math.cos(angle) - 8) * GamePanel.SIZE,
                getCenterY() + (32 * Math.sin(angle) - 16) * GamePanel.SIZE, instrument, level));
        instrument = null;
    }

    public int getShield() {
        return shield;
    }

    private void move() {

        int dx = level.panel.keyHandler.dx;
        int dy = level.panel.keyHandler.dy;
        boolean[] collision = getCollision();

        if (dx != 0 || dy != 0) {
            if (dx < 0 && collision[0]) dx = 0;
            if (dx > 0 && collision[1]) dx = 0;
            if (dy < 0 && collision[2]) dy = 0;
            if (dy > 0 && collision[3]) dy = 0;
        }

        if (dx == 0 || dy == 0) {
            x += dx * velocity;
            y += dy * velocity;
        } else {
            x += dx * velocity / 1.414d;
            y += dy * velocity / 1.414d;
        }

        if (dx != 0 || dy != 0) {
            if (counter == 15) {
                imageAlt = !imageAlt;
                counter = 0;
                if (!imageAlt) playStepSound();
            }
            ++counter;
        } else {
            counter = 15;
            imageAlt = false;
        }
    }

    private boolean[] getCollision() {

        boolean[] collision = new boolean[4];

        boolean collisionTopLeft = level.map.collisionMap
                [hitBox.y / TileManager.TILE_SIZE][hitBox.x / TileManager.TILE_SIZE];
        boolean collisionTopRight = level.map.collisionMap
                [hitBox.y / TileManager.TILE_SIZE][(hitBox.x + hitBox.width) / TileManager.TILE_SIZE];
        boolean collisionBottomLeft = level.map.collisionMap
                [(hitBox.y + hitBox.height) / TileManager.TILE_SIZE][hitBox.x / TileManager.TILE_SIZE];
        boolean collisionBottomRight = level.map.collisionMap
                [(hitBox.y + hitBox.height) / TileManager.TILE_SIZE][(hitBox.x + hitBox.width) / TileManager.TILE_SIZE];

        boolean collisionLeft = level.map.collisionMap
                [getCenterY() / TileManager.TILE_SIZE][hitBox.x / TileManager.TILE_SIZE];
        boolean collisionRight = level.map.collisionMap
                [getCenterY() / TileManager.TILE_SIZE][(hitBox.x + hitBox.width) / TileManager.TILE_SIZE];
        boolean collisionTop = level.map.collisionMap
                [hitBox.y / TileManager.TILE_SIZE][getCenterX() / TileManager.TILE_SIZE];
        boolean collisionBottom = level.map.collisionMap
                [(hitBox.y + hitBox.height) / TileManager.TILE_SIZE][getCenterX() / TileManager.TILE_SIZE];

        if (collisionTopLeft || collisionTopRight || collisionBottomLeft || collisionBottomRight) {

            if (collisionTopLeft && collisionBottomLeft || collisionLeft) collision[0] = true;
            if (collisionTopRight && collisionBottomRight || collisionRight) collision[1] = true;
            if (collisionTopLeft && collisionTopRight || collisionTop) collision[2] = true;
            if (collisionBottomLeft && collisionBottomRight || collisionBottom) collision[3] = true;

            if (collisionTopLeft && !collision[0] && !collision[2]) {
                if (hitBox.x % TileManager.TILE_SIZE > hitBox.y % TileManager.TILE_SIZE) collision[0] = true;
                else collision[2] = true;
            }
            if (collisionTopRight && !collision[1] && !collision[2]) {
                if (TileManager.TILE_SIZE - (hitBox.x + hitBox.width) % TileManager.TILE_SIZE > hitBox.y % TileManager.TILE_SIZE) collision[1] = true;
                else collision[2] = true;
            }
            if (collisionBottomLeft && !collision[0] && !collision[3]) {
                if ((hitBox.y + hitBox.height) % TileManager.TILE_SIZE > TileManager.TILE_SIZE - hitBox.x % TileManager.TILE_SIZE) collision[0] = true;
                else collision[3] = true;
            }
            if (collisionBottomRight && !collision[1] && !collision[3]) {
                if ((hitBox.y + hitBox.height) % TileManager.TILE_SIZE > (hitBox.x + hitBox.width) % TileManager.TILE_SIZE) collision[1] = true;
                else collision[3] = true;
            }
        }

        return collision;
    }

    private void playStepSound() {
        switch (new Random().nextInt(4)) {
            case 0 -> new Sound(new File("res/sound/step_1.wav")).play();
            case 1 -> new Sound(new File("res/sound/step_2.wav")).play();
            case 2 -> new Sound(new File("res/sound/step_3.wav")).play();
            case 3 -> new Sound(new File("res/sound/step_4.wav")).play();
        }
    }
}
