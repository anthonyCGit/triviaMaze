package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.TriviaMaze;

class TriviaMazeTests {

    @Test
    void testUpOpenDefault() {
        TriviaMaze tm = new TriviaMaze();
        assertFalse(tm.getUpOpen());
    }
    
    @Test
    void testDownOpenDefault() {
        TriviaMaze tm = new TriviaMaze();
        assertTrue(tm.getDownOpen());
    }
    
    @Test
    void testLeftOpenDefault() {
        TriviaMaze tm = new TriviaMaze();
        assertFalse(tm.getLeftOpen());
    }
    
    @Test
    void testRightOpenDefault() {
        TriviaMaze tm = new TriviaMaze();
        assertTrue(tm.getRightOpen());
    }
    
    @Test
    void testFinishStateDefault() {
        TriviaMaze tm = new TriviaMaze();
        assertFalse(tm.getFinishState());
    }
    
    @Test
    void testSolvableStateDefault() {
        TriviaMaze tm = new TriviaMaze();
        assertTrue(tm.getSolvableState());
    }
    
    @Test
    void testMazeForcedMoveDefault() {
        TriviaMaze tm = new TriviaMaze();
        assertFalse(tm.getMazeForcedMove());
    }
    
    @Test
    void testMazeForcedMoveToggle() {
        TriviaMaze tm = new TriviaMaze();
        tm.toggleMazeForcedMove();
        assertTrue(tm.getMazeForcedMove());
    }
    
    @Test
    void testMazeForcedMoveToggle2() {
        TriviaMaze tm = new TriviaMaze();
        tm.toggleMazeForcedMove();
        tm.toggleMazeForcedMove();
        assertFalse(tm.getMazeForcedMove());
    }
    
    @Test
    void testAnswerDisplayDefault() {
        TriviaMaze tm = new TriviaMaze();
        assertFalse(tm.getAnswerDisplay());
    }
    
    @Test
    void testAnswerDisplayToggle() {
        TriviaMaze tm = new TriviaMaze();
        tm.toggleAnswerDisplay();
        assertTrue(tm.getAnswerDisplay());
    }
    
    @Test
    void testAnswerDisplayToggle2() {
        TriviaMaze tm = new TriviaMaze();
        tm.toggleAnswerDisplay();
        tm.toggleAnswerDisplay();
        assertFalse(tm.getAnswerDisplay());
    }
    
    @Test
    void testAboutText() {
        TriviaMaze tm = new TriviaMaze();
        String test = "This trivia maze was developed by Anthony Carrillo."
                + "\nVersion: 1.0 Release";
        assertTrue(test.equals(tm.getAboutText()));
    }
    
    @Test
    void testInstructionsText() {
        TriviaMaze tm = new TriviaMaze();
        String test = "The goal of the trivia maze is to make it from your current position to the finish position."
                + "\nIn order to do so, you must move from room to room."
                + "\nWhile moving to a room, you will be prompted a question."
                + "\nA correct answer allows you to move while an incorrect one blocks the path you attempted to travel to."
                + "\nYou fail if there is no possible way to make it to the finish."
                + "\nEach question is in reference to Dungeons and Dragons 5th Edition."
                + "\nHave fun and good luck!";
        assertTrue(test.equals(tm.getInstructionsText()));
    }
    
    @Test
    void testQuestion() {
        TriviaMaze tm = new TriviaMaze();
        assertTrue(tm.getCorrectAnswer().equals(tm.getCurrentQuestion().getCorrectAnswer()));
    }
    
    @Test
    void testMazeBoard() {
        TriviaMaze tm = new TriviaMaze();
        String test = "XXXXXXXXX\n"
                + "XCO=O=O=X\n"
                + "XOXOXOXOX\n"
                + "X=O=O=O=X\n"
                + "XOXOXOXOX\n"
                + "X=O=O=O=X\n"
                + "XOXOXOXOX\n"
                + "X=O=O=OFX\n"
                + "XXXXXXXXX";
        assertTrue(test.equals(tm.getMazeBoard()));
    }
    
    @Test
    void testMoveRightCorrect() {
        TriviaMaze tm = new TriviaMaze();
        tm.setCurrentAnswer(tm.getCorrectAnswer());
        tm.move("right");
        assertTrue(tm.getLeftOpen());
    }
    
    @Test
    void testMoveRightIncorrect() {
        TriviaMaze tm = new TriviaMaze();
        tm.setCurrentAnswer("INCORRECTANSWER123");
        tm.move("right");
        assertFalse(tm.getLeftOpen());
    }
    
    @Test
    void testMoveDownCorrect() {
        TriviaMaze tm = new TriviaMaze();
        tm.setCurrentAnswer(tm.getCorrectAnswer());
        tm.move("down");
        assertTrue(tm.getUpOpen());
    }
    
    @Test
    void testMoveDownIncorrect() {
        TriviaMaze tm = new TriviaMaze();
        tm.setCurrentAnswer("INCORRECTANSWER123");
        tm.move("down");
        assertFalse(tm.getUpOpen());
    }
    
    @Test
    void testMoveUpCorrect() {
        TriviaMaze tm = new TriviaMaze();
        tm.setCurrentAnswer(tm.getCorrectAnswer());
        tm.move("down");
        tm.setCurrentAnswer(tm.getCorrectAnswer());
        tm.move("up");
        assertFalse(tm.getUpOpen());
    }
    
    @Test
    void testMoveUpIncorrect() {
        TriviaMaze tm = new TriviaMaze();
        tm.setCurrentAnswer(tm.getCorrectAnswer());
        tm.move("down");
        tm.setCurrentAnswer("INCORRECTANSWER123");
        tm.move("up");
        assertFalse(tm.getUpOpen());
    }
    
    @Test
    void testMoveLeftCorrect() {
        TriviaMaze tm = new TriviaMaze();
        tm.setCurrentAnswer(tm.getCorrectAnswer());
        tm.move("right");
        tm.setCurrentAnswer(tm.getCorrectAnswer());
        tm.move("left");
        assertFalse(tm.getLeftOpen());
    }
    
    @Test
    void testMoveLeftIncorrect() {
        TriviaMaze tm = new TriviaMaze();
        tm.setCurrentAnswer(tm.getCorrectAnswer());
        tm.move("right");
        tm.setCurrentAnswer("INCORRECTANSWER123");
        tm.move("left");
        assertFalse(tm.getLeftOpen());
    }
    
    @Test
    void testForcedMovement() {
        TriviaMaze tm = new TriviaMaze();
        tm.toggleMazeForcedMove();
        tm.setCurrentAnswer("INCORRECTANSWER123");
        tm.move("right");
        assertTrue(tm.getRightOpen());
    }
    
    @Test
    void testQuestionCycle() {
        TriviaMaze tm = new TriviaMaze();
        for (int i = 0; i < 100; i++) {
            tm.setCurrentAnswer("INCORRECTANSWER123");
            tm.move("left");
        }
        assertTrue(tm.getCorrectAnswer().equals(tm.getCurrentQuestion().getCorrectAnswer()));
    }
    
}
