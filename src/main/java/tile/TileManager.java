package main.java.tile;

import main.java.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class TileManager {

    GamePanel panel;
    Tile[] tileTypes;
    Tile[] dynamicTileTypes;
    int[][] map;

    public TileManager(GamePanel panel) {
        this.panel = panel;
        tileTypes = new Tile[16];
        dynamicTileTypes = new Tile[16];
        map = new int[64][64];
        loadMap();
        getTileTypes();
    }

    public void getTileTypes() {
        tileTypes[1] = new Tile(new ImageIcon("res/tile/bricks_small_tile.png").getImage(), false);
        tileTypes[2] = new Tile(new ImageIcon("res/tile/bricks_tile.png").getImage(), false);

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
    }

    public void loadMap() {
        try {
            File file = new File("res/map/map_a.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            for (int y = 0; y < 32; ++y) {
                String line = reader.readLine();
                String[] numbers = line.split(" ");
                for (int x = 0; x < 32; ++x) {
                    map[y][x] = Integer.parseInt(numbers[x]);
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void draw(Graphics2D g2) {
        for (int y = 0; y < 32; ++y) {
            for (int x = 0; x < 32; ++x) {
                if (map[y][x] == 9) {
                    int index = 0;
                    if (x != 0 && map[y][x - 1] == 9) index += 1;
                    if (x != 31 && map[y][x + 1] == 9) index += 2;
                    if (y != 0 && map[y - 1][x] == 9) index += 4;
                    if (y != 31 && map[y + 1][x] == 9) index += 8;
                    g2.drawImage(dynamicTileTypes[index].image(), x * 16 * GamePanel.SIZE, y * 16 * GamePanel.SIZE,
                            16 * GamePanel.SIZE, 16 * GamePanel.SIZE, null);
                } else {
                    g2.drawImage(tileTypes[map[y][x]].image(), x * 16 * GamePanel.SIZE, y * 16 * GamePanel.SIZE,
                            16 * GamePanel.SIZE, 16 * GamePanel.SIZE, null);
                }
            }
        }
    }
}
