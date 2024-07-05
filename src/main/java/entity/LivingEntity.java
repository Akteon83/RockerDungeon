package main.java.entity;

import main.java.GamePanel;

import java.awt.*;

public abstract class LivingEntity extends Entity {

    protected int health;
    protected int maxHealth;
    public Rectangle collisionModel;

    public LivingEntity(double x, double y, int maxHealth, GamePanel panel) {
        super(x, y, panel);
        this.maxHealth = maxHealth;
        health = maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void hurt(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) health = maxHealth;
    }
}
