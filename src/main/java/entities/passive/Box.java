package entities.passive;

import view.GameView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Box extends Obstacle {
    public Box() {
        name = "Box";
        hitbox = new Rectangle(2, 2, 46, 46);
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
        collision = true;
        try {
            img = ImageIO.read(new File("bomberblitz/src/assets/passive_entities/box.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public PowerUp explode(GameView gameView) {
        PowerUp randomPowerUp = spawnRandomPowerUp(1.5);
        if (randomPowerUp != null) {
            randomPowerUp.position.setPosX(currentGridX * gameView.tileSize);
            randomPowerUp.position.setPosY(currentGridY * gameView.tileSize);
            randomPowerUp.currentGridX = (randomPowerUp.position.getPosX() + gameView.tileSize / 2) / gameView.tileSize;
            randomPowerUp.currentGridY = (randomPowerUp.position.getPosY() + gameView.tileSize / 2) / gameView.tileSize;

            return randomPowerUp;
        }
        return null;
    }

    public PowerUp spawnRandomPowerUp(double probability) {
        Random random = new Random();
        double chance = random.nextDouble(); // Véletlenszerű valós szám generálása 0 és 1 között

        // Ha a generált valószínűség kisebb vagy egyenlő a megadott valószínűséggel, akkor létrehozunk egy powerup-ot
        if (chance <= probability) {
            int randomPowerUpType = random.nextInt(2); // Például 3 típusú powerup van

            switch (randomPowerUpType) {
                case 0:
                    PowerUp rollerSkate = new RollerSkate(); // Például Powerup1 típusú powerupp
                    System.out.println("rollerskate spanwned");
                    return rollerSkate;
                case 1:
                    PowerUp slow = new Slow();  // Például Powerup1 típusú powerup
                    System.out.println("slow spawned");
                    return slow;
//                case 2:
//                    PowerUp powerup3 = new RollerSkate();  // Például Powerup1 típusú powerup
//                    System.out.println("3. roller spanwned");
//                    return powerup3;
                default:
                    return null;
            }
        }
        return null;
    }
}
