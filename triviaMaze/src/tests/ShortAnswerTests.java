package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.ShortAnswer;

class ShortAnswerTests {

    final String question = "1+1=?";
    
    final String correct = "2";
    
    @Test
    void testGetQuestion() {
        ShortAnswer sa = new ShortAnswer(question, correct);
        assertTrue(question.equals(sa.getQuestion()), "Failed when checking question string.");
    }
    
    @Test
    void testGetCorrectAnswer() {
        ShortAnswer sa = new ShortAnswer(question, correct);
        assertTrue(correct.equals(sa.getCorrectAnswer()), "Failed when checking correct answer string.");
    }
    
    @Test
    void testGetType() {
        ShortAnswer sa = new ShortAnswer(question, correct);
        assertTrue("Short Answer".equals(sa.getType()), "Failed when checking type string.");
    }

}
