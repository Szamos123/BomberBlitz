package entities.active;

import entities.Position;
import entities.passive.Bomb;
import entities.passive.Box;
import entities.passive.Entity;
import entities.passive.Wall;
import view.GameView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class GhostMonster extends BaseMonster {
    private static final double CONTINUE_MOVING_PROBABILITY = 0.75; // 75% chance to continue moving in the same direction when hitting an obstacle
    public Random random = new Random();
    public GhostMonster(GameView gameView) {
        super(gameView);
        speed = 2; // Move slower than the base monster
        position = new Position();
        hitbox = new Rectangle(10, 10, 30, 30);
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;

        position.setPosX(200);
        position.setPosY(875);

        try {
            img = ImageIO.read(new File("bomberblitz/src/assets/active_entities/ghost_monster.png"));
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

        // Check for a collision with an entity
        Entity entity = gameView.collisionChecker.checkEntity(this, false);

        // If collides with a bomb, move back to the old position and generate a new direction
        if (entity instanceof Bomb) {
            position.setPosX(oldPosX);
            position.setPosY(oldPosY);
            // Store the direction of the collision
            previousCollisionDirection = direction;
            // Generate a new random direction that is not the same as the previous collision direction
            do {
                direction = Direction.values()[random.nextInt(Direction.values().length)];
            } while (direction == previousCollisionDirection);
        }

        // Check for a collision with the game map boundaries
        if (isOutsideMap(position.getPosX(), position.getPosY())) {
            // If the new position is outside the map, treat it as a collision and move back to the old position
            position.setPosX(oldPosX);
            position.setPosY(oldPosY);
            // Store the direction of the collision
            previousCollisionDirection = direction;
            // Generate a new random direction that is not the same as the previous collision direction
            do {
                direction = Direction.values()[random.nextInt(Direction.values().length)];
            } while (direction == previousCollisionDirection);
        }

        //Logic for changing direction randomly(1/500 chance, cannot change to the same direction)
        int chanceToChangeDirection = random.nextInt(500);
        if (chanceToChangeDirection < 5) {
            Direction oldDirection = direction;
            do {
                direction = Direction.values()[random.nextInt(Direction.values().length)];
            } while (direction == oldDirection);
        }
    }
    private boolean isOutsideMap(int posX, int posY) {
        int MAP_WIDTH = 20; // replace with your actual map width
        int MAP_HEIGHT = 20; // replace with your actual map height
        int CELL_SIZE = 50; // replace with your actual cell size
        int BUFFER = 50; // buffer distance from the edge

        int leftEdge = BUFFER;
        int rightEdge = (MAP_WIDTH - 1) * CELL_SIZE - BUFFER;
        int topEdge = BUFFER;
        int bottomEdge = (MAP_HEIGHT - 1) * CELL_SIZE - BUFFER;

        return posX < leftEdge || posX > rightEdge || posY < topEdge || posY > bottomEdge;
    }
}
