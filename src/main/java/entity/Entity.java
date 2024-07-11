package main.java.entity;

import main.java.GamePanel;
import main.java.level.Level;

import java.awt.*;

public abstract class Entity {

    public Level level;
    protected double x, y;
    protected int width, height;
    protected int velocity;
    protected Image image;

    public Entity(double x, double y, Level level) {
        this.x = x;
        this.y = y;
        this.level = level;
    }

    public void draw(Graphics2D g2, PlayerEntity player, GamePanel panel) {
        Point drawPosition = new Point(getX() + panel.screenCenter.x - player.getCenterX(),
                getY() + panel.screenCenter.y - player.getCenterY());
        if (new Rectangle(drawPosition, new Dimension(width, height)).intersects(panel.screenRectangle)) {
            g2.drawImage(image, drawPosition.x, drawPosition.y, width, height, null);
        }
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public Point getPosition() {
        return new Point(getX(), getY());
    }

    public int getCenterX() {
        return (int) x + width / 2;
    }

    public int getCenterY() {
        return (int) y + height / 2;
    }

    public Point getCenterPosition() {
        return new Point(getCenterX(), getCenterY());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage() {
        return image;
    }
}
