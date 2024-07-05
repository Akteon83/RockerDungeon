package main.java.entity;

import main.java.GamePanel;
import main.java.Instrument;
import main.java.InstrumentTypes;
import main.java.Sound;
import main.java.handler.KeyHandler;
import main.java.tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerEntity extends LivingEntity {

    private final KeyHandler handler;
    private int shield;
    private final int maxShield;
    private int shieldDelay;
    public int fireDelay;
    public int fireCharge;
    private double angle;
    private int counter;
    private boolean imageAlt;
    public Instrument instrument;
    public Image instrumentImage;

    Image imageRight = (new ImageIcon("res/player_sprite_right.png")).getImage();
    Image imageRightMoving = (new ImageIcon("res/player_sprite_right_moving.png")).getImage();
    Image imageLeft = (new ImageIcon("res/player_sprite_left.png")).getImage();
    Image imageLeftMoving = (new ImageIcon("res/player_sprite_left_moving.png")).getImage();

    public PlayerEntity(double x, double y, GamePanel panel) {
        super(x, y, 100, panel);
        width = 32 * GamePanel.SIZE;
        height = 32 * GamePanel.SIZE;
        maxShield = 100;
        shield = maxShield;
        this.handler = panel.keyHandler;
        velocity = 3;
    }

    public int getShield() {
        return shield;
    }

    public double getAngle() {
        return angle;
    }

    public void move() {
        int dx = handler.dx;
        int dy = handler.dy;

        if (dx == 0 || dy == 0) {
            x += dx * velocity;
            y += dy * velocity;
        } else {
            x += dx * velocity / 1.414d;
            y += dy * velocity / 1.414d;
        }

        collisionModel = new Rectangle(getX() + 8 * GamePanel.SIZE, getY() + 24 * GamePanel.SIZE, getWidth() / 2, getHeight() / 4);
        boolean collisionTopLeft = panel.tileManager.collisionMap[collisionModel.y / TileManager.TILE_SIZE][collisionModel.x / TileManager.TILE_SIZE];
        boolean collisionTopRight = panel.tileManager.collisionMap[collisionModel.y / TileManager.TILE_SIZE][(collisionModel.x + collisionModel.width) / TileManager.TILE_SIZE];
        boolean collisionBottomLeft = panel.tileManager.collisionMap[(collisionModel.y + collisionModel.height) / TileManager.TILE_SIZE ][collisionModel.x / TileManager.TILE_SIZE];
        boolean collisionBottomRight = panel.tileManager.collisionMap[(collisionModel.y + collisionModel.height) / TileManager.TILE_SIZE][(collisionModel.x + collisionModel.width) / TileManager.TILE_SIZE];

        if (collisionTopLeft || collisionTopRight || collisionBottomLeft || collisionBottomRight) {
            if (collisionTopLeft && collisionTopRight) {
                y += TileManager.TILE_SIZE - collisionModel.y % TileManager.TILE_SIZE;
            }
            if (collisionBottomLeft && collisionBottomRight) {
                y -= (collisionModel.y + collisionModel.height) % TileManager.TILE_SIZE;
            }
            if (collisionTopLeft && collisionBottomLeft) {
                x += TileManager.TILE_SIZE - collisionModel.x % TileManager.TILE_SIZE;
            }
            if (collisionTopRight && collisionBottomRight) {
                x -= (collisionModel.x + collisionModel.width) % TileManager.TILE_SIZE;
            }

            if (collisionTopLeft) {
                if (collisionModel.y % TileManager.TILE_SIZE > collisionModel.x % TileManager.TILE_SIZE) {
                    y += TileManager.TILE_SIZE - collisionModel.y % TileManager.TILE_SIZE;
                } else {
                    x += TileManager.TILE_SIZE - collisionModel.x % TileManager.TILE_SIZE;
                }
            }
            if (collisionTopRight) {
                if (collisionModel.y % TileManager.TILE_SIZE > TileManager.TILE_SIZE - (collisionModel.x + collisionModel.width) % TileManager.TILE_SIZE) {
                    y += TileManager.TILE_SIZE - collisionModel.y % TileManager.TILE_SIZE;
                } else {
                    x -= (collisionModel.x + collisionModel.width) % TileManager.TILE_SIZE;
                }
            }
            if (collisionBottomLeft) {
                if ((collisionModel.y + collisionModel.height) % TileManager.TILE_SIZE < TileManager.TILE_SIZE - collisionModel.x % TileManager.TILE_SIZE) {
                    y -= (collisionModel.y + collisionModel.height) % TileManager.TILE_SIZE;
                } else {
                    x += TileManager.TILE_SIZE - collisionModel.x % TileManager.TILE_SIZE;
                }
            }
            if (collisionBottomRight) {
                if ((collisionModel.y + collisionModel.height) % TileManager.TILE_SIZE < (collisionModel.x + collisionModel.width) % TileManager.TILE_SIZE) {
                    y -= (collisionModel.y + collisionModel.height) % TileManager.TILE_SIZE;
                } else {
                    x -= (collisionModel.x + collisionModel.width) % TileManager.TILE_SIZE;
                }
            }
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

    public void fire() {
        int projectileCount = instrument.projectileCount;
        if (projectileCount == 1) {
            panel.projectiles.add(new ProjectileEntity(getCenterX() + (24 * Math.cos(angle) - 4) * GamePanel.SIZE, getCenterY() + (24 * Math.sin(angle) - 4) * GamePanel.SIZE,
                    angle, false, instrument.projectileDamage, instrument.projectileVelocity, instrument.projectileImage, panel));
        } else {
            double baseAngle = Math.toDegrees(angle) - 5 * (projectileCount - 1);
            for (int i = 0; i < projectileCount; ++i) {
                double currentAngle = Math.toRadians(baseAngle + 10 * i);
                panel.projectiles.add(new ProjectileEntity(getCenterX() + (24 * Math.cos(currentAngle) - 4) * GamePanel.SIZE, getCenterY() + (24 * Math.sin(currentAngle) - 4) * GamePanel.SIZE,
                        currentAngle, false, instrument.projectileDamage, instrument.projectileVelocity, instrument.projectileImage, panel));
            }
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

    private void playStepSound() {
        switch (new Random().nextInt(4)) {
            case 0 -> new Sound(new File("res/sound/step_1.wav")).play();
            case 1 -> new Sound(new File("res/sound/step_2.wav")).play();
            case 2 -> new Sound(new File("res/sound/step_3.wav")).play();
            case 3 -> new Sound(new File("res/sound/step_4.wav")).play();
        }
    }

    public void update(Point mousePosition) {
        move();

        int dx = mousePosition.x - panel.screenCenter.x;
        int dy = mousePosition.y - panel.screenCenter.y;
        double distance = panel.screenCenter.distance(mousePosition);
        angle = Math.acos(dx / distance) * Integer.signum(dy);

        if (shieldDelay > 0) --shieldDelay;

        if (shield < maxShield && shieldDelay == 0) ++shield;

        if (mousePosition.x > panel.screenCenter.x) {
            if (imageAlt) {
                image = imageRightMoving;
            } else {
                image = imageRight;
            }
            if (instrument != null) instrumentImage = instrument.imageRight;
        } else {
            if (imageAlt) {
                image = imageLeftMoving;
            } else {
                image = imageLeft;
            }
            if (instrument != null) instrumentImage = instrument.imageLeft;
        }
    }
}
