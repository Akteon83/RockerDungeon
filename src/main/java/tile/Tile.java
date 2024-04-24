package main.java.tile;

import java.awt.*;

public record Tile(Image image, boolean collision) {

    @Override
    public Image image() {
        return image;
    }

    @Override
    public boolean collision() {
        return collision;
    }
}
