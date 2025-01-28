package entities.passive;

import entities.active.Player;
import entities.active.Sprite;
import view.GameView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class RollerSkate extends PowerUp {
    private static final double SPEED_BOOST_FACTOR = 1.5; // A sebesség növelésének tényezője
    private static final long EFFECT_DURATION = 3000; // Az effektus ideje milliszekundumban


    public RollerSkate() {
        name = "RollerSkate";
        hitbox = new Rectangle(10, 10, 30, 30);
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
        collision = true;
        try{
            img = ImageIO.read(new File("bomberblitz/src/assets/passive_entities/rollerskate.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void applyEffect(GameView gameView, Player player) {
        for (Sprite sprite : gameView.gameModel.sprites) {
            if (sprite == player) {
                player.increaseSpeed(SPEED_BOOST_FACTOR);
                player.activePowerUps.add(RollerSkate.this);

                Timer effectTimer = new Timer();
                effectTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        player.speed = player.originalSpeed;
                        gameView.gameModel.entities.remove(RollerSkate.this);
                        player.activePowerUps.remove(RollerSkate.this);
                    }
                }, EFFECT_DURATION);
            }
        }
    }
}
