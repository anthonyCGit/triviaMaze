package model;

import java.io.Serializable;

/**
 * A maze that contains rooms with ability to lock doors and move between rooms.
 * @author ajc77
 * @version 3/11/2022
 */
public class Maze implements Serializable {
    
    /**
     * The UID used for serialization.
     */
    private static final long serialVersionUID = 8077601493963648964L;

    /**
     * A 2D array of rooms with doors that can be individually locked.
     */
    private Room[][] myMaze;
    
    /**
     * The current x index ([Y][X]) of the user.
     */
    private int myCurrentX;
    
    /**
     * The current y index ([Y][X]) of the user.
     */
    private int myCurrentY;
    
    /**
     * The finish x index ([Y][X]) of the maze.
     */
    private int myFinishX;
    
    /**
     * The finish y index ([Y][X]) of the maze.
     */
    private int myFinishY;
    
    /**
     * Whether movement is forced through locked doors.
     */
    private boolean myForcedMove;
    
    /**
     * Constructs a maze of a specified size and initializes the rooms.
     * @param theYSize The height of the maze in rooms.
     * @param theXSize The width of the maze in rooms.
     */
    public Maze(final int theYSize, final int theXSize) {
        if (theYSize <= 0 || theXSize <= 0) {
            throw new IllegalArgumentException("Maze must contain at least 1 room. theYSize and theXSize must be at least 1.");
        }
        myMaze = new Room[theYSize][theXSize];
        prepareMaze();
        myCurrentX = 0;
        myCurrentY = 0;
        myFinishX = theXSize - 1;
        myFinishY = theYSize - 1;
        myForcedMove = false;
    }    
    
    /**
     * Prepares a given 2D room array by locking the outer doors.
     * @param theMaze The 2D room array to prepare.
     */
    private void prepareMaze() {
        for (int i = 0; i < myMaze.length; i++) {
            for (int j = 0; j < myMaze[i].length; j++) {
                myMaze[i][j] = new Room();
                
                if (i == 0) {
                    myMaze[i][j].lockUpDoor();
                }
                if (j == 0) {
                    myMaze[i][j].lockLeftDoor();
                }
                if (i == myMaze.length - 1) {
                    myMaze[i][j].lockDownDoor();
                }
                if (j == myMaze[i].length - 1) {
                    myMaze[i][j].lockRightDoor();
                }
            }
        }
    }
    
    /**
     * Checks whether the door above the user is open.
     * @return Whether the door above the user is open.
     */
    public boolean isUpOpen() {
        return ((myCurrentY > 0) && (myMaze[myCurrentY][myCurrentX].isUpDoorOpen()));
    }
    
    /**
     * Checks whether the door below the user is open.
     * @return Whether the door below the user is open.
     */
    public boolean isDownOpen() {
        return ((myCurrentY < myMaze.length - 1) && (myMaze[myCurrentY][myCurrentX].isDownDoorOpen()));
    }
    
    /**
     * Checks whether the door to the left of the user is open.
     * @return Whether the door to the left of the user is open.
     */
    public boolean isLeftOpen() {
        return ((myCurrentX > 0) && (myMaze[myCurrentY][myCurrentX].isLeftDoorOpen()));
    }
    
    /**
     * Checks whether the door to the right of the user is open.
     * @return Whether the door to the right of the user is open.
     */
    public boolean isRightOpen() {
        return ((myCurrentX < myMaze[myCurrentY].length - 1) && (myMaze[myCurrentY][myCurrentX].isRightDoorOpen()));
    }
    
    /**
     * Checks whether the user is at the finish.
     * @return Whether the user is at the finish.
     */
    public boolean isAtFinish() {
        return ((myCurrentX == myFinishX) && (myCurrentY == myFinishY));
    }
    
    /**
     * Moves the user up one room if possible.
     */
    public void moveUp() {
        if (this.isUpOpen() || (myForcedMove && myCurrentY > 0)) {
            myCurrentY--;
        }
    }
    
    /**
     * Moves the user down one room if possible.
     */
    public void moveDown() {
        if (this.isDownOpen() || (myForcedMove && myCurrentY < myMaze.length - 2)) {
            myCurrentY++;
        }
    }
    
    /**
     * Moves the user left one room if possible.
     */
    public void moveLeft() {
        if (this.isLeftOpen() || (myForcedMove && myCurrentX > 0)) {
            myCurrentX--;
        }
    }
    
    /**
     * Moves the user right one room if possible.
     */
    public void moveRight() {
        if (this.isRightOpen() || (myForcedMove && myCurrentX < myMaze[myCurrentY].length - 1)) {
            myCurrentX++;
        } 
    }
    
    /**
     * Locks the door above the user.
     */
    public void lockUp() {
        if (this.isUpOpen()) {
            myMaze[myCurrentY][myCurrentX].lockUpDoor();
            myMaze[myCurrentY - 1][myCurrentX].lockDownDoor();
        }
    }
    
    /**
     * Locks the door below the user.
     */
    public void lockDown() {
        if (this.isDownOpen()) {
            myMaze[myCurrentY][myCurrentX].lockDownDoor();
            myMaze[myCurrentY + 1][myCurrentX].lockUpDoor();
        }
    }
    
