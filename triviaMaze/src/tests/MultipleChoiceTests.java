package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.MultipleChoice;

class MultipleChoiceTests {
    
    final String question = "1+1=?";
    
    final String[] answers = new String[] {"1", "2", "3"};
    
    final String correct = "2";
    
    @Test
    void testGetQuestion() {
        MultipleChoice mc = new MultipleChoice(question, answers, correct);
        assertTrue(question.equals(mc.getQuestion()), "Failed when checking question string.");
    }
    
    @Test
    void testGetCorrectAnswer() {
        MultipleChoice mc = new MultipleChoice(question, answers, correct);
        assertTrue(correct.equals(mc.getCorrectAnswer()), "Failed when checking correct answer string.");
    }
    
    @Test
    void testGetType() {
        MultipleChoice mc = new MultipleChoice(question, answers, correct);
        assertTrue("Multiple Choice".equals(mc.getType()), "Failed when checking type string.");
    }
    
    @Test
    void testGetAnswer() {
        MultipleChoice mc = new MultipleChoice(question, answers, correct);
        String[] mcAnswers = mc.getAnswers();
        for (int i = 0; i < answers.length; i++) {
            assertTrue(answers[i].equals(mcAnswers[i]), "Failed when answer string equality.");
        }
    }
    
}
