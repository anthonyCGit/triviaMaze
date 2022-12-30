package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Maze;

class MazeTests {
    
    final int size = 2;
    
    @Test
    void testUpOpen() {
        Maze maze = new Maze(size, size);
        assertEquals(false, maze.isUpOpen());
    }
    
    @Test
    void testDownOpen() {
        Maze maze = new Maze(size, size);
        assertEquals(true, maze.isDownOpen());
    }
    
    @Test
    void testLeftOpen() {
        Maze maze = new Maze(size, size);
        assertEquals(false, maze.isLeftOpen());
    }
    
    @Test
    void testRightOpen() {
        Maze maze = new Maze(size, size);
        assertEquals(true, maze.isRightOpen());
    }
    
    @Test
    void testConstructorIllegalY() {
        try {
            Maze maze = new Maze(0, size);
            maze.moveRight();
            fail("Didn't throw IllegalArgumentException for Illegal y size");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
        
    }
    
    @Test
    void testConstructorIllegalX() {
        try {
            Maze maze = new Maze(size, 0);
            maze.moveRight();
            fail("Didn't throw IllegalArgumentException for Illegal x size");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }
    
    @Test
    void testAtFinish() {
        Maze maze = new Maze(size, size);
        assertEquals(false, maze.isAtFinish());
    }
    
    @Test
    void testMoveDown() {
        Maze maze = new Maze(size, size);
        maze.moveDown();
        assertEquals(false, maze.isDownOpen());
    }
    
    @Test
    void testMoveRight() {
        Maze maze = new Maze(size, size);
        maze.moveRight();
        assertEquals(false, maze.isRightOpen());
    }
    
    @Test
    void testMoveLeft() {
        Maze maze = new Maze(size, size);
        maze.moveRight();
        maze.moveLeft();
        assertEquals(false, maze.isLeftOpen());
    }
    
    @Test
    void testMoveUp() {
        Maze maze = new Maze(size, size);
        maze.moveDown();
        maze.moveUp();
        assertEquals(false, maze.isUpOpen());
    }
    
    @Test
    void testMoveFinish() {
        Maze maze = new Maze(size, size);
        maze.moveRight();
        maze.moveDown();
        assertEquals(true, maze.isAtFinish());
    }
    
    @Test
    void testLockUp() {
        Maze maze = new Maze(size, size);
        maze.moveDown();
        maze.lockUp();
        assertEquals(false, maze.isUpOpen());
    }
    
    @Test
    void testLockDown() {
        Maze maze = new Maze(size, size);
        maze.lockDown();
        assertEquals(false, maze.isDownOpen());
    }
    
    @Test
    void testLockLeft() {
        Maze maze = new Maze(size, size);
        maze.moveRight();
        maze.lockLeft();
        assertEquals(false, maze.isLeftOpen());
    }
    
    @Test
    void testLockRight() {
        Maze maze = new Maze(size, size);
        maze.lockRight();
        assertEquals(false, maze.isRightOpen());
    }
    
    @Test
    void testSolvable() {
        Maze maze = new Maze(size, size);
        assertEquals(true, maze.isSolvable());
    }
    
    @Test
    void testUnsolvable() {
        Maze maze = new Maze(size, size);
        maze.lockRight();
        maze.lockDown();
        assertEquals(false, maze.isSolvable());
    }
    
    @Test
    void testForcedMoveDefault() {
        Maze maze = new Maze(size, size);
        assertEquals(false, maze.getForcedMove());
    }
    
    @Test
    void testForcedMoveToggle() {
        Maze maze = new Maze(size, size);
        maze.toggleForcedMove();
        assertEquals(true, maze.getForcedMove());
    }
    
    @Test
    void testForcedMoveToggle2() {
        Maze maze = new Maze(size, size);
        maze.toggleForcedMove();
        maze.toggleForcedMove();
        assertEquals(false, maze.getForcedMove());
    }
    
    @Test
    void testToString() {
        Maze maze = new Maze(size, size);
        String test = "XXXXX\n"
                + "XCO=X\n"
                + "XOXOX\n"
                + "X=OFX\n"
                + "XXXXX";
        assertTrue(test.equals(maze.toString()));
    }
    
}
