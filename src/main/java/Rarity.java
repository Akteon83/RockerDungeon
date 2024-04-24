package main.java;

import java.awt.*;

public enum Rarity {

    COMMON("Common", new Color(255, 255, 255)),
    UNCOMMON("Uncommon", new Color(0, 255, 0)),
    RARE("Rare", new Color(64, 64, 255)),
    EPIC("Epic", new Color(192, 0, 192)),
    LEGENDARY("Legendary", new Color(255, 255, 0));

    private final String title;
    private final Color color;

    Rarity(String title, Color color) {
        this.title = title;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public Color getColor() {
        return color;
    }
}
