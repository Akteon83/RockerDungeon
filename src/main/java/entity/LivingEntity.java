package main.java.entity;

public class LivingEntity extends Entity {

    protected int health;
    protected int maxHealth;

    public int getHealth() {
        return health;
    }

    public void hurt(int damage) {
        health -= damage;
        if(health < 0) health = 0;
    }

    public void heal(int amount) {
        health += amount;
        if(health > maxHealth) health = maxHealth;
    }
}
