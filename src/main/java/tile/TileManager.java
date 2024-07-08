package main.java.tile;

import main.java.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class TileManager {

    public static final int TILE_SIZE = 16 * GamePanel.SIZE;
    private final GamePanel panel;
    public final Tile[] tileTypes;
    public final Tile[] dynamicTileTypes;
    public int[][] map;
    public boolean[][] collisionMap;
    public Image[][] visualMap;

    public TileManager(File file, GamePanel panel) {
        this.panel = panel;
        tileTypes = new Tile[16];
        dynamicTileTypes = new Tile[32];
        map = new int[32][32];
        collisionMap = new boolean[32][32];
        visualMap = new Image[32][32];
        getTileTypes();
        loadMap(file);
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

    private void loadMap(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            for (int y = 0; y < 32; ++y) {
                String line = reader.readLine();
                String[] numbers = line.split(" ");
                for (int x = 0; x < 32; ++x) {
                    map[y][x] = Integer.parseInt(numbers[x]);
                    collisionMap[y][x] = tileTypes[map[y][x]].collision();
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loadVisualMap();
    }

    private void loadVisualMap() {
        for (int y = 0; y < 32; ++y) {
            for (int x = 0; x < 32; ++x) {
                if (map[y][x] == 3) {
                    int index = 0;
                    if (x != 0 && map[y][x - 1] == 3) index += 1;
                    if (x != 31 && map[y][x + 1] == 3) index += 2;
                    if (y != 0 && map[y - 1][x] == 3) index += 4;
                    if (y != 31 && map[y + 1][x] == 3) index += 8;
                    visualMap[y][x] = dynamicTileTypes[index].image();
                } else if (map[y][x] == 6) {
                    int index = 0;
                    if (x != 0 && map[y][x - 1] == 6) index += 1;
                    if (x != 31 && map[y][x + 1] == 6) index += 2;
                    if (y != 0 && map[y - 1][x] == 6) index += 4;
                    if (y != 31 && map[y + 1][x] == 6) index += 8;
                    visualMap[y][x] = dynamicTileTypes[index + 16].image();
                } else {
                    visualMap[y][x] = tileTypes[map[y][x]].image();
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        for (int y = 0; y < 32; ++y) {
            for (int x = 0; x < 32; ++x) {
                Point drawPosition = new Point(x * 16 * GamePanel.SIZE + panel.screenCenter.x - panel.player.getCenterX(),
                        y * 16 * GamePanel.SIZE + panel.screenCenter.y - panel.player.getCenterY());
                if (new Rectangle(panel.screenSize).intersects(new Rectangle(drawPosition, new Dimension(TILE_SIZE, TILE_SIZE)))) {
                    g2.drawImage(visualMap[y][x], drawPosition.x, drawPosition.y, TILE_SIZE, TILE_SIZE, null);
                }
            }
        }
    }
}
