package main.java;

import main.java.entity.InstrumentEntity;
import main.java.entity.BotEntity;
import main.java.entity.PlayerEntity;
import main.java.entity.ProjectileEntity;

import java.util.List;

public class Level {

    public List<PlayerEntity> players;
    public List<BotEntity> bots;
    public List<ProjectileEntity> projectiles;
    public List<InstrumentEntity> instruments;

    public void updateProjectiles() {
        for (int i = 0; i < projectiles.size(); ++i) {
            ProjectileEntity projectile = projectiles.get(i);
            projectile.update();
            if (projectile.isHostile) {
                for (PlayerEntity player : players) {
                    if (player.collisionModel.contains(projectile.getCenterPosition())) {
                        player.hurt(projectile.getDamage());
                        projectile.deactivate();
                        break;
                    }
                }
            } else {
                for (BotEntity bot : bots) {
                    if (bot.collisionModel.contains(projectile.getCenterPosition())) {
                        bot.hurt(projectile.getDamage());
                        projectile.deactivate();
                        break;
                    }
                }
            }
            if (!projectile.isActive) projectiles.remove(i);
        }
    }
}
