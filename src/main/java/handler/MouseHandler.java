package main.java.handler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {

    public boolean leftPressed, rightPressed, leftClicked, rightClicked;

    @Override
    public void mouseClicked(MouseEvent e) {
        int key = e.getButton();
        switch (key) {
            case MouseEvent.BUTTON1 -> leftClicked = true;
            case MouseEvent.BUTTON3 -> rightClicked = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int key = e.getButton();
        switch (key) {
            case MouseEvent.BUTTON1 -> leftPressed = true;
            case MouseEvent.BUTTON3 -> rightPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int key = e.getButton();
        switch (key) {
            case MouseEvent.BUTTON1 -> leftPressed = false;
            case MouseEvent.BUTTON3 -> rightPressed = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
