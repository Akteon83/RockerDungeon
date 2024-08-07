package main.java.entity;

import main.java.level.Level;
import main.java.tile.TileManager;

import java.awt.*;

public class ProjectileEntity extends Entity {

    private final int damage;
    private final double cos;
    private final double sin;
    private final boolean isHostile;
    private boolean isActive;

    public ProjectileEntity(double x, double y, double angle, boolean isHostile, int damage, int velocity, Image image, Level level) {
        super(x, y, level);
        width = 8 * 3;
        height = 8 * 3;
        isActive = true;
        this.isHostile = isHostile;
        this.cos = Math.cos(angle);
        this.sin = Math.sin(angle);
        this.damage = damage;
        this.velocity = velocity;
        this.image = image;
    }

    public void update() {
        move();
        if (level.map.collisionMap[getCenterY() / TileManager.TILE_SIZE][getCenterX() / TileManager.TILE_SIZE]) {
            deactivate();
        }
    }

    public void deactivate() {
        isActive = false;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isHostile() {
        return isHostile;
    }

    public boolean isActive() {
        return isActive;
    }

    private void move() {
        x += velocity * cos;
        y += velocity * sin;
    }
}
