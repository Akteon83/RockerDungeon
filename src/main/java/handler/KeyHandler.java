package main.java.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean aPressed, wPressed, sPressed, dPressed, qPressed, escPressed, spacePressed;
    public int dx = 0, dy = 0;


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A -> aPressed = true;
            case KeyEvent.VK_W -> wPressed = true;
            case KeyEvent.VK_S -> sPressed = true;
            case KeyEvent.VK_D -> dPressed = true;
            case KeyEvent.VK_Q -> qPressed = true;
            case KeyEvent.VK_ESCAPE -> escPressed = true;
            case KeyEvent.VK_SPACE -> spacePressed = true;
        }
        keyUpdate();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A -> aPressed = false;
            case KeyEvent.VK_W -> wPressed = false;
            case KeyEvent.VK_S -> sPressed = false;
            case KeyEvent.VK_D -> dPressed = false;
            case KeyEvent.VK_Q -> qPressed = false;
            case KeyEvent.VK_ESCAPE -> escPressed = false;
            case KeyEvent.VK_SPACE -> spacePressed = false;
        }
        keyUpdate();
    }

    public void keyUpdate() {
        dx = 0;
        dy = 0;
        if(aPressed) dx -= 1;
        if(wPressed) dy -= 1;
        if(sPressed) dy += 1;
        if(dPressed) dx += 1;
    }
}
