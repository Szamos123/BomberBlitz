package logic;

import entities.active.Player;
import entities.passive.Bomb;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BombTimer {
    private GameModel gameModel;
    private Bomb bomb;
    private int detonationTime;
    private ActionListener actionListener;
    private Timer timer;

    public BombTimer(GameModel gamemodel, Player player, Bomb bomb) {
        this.detonationTime = player.detonationTime;
        this.bomb = bomb;

        actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bomb.explode();
                gamemodel.entities.remove(bomb);
                player.availableBombs++;
                player.placedBombs.remove(bomb);
                timer.stop();
            }
        };

        timer = new Timer(detonationTime, actionListener);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }
}