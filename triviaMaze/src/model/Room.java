package model;

import java.io.Serializable;

/**
 * A square room with a door on each side.
 * @author ajc77
 * @version 3/11/2022
 */
public class Room implements Serializable {
    
    /**
     * The UID used for serialization.
     */
    private static final long serialVersionUID = -7699356677539812561L;

    /**
     * Whether or not the left door is open.
     */
    private boolean myLeftDoorOpen;
    
    /**
     * Whether or not the right door is open.
     */
    private boolean myRightDoorOpen;
    
    /**
     * Whether or not the top door is open.
     */
    private boolean myUpDoorOpen;
    
    /**
     * Whether or not the bottom door is open.
     */
    private boolean myDownDoorOpen;
    
    /**
     * Creates a room with all doors open.
     */
    public Room () {
        myLeftDoorOpen = true;
        myRightDoorOpen = true;
        myUpDoorOpen = true;
        myDownDoorOpen = true;
    }
    
    /**
     * Returns whether the left door is open.
     * @return Whether the left door is open.
     */
    public boolean isLeftDoorOpen() {
        return myLeftDoorOpen;
    }
    
    /**
     * Returns whether the right door is open.
     * @return Whether the right door is open.
     */
    public boolean isRightDoorOpen() {
        return myRightDoorOpen;
    }
    
    /**
     * Returns whether the top door is open.
     * @return Whether the top door is open.
     */
    public boolean isUpDoorOpen() {
        return myUpDoorOpen;
    }
    
    /**
     * Returns whether the bottom door is open.
     * @return Whether the bottom door is open.
     */
    public boolean isDownDoorOpen() {
        return myDownDoorOpen;
    }
    
    /**
     * Locks the left door, closing it.
     */
    public void lockLeftDoor() {
        myLeftDoorOpen = false;
    }
    
    /**
     * Locks the right door, closing it.
     */
    public void lockRightDoor() {
        myRightDoorOpen = false;
    }
    
    /**
     * Locks the top door, closing it.
     */
    public void lockUpDoor() {
        myUpDoorOpen = false;
    }
    
    /**
     * Locks the bottom door, closing it.
     */
    public void lockDownDoor() {
        myDownDoorOpen = false;
    }
    
    /**
     * Returns a string containing the state of the room's doors.
     */
    public String toString() {
        return ("Up: " + this.isUpDoorOpen() + ", "
                + "Down: " + this.isDownDoorOpen() + ", "
                + "Left: " + this.isLeftDoorOpen() + ", "
                + "Right: " + this.isRightDoorOpen());
    }
    
}
