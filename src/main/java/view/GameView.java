package view;

import entities.active.Monster;
import entities.passive.Entity;
import logic.CollisionChecker;
import logic.GameModel;

import tile.TileManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameView extends JPanel implements Runnable  {

    public TileManager tileManager = new TileManager(this);
    public GameModel gameModel;
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    Thread gameThread;// thread for the game loop to keep the game running and updating
    public Timer player;
    public final int tileSize = 50; // size of each tile in pixels
    public final int maxScreenCol = 20; // max number of columns on the screen
    public final int maxScreenRow = 20; // max number of rows on the screen
    public int screenWidth = tileSize * maxScreenCol;
    public int screenHeight = tileSize * maxScreenRow;
    public int fps = 60;

    public GameView() {
        this.gameModel = new GameModel(this);

        this.setSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);

        this.addKeyListener(gameModel.keyHandler);
        this.addKeyListener(gameModel.keyHandler2);
        this.setFocusable(true); // allows the panel to get focus

        startGameThread();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                gameModel.update();
                repaint();
                delta--;
            }
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileManager.draw(g2);

        //draw entities
        for (Entity entity : gameModel.entities) {
            if(!gameModel.entities.isEmpty())
                entity.drawEntity(g2, this);
        }
        //draw monsters
        for(Monster monster : gameModel.monsters) {
            monster.drawEntity(g2, gameModel);
        }

        //hitbox
        g2.setColor(Color.RED);
        g2.fillRect(gameModel.testPlayer.position.getPosX() + gameModel.testPlayer.hitbox.x, gameModel.testPlayer.position.getPosY() + gameModel.testPlayer.hitbox.y, gameModel.testPlayer.hitbox.width, gameModel.testPlayer.hitbox.height);
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("bomberblitz/src/assets/down.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(image, gameModel.testPlayer.position.getPosX(), gameModel.testPlayer.position.getPosY(), 50, 50, null);
        g2.setColor(Color.BLUE);
        g2.fillRect(gameModel.testPlayer2.position.getPosX(), gameModel.testPlayer2.position.getPosY(), 50, 50); //draws player2

        //hitbox
        g2.setColor(Color.RED);
        g2.fillRect(gameModel.testPlayer2.position.getPosX() + gameModel.testPlayer2.hitbox.x, gameModel.testPlayer2.position.getPosY() + gameModel.testPlayer2.hitbox.y, gameModel.testPlayer2.hitbox.width, gameModel.testPlayer2.hitbox.height);





        g2.dispose();
    }


}