    /**
     * Locks the door to the left of the user.
     */
    public void lockLeft() {
        if (this.isLeftOpen()) {
            myMaze[myCurrentY][myCurrentX].lockLeftDoor();
            myMaze[myCurrentY][myCurrentX - 1].lockRightDoor();
        }
    }
    
    /**
     * Locks the door to the right of the user.
     */
    public void lockRight() {
        if (this.isRightOpen()) {
            myMaze[myCurrentY][myCurrentX].lockRightDoor();
            myMaze[myCurrentY][myCurrentX + 1].lockLeftDoor();
        } 
    }
    
    /**
     * Returns whether the maze is currently solvable.
     * @return Whether the maze is currently solvable.
     */
    public boolean isSolvable() {
        
        if (isAtFinish()) {
            return true;
        }
        
        int[][] tracker = new int[myMaze.length][myMaze[0].length];
        int currentY = myCurrentY;
        int currentX = myCurrentX;
        tracker[currentY][currentX] = 1;
        
        boolean up, down, left, right;
        if (myMaze[currentY][currentX].isUpDoorOpen()) {
            up = isSolvableHelper(tracker, currentY - 1, currentX);
        } else {
            up = false;
        }
        
        if (myMaze[currentY][currentX].isDownDoorOpen()) {
            down = isSolvableHelper(tracker, currentY + 1, currentX);
        } else {
            down = false;
        }
        
        if (myMaze[currentY][currentX].isLeftDoorOpen()) {
            left = isSolvableHelper(tracker, currentY, currentX - 1);
        } else {
            left = false;
        }
        
        if (myMaze[currentY][currentX].isRightDoorOpen()) {
            right = isSolvableHelper(tracker, currentY, currentX + 1);
        } else {
            right = false;
        }
        
        return (up || down || left || right);
    }
    
    /**
     * Assists in recursively checking whether the maze is solvable.
     * @param theTracker The array that tracks whether a space has been visited.
     * @param theCurrentY The current x index ([Y][X]) of the check.
     * @param theCurrentX The current y index ([Y][X]) of the check.
     * @return Whether the maze is currently solvable.
     */
    private boolean isSolvableHelper(int[][] theTracker, int theCurrentY, int theCurrentX) {
        if (theCurrentY == myFinishY && theCurrentX == myFinishX) {
            return true;
        } else {
            theTracker[theCurrentY][theCurrentX] = 1;
            
            boolean up, down, left, right;
            if (myMaze[theCurrentY][theCurrentX].isUpDoorOpen()
                    && theTracker[theCurrentY - 1][theCurrentX] != 1) {
                up = isSolvableHelper(theTracker, theCurrentY - 1, theCurrentX);
            } else {
                up = false;
            }
            
            if (myMaze[theCurrentY][theCurrentX].isDownDoorOpen()
                    && theTracker[theCurrentY + 1][theCurrentX] != 1) {
                down = isSolvableHelper(theTracker, theCurrentY + 1, theCurrentX);
            } else {
                down = false;
            }
            
            if (myMaze[theCurrentY][theCurrentX].isLeftDoorOpen()
                    && theTracker[theCurrentY][theCurrentX - 1] != 1) {
                left = isSolvableHelper(theTracker, theCurrentY, theCurrentX - 1);
            } else {
                left = false;
            }
            
            if (myMaze[theCurrentY][theCurrentX].isRightDoorOpen()
                    && theTracker[theCurrentY][theCurrentX + 1] != 1) {
                right = isSolvableHelper(theTracker, theCurrentY, theCurrentX + 1);
            } else {
                right = false;
            }
            
            return (up || down || left || right);
        }
    }
    
    /**
     * Changes whether or not to force movement through locked doors.
     */
    public void toggleForcedMove() {
        myForcedMove = !myForcedMove;
    }
    
    /**
     * Returns whether movement is forced through locked doors.
     * @return Whether movement is forced through locked doors.
     */
    public boolean getForcedMove() {
        return myForcedMove;
    }
    
    /**
     * Returns the maze as a string displaying open and closed areas as well as
     * current position and finish position.
     */
    public String toString() {
        String internalLines = "";
        
        for (int i = 0; i < myMaze.length; i++) {
            
            internalLines += "X";
            
            for (int j = 0; j < myMaze[i].length; j++) {
                if (myMaze[i][j].isUpDoorOpen()) {
                    internalLines += "OX";
                } else {
                    internalLines += "XX";
                }
            }
            
            internalLines += "\n";
            
            for (int j = 0; j < myMaze[i].length; j++) {
                
                if (myMaze[i][j].isLeftDoorOpen()) {
                    internalLines += "O";
                } else {
                    internalLines += "X";
                }
                
                if (i == myCurrentY && j == myCurrentX) {
                    internalLines += "C";
                } else if (i == myFinishY && j == myFinishX) {
                    internalLines += "F";
                } else {
                    internalLines += "=";
                }
            }
            internalLines += "X\n";
        }
        
        for (int j = 0; j < myMaze[0].length; j++) {
            internalLines += "XX";
        }
        
        return internalLines + "X";
        
    }
    
}