package com.example.project;


//DO NOT DELETE ANY METHODS BELOW
public class Grid{
    private Sprite[][] grid;
    private int size;

    public Grid(int size) { //initialize and create a grid with all DOT objects
        this.size = size;
        grid = new Sprite[size][size];
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                grid[i][j] = new Dot(i, j);
            }
        }
    }

 
    public Sprite[][] getGrid(){return grid;}

    public void placeSprite(Sprite s){ //place sprite in new spot
        grid[(size - 1) - s.getY()][s.getX()] = s;
    }

    public void placeSprite(Sprite s, String direction) { //place sprite in a new spot based on direction
        Dot empty = new Dot(0, 0);
        placeSprite(s);
        if(direction.equals("w")){
            empty.setY(s.getY() - 1);
        } else if(direction.equals("s")){
            empty.setY(s.getY() + 1);
        } else {
            empty.setY(s.getY());
        }
        if(direction.equals("a")){
            empty.setX(s.getX() + 1 );
        }else if(direction.equals("d")){
            empty.setX(s.getX() - 1);
        } else{
            empty.setX(s.getX());
        }
        placeSprite(empty);
    }


    public void display() { //print out the current grid to the screen 
        for(Sprite[] row : grid){
            for(Sprite thing : row){
                if(thing instanceof Dot){
                    System.out.print("⬜");
                } else if(thing instanceof Player){
                    System.out.print("👾");
                } else if (thing instanceof Enemy){
                    System.out.print("🐲");
                }else if(thing instanceof Treasure && !(thing instanceof Trophy)){
                    System.out.print("🪙 ");
                } else if(thing instanceof Trophy){
                    System.out.print("🏆");
                }
            }
            System.out.println();
        }
    }
    
    public void gameover(){ //use this method to display a loss
        for(Sprite[] row : grid){
            for(Sprite thing : row){
                if(!(thing instanceof Player)){
                    System.out.print("❌");
                } else if(thing instanceof Player){
                    System.out.print("👾");
                }
            }
            System.out.println();
        }
    }

    public void win(){ //use this method to display a win 
        for(Sprite[] row : grid){
            for(Sprite thing : row){
                if(!(thing instanceof Player)){
                    System.out.print("🟨");
                } else if(thing instanceof Player){
                    System.out.print("🌌");
                }
            }
            System.out.println();
        }
    }


}