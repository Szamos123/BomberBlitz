package tile;

import entities.passive.Box;
import entities.passive.Entity;
import view.GameView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class TileManager {
    GameView gameView;
    public Tile[] tiles; // Will be used to store the different tiles
    public int[][] mapTileNum;
    public ArrayList<Entity> boxes = new ArrayList<Entity>();

    public TileManager(GameView gameView) {
        this.gameView = gameView;
        mapTileNum = new int[gameView.maxScreenRow][gameView.maxScreenCol]; // Will be used to store the tile numbers of the map

        tiles = new Tile[13];
        getTileImg();
        loadMap();
    }
    public void getTileImg() {
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(new File("bomberblitz/src/assets/tiles/wall.png")); // wall tile(unbreakable), number 0 in matrix
            tiles[0].collision = true;

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(new File("bomberblitz/src/assets/tiles/dirt.png")); // floor tile, number 1 in matrix

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(new File("bomberblitz/src/assets/tiles/dirt.png")); // box tile (contains powerups, breakable), number 2 in matrix

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Entity> loadBoxes() {
        return boxes;
    }

    public void loadMap(){
        try{
            FileReader fr = new FileReader("bomberblitz/src/assets/maps/map1.txt");
            BufferedReader br = new BufferedReader(fr);
            int col = 0;
            int row = 0;
            while(col < gameView.maxScreenCol && row < gameView.maxScreenRow){
                String line = br.readLine();
                while (col < gameView.maxScreenCol){
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;

                    if(num == 2) {
                        Box box = new Box();
                        box.position.setPosX(col * gameView.tileSize);
                        box.position.setPosY(row * gameView.tileSize);
                        box.currentGridX = (box.position.getPosX() + gameView.tileSize / 2) / gameView.tileSize;
                        box.currentGridY = (box.position.getPosY() + gameView.tileSize / 2) / gameView.tileSize;
                        boxes.add(box);
                    }
                    col++;
                }
                if(col == gameView.maxScreenCol){
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gameView.maxScreenCol && row < gameView.maxScreenRow) {
            int tileNum = mapTileNum[col][row];

            g2.drawImage(tiles[tileNum].image, x, y, gameView.tileSize, gameView.tileSize, null);
            col++;
            x += gameView.tileSize;

            if(col == gameView.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gameView.tileSize;
            }
        }

    }
}