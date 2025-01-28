package logic;

import entities.active.BaseMonster;
import entities.active.GhostMonster;
import entities.active.Monster;
import entities.active.Player;
import entities.active.Sprite;
import entities.passive.Bomb;
import entities.passive.Entity;
import view.GameView;

import java.util.ArrayList;


public class GameModel{
    public GameView gameView;
    public Player testPlayer;
    public Player testPlayer2;
    public Monster baseMonster;
    public Monster baseMonster2;
    public Monster ghostMonster;
    public KeyHandler keyHandler = new KeyHandler();
    public KeyHandler keyHandler2 = new KeyHandler();

    public ArrayList<Sprite> sprites = new ArrayList<Sprite>();

    public ArrayList<Entity> entities = new ArrayList<Entity>();
    public ArrayList<Monster> monsters = new ArrayList<Monster>();

    public GameModel(GameView gameView) {
        this.gameView = gameView;

        testPlayer = new Player(gameView);
        testPlayer2 = new Player(gameView);

        sprites.add(testPlayer);
        sprites.add(testPlayer2);

        baseMonster = new BaseMonster(gameView);
        baseMonster2 = new BaseMonster(gameView);
        ghostMonster = new GhostMonster(gameView);


        testPlayer.position.setPosX(gameView.tileSize);
        testPlayer.position.setPosY(gameView.tileSize);

        testPlayer2.position.setPosX(gameView.maxScreenRow * gameView.tileSize - gameView.tileSize * 2);
        testPlayer2.position.setPosY(gameView.maxScreenCol * gameView.tileSize - gameView.tileSize * 2);

        entities = gameView.tileManager.loadBoxes();
        monsters.add(baseMonster);
        monsters.add(baseMonster2);
        monsters.add(ghostMonster);
    }

    public void update() {
        for (Monster monster : monsters) {
            monster.moveMonster();
        }

        if (keyHandler.up1Pressed || keyHandler.down1Pressed ||keyHandler.left1Pressed ||keyHandler.right1Pressed) {
            testPlayer.move(keyHandler.direction1);
        }
        if (keyHandler.up2Pressed || keyHandler.down2Pressed ||keyHandler.left2Pressed ||keyHandler.right2Pressed) {
            testPlayer2.move(keyHandler.direction2);
        }

        if (keyHandler.setBombPressed) {
            Bomb bomb = testPlayer.placeBomb();
            if(bomb != null )
            {
                entities.add(bomb);
                BombTimer bombTimer = new BombTimer(this, testPlayer, bomb);
                bombTimer.start();
            }

        }
        if(keyHandler.setBombPressed2) {
            Bomb bomb2 = testPlayer2.placeBomb();
            if(bomb2 != null )
            {
                entities.add(bomb2);
                BombTimer bombTimer = new BombTimer(this, testPlayer2, bomb2);
                bombTimer.start();
            }
        }
    }
}
