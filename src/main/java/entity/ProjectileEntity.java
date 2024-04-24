package main.java.entity;

import main.java.GamePanel;

import javax.swing.*;
import java.awt.*;

public class ProjectileEntity extends Entity {

    public int damage;
    private final double cos;
    private final double sin;
    public boolean visible = true;

    public ProjectileEntity(double x, double y, double cos, double sin, int damage, int velocity, Image image, GamePanel panel) {
        width = 8 * 3;
        height = 8 * 3;
        this.x = x;
        this.y = y;
        this.cos = cos;
        this.sin = sin;
        this.damage = damage;
        this.velocity = velocity;
        this.image = image;
        this.panel = panel;
    }

    private void move() {
        x += (float) (velocity * cos);
        y += (float) (velocity * sin);
    }

    public void update() {
        move();
        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();
        if (x > panelWidth || y > panelHeight || x + 8 * GamePanel.SIZE < 0 || y + 8 * GamePanel.SIZE < 0)
            visible = false;
    }
}
