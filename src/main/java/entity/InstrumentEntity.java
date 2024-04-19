package main.java.entity;

import main.java.GamePanel;
import main.java.Instrument;

public class InstrumentEntity extends Entity {

    public Instrument instrument;

    public InstrumentEntity(double x, double y, Instrument instrument) {
        width = 16 * GamePanel.SIZE;
        height = 32 * GamePanel.SIZE;
        velocity = 0;
        this.x = x;
        this.y = y;
        this.instrument = instrument;
        this.image = instrument.imageRight;
    }
}
