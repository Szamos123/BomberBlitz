package entities.active;

import entities.Position;
import logic.GameModel;
import view.GameView;

import java.awt.*;

public abstract class Monster extends Sprite {
    public Monster(GameView gameView){
        super(gameView);
        position = new Position();

    }
    public abstract void moveMonster();
    public void drawEntity(Graphics2D g2, GameModel gameModel) {
        g2.drawImage(img, position.getPosX(), position.getPosY(), gameModel.gameView);
    }
}
