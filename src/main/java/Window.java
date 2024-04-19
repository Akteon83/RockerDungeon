package main.java;

import javax.swing.*;

public class Window extends JFrame {

    public Window(int width, int height) {
        add(new GamePanel(width, height));
        setTitle("Rocker Dungeon");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }
}
