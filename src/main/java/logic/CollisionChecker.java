package logic;

import entities.active.Sprite;
import entities.passive.Bomb;
import entities.passive.Entity;
import view.GameView;

public class CollisionChecker {
    GameView gameView;
    public CollisionChecker(GameView gameView) {
        this.gameView = gameView;
    }

    public void checkTile(Sprite sprite) {
        int spriteLeftPosX = sprite.position.getPosX() + sprite.hitbox.x;
        int spriteRightPosX = sprite.position.getPosX() + sprite.hitbox.x + sprite.hitbox.width - 1;
        int spriteTopPosY = sprite.position.getPosY() + sprite.hitbox.y;
        int spriteBottomPosY = sprite.position.getPosY() + sprite.hitbox.y + sprite.hitbox.height - 1;

        int spriteLeftCol = spriteLeftPosX / gameView.tileSize;
        int spriteRightCol = spriteRightPosX / gameView.tileSize;
        int spriteTopRow = spriteTopPosY / gameView.tileSize;
        int spriteBottomRow = spriteBottomPosY / gameView.tileSize;

        int tileNum1;
        int tileNum2;

        switch (sprite.direction) {
            case UP:
                spriteTopRow = (int)Math.floor((spriteTopPosY - sprite.speed) / gameView.tileSize);
                tileNum1 = gameView.tileManager.mapTileNum[spriteLeftCol][spriteTopRow];
                tileNum2 = gameView.tileManager.mapTileNum[spriteRightCol][spriteTopRow];
                if(gameView.tileManager.tiles[tileNum1].collision || gameView.tileManager.tiles[tileNum2].collision) {
                    sprite.collision = true;
                }
                break;
            case DOWN:
                spriteBottomRow = (int)Math.floor((spriteBottomPosY + sprite.speed) / gameView.tileSize);
                tileNum1 = gameView.tileManager.mapTileNum[spriteLeftCol][spriteBottomRow];
                tileNum2 = gameView.tileManager.mapTileNum[spriteRightCol][spriteBottomRow];
                if(gameView.tileManager.tiles[tileNum1].collision || gameView.tileManager.tiles[tileNum2].collision) {
                    sprite.collision = true;
                }
                break;
            case LEFT:
                spriteLeftCol = (int)Math.floor((spriteLeftPosX - sprite.speed) / gameView.tileSize);
                tileNum1 = gameView.tileManager.mapTileNum[spriteLeftCol][spriteTopRow];
                tileNum2 = gameView.tileManager.mapTileNum[spriteLeftCol][spriteBottomRow];
                if(gameView.tileManager.tiles[tileNum1].collision || gameView.tileManager.tiles[tileNum2].collision) {
                    sprite.collision = true;
                }
                break;
            case RIGHT:
                spriteRightCol = (int)Math.floor((spriteRightPosX + sprite.speed) / gameView.tileSize);
                tileNum1 = gameView.tileManager.mapTileNum[spriteRightCol][spriteTopRow];
                tileNum2 = gameView.tileManager.mapTileNum[spriteRightCol][spriteBottomRow];
                if(gameView.tileManager.tiles[tileNum1].collision || gameView.tileManager.tiles[tileNum2].collision) {
                    sprite.collision = true;
                }
                break;
        }
    }

    public Entity checkEntity(Sprite sprite, boolean player) {
        Entity collidedEntity = null;

        for (Entity entity : gameView.gameModel.entities) {
            if (entity != null) {
                sprite.hitbox.x = sprite.position.getPosX() + sprite.hitbox.x;
                sprite.hitbox.y = sprite.position.getPosY() + sprite.hitbox.y;

                entity.hitbox.x = entity.position.getPosX() + entity.hitbox.x;
                entity.hitbox.y = entity.position.getPosY() + entity.hitbox.y;

                switch (sprite.direction) {
                    case UP:
                        sprite.hitbox.y -= sprite.speed;
                        if (sprite.hitbox.intersects(entity.hitbox)) {
                            if(sprite.onBomb && entity instanceof Bomb) {
                                System.out.println("FEL");
                                collidedEntity = entity;
                            } else {
                                if (entity.collision) {
                                    sprite.collision = true;
                                }
                                if (player) {
                                    collidedEntity = entity;
                                }
                            }
                        }
                        break;
                    case DOWN:
                        sprite.hitbox.y += sprite.speed;
                        if (sprite.hitbox.intersects(entity.hitbox)) {
                            if(sprite.onBomb && entity instanceof Bomb) {
                                System.out.println("LE");
                                collidedEntity = entity;
                            } else {
                                if (entity.collision) {
                                    sprite.collision = true;
                                }
                                if (player) {
                                    collidedEntity = entity;
                                }
                            }
                        }
                        break;
                    case LEFT:
                        sprite.hitbox.x -= sprite.speed;
                        if (sprite.hitbox.intersects(entity.hitbox)) {
                            if(sprite.onBomb && entity instanceof Bomb) {
                                System.out.println("BALRA");
                                collidedEntity = entity;
                            } else {
                                if (entity.collision) {
                                    sprite.collision = true;
                                }
                                if (player) {
                                    collidedEntity = entity;
                                }
                            }
                        }
                        break;
                    case RIGHT:
                        sprite.hitbox.x += sprite.speed;
                        if (sprite.hitbox.intersects(entity.hitbox)) {
                            if(sprite.onBomb && entity instanceof Bomb) {
                                System.out.println("JOBBRA");
                                collidedEntity = entity;
                            } else {
                                if (entity.collision) {
                                    sprite.collision = true;
                                }
                                if (player) {
                                    collidedEntity = entity;
                                }
                            }
                        }
                        break;
                }
                sprite.hitbox.x = sprite.hitboxDefaultX;
                sprite.hitbox.y = sprite.hitboxDefaultY;

                entity.hitbox.x = entity.hitboxDefaultX;
                entity.hitbox.y = entity.hitboxDefaultY;
            }
        }
        return collidedEntity;
    }
}
