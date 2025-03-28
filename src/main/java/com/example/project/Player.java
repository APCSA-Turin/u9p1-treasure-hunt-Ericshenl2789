package com.example.project;

//DO NOT DELETE ANY METHODS BELOW
public class Player extends Sprite{
    private int treasureCount;
    private int numLives;
    private boolean win;

    public Player(int x, int y) { //set treasureCount = 0 and numLives = 2 
        super(x, y);
        treasureCount = 0;
        numLives = 2;
        win = false;
    }


    public int getTreasureCount(){return treasureCount;}
    public int getLives(){return numLives;}
    public boolean getWin(){return win;}

  
    //move method should override parent class, sprite
    public void move(String direction) { //move the (x,y) coordinates of the player
        if(direction.equals("w")){
            setY(getY()+1);
        }
        if(direction.equals("a")){
            setX(getX() - 1);
        }
        if(direction.equals("s")){
            setY(getY() - 1);
        }
        if(direction.equals("d")){
            setX(getX() + 1);
        }
    }


    public void interact(int size, String direction, int numTreasures, Object obj) { // interact with an object in the position you are moving to 
    //numTreasures is the total treasures at the beginning of the game
        if(isValid(size, direction)){
            if(obj instanceof Enemy){
                numLives--;
            } else if(obj instanceof Treasure && !(obj instanceof Trophy)){
                treasureCount++;
            } else if(obj instanceof Trophy && treasureCount == numTreasures){
                win = true;
            }
        }
    }


    public boolean isValid(int size, String direction){ //check grid boundaries
        if(direction.equals("a") && (getX() == 0)){
            return false;
        }
        if(direction.equals("d") && (getX() == size - 1)){
            return false;
        }
        if(direction.equals("w") && (getY() == size - 1)){
            return false;
        }
        if(direction.equals("s") && (getY() == 0)){
            return false;
        }
        return true;
    }

    public String getRowCol(int size){
        return "Player:" + super.getRowCol(size);
    }

    public String getCoords(){
        return "Player:" + super.getCoords();
    }
   
}



