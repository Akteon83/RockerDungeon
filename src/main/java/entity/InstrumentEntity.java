package main.java.entity;

import main.java.GamePanel;
import main.java.Instrument;

public class InstrumentEntity extends Entity {

    public Instrument instrument;

    public InstrumentEntity(double x, double y, Instrument instrument, GamePanel panel) {
        super(x, y, panel);
        width = 16 * GamePanel.SIZE;
        height = 32 * GamePanel.SIZE;
        velocity = 0;
        this.instrument = instrument;
        this.image = instrument.imageRight;
    }
}
