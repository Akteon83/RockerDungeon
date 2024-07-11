package main.java;

import main.java.handler.KeyHandler;
import main.java.handler.MouseHandler;
import main.java.level.Level;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable {

    public final int FPS = 60;
    public static final int SIZE = 4;
    public int gameState = 0;

    private Thread gameThread;
    public Font font;
    private int frame;
    private UI ui;
    public final KeyHandler keyHandler = new KeyHandler();
    public final MouseHandler mouseHandler = new MouseHandler();

    public final Dimension screenSize;
    public final Rectangle screenRectangle;
    public final Point screenCenter;
    public Point mousePosition;
    private final Level level;

    public GamePanel(int width, int height) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        addMouseListener(mouseHandler);
        setFocusable(true);
        initFonts();
        screenSize = toolkit.getScreenSize();
        screenRectangle = new Rectangle(screenSize);
        screenCenter = new Point(screenSize.width / 2, screenSize.height / 2);
        level = new Level(new File("res/map/map_b.txt"), this);
        ui = new UI(this);
        setCursor(toolkit.createCustomCursor(new ImageIcon("res/cursor_1.png").getImage(),
                new Point(4 * SIZE, 4 * SIZE), "crs"));
        startGameThread();
        frame = 0;
    }

    private void initFonts() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/font/pixeloid_sans.ttf")));
            //font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/font/pixeloid_sans_bold.ttf")));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000D / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        mousePosition = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mousePosition, this);

        if (keyHandler.escPressed) {
            keyHandler.escPressed = false;
            gameState = 1 - gameState;
        }

        if(gameState == 0) {
            frame = (frame + 1) % 60;
            level.update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(font.deriveFont(Font.PLAIN, 4 * SIZE));

        level.draw(g2);

        if (gameState == 1) {
            ui.drawPauseScreen(g2);
        }

        g2.dispose();
    }
}
