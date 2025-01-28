package logic;

import entities.active.Direction;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public Direction direction1;
    public Direction direction2;

    public boolean up1Pressed, down1Pressed, left1Pressed, right1Pressed;
    public boolean up2Pressed, down2Pressed, left2Pressed, right2Pressed;
    public boolean setBombPressed, setBombPressed2;

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        //extended keycode az ekezetek miatt
        switch (keyCode) {
            case KeyEvent.VK_W:
                up1Pressed = true;
                direction1 = Direction.UP;
                break;
            case KeyEvent.VK_S:
                down1Pressed = true;
                direction1 = Direction.DOWN;
                break;
            case KeyEvent.VK_A:
                left1Pressed = true;
                direction1 = Direction.LEFT;
                break;
            case KeyEvent.VK_D:
                right1Pressed = true;
                direction1 = Direction.RIGHT;
                break;
            case KeyEvent.VK_UP:
                up2Pressed = true;
                direction2 = Direction.UP;
                break;
            case KeyEvent.VK_DOWN:
                down2Pressed = true;
                direction2 = Direction.DOWN;
                break;
            case KeyEvent.VK_LEFT:
                left2Pressed = true;
                direction2 = Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                right2Pressed = true;
                direction2 = Direction.RIGHT;
                break;
            case KeyEvent.VK_SPACE:
                setBombPressed = true;
                break;
            case KeyEvent.VK_ENTER:
                setBombPressed2 = true;
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_W:
                up1Pressed = false;
                break;
            case KeyEvent.VK_A:
                left1Pressed = false;
                break;
            case KeyEvent.VK_S:
                down1Pressed = false;
                break;
            case KeyEvent.VK_D:
                right1Pressed = false;
                break;
            case KeyEvent.VK_UP:
                up2Pressed = false;
                break;
            case KeyEvent.VK_DOWN:
                down2Pressed = false;
                break;
            case KeyEvent.VK_LEFT:
                left2Pressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                right2Pressed = false;
                break;
            case KeyEvent.VK_SPACE:
                setBombPressed = false;
                break;
            case KeyEvent.VK_ENTER:
                setBombPressed2 = false;
                break;
        }
    }

}
