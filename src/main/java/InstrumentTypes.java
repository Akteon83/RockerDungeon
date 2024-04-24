package main.java;

import main.java.entity.ProjectileEntity;

import javax.swing.*;

public class InstrumentTypes {
    public static final Instrument HETFIELD_GUITAR = new Instrument("Hetfield Guitar", Rarity.LEGENDARY,
            30, 10, 5, 30, false, false,
            (new ImageIcon("res/instrument/hetfield_guitar_right.png")).getImage(),
            (new ImageIcon("res/instrument/hetfield_guitar_left.png")).getImage(),
            (new ImageIcon("res/projectile/note.png")).getImage());

    public static final Instrument ARROW_GUITAR = new Instrument("Arrow Guitar", Rarity.EPIC,
            50, 20, 1, 20, false, false,
            (new ImageIcon("res/instrument/arrow_guitar_right.png")).getImage(),
            (new ImageIcon("res/instrument/arrow_guitar_left.png")).getImage(),
            (new ImageIcon("res/projectile/note_blue.png")).getImage());

    public static final Instrument STRATOCASTER_GUITAR = new Instrument("Electric Guitar", Rarity.RARE,
            20, 10, 1, 30, true, false,
            (new ImageIcon("res/instrument/stratocaster_guitar_right.png")).getImage(),
            (new ImageIcon("res/instrument/stratocaster_guitar_left.png")).getImage(),
            (new ImageIcon("res/projectile/note_red.png")).getImage());

    public static final Instrument DRUM = new Instrument("Drum", Rarity.UNCOMMON,
            30, 10, 1, 15, true, true,
            (new ImageIcon("res/instrument/drum_right.png")).getImage(),
            (new ImageIcon("res/instrument/drum_left.png")).getImage(),
            (new ImageIcon("res/projectile/note_orange.png")).getImage());

    public static final Instrument BLACK_ARROW_GUITAR = new Instrument("Black Arrow Guitar", Rarity.LEGENDARY,
            75, 25, 1, 20, false, false,
            (new ImageIcon("res/instrument/black_arrow_guitar_right.png")).getImage(),
            (new ImageIcon("res/instrument/black_arrow_guitar_left.png")).getImage(),
            (new ImageIcon("res/projectile/note_yellow.png")).getImage());
}
