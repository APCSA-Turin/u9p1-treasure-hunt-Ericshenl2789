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
        boolean a = true;
        
        while(a){
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
            
            clearScreen();
            
            if(player.getLives() == 0 || player.getWin()){
                if(player.getWin()){
                    grid.win();
                }else if(player.getLives() == 0){
                    grid.gameover();
                }
                System.out.println("Do you want to play again? (Y/N)");
                a = scanner.nextLine().toLowerCase().equals("y");
                if(a){
                    initialize();
                }
            }
        }
        scanner.close();
    }

    public void initialize(){
        //to test, create a player, trophy, grid, treasure, and enemies. Then call placeSprite() to put them on the grid
        Scanner scan = new Scanner(System.in);
        System.out.println("Choose difficulty: \n1. Easy\n2. Medium\n3. Hard");
        int input = scan.nextInt();
        scan.nextLine();
        switch (input) {
            case 1:
                size = 5;
                enemies = new Enemy[1];
                treasures = new Treasure[1];
                break;
        
            case 2:
                size = 10;
                enemies = new Enemy[2];
                treasures = new Treasure[2];
                break;
            
            case 3:
                size = 15;
                enemies = new Enemy[5];
                treasures = new Treasure[3];
                break;
        }
        grid = new Grid(size);
        player = new Player(0, 0);
        grid.placeSprite(player);
        
        trophy = new Trophy(size - 1, size - 1);
        grid.placeSprite(trophy);

        for(int i = 0; i < enemies.length; i++){
            int randX = 0, randY = 0;
            while(!(grid.getGrid()[size - 1 - randY][randX] instanceof Dot)){
                randX = (int)(Math.random() * size - 1);
                randY = (int)(Math.random() * size - 1);
            }

            Enemy newEnemy = new Enemy(randX, randY);
            grid.placeSprite(newEnemy);
            enemies[i] = newEnemy;
        }        
        for(int i = 0; i < treasures.length; i++){
            int randX = 0, randY = 0;

            while(!(grid.getGrid()[size - 1 - randY][randX] instanceof Dot)){
                randX = (int)(Math.random() * size - 1);
                randY = (int)(Math.random() * size - 1);
            }
            
            Treasure newTreasure = new Treasure(randX, randY);
            grid.placeSprite(newTreasure);
            treasures[i] = newTreasure;
        }        
    }

    public static void main(String[] args) {
        Game a = new Game(10);
        a.getClass();
    }
}