package main.java.instrument;

import main.java.Rarity;

import java.awt.*;

public class Instrument {
    public String name;
    public Rarity rarity;
    public int projectileDamage;
    public int projectileVelocity;
    public int projectileCount;
    public int fireDelay;
    public boolean semiAuto;
    public boolean chargeable;
    public Image image;
    public Image projectileImage;

    public Instrument(String name, Rarity rarity, int projectileDamage, int projectileVelocity, int projectileCount,
                      int fireDelay, boolean semiAuto, boolean chargeable, Image image, Image projectileImage) {
        this.name = name;
        this.rarity = rarity;
        this.projectileDamage = projectileDamage;
        this.projectileVelocity = projectileVelocity;
        this.fireDelay = fireDelay;
        this.projectileCount = projectileCount;
        this.semiAuto = semiAuto;
        this.chargeable = chargeable;
        this.image = image;
        this.projectileImage = projectileImage;
        if (projectileCount < 1 || projectileCount > 10) this.projectileCount = 1;
    }
}
