package main.java;

import java.awt.*;

public class Instrument {

    public int projectileDamage;
    public int projectileVelocity;
    public int fireDelay;
    public boolean multipleProjectiles;
    public boolean semiAuto;
    public boolean chargeable;
    public Image imageRight;
    public Image imageLeft;
    public int projectileColor;

    public Instrument(int projectileDamage, int projectileVelocity, int fireDelay, int projectileColor,
                      boolean multipleProjectiles, boolean semiAuto, boolean chargeable, Image imageRight, Image imageLeft) {
        this.projectileDamage = projectileDamage;
        this.projectileVelocity = projectileVelocity;
        this.fireDelay = fireDelay;
        this.projectileColor = projectileColor;
        this.multipleProjectiles = multipleProjectiles;
        this.semiAuto = semiAuto;
        this.chargeable = chargeable;
        this.imageRight = imageRight;
        this.imageLeft = imageLeft;
    }
}
