package main.java.entity;

import main.java.GamePanel;
import main.java.level.Level;

import java.awt.*;

public abstract class LivingEntity extends Entity implements Dimensional {

    protected int health;
    protected int maxHealth;
    protected double angle;
    protected Rectangle hitBox;

    public LivingEntity(double x, double y, int maxHealth, Level level) {
        super(x, y, level);
        this.maxHealth = maxHealth;
        health = maxHealth;
    }

    @Override
    public void draw(Graphics2D g2, PlayerEntity player, GamePanel panel) {
        Point drawPosition = new Point(getX() + panel.screenCenter.x - player.getCenterX(),
                getY() + panel.screenCenter.y - player.getCenterY());
        if (new Rectangle(drawPosition, new Dimension(width, height)).intersects(panel.screenRectangle)) {
            if (Math.abs(angle) < Math.PI / 2) {
                g2.drawImage(image, drawPosition.x, drawPosition.y, width, height, null);
            } else {
                g2.drawImage(image, drawPosition.x + width, drawPosition.y, -width, height, null);
            }
        }
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
