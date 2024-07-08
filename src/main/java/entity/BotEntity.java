package main.java.entity;

import main.java.GamePanel;
import main.java.instrument.Instrument;

import java.awt.*;

public class BotEntity extends LivingEntity {

    private boolean isAggressive;
    public int fireDelay;
    public Instrument instrument;
    public Image instrumentImage;

    public BotEntity(double x, double y, int maxHealth, GamePanel panel) {
        super(x, y, maxHealth, panel);
    }
}
