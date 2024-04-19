package main.java;

import main.java.entity.ProjectileEntity;

import javax.swing.*;

public class InstrumentTypes {
    public static final Instrument HETFIELD_GUITAR = new Instrument(30, 8, 30,
            0, true, false, false,
            (new ImageIcon("src/main/resources/hetfield_guitar_right.png")).getImage(),
            (new ImageIcon("src/main/resources/hetfield_guitar_left.png")).getImage());

    public static final Instrument ARROW_GUITAR = new Instrument(50, 20, 20,
            ProjectileEntity.BLUE, false, false, false,
            (new ImageIcon("src/main/resources/arrow_guitar_right.png")).getImage(),
            (new ImageIcon("src/main/resources/arrow_guitar_left.png")).getImage());

    public static final Instrument STRATOCASTER_GUITAR = new Instrument(20, 10, 30,
            ProjectileEntity.RED, false, true, false,
            (new ImageIcon("src/main/resources/stratocaster_guitar_right.png")).getImage(),
            (new ImageIcon("src/main/resources/stratocaster_guitar_left.png")).getImage());

    public static final Instrument DRUM = new Instrument(30, 10, 15,
            ProjectileEntity.ORANGE, false, true, true,
            (new ImageIcon("src/main/resources/drum_right.png")).getImage(),
            (new ImageIcon("src/main/resources/drum_left.png")).getImage());
}
