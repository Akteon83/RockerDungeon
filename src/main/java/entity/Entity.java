package main.java.entity;

import main.java.GamePanel;

import java.awt.*;

public abstract class Entity {

    protected GamePanel panel = null;
    protected double x, y;
    protected int width, height;
    protected int velocity;
    protected Image image;

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
