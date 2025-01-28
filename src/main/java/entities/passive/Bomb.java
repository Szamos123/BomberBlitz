package entities.passive;

import entities.active.Direction;
import entities.active.Sprite;
import view.GameView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bomb extends Entity {
    BufferedImage explosionImage;
    public Bomb(GameView gameView){
        this.gameView = gameView;
        name = "Bomb";
        hitbox = new Rectangle(10, 10, 30, 30);
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
        collision = true;
        try{
            img = ImageIO.read(new File("bomberblitz/src/assets/passive_entities/bomb.png"));
            explosionImage = ImageIO.read(new File("bomberblitz/src/assets/explosion.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void explode() {
        Graphics g = gameView.getGraphics();
        g.drawImage(explosionImage, currentGridX * gameView.tileSize, currentGridY * gameView.tileSize, gameView.tileSize, gameView.tileSize, null);


//        for (int i = 1; i <= 3; i++) {
//            explodeInDirection(-i, 0); // Balra
//            explodeInDirection(i, 0);  // Jobbra
//            explodeInDirection(0, -i); // Felfelé
//            explodeInDirection(0, i);  // Lefelé
//        }

        explodeInDirection(-1, 0); // Balra
        explodeInDirection(1, 0);  // Jobbra
        explodeInDirection(0, -1); // Felfelé
        explodeInDirection(0, 1);  // Lefelé

        explodeInDirection(-2, 0); // Balra
        explodeInDirection(2, 0);  // Jobbra
        explodeInDirection(0, -2); // Felfelé
        explodeInDirection(0, 2);  // Lefelé
    }

    private void explodeInDirection(int dx, int dy) {

        int x = currentGridX + dx;
        int y = currentGridY + dy;

        Entity explodedBox = new Entity();
        Entity randomPowerUp = null;

        if (!gameView.gameModel.entities.isEmpty()) {
            for (Entity entity : gameView.gameModel.entities) {
                if (entity.currentGridX == x && entity.currentGridY == y && entity instanceof Box) {
                    explodedBox = entity;
                    randomPowerUp = ((Box) entity).explode(gameView);
                    System.out.println(randomPowerUp);
                }
                if (x >= 0 && x <= gameView.maxScreenCol && y >= 0 && y <= gameView.maxScreenRow && gameView.tileManager.mapTileNum[x][y] == 0) {

                }

//                if (entity.currentGridX == x && entity.currentGridY == y && entity instanceof Bomb) {
//                    ((Bomb) entity).explode();
//
//                }
            }
        }

        gameView.gameModel.entities.remove(explodedBox);
        if(randomPowerUp != null) {
            gameView.gameModel.entities.add(randomPowerUp);
        }

        for (Sprite sprite : gameView.gameModel.sprites) {
            if (sprite.currentGridX == x && sprite.currentGridY == y) {
                System.out.println("sprite dead: " + sprite);
                sprite.die();
            }
        }

        if (x >= 0 && x <= gameView.maxScreenCol && y >= 0 && y <= gameView.maxScreenRow && gameView.tileManager.mapTileNum[x][y] != 0) {
            Graphics g = gameView.getGraphics();
            g.drawImage(explosionImage, x * gameView.tileSize, y * gameView.tileSize, gameView.tileSize, gameView.tileSize, null);
        }


    }


}