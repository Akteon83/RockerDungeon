package main.java.entity;

import main.java.GamePanel;
import main.java.instrument.Instrument;
import main.java.instrument.InstrumentTypes;
import main.java.level.Level;

import javax.swing.*;
import java.awt.*;

public class BotEntity extends LivingEntity {

    private boolean isAggressive;
    public int fireDelay;
    public Instrument instrument;
    public Image instrumentImage;

    public BotEntity(double x, double y, int maxHealth, Level level) {
        super(x, y, maxHealth, level);
        angle = 0;
        width = 32 * GamePanel.SIZE;
        height = 32 * GamePanel.SIZE;
        instrument = InstrumentTypes.ACOUSTIC_GUITAR;
        velocity = 3;
        image = new ImageIcon("res/test_player.png").getImage();
        instrumentImage = instrument.image;
        updateHitBox();
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

    public void update() {

        updateHitBox();

        if (fireDelay > 0) --fireDelay;

        Point target = level.players.getFirst().getCenterPosition();
        double distance = getCenterPosition().distance(target);
        if (!isAggressive && distance < 256) isAggressive = true;
        if (health == 0) isAggressive = false;
        if (isAggressive) {
            angle = Math.acos((target.x - getCenterX()) / distance) *
                    Integer.signum(target.y - getCenterY());
            if (distance > 128 * GamePanel.SIZE) move();
            if (fireDelay == 0) {
                fire();
                fireDelay = instrument.fireDelay;
            }
        }
    }

    public void fire() {
        int projectileCount = instrument.projectileCount;
        if (projectileCount == 1) {
            level.projectiles.add(new ProjectileEntity(getCenterX() + (24 * Math.cos(angle) - 4) * GamePanel.SIZE, getCenterY() + (24 * Math.sin(angle) - 4) * GamePanel.SIZE,
                    angle, true, instrument.projectileDamage, instrument.projectileVelocity, instrument.projectileImage, level));
        } else {
            double baseAngle = Math.toDegrees(angle) - 5 * (projectileCount - 1);
            for (int i = 0; i < projectileCount; ++i) {
                double currentAngle = Math.toRadians(baseAngle + 10 * i);
                level.projectiles.add(new ProjectileEntity(getCenterX() + (24 * Math.cos(currentAngle) - 4) * GamePanel.SIZE, getCenterY() + (24 * Math.sin(currentAngle) - 4) * GamePanel.SIZE,
                        currentAngle, true, instrument.projectileDamage, instrument.projectileVelocity, instrument.projectileImage, level));
            }
        }
    }

    private void move() {
        x += velocity * Math.cos(angle);
        y += velocity * Math.sin(angle);
    }
}
