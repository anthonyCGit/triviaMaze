package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Room;

class RoomTests {

    @Test
    void testConstructorUp() {
        Room room = new Room();
        assertEquals(true, room.isUpDoorOpen(), "Failed when checking up openness after constructing.");
    }
    
    @Test
    void testConstructorDown() {
        Room room = new Room();
        assertEquals(true, room.isDownDoorOpen(), "Failed when checking down openness after constructing.");
    }
    
    @Test
    void testConstructorLeft() {
        Room room = new Room();
        assertEquals(true, room.isLeftDoorOpen(), "Failed when checking left openness after constructing.");
    }
    
    @Test
    void testConstructorRight() {
        Room room = new Room();
        assertEquals(true, room.isRightDoorOpen(), "Failed when checking right openness after constructing.");
    }
    
    @Test
    void testLockUp() {
        Room room = new Room();
        room.lockUpDoor();
        assertEquals(false, room.isUpDoorOpen(), "Failed when checking up openness after locking.");
    }
    
    @Test
    void testLockDown() {
        Room room = new Room();
        room.lockDownDoor();
        assertEquals(false, room.isDownDoorOpen(), "Failed when checking down openness after locking.");
    }
    
    @Test
    void testLockLeft() {
        Room room = new Room();
        room.lockLeftDoor();
        assertEquals(false, room.isLeftDoorOpen(), "Failed when checking left openness after locking.");
    }
    
    @Test
    void testLockRight() {
        Room room = new Room();
        room.lockRightDoor();
        assertEquals(false, room.isRightDoorOpen(), "Failed when checking right openness after locking.");
    }
    
    @Test
    void testToString() {
        Room room = new Room();
        room.lockUpDoor();
        room.lockLeftDoor();
        String test = "Up: false, Down: true, Left: false, Right: true";
        assertTrue(test.equals(room.toString()), "Failed when checking toString output.");
    }
    
}
