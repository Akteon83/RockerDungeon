package main.java.entity;

import main.java.GamePanel;

import javax.swing.*;

public class ProjectileEntity extends Entity {

    public static final int RED = 1;
    public static final int GREEN = 2;
    public static final int BLUE = 3;
    public static final int ORANGE = 4;

    public int damage;
    private final double cos;
    private final double sin;
    public boolean visible = true;

    public ProjectileEntity(double x, double y, double cos, double sin, int damage, int velocity, GamePanel panel) {
        image = (new ImageIcon("src/main/resources/note.png")).getImage();
        width = 8 * 3;
        height = 8 * 3;
        this.x = x;
        this.y = y;
        this.cos = cos;
        this.sin = sin;
        this.damage = damage;
        this.velocity = velocity;
        this.panel = panel;
    }

    public ProjectileEntity(double x, double y, double cos, double sin, int damage, int velocity, int color, GamePanel panel) {
        switch (color) {
            case 0 -> image = (new ImageIcon("src/main/resources/note.png")).getImage();
            case 1 -> image = (new ImageIcon("src/main/resources/note_red.png")).getImage();
            case 2 -> image = (new ImageIcon("src/main/resources/note_green.png")).getImage();
            case 3 -> image = (new ImageIcon("src/main/resources/note_blue.png")).getImage();
            case 4 -> image = (new ImageIcon("src/main/resources/note_orange.png")).getImage();
        }

        width = 8 * 3;
        height = 8 * 3;
        this.x = x;
        this.y = y;
        this.cos = cos;
        this.sin = sin;
        this.damage = damage;
        this.velocity = velocity;
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
        if(x > panelWidth || y > panelHeight || x + 8 * GamePanel.SIZE < 0 || y + 8 * GamePanel.SIZE < 0) visible = false;
    }
}
