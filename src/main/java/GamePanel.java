package main.java;

import main.java.entity.InstrumentEntity;
import main.java.entity.Player;
import main.java.entity.ProjectileEntity;
import main.java.handler.KeyHandler;
import main.java.handler.MouseHandler;
import main.java.tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable {

    public final int FPS = 60;
    public static final int SIZE = 4;
    private Thread gameThread;
    private Font font;
    private int frame;
    public final KeyHandler keyHandler = new KeyHandler();
    public final MouseHandler mouseHandler = new MouseHandler();
    public final Dimension screenSize;
    public final Rectangle screenRectangle;
    public final Point screenCenter;
    public TileManager tileManager;
    private Point mousePosition;
    public Player player;
    private List<InstrumentEntity> instrumentEntities;
    private int instrumentNameTimer = 0;

    public GamePanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        addMouseListener(mouseHandler);
        setFocusable(true);
        initFonts();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenRectangle = new Rectangle(screenSize);
        screenCenter = new Point(screenSize.width / 2, screenSize.height / 2);
        tileManager = new TileManager(this);
        initEntities();
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

    private void initEntities() {
        player = new Player(screenSize.getWidth() / 2 - 16 * SIZE, screenSize.getHeight() / 2 - 16 * SIZE, this);
        instrumentEntities = new ArrayList<>();
        instrumentEntities.add(new InstrumentEntity(256, 256, InstrumentTypes.HETFIELD_GUITAR));
        instrumentEntities.add(new InstrumentEntity(128, 128, InstrumentTypes.ARROW_GUITAR));
        instrumentEntities.add(new InstrumentEntity(256, 128, InstrumentTypes.STRATOCASTER_GUITAR));
        instrumentEntities.add(new InstrumentEntity(128, 256, InstrumentTypes.DRUM));
        instrumentEntities.add(new InstrumentEntity(512, 512, InstrumentTypes.BLACK_ARROW_GUITAR));
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
        frame = (frame + 1) % 60;

        mousePosition = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mousePosition, this);
        player.update(mousePosition);

        for (int i = 0; i < player.projectiles.size(); ++i) {
            player.projectiles.get(i).update();
            if (!player.projectiles.get(i).visible) player.projectiles.remove(i);
        }

        if (player.fireDelay > 0) --player.fireDelay;

        if (player.instrument != null) {
            Instrument instrument = player.instrument;

            if (mouseHandler.leftPressed) {
                if (player.fireDelay == 0) {
                    if (instrument.chargeable) {
                        if (player.fireCharge < 60) ++player.fireCharge;
                    } else {
                        player.fire();
                        player.fireDelay = instrument.fireDelay;
                    }
                }
            } else {
                if (instrument.chargeable) {
                    if (player.fireCharge > 11) {
                        player.fire();
                        if (player.fireCharge == 60) {
                            player.fireDelay = instrument.fireDelay;
                        }
                    }
                }
                player.fireCharge = 0;
            }

            if (instrument.semiAuto && (instrument.fireDelay - player.fireDelay == 6 || instrument.fireDelay - player.fireDelay == 12)) {
                player.fire();
            }

            if (keyHandler.qPressed) {
                player.fireCharge = 0;
                instrumentEntities.add(new InstrumentEntity(player.getCenterX() + (32 * Math.cos(player.getAngle()) - 8) * SIZE,
                        player.getCenterY() + (32 * Math.sin(player.getAngle()) - 16) * SIZE, player.instrument));
                player.instrument = null;
                instrumentNameTimer = 0;
            }
        }


        if (mouseHandler.rightClicked) {
            mouseHandler.rightClicked = false;
            player.fireCharge = 0;

            if (screenCenter.distance(mousePosition) < 64 * SIZE) {
                for (int i = 0; i < instrumentEntities.size(); ++i) {
                    InstrumentEntity instrumentEntity = instrumentEntities.get(i);
                    Point drawPosition = new Point(instrumentEntity.getX() + screenCenter.x - player.getCenterX(), instrumentEntity.getY() + screenCenter.y - player.getCenterY());
                    if (mousePosition.getX() > drawPosition.x && mousePosition.getX() < drawPosition.x + instrumentEntity.getWidth() &&
                            mousePosition.getY() > drawPosition.y && mousePosition.getY() < drawPosition.y + instrumentEntity.getHeight()) {
                        if (player.getCenterPosition().distance(instrumentEntity.getCenterPosition()) < 48 * SIZE) {
                            if (player.instrument != null) {
                                instrumentEntities.set(i, new InstrumentEntity(instrumentEntity.getX(), instrumentEntity.getY(), player.instrument));
                            } else {
                                instrumentEntities.remove(i);
                            }
                            player.instrument = instrumentEntity.instrument;
                            instrumentNameTimer = 120;
                            break;
                        }
                    }
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(font.deriveFont(Font.PLAIN, 4 * SIZE));

        tileManager.draw(g2);

        InstrumentEntity viewedInstrument = null;
        for (InstrumentEntity instrumentEntity : instrumentEntities) {
            Point drawPosition = new Point(instrumentEntity.getX() + screenCenter.x - player.getCenterX(), instrumentEntity.getY() + screenCenter.y - player.getCenterY());
            if (screenRectangle.intersects(new Rectangle(drawPosition, new Dimension(16 * SIZE, 32 * SIZE)))) {
                g2.drawImage(instrumentEntity.getImage(), drawPosition.x, drawPosition.y, instrumentEntity.getWidth(), instrumentEntity.getHeight(), this);
                if (screenCenter.distance(mousePosition) < 64 * SIZE) {
                    if (mousePosition.getX() > drawPosition.x && mousePosition.getX() < drawPosition.x + instrumentEntity.getWidth() &&
                            mousePosition.getY() > drawPosition.y && mousePosition.getY() < drawPosition.y + instrumentEntity.getHeight()) {
                        if (player.getCenterPosition().distance(instrumentEntity.getCenterPosition()) < 48 * SIZE) viewedInstrument = instrumentEntity;
                    }
                }
            }
        }

        g2.drawImage(player.getImage(), screenCenter.x - 16 * SIZE, screenCenter.y - 16 * SIZE, player.getWidth(), player.getHeight(), this);

        if (player.fireCharge > 0) {
            Image image;
            switch (player.fireCharge / 12) {
                case 0 -> image = (new ImageIcon("res/ui/charge_indicator_0.png")).getImage();
                case 1 -> image = (new ImageIcon("res/ui/charge_indicator_1.png")).getImage();
                case 2 -> image = (new ImageIcon("res/ui/charge_indicator_2.png")).getImage();
                case 3 -> image = (new ImageIcon("res/ui/charge_indicator_3.png")).getImage();
                case 4 -> image = (new ImageIcon("res/ui/charge_indicator_4.png")).getImage();
                default -> image = (new ImageIcon("res/ui/charge_indicator_5.png")).getImage();
            }
            g2.drawImage(image, screenCenter.x - 8 * SIZE, screenCenter.y - 24 * SIZE, 16 * SIZE, 8 * SIZE, this);
        }

        if (player.instrument != null) {
            g2.rotate(player.getAngle(), screenCenter.x, screenCenter.y);
            g2.drawImage(player.instrumentImage, screenCenter.x + 2 * SIZE, screenCenter.y - 16 * SIZE,
                    16 * SIZE, 32 * SIZE, this);
            g2.rotate(-player.getAngle(), screenCenter.x, screenCenter.y);
        }

        for (ProjectileEntity projectile : player.projectiles) {
            Point drawPosition = new Point(projectile.getX() + screenCenter.x - player.getCenterX(), projectile.getY() + screenCenter.y - player.getCenterY());
            if (screenRectangle.intersects(new Rectangle(drawPosition, new Dimension(projectile.getWidth(), projectile.getHeight())))) {
                g2.drawImage(projectile.getImage(), drawPosition.x, drawPosition.y, projectile.getWidth(), projectile.getHeight(), this);
            }
        }

        g2.drawImage(new ImageIcon("res/ui/health_bar.png").getImage(), 11 * SIZE, 11 * SIZE, (player.getHealth() + 1) / 2 * SIZE, 5 * SIZE, this);
        g2.drawImage(new ImageIcon("res/ui/shield_bar.png").getImage(), 11 * SIZE, 19 * SIZE, (player.getShield() + 1) / 2 * SIZE, 5 * SIZE, this);
        g2.drawImage(new ImageIcon("res/ui/health_frame.png").getImage(), 0, 0, 64 * SIZE, 32 * SIZE, this);

        if (viewedInstrument != null) {
            int k = 1;
            if (viewedInstrument.instrument.equals(InstrumentTypes.DRUM)) k = -3;
            g2.setColor(viewedInstrument.instrument.rarity.getColor());
            g2.drawString(viewedInstrument.instrument.name,
                    viewedInstrument.getCenterX() + screenCenter.x - player.getCenterX() - (int) g2.getFontMetrics().getStringBounds(viewedInstrument.instrument.name, g2).getWidth() / 2,
                    viewedInstrument.getY() + screenCenter.y - player.getCenterY() - (4 * SIZE) * k);
        }

        if (instrumentNameTimer > 0) {
            assert player.instrument != null;
            g2.setColor(player.instrument.rarity.getColor());
            g2.drawString(player.instrument.name,
                    screenCenter.x - (int) g2.getFontMetrics().getStringBounds(player.instrument.name, g2).getWidth() / 2, screenCenter.y - 20 * SIZE);
            --instrumentNameTimer;
        }

        g2.dispose();
    }
}
