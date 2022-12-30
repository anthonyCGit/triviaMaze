package model;

/**
 * A question with multiple choices to choose from.
 * @author ajc77
 * @version 3/11/2022
 */
public class MultipleChoice extends Question {
    
    /**
     * The UID used for serialization.
     */
    private static final long serialVersionUID = -500843174942381135L;
    /**
     * The array of possible answers.
     */
    private String[] myAnswers;
    
    /**
     * Creates the multiple choice question.
     * @param theQuestion The question to ask.
     * @param theAnswers The array of possible answers.
     * @param theCorrectAnswer The correct answer.
     */
    public MultipleChoice(final String theQuestion, final String[] theAnswers,
            final String theCorrectAnswer) {
        super(theQuestion, theCorrectAnswer, "Multiple Choice");
        myAnswers = theAnswers;
    }
    
    /**
     * Returns the array of possible answers.
     * @return The array of possible answers.
     */
    public String[] getAnswers() {
        return myAnswers.clone();
    }
}
