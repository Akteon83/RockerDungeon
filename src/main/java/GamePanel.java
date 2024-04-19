package main.java;

import main.java.entity.InstrumentEntity;
import main.java.entity.Player;
import main.java.entity.ProjectileEntity;
import main.java.handler.KeyHandler;
import main.java.handler.MouseHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    public final int FPS = 60;
    public static final int SIZE = 4;
    public final KeyHandler keyHandler = new KeyHandler();
    public final MouseHandler mouseHandler = new MouseHandler();
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Player player = new Player(screenSize.getWidth() / 2 - 16 * SIZE, screenSize.getHeight() / 2 - 16 * SIZE, this);
    private List<InstrumentEntity> instruments;
    private int fireDelay;
    private int fireCharge;
    private Thread gameThread;

    public GamePanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.DARK_GRAY);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        addMouseListener(mouseHandler);
        setFocusable(true);
        instruments = new ArrayList<>();
        instruments.add(new InstrumentEntity(256, 256, InstrumentTypes.HETFIELD_GUITAR));
        instruments.add(new InstrumentEntity(128, 128, InstrumentTypes.ARROW_GUITAR));
        instruments.add(new InstrumentEntity(256, 128, InstrumentTypes.STRATOCASTER_GUITAR));
        instruments.add(new InstrumentEntity(128, 256, InstrumentTypes.DRUM));
        startGameThread();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while(gameThread != null) {
            update();
            repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;
                if(remainingTime < 0) remainingTime = 0;
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {
        Point mousePosition = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mousePosition, this);
        player.update(mousePosition);

        for (int i = 0; i < player.projectiles.size(); ++i) {
            player.projectiles.get(i).update();
            if (!player.projectiles.get(i).visible) player.projectiles.remove(i);
        }

        if (fireDelay > 0) --fireDelay;

        if (player.instrument != null) {
            Instrument instrument = player.instrument;

            if (mouseHandler.leftPressed) {
                if (fireDelay == 0) {
                    if (instrument.chargeable) {
                        if (fireCharge < 60) ++fireCharge;
                    } else {
                        player.fire();
                        fireDelay = instrument.fireDelay;
                    }
                }
            } else {
                if (instrument.chargeable) {
                    if (fireCharge > 11) {
                        player.fire();
                        if (fireCharge == 60) {
                            fireDelay = instrument.fireDelay;
                        }
                    }
                }
                fireCharge = 0;
            }

            if (instrument.semiAuto && (instrument.fireDelay - fireDelay == 6 || instrument.fireDelay - fireDelay == 12)) {
                player.fire();
            }

            if (keyHandler.qPressed) {
                fireCharge = 0;
                instruments.add(new InstrumentEntity(player.getCenterX() + (32 * Math.cos(player.getAngle()) - 8) * SIZE,
                        player.getCenterY() + (32 * Math.sin(player.getAngle()) - 16) * SIZE, player.instrument));
                player.instrument = null;
            }
        }

        if (mouseHandler.rightClicked) {
            mouseHandler.rightClicked = false;
            fireCharge = 0;

            if (player.getCenterPosition().distance(mousePosition) < 64 * SIZE) {
                for (int i = 0; i < instruments.size(); ++i) {
                    InstrumentEntity instrumentEntity = instruments.get(i);
                    if (mousePosition.getX() > instrumentEntity.getX() && mousePosition.getX() < instrumentEntity.getX() + instrumentEntity.getWidth() &&
                            mousePosition.getY() > instrumentEntity.getY() && mousePosition.getY() < instrumentEntity.getY() + instrumentEntity.getHeight()) {
                        if(player.getCenterPosition().distance(instrumentEntity.getCenterPosition()) < 48 * SIZE) {
                            if (player.instrument != null) {
                                instruments.set(i, new InstrumentEntity(instrumentEntity.getX(), instrumentEntity.getY(), player.instrument));
                            } else {
                                instruments.remove(i);
                            }
                            player.instrument = instrumentEntity.instrument;
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

        for(InstrumentEntity instrument : instruments) {
            g2.drawImage(instrument.getImage(), instrument.getX(), instrument.getY(), instrument.getWidth(), instrument.getHeight(), this);
        }

        g2.drawImage(player.getImage(), player.getX(), player.getY(), player.getWidth(), player.getHeight(), this);

        if(fireCharge > 0) {
            Image image;
            switch (fireCharge / 12) {
                case 0 -> image = (new ImageIcon("src/main/resources/charge_indicator_0.png")).getImage();
                case 1 -> image = (new ImageIcon("src/main/resources/charge_indicator_1.png")).getImage();
                case 2 -> image = (new ImageIcon("src/main/resources/charge_indicator_2.png")).getImage();
                case 3 -> image = (new ImageIcon("src/main/resources/charge_indicator_3.png")).getImage();
                case 4 -> image = (new ImageIcon("src/main/resources/charge_indicator_4.png")).getImage();
                default -> image = (new ImageIcon("src/main/resources/charge_indicator_5.png")).getImage();
            }
            g2.drawImage(image, player.getX() + 8 * SIZE, player.getY() - 8 * SIZE, 16 * SIZE, 8 * SIZE, this);
        }

        if(player.instrument != null) {
            g2.rotate(player.getAngle(), player.getCenterX(), player.getCenterY());
            g2.drawImage(player.instrumentImage, player.getCenterX() + 2 * SIZE, player.getCenterY() - 16 * SIZE,
                    16 * SIZE, 32 * SIZE, this);
            g2.rotate(-player.getAngle(), player.getCenterX(), player.getCenterY());
        }

        for(ProjectileEntity projectile : player.projectiles) {
            g2.drawImage(projectile.getImage(), projectile.getX(), projectile.getY(), projectile.getWidth(), projectile.getHeight(), this);
        }

        g2.drawImage(new ImageIcon("src/main/resources/health_bar.png").getImage(), 11 * SIZE, 11 * SIZE, (player.getHealth() + 1) / 2 * SIZE, 5 * SIZE, this);
        g2.drawImage(new ImageIcon("src/main/resources/shield_bar.png").getImage(), 11 * SIZE, 19 * SIZE, (player.getShield() + 1) / 2 * SIZE, 5 * SIZE, this);
        g2.drawImage(new ImageIcon("src/main/resources/health_frame.png").getImage(), 0, 0, 64 * SIZE, 32 * SIZE, this);

        g2.dispose();
    }
}
