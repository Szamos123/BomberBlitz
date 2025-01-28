package view;

import javax.swing.*;


public class GameFrame extends JFrame {
    GameView gameView;
    public GameFrame(){
        gameView = new GameView();
        getContentPane().add(gameView);

        this.setTitle("BomberBlitz");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1920,1080);
        this.setVisible(true);
    }

}
