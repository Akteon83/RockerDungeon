package main.java.entity;

import main.java.GamePanel;
import main.java.Instrument;
import main.java.handler.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player extends LivingEntity {

    private final KeyHandler handler;
    private int shield;
    private int maxShield;
    private int shieldDelay;
    public int fireDelay;
    public int fireCharge;
    private double angle;
    private int counter;
    private boolean imageAlt;
    public List<ProjectileEntity> projectiles;
    public Instrument instrument;
    public Image instrumentImage;

    Image imageRight = (new ImageIcon("res/player_sprite_right.png")).getImage();
    Image imageRightMoving = (new ImageIcon("res/player_sprite_right_moving.png")).getImage();
    Image imageLeft = (new ImageIcon("res/player_sprite_left.png")).getImage();
    Image imageLeftMoving = (new ImageIcon("res/player_sprite_left_moving.png")).getImage();

    public Player(double x, double y, GamePanel panel) {
        projectiles = new ArrayList<>();
        width = 32 * GamePanel.SIZE;
        height = 32 * GamePanel.SIZE;
        maxHealth = 100;
        health = maxHealth;
        maxShield = 100;
        shield = maxShield;
        this.x = x;
        this.y = y;
        this.panel = panel;
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
            x += dx * velocity / 1.4f;
            y += dy * velocity / 1.4f;
        }
        if (dx != 0 || dy != 0) {
            if (counter == 15) {
                imageAlt = !imageAlt;
                counter = 0;
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
            projectiles.add(new ProjectileEntity(getCenterX() + (24 * Math.cos(angle) - 4) * GamePanel.SIZE, getCenterY() + (24 * Math.sin(angle) - 4) * GamePanel.SIZE,
                    Math.cos(angle), Math.sin(angle), instrument.projectileDamage, instrument.projectileVelocity, instrument.projectileImage, panel));
        } else {
            double baseAngle = Math.toDegrees(angle) - 5 * (projectileCount - 1);
            for (int i = 0; i < projectileCount; ++i) {
                double currentAngle = Math.toRadians(baseAngle + 10 * i);
                projectiles.add(new ProjectileEntity(getCenterX() + (24 * Math.cos(currentAngle) - 4) * GamePanel.SIZE, getCenterY() + (24 * Math.sin(currentAngle) - 4) * GamePanel.SIZE,
                        Math.cos(currentAngle), Math.sin(currentAngle), instrument.projectileDamage, instrument.projectileVelocity, instrument.projectileImage, panel));
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

    public void update(Point mousePosition) {
        move();

        int dx = mousePosition.x - getCenterX();
        int dy = mousePosition.y - getCenterY();
        double distance = getCenterPosition().distance(mousePosition);
        angle = Math.acos(dx / distance) * Integer.signum(dy);

        if (shieldDelay > 0) --shieldDelay;

        if (shield < maxShield && shieldDelay == 0) ++shield;

        if (mousePosition.x > getCenterX()) {
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
