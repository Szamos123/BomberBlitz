package entities.active;

import entities.Position;
import entities.passive.*;
import view.GameView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public  class Player extends Sprite {
    String name;
    int score;
    int reloadTime;
    public int detonationTime;
    boolean isInvincible = false;
    boolean isAlive;
    int placeableBarriers;
    List<PowerUp> availablePowerUps;
    public ArrayList<PowerUp> activePowerUps;
    public int availableBombs;
    public ArrayList<Bomb> placedBombs;
    GameView gameView;
    
    public Player(GameView gameView){
        super(gameView);
        this.gameView = gameView;
        this.position = new Position();
        this.direction = Direction.DOWN;
        this.speed = 10;
        this.originalSpeed = 10;
        this.availableBombs = 1;
        this.detonationTime = 5000;
        this.placedBombs = new ArrayList<Bomb>();
        this.activePowerUps = new ArrayList<PowerUp>();

        hitbox = new Rectangle(10, 20, 30, 30);
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
    }

    public void move(Direction direction) {
        currentGridX = (position.getPosX() + gameView.tileSize / 2) / gameView.tileSize;
        currentGridY = (position.getPosY() + gameView.tileSize / 2) / gameView.tileSize;
        switch (direction) {
            case UP:
                this.direction = Direction.UP;
                break;
            case DOWN:
                this.direction = Direction.DOWN;
                break;
            case LEFT:
                this.direction = Direction.LEFT;
                break;
            case RIGHT:
                this.direction = Direction.RIGHT;
                break;
        }


        collision = false;
        gameView.collisionChecker.checkTile(this);
        Entity entity = gameView.collisionChecker.checkEntity(this, true);
        pickUpPowerUp(entity);

        if(!collision) {
            switch (this.direction) {
                case UP:
                    position.setPosY(position.getPosY() - speed);
                    if(!placedBombs.isEmpty()) {
                        for (Bomb bomb : placedBombs) {
                           if(((position.getPosY() + gameView.tileSize / 2) / gameView.tileSize) == ((bomb.position.getPosY() + gameView.tileSize / 2) / gameView.tileSize)) {
                               onBomb = true;
                           } else {
                               onBomb = false;
                           }
                        }
                    }
                    //onBomb = false;
                    break;
                case DOWN:
                    position.setPosY(position.getPosY() + speed);
                    if(!placedBombs.isEmpty()) {
                        for (Bomb bomb : placedBombs) {
                            if(((position.getPosY() + gameView.tileSize / 2) / gameView.tileSize) == ((bomb.position.getPosY() + gameView.tileSize / 2) / gameView.tileSize)) {
                                onBomb = true;
                            } else {
                                onBomb = false;
                            }
                        }
                    }
                    //onBomb = false;
                    break;
                case LEFT:
                    position.setPosX(position.getPosX() - speed);
                    if(!placedBombs.isEmpty()) {
                        for (Bomb bomb : placedBombs) {
                            if(((position.getPosX() + gameView.tileSize / 2) / gameView.tileSize) == ((bomb.position.getPosX() + gameView.tileSize / 2) / gameView.tileSize)) {
                                onBomb = true;
                            } else {
                                onBomb = false;
                            }
                        }
                    }
                    //onBomb = false;
                    break;
                case RIGHT:
                    position.setPosX(position.getPosX() + speed);
                    if(!placedBombs.isEmpty()) {
                        for (Bomb bomb : placedBombs) {
                            if(((position.getPosX() + gameView.tileSize / 2) / gameView.tileSize) == ((bomb.position.getPosX() + gameView.tileSize / 2) / gameView.tileSize)) {
                                onBomb = true;
                            } else {
                                onBomb = false;
                            }
                        }
                    }
                    //onBomb = false;
                    break;
            }
        }
    }


    public void pickUpPowerUp(Entity entity) {
        if (entity != null && !(entity instanceof Box) && !(entity instanceof Bomb)) {
            if(entity instanceof RollerSkate && !isPowerUpActive(RollerSkate.class)) {
                ((RollerSkate) entity).applyEffect(gameView, this);
            } else if (entity instanceof Slow && !isPowerUpActive(Slow.class)) {
                ((Slow) entity).applyEffect(gameView, this);
            }
            gameView.gameModel.entities.remove(entity);
        }
    }

    private boolean isPowerUpActive(Class<?> powerUpClass) {
        for (PowerUp activePowerUp : activePowerUps) {
            if (powerUpClass.isInstance(activePowerUp)) {
                return true;
            }
        }
        return false;
    }

    public Bomb placeBomb(){
        if(availableBombs > 0){
            onBomb = true;
            Bomb bomb = new Bomb(gameView);

            currentGridX = (position.getPosX() + gameView.tileSize / 2) / gameView.tileSize;
            currentGridY = (position.getPosY() + gameView.tileSize / 2) / gameView.tileSize;

            bomb.position.setPosX((currentGridX * gameView.tileSize));
            bomb.position.setPosY((currentGridY * gameView.tileSize));

            bomb.currentGridX = this.currentGridX;
            bomb.currentGridY = this.currentGridY;

            availableBombs--;
            placedBombs.add(bomb);
            return bomb;
        } else {
            return null;
        }
    }

    public void increaseSpeed(double factor) {
        double dSPeed = (double)speed;
        double newSpeed = dSPeed *= factor;
        speed = (int)newSpeed;
    }

    public void decreaseSpeed(double factor) {
        double dSPeed = (double)speed;
        double newSpeed = dSPeed /= factor;
        speed = (int)newSpeed;
    }
}
