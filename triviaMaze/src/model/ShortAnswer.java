package model;

/**
 * A question with a single typed answer.
 * @author ajc77
 * @version 3/11/2022
 */
public class ShortAnswer extends Question {
    
    /**
     * The UID used for serialization.
     */
    private static final long serialVersionUID = -7211643859486300417L;

    /**
     * Creates the short answer question.
     * @param theQuestion The question to ask.
     * @param theCorrectAnswer The correct answer.
     */
    public ShortAnswer(final String theQuestion, final String theCorrectAnswer) {
        super(theQuestion, theCorrectAnswer, "Short Answer");
    }

}
