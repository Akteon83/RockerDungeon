package main.java.level;

import main.java.GamePanel;
import main.java.Sound;
import main.java.entity.InstrumentEntity;
import main.java.entity.BotEntity;
import main.java.entity.PlayerEntity;
import main.java.entity.ProjectileEntity;
import main.java.instrument.Instrument;
import main.java.instrument.InstrumentTypes;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Level {

    public GamePanel panel;
    public LevelMap map;
    public List<PlayerEntity> players;
    public List<BotEntity> bots;
    public List<ProjectileEntity> projectiles;
    public List<InstrumentEntity> instruments;
    private int instrumentNameTimer;

    public Level(File file, GamePanel panel) {
        this.panel = panel;
        map = new LevelMap(file);
        players = new ArrayList<>();
        projectiles = new ArrayList<>();
        bots = new ArrayList<>();
        instruments = new ArrayList<>();

        players.add(new PlayerEntity(512, 1024, this));
        bots.add(new BotEntity(1024, 1024, 50, this));
        instruments.add(new InstrumentEntity(256, 256, InstrumentTypes.HETFIELD_GUITAR, this));
        instruments.add(new InstrumentEntity(256, 384, InstrumentTypes.ARROW_GUITAR, this));
        instruments.add(new InstrumentEntity(384, 256, InstrumentTypes.ELECTRIC_GUITAR, this));
        instruments.add(new InstrumentEntity(384, 384, InstrumentTypes.DRUM, this));
        instruments.add(new InstrumentEntity(512, 512, InstrumentTypes.BLACK_ARROW_GUITAR, this));
        instruments.add(new InstrumentEntity(512, 384, InstrumentTypes.ACOUSTIC_GUITAR, this));
    }

    public void update() {
        players.getFirst().update(panel.mousePosition);
        updateBots();
        updateProjectiles();
        updateInstrument();
        updatePicking();
    }

    public void draw(Graphics2D g2) {

        PlayerEntity player = players.getFirst();
        map.draw(g2, players.getFirst());
        Point cursorPosition = new Point(panel.mousePosition.x + player.getCenterX() - panel.screenCenter.x,
                panel.mousePosition.y + player.getCenterY() - panel.screenCenter.y);

        InstrumentEntity viewedInstrument = null;
        for (InstrumentEntity instrument : instruments) {
            instrument.draw(g2, player, panel);
            if (player.getCenterPosition().distance(cursorPosition) < 64 * GamePanel.SIZE
                    && instrument.getHitBox().contains(cursorPosition)
                    && player.getCenterPosition().distance(instrument.getCenterPosition()) < 48 * GamePanel.SIZE) {
                viewedInstrument = instrument;
            }
        }

        for (BotEntity bot : bots) {
            bot.draw(g2, player, panel);
        }

        player.draw(g2, player, panel);

        for (ProjectileEntity projectile : projectiles) {
            projectile.draw(g2, player, panel);
        }

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
            g2.drawImage(image, panel.screenCenter.x - 8 * GamePanel.SIZE, panel.screenCenter.y - 24 * GamePanel.SIZE, 16 * GamePanel.SIZE, 8 * GamePanel.SIZE, null);
        }

        g2.drawImage(new ImageIcon("res/ui/health_bar.png").getImage(), 11 * GamePanel.SIZE, 11 * GamePanel.SIZE, (player.getHealth() + 1) / 2 * GamePanel.SIZE, 5 * GamePanel.SIZE, null);
        g2.drawImage(new ImageIcon("res/ui/shield_bar.png").getImage(), 11 * GamePanel.SIZE, 19 * GamePanel.SIZE, (player.getShield() + 1) / 2 * GamePanel.SIZE, 5 * GamePanel.SIZE, null);
        g2.drawImage(new ImageIcon("res/ui/health_frame.png").getImage(), 0, 0, 64 * GamePanel.SIZE, 32 * GamePanel.SIZE, null);

        if (viewedInstrument != null) {
            int k = 1;
            if (viewedInstrument.instrument.equals(InstrumentTypes.DRUM)) k = -3;
            g2.setColor(viewedInstrument.instrument.rarity.getColor());
            g2.drawString(viewedInstrument.instrument.name,
                    viewedInstrument.getCenterX() + panel.screenCenter.x - player.getCenterX() - (int) g2.getFontMetrics().getStringBounds(viewedInstrument.instrument.name, g2).getWidth() / 2,
                    viewedInstrument.getY() + panel.screenCenter.y - player.getCenterY() - (4 * GamePanel.SIZE) * k);
        }

        if (instrumentNameTimer > 0) {
            assert player.instrument != null;
            g2.setColor(player.instrument.rarity.getColor());
            g2.drawString(player.instrument.name,
                    panel.screenCenter.x - (int) g2.getFontMetrics().getStringBounds(player.instrument.name, g2).getWidth() / 2,
                    panel.screenCenter.y - 20 * GamePanel.SIZE);
        }
    }

    private void updateBots() {
        for (BotEntity bot : bots) {
            bot.update();
        }
    }

    private void updateProjectiles() {
        for (int i = 0; i < projectiles.size(); ++i) {
            ProjectileEntity projectile = projectiles.get(i);
            projectile.update();
            if (projectile.isHostile()) {
                for (PlayerEntity player : players) {
                    if (player.getHitBox().contains(projectile.getCenterPosition())) {
                        player.hurt(projectile.getDamage());
                        projectile.deactivate();
                        break;
                    }
                }
            } else {
                for (BotEntity bot : bots) {
                    if (bot.getHitBox().contains(projectile.getCenterPosition())) {
                        bot.hurt(projectile.getDamage());
                        projectile.deactivate();
                        break;
                    }
                }
            }
            if (!projectile.isActive()) projectiles.remove(i);
        }
    }

    private void updateInstrument() {
        PlayerEntity player = players.getFirst();
        if (player.instrument != null) {
            Instrument instrument = player.instrument;
            if (instrumentNameTimer > 0) --instrumentNameTimer;

            if (panel.mouseHandler.leftPressed) {
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
                            new Sound(new File("res/sound/drum_2.wav")).play();
                        } else {
                            new Sound(new File("res/sound/drum_1.wav")).play();
                        }
                    }
                }
                player.fireCharge = 0;
            }

            if (instrument.semiAuto && (instrument.fireDelay - player.fireDelay == 6 || instrument.fireDelay - player.fireDelay == 12)) {
                player.fire();
            }

            if (panel.keyHandler.qPressed) {
                player.throwInstrument();
                instrumentNameTimer = 0;
            }
        }
    }

    private void updatePicking() {
        PlayerEntity player = players.getFirst();
        if (panel.mouseHandler.rightClicked) {
            panel.mouseHandler.rightClicked = false;
            Point cursorPosition = new Point(player.getCenterX() - panel.screenSize.width / 2 + panel.mousePosition.x,
                    player.getCenterY() - panel.screenSize.height / 2 + panel.mousePosition.y);
            if (panel.screenCenter.distance(panel.mousePosition) < 64 * GamePanel.SIZE) {
                for (InstrumentEntity instrumentEntity : instruments) {
                    if (instrumentEntity.getHitBox().contains(cursorPosition) &&
                            player.getCenterPosition().distance(instrumentEntity.getCenterPosition()) < 48 * GamePanel.SIZE) {
                        player.pickInstrument(instrumentEntity);
                        instrumentNameTimer = 120;
                        new Sound(new File("res/sound/test_sound.wav")).play();
                        break;
                    }
                }
            }
        }
    }
}
