package main.java.entity;

import main.java.GamePanel;
import main.java.instrument.Instrument;
import main.java.level.Level;

import java.awt.*;

public class InstrumentEntity extends Entity implements Dimensional {

    public Instrument instrument;

    public InstrumentEntity(double x, double y, Instrument instrument, Level level) {
        super(x, y, level);
        width = 16 * GamePanel.SIZE;
        height = 32 * GamePanel.SIZE;
        velocity = 0;
        this.instrument = instrument;
        this.image = instrument.image;
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(getX(), getY(), width, height);
    }
}
