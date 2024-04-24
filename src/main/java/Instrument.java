package main.java;

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
    public Image imageRight;
    public Image imageLeft;
    public Image projectileImage;

    public Instrument(String name, Rarity rarity, int projectileDamage, int projectileVelocity, int projectileCount,
                      int fireDelay, boolean semiAuto, boolean chargeable, Image imageRight, Image imageLeft, Image projectileImage) {
        this.name = name;
        this.rarity = rarity;
        this.projectileDamage = projectileDamage;
        this.projectileVelocity = projectileVelocity;
        this.fireDelay = fireDelay;
        this.projectileCount = projectileCount;
        this.semiAuto = semiAuto;
        this.chargeable = chargeable;
        this.imageRight = imageRight;
        this.imageLeft = imageLeft;
        this.projectileImage = projectileImage;
        if(projectileCount < 1 || projectileCount > 10) this.projectileCount = 1;
    }
}
