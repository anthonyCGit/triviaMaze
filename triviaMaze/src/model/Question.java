package model;

import java.io.Serializable;

/**
 * Abstract question class containing requirements for each question type.
 * @author ajc77
 * @version 3/11/2022
 */
public abstract class Question implements Serializable {
    
    /**
     * The UID used for serialization.
     */
    private static final long serialVersionUID = 4282171328110781834L;

    /**
     * The question to ask.
     */
    private String myQuestion;
    
    /**
     * The correct answer.
     */
    private String myCorrectAnswer;
    
    /**
     * The type of question.
     */
    private String myType;
    
    /**
     * Creates a question object from a given question, answer, and type.
     * @param theQuestion The question to ask.
     * @param theCorrectAnswer The correct answer to the question.
     * @param theType The type of question being asked.
     */
    Question(final String theQuestion, final String theCorrectAnswer, final String theType) {
        myQuestion = theQuestion;
        myCorrectAnswer = theCorrectAnswer;
        myType = theType;
    }
    
    /**
     * Returns the question to ask the user.
     * @return The question to ask the user.
     */
    public String getQuestion() {
        return myQuestion;
    }
    
    /**
     * Returns the correct answer.
     * @return The correct answer.
     */
    public String getCorrectAnswer() {
        return myCorrectAnswer;
    }
    
    /**
     * Returns the type of question.
     * @return The type of question.
     */
    public String getType() {
        return myType;
    }
    
}
