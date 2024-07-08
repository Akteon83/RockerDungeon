package main.java.entity;

import main.java.GamePanel;

import java.awt.*;

public abstract class LivingEntity extends Entity implements Dimensional {

    protected int health;
    protected int maxHealth;
    protected double angle;
    public Rectangle hitBox;

    public LivingEntity(double x, double y, int maxHealth, GamePanel panel) {
        super(x, y, panel);
        this.maxHealth = maxHealth;
        health = maxHealth;
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    protected void updateHitBox() {
        hitBox = new Rectangle(getX() + width / 4, getY() + height / 4, width / 2, height / 4 * 3);
    }

    public void hurt(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) health = maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public double getAngle() {
        return angle;
    }
}
