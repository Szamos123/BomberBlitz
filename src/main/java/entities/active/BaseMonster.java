package entities.active;

import entities.Position;
import entities.passive.Entity;
import logic.GameModel;
import view.GameView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class BaseMonster extends Monster{
    public Direction previousCollisionDirection = null;
    private Random random = new Random();
    GameView gameView;
    public BaseMonster(GameView gameView){
        super(gameView);
        this.gameView = gameView;
        speed = 4;
        position = new Position();
        hitbox = new Rectangle(10, 10, 30, 30);
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;

        position.setPosX(100);
        position.setPosY(875);

        Random random = new Random();
        Direction[] directions = Direction.values(); //gets values of the direction enum
        Direction randDirection = directions[random.nextInt(directions.length)];
        direction = randDirection;

        try {
            img = ImageIO.read(new File("bomberblitz/src/assets/active_entities/base_monster.png"));
        } catch (IOException e) {
            e.printStackTrace();

        }

    }
    @Override
    public void moveMonster() {
        // Store the current position
        int oldPosX = position.getPosX();
        int oldPosY = position.getPosY();

        // Move the monster
        switch (direction) {
            case UP:
                position.setPosY(position.getPosY() - speed);
                break;
            case DOWN:
                position.setPosY(position.getPosY() + speed);
                break;
            case LEFT:
                position.setPosX(position.getPosX() - speed);
                break;
            case RIGHT:
                position.setPosX(position.getPosX() + speed);
                break;
        }

        //Logic for changing direction randomly(1/500 chance, cannot change to the same direction)
        int chanceToChangeDirection = random.nextInt(500);
        if (chanceToChangeDirection < 5) {
            Direction oldDirection = direction;
            do {
                direction = Direction.values()[random.nextInt(Direction.values().length)];
            } while (direction == oldDirection);
        }
        // Check for a collision
        collision = false;
        gameView.collisionChecker.checkTile(this);
        Entity entity=gameView.collisionChecker.checkEntity(this, false);

        // if collides, move back to the old position and generate a new direction
        if(collision){
            position.setPosX(oldPosX);
            position.setPosY(oldPosY);
            // Store the direction of the collision
            previousCollisionDirection = direction;
            // Generate a new random direction that is not the same as the previous collision direction
            do {
                direction = Direction.values()[random.nextInt(Direction.values().length)];
            } while (direction == previousCollisionDirection);
        }
    }


}
