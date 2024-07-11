package main.java.tile;

import main.java.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class TileManager {

    public static final int TILE_SIZE = 16 * GamePanel.SIZE;
    public final Tile[] tileTypes;
    public final Tile[] dynamicTileTypes;

    public TileManager() {
        tileTypes = new Tile[16];
        dynamicTileTypes = new Tile[32];
        getTileTypes();
    }

    private void getTileTypes() {
        tileTypes[0] = new Tile(null, true);
        tileTypes[1] = new Tile(new ImageIcon("res/tile/bricks_small_tile.png").getImage(), false);
        tileTypes[2] = new Tile(new ImageIcon("res/tile/bricks_tile.png").getImage(), false);
        tileTypes[3] = new Tile(null, false);
        tileTypes[4] = new Tile(new ImageIcon("res/tile/wall_tile.png").getImage(), true);
        tileTypes[5] = new Tile(new ImageIcon("res/tile/wall_bottom_tile.png").getImage(), false);
        tileTypes[6] = new Tile(null, true);

        dynamicTileTypes[0] = new Tile(new ImageIcon("res/tile/bricks_tile.png").getImage(), false);
        dynamicTileTypes[1] = new Tile(new ImageIcon("res/tile/dynamic/bricks_dynamic_1_tile.png").getImage(), false);
        dynamicTileTypes[2] = new Tile(new ImageIcon("res/tile/dynamic/bricks_dynamic_2_tile.png").getImage(), false);
        dynamicTileTypes[3] = new Tile(new ImageIcon("res/tile/dynamic/bricks_dynamic_3_tile.png").getImage(), false);
        dynamicTileTypes[4] = new Tile(new ImageIcon("res/tile/dynamic/bricks_dynamic_4_tile.png").getImage(), false);
        dynamicTileTypes[5] = new Tile(new ImageIcon("res/tile/dynamic/bricks_dynamic_5_tile.png").getImage(), false);
        dynamicTileTypes[6] = new Tile(new ImageIcon("res/tile/dynamic/bricks_dynamic_6_tile.png").getImage(), false);
        dynamicTileTypes[7] = new Tile(new ImageIcon("res/tile/dynamic/bricks_dynamic_7_tile.png").getImage(), false);
        dynamicTileTypes[8] = new Tile(new ImageIcon("res/tile/dynamic/bricks_dynamic_8_tile.png").getImage(), false);
        dynamicTileTypes[9] = new Tile(new ImageIcon("res/tile/dynamic/bricks_dynamic_9_tile.png").getImage(), false);
        dynamicTileTypes[10] = new Tile(new ImageIcon("res/tile/dynamic/bricks_dynamic_10_tile.png").getImage(), false);
        dynamicTileTypes[11] = new Tile(new ImageIcon("res/tile/dynamic/bricks_dynamic_11_tile.png").getImage(), false);
        dynamicTileTypes[12] = new Tile(new ImageIcon("res/tile/dynamic/bricks_dynamic_12_tile.png").getImage(), false);
        dynamicTileTypes[13] = new Tile(new ImageIcon("res/tile/dynamic/bricks_dynamic_13_tile.png").getImage(), false);
        dynamicTileTypes[14] = new Tile(new ImageIcon("res/tile/dynamic/bricks_dynamic_14_tile.png").getImage(), false);
        dynamicTileTypes[15] = new Tile(new ImageIcon("res/tile/dynamic/bricks_dynamic_15_tile.png").getImage(), false);

        dynamicTileTypes[16] = new Tile(new ImageIcon("res/tile/dynamic/roof_tile.png").getImage(), true);
        dynamicTileTypes[17] = new Tile(new ImageIcon("res/tile/dynamic/roof_dynamic_1_tile.png").getImage(), true);
        dynamicTileTypes[18] = new Tile(new ImageIcon("res/tile/dynamic/roof_dynamic_2_tile.png").getImage(), true);
        dynamicTileTypes[19] = new Tile(new ImageIcon("res/tile/dynamic/roof_dynamic_3_tile.png").getImage(), true);
        dynamicTileTypes[20] = new Tile(new ImageIcon("res/tile/dynamic/roof_dynamic_4_tile.png").getImage(), true);
        dynamicTileTypes[21] = new Tile(new ImageIcon("res/tile/dynamic/roof_dynamic_5_tile.png").getImage(), true);
        dynamicTileTypes[22] = new Tile(new ImageIcon("res/tile/dynamic/roof_dynamic_6_tile.png").getImage(), true);
        dynamicTileTypes[23] = new Tile(new ImageIcon("res/tile/dynamic/roof_dynamic_7_tile.png").getImage(), true);
        dynamicTileTypes[24] = new Tile(new ImageIcon("res/tile/dynamic/roof_dynamic_8_tile.png").getImage(), true);
        dynamicTileTypes[25] = new Tile(new ImageIcon("res/tile/dynamic/roof_dynamic_9_tile.png").getImage(), true);
        dynamicTileTypes[26] = new Tile(new ImageIcon("res/tile/dynamic/roof_dynamic_10_tile.png").getImage(), true);
        dynamicTileTypes[27] = new Tile(new ImageIcon("res/tile/dynamic/roof_dynamic_11_tile.png").getImage(), true);
        dynamicTileTypes[28] = new Tile(new ImageIcon("res/tile/dynamic/roof_dynamic_12_tile.png").getImage(), true);
        dynamicTileTypes[29] = new Tile(new ImageIcon("res/tile/dynamic/roof_dynamic_13_tile.png").getImage(), true);
        dynamicTileTypes[30] = new Tile(new ImageIcon("res/tile/dynamic/roof_dynamic_14_tile.png").getImage(), true);
        dynamicTileTypes[31] = new Tile(new ImageIcon("res/tile/dynamic/roof_dynamic_15_tile.png").getImage(), true);
    }
}
