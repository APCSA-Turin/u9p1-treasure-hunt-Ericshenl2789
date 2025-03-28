package com.example.project;
import java.util.Scanner;

public class Game{
    private Grid grid;
    private Player player;
    private Enemy[] enemies;
    private Treasure[] treasures;
    private Trophy trophy;
    private int size; 

    public Game(int size){ //the constructor should call initialize() and play()
        this.size = size;
        enemies = new Enemy[2];
        treasures = new Treasure[2];
        initialize();
        play();
    }

    public static void clearScreen() { //do not modify
        try {
            final String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Unix-based (Linux, macOS)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(){ //write your game logic here
        Scanner scanner = new Scanner(System.in);

        while(true){
            try {
                Thread.sleep(100); // Wait for 1/10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clearScreen(); // Clear the screen at the beggining of the while loop
            grid.display();
            //coordinates, rowCol, treasure count and lives for debugging.
            System.out.println(player.getCoords());
            System.out.println(player.getRowCol(size));
            System.out.println("Treasure: " + player.getTreasureCount());
            System.out.println("Lives: " + player.getLives());
            //userinput for direction.
            String input = scanner.nextLine().toLowerCase();
            if(input.equals("w")|| input.equals("s") || input.equals("a") || input.equals("d")){//if the key is correct, then player would move
                //creating Sprite to represent the sprite that the player is going to land on
                Sprite thing = new Sprite(0,0);
                //converting x, y to row col to get the sprite from the grid
                if(player.isValid(size, input)){
                    thing = grid.getGrid()[size - 1 - (input.equals("w") ? player.getY() + 1 : input.equals("s") ? player.getY() - 1 : player.getY())][input.equals("d") ? player.getX() + 1 : input.equals("a") ? player.getX() - 1 : player.getX()];
                }
                //player actually moving
                player.interact(size, input, treasures.length, thing);
                if((!(thing instanceof Trophy)|| player.getTreasureCount() == treasures.length) && player.isValid(size, input)){
                    player.move(input);
                }
                grid.placeSprite(player, input);
            }
            if(player.getLives() == 0){
                grid.gameover();
                break;
            } else if(player.getWin()){
                grid.win();
                break;
            }
        }
            
    }

    public void initialize(){
        //to test, create a player, trophy, grid, treasure, and enemies. Then call placeSprite() to put them on the grid
        grid = new Grid(size);
        player = new Player(0, 0);
        grid.placeSprite(player);
        Enemy enemy = new Enemy(5, 5);
        Enemy enemy2 = new Enemy(7, 8);
        grid.placeSprite(enemy2);grid.placeSprite(enemy);
        enemies[0] = enemy;
        enemies[1] =  enemy2;
        Treasure treasure = new Treasure(2, 2);
        Treasure treasure2 = new Treasure(1,7);
        grid.placeSprite(treasure);grid.placeSprite(treasure2);
        treasures[0] = treasure;
        treasures[1] = treasure2;
        trophy = new Trophy(9, 9);
        grid.placeSprite(trophy);
    }

    public static void main(String[] args) {
        Game a = new Game(10);
    }
}