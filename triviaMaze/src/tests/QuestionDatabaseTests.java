package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.Question;
import model.QuestionDatabase;

class QuestionDatabaseTests {
    
    final String file = "questions.txt";
    
    @Test
    void testGetQuestionList() throws FileNotFoundException {
        QuestionDatabase qd = new QuestionDatabase(file);
        assertTrue(qd.getQuestionList() != null);
    }
    
    @Test
    void testGetQuestionListQuestion() throws FileNotFoundException {
        QuestionDatabase qd = new QuestionDatabase(file);
        List<Question> l = qd.getQuestionList();
        assertTrue(l.get(0) != null);
    }
    
    @Test
    void testFileNotFound() {
        try {
            QuestionDatabase qd = new QuestionDatabase("abc123DOESNTEXIST.txt");
            qd.getQuestionList();
            fail("Did not throw an exception when a file didn't exist");
        } catch (FileNotFoundException e) {
            assertTrue(true);
        }
    }
    
}
