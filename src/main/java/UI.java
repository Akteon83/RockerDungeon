package main.java;

import javax.swing.*;
import java.awt.*;

public class UI {

    private GamePanel panel;

    public UI(GamePanel panel) {
        this.panel = panel;
    }

    public void drawPauseScreen(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 192));
        g2.fillRect(0, 0, panel.screenSize.width, panel.screenSize.height);
        g2.setFont(panel.font.deriveFont(Font.PLAIN, 16 * GamePanel.SIZE));
        g2.setColor(Color.WHITE);

        g2.drawImage(new ImageIcon("res/ui/button.png").getImage(),
                panel.screenCenter.x - 32 * GamePanel.SIZE * 2,
                panel.screenCenter.y - 20 * GamePanel.SIZE * 2,
                64 * GamePanel.SIZE * 2, 16 * GamePanel.SIZE * 2, null);

        g2.drawImage(new ImageIcon("res/ui/button.png").getImage(),
                panel.screenCenter.x - 32 * GamePanel.SIZE * 2,
                panel.screenCenter.y + 4 * GamePanel.SIZE * 2,
                64 * GamePanel.SIZE * 2, 16 * GamePanel.SIZE * 2, null);

        g2.drawString("PAUSE",
                panel.screenCenter.x - (int) g2.getFontMetrics().getStringBounds("PAUSE", g2).getWidth() / 2,
                panel.screenCenter.y - 28 * GamePanel.SIZE * 2);

        g2.setColor(Color.GRAY);

        g2.drawString("OPTIONS",
                panel.screenCenter.x - (int) g2.getFontMetrics().getStringBounds("OPTIONS", g2).getWidth() / 2,
                panel.screenCenter.y - 9 * GamePanel.SIZE * 2);

        g2.drawString("EXIT",
                panel.screenCenter.x - (int) g2.getFontMetrics().getStringBounds("EXIT", g2).getWidth() / 2,
                panel.screenCenter.y + 15 * GamePanel.SIZE * 2);
    }
}
