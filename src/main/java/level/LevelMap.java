package main.java.level;

import main.java.GamePanel;
import main.java.entity.PlayerEntity;
import main.java.tile.TileManager;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LevelMap {

    private TileManager tileManager;
    public int[][] map;
    public boolean[][] collisionMap;
    public Image[][] visualMap;
    public Point spawnPoint;

    public LevelMap(File file, GamePanel panel) {
        tileManager = new TileManager(file, panel);
        loadMap(file);
        loadCollisionMap();
        loadVisualMap();
    }

    public void draw(Graphics2D g2, PlayerEntity player) {
        for (int y = 0; y < 32; ++y) {
            for (int x = 0; x < 32; ++x) {

            }
        }
    }

    private void loadMap(File file) {
        map = new int[32][32];
        try {
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

    private void loadCollisionMap() {
        collisionMap = new boolean[32][32];
        for (int y = 0; y < 32; ++y) {
            for (int x = 0; x < 32; ++x) {
                collisionMap[y][x] = tileManager.tileTypes[map[y][x]].collision();
            }
        }
    }

    private void loadVisualMap() {
        visualMap = new Image[32][32];
        for (int y = 0; y < 32; ++y) {
            for (int x = 0; x < 32; ++x) {
                if (map[y][x] == 3) {
                    int index = 0;
                    if (x != 0 && map[y][x - 1] == 3) index += 1;
                    if (x != 31 && map[y][x + 1] == 3) index += 2;
                    if (y != 0 && map[y - 1][x] == 3) index += 4;
                    if (y != 31 && map[y + 1][x] == 3) index += 8;
                    visualMap[y][x] = tileManager.dynamicTileTypes[index].image();
                } else if (map[y][x] == 6) {
                    int index = 0;
                    if (x != 0 && map[y][x - 1] == 6) index += 1;
                    if (x != 31 && map[y][x + 1] == 6) index += 2;
                    if (y != 0 && map[y - 1][x] == 6) index += 4;
                    if (y != 31 && map[y + 1][x] == 6) index += 8;
                    visualMap[y][x] = tileManager.dynamicTileTypes[index + 16].image();
                } else {
                    visualMap[y][x] = tileManager.tileTypes[map[y][x]].image();
                }
            }
        }
    }
}
