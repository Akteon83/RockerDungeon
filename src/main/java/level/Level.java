package main.java.level;

import main.java.GamePanel;
import main.java.entity.InstrumentEntity;
import main.java.entity.BotEntity;
import main.java.entity.PlayerEntity;
import main.java.entity.ProjectileEntity;

import java.util.ArrayList;
import java.util.List;

public class Level {

    public GamePanel panel;
    public LevelMap map;
    public List<PlayerEntity> players;
    public List<BotEntity> bots;
    public List<ProjectileEntity> projectiles;
    public List<InstrumentEntity> instruments;

    public Level(GamePanel panel) {
        this.panel = panel;
        players = new ArrayList<>();
        projectiles = new ArrayList<>();
        bots = new ArrayList<>();
        instruments = new ArrayList<>();
    }

    public void update() {
        updateProjectiles();
    }

    private void updateProjectiles() {
        for (int i = 0; i < projectiles.size(); ++i) {
            ProjectileEntity projectile = projectiles.get(i);
            projectile.update();
            if (projectile.isHostile()) {
                for (PlayerEntity player : players) {
                    if (player.hitBox.contains(projectile.getCenterPosition())) {
                        player.hurt(projectile.getDamage());
                        projectile.deactivate();
                        break;
                    }
                }
            } else {
                for (BotEntity bot : bots) {
                    if (bot.hitBox.contains(projectile.getCenterPosition())) {
                        bot.hurt(projectile.getDamage());
                        projectile.deactivate();
                        break;
                    }
                }
            }
            if (!projectile.isActive()) projectiles.remove(i);
        }
    }
}
