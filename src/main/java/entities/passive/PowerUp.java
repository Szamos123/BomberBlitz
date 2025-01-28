package entities.passive;

import entities.active.Player;
import view.GameView;

public abstract class PowerUp extends Entity {
    public abstract void applyEffect(GameView gameView, Player player);
}
