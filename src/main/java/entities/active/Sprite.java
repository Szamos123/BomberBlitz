package entities.active;

import entities.Position;
import view.GameView;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Sprite {
    public Position position;
    public int speed;
    public int originalSpeed;
    public boolean collision = false;
    public Rectangle hitbox;
    public int hitboxDefaultX;
    public int hitboxDefaultY;
    public int currentGridX;
    public int currentGridY;
    public boolean onBomb = false;
    public Direction direction;
    GameView gameView;
    public BufferedImage img;

    public Sprite(GameView gameView){
        this.gameView = gameView;
    }

    public void die() {
        gameView.gameModel.sprites.remove(this);
    }
}
