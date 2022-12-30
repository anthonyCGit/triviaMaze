package model;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A trivia maze that can be traversed and has questions.
 * @author ajc77
 * @version 3/11/2022
 */
public class TriviaMaze implements Serializable {
    
    /**
     * The UID used for serialization.
     */
    private static final long serialVersionUID = 4668845708175717034L;
    
    /**
     * The side length of the trivia maze.
     */
    private static final int MAZE_SIZE = 4;
    
    /**
     * The file name of the questions document.
     */
    private static final String FILE_NAME = "questions.txt";
    
    /**
     * The maze of the trivia maze.
     */
    private Maze myMaze;
    
    /**
     * The currently selected question.
     */
    private Question myCurrentQuestion;
    
    /**
     * The list of questions that can be asked.
     */
    private List<Question> myQuestionList;
    
    /**
     * An iterator that goes over each question in the list.
     */
    private transient Iterator<Question> myQuestionIterator = null;
    
    /**
     * The user's current answer to the question.
     */
    private String myCurrentAnswer;
    
    /**
     * Whether or not the user made it to the end of the maze.
     */
    private boolean myFinishState;
    
    /**
     * Whether or not the maze is currently solvable.
     */
    private boolean mySolvableState;
    
    /**
     * Whether the up direction is open.
     */
    private boolean myUpOpen;
    
    /**
     * Whether the down direction is open.
     */
    private boolean myDownOpen;
    
    /**
     * Whether the left direction is open.
     */
    private boolean myLeftOpen;
    
    /**
     * Whether the right direction is open.
     */
    private boolean myRightOpen;
    
    /**
     * Whether the user's answer to the question is correct.
     */
    private boolean myCorrectness;
    
    /**
     * Whether or not to display the answer to the current question.
     */
    private boolean myAnswerDisplay;
    
    /**
     * Creates a trivia maze that can be traversed and has questions.
     */
    public TriviaMaze() {
        try {
            myMaze = new Maze(MAZE_SIZE, MAZE_SIZE);   
            myQuestionList = new QuestionDatabase(FILE_NAME).getQuestionList();
            Collections.shuffle(myQuestionList);
            myQuestionIterator = myQuestionList.iterator();
            myCurrentQuestion = myQuestionIterator.next();
            myFinishState = myMaze.isAtFinish();
            mySolvableState = myMaze.isSolvable();
            updateOpenDirections();
            myCorrectness = false;
            myAnswerDisplay = false;
        } catch (FileNotFoundException e) {
            System.out.println("The questions.txt file was not found.");
            e.printStackTrace();
            System.exit(0);
        }
       
    }
    
    /**
     * Updates whether the current answer is correct and returns the status.
     * @return Whether the current answer is correct.
     */
    protected boolean updateCorrectness() {
        myCorrectness = myCurrentAnswer.toLowerCase().equals(myCurrentQuestion.getCorrectAnswer().toLowerCase());
        return myCorrectness;
    }
    
    /**
     * Moves the user in a specified direction if the answer is correct and the direction is open.
     * @param theDirection The direction to move.
     */
    public void move(String theDirection) {
        
        boolean correct = updateCorrectness();
        
        if (theDirection.toLowerCase().equals("up") && myUpOpen) {
            choseUp(correct);
        } else if (theDirection.toLowerCase().equals("down") && myDownOpen) {
            choseDown(correct);
        } else if (theDirection.toLowerCase().equals("left") && myLeftOpen) {
            choseLeft(correct);
        } else if (theDirection.toLowerCase().equals("right") && myRightOpen) {
            choseRight(correct);
        }
        
        myFinishState = myMaze.isAtFinish();
        mySolvableState = myMaze.isSolvable();
        updateOpenDirections();
        nextQuestion();
    }
    
    /**
     * Updates whether each direction is open.
     * Forces movement through locked doors if set.
     */
    protected void updateOpenDirections() {
        myUpOpen = myMaze.isUpOpen() || myMaze.getForcedMove();
        myDownOpen = myMaze.isDownOpen() || myMaze.getForcedMove();
        myLeftOpen = myMaze.isLeftOpen() || myMaze.getForcedMove();
        myRightOpen = myMaze.isRightOpen() || myMaze.getForcedMove();
    }
    
    /**
     * Sets the current answer of the user for the trivia maze.
     * @param theAnswer The answer to set for the trivia maze.
     */
    public void setCurrentAnswer(String theAnswer) {
        myCurrentAnswer = theAnswer;
    }
    
    /**
     * Returns the correct answer to the current question.
     * @return The correct answer to the current question.
     */
    public String getCorrectAnswer() {
        return myCurrentQuestion.getCorrectAnswer();
    }
    
    /**
     * Returns the string representations of the maze.
     * @return The string representations of the maze.
     */
    public String getMazeBoard() {
        return myMaze.toString();
    }
    
    /**
     * Whether or not the user is at the finish.
     * @return Whether or not the user is at the finish.
     */
    public boolean getFinishState() {
        return myFinishState;
    }
    
    /**
     * Whether or not the maze is currently solvable.
     * @return Whether or not the maze is currently solvable.
     */
    public boolean getSolvableState() {
        return mySolvableState;
    }
    
    /**
     * Returns the current question to prompt.
     * @return The current question to prompt.
     */
    public Question getCurrentQuestion() {
        return myCurrentQuestion;
    }
    
    /**
     * Returns whether the up direction is open.
     * @return Whether the up direction is open.
     */
    public boolean getUpOpen() {
        return myUpOpen;
    }
    
    /**
     * Returns whether the down direction is open.
     * @return Whether the down direction is open.
     */
    public boolean getDownOpen() {
        return myDownOpen;
    }
    
    /**
     * Returns whether the right direction is open.
     * @return Whether the right direction is open.
     */
    public boolean getRightOpen() {
        return myRightOpen;
    }
    
    /**
     * Returns whether the left direction is open.
     * @return Whether the left direction is open.
     */
    public boolean getLeftOpen() {
        return myLeftOpen;
    }
    
    /**
     * Changes whether or not to force movement through locked doors.
     */
    public void toggleMazeForcedMove() {
        myMaze.toggleForcedMove();
    }
    
    /**
     * Returns whether or not movement is forced through locked doors.
     * @return Whether or not movement is forced through locked doors.
     */
    public boolean getMazeForcedMove() {
        return myMaze.getForcedMove();
    }
    
    /**
     * Changes whether or not to display the correct answer.
     */
    public void toggleAnswerDisplay() {
        myAnswerDisplay = !myAnswerDisplay;
    }
    
    /**
     * Returns whether or not the correct answer should be displayed.
     * @return Whether or not the correct answer should be displayed.
     */
    public boolean getAnswerDisplay() {
        return myAnswerDisplay;
    }
    
    /**
     * Returns the about information for the trivia maze program as a string.
     * @return The about information for the trivia maze program as a string.
     */
    public String getAboutText() {
        String about = "This trivia maze was developed by Anthony Carrillo."
                + "\nVersion: 1.0 Release";
        return about;
    }
    
    /**
     * Returns the instructions for the trivia maze program as a string.
     * @return The instructions for the trivia maze program as a string.
     */
    public String getInstructionsText() {
        String inst = "The goal of the trivia maze is to make it from your current position to the finish position."
                + "\nIn order to do so, you must move from room to room."
                + "\nWhile moving to a room, you will be prompted a question."
                + "\nA correct answer allows you to move while an incorrect one blocks the path you attempted to travel to."
                + "\nYou fail if there is no possible way to make it to the finish."
                + "\nEach question is in reference to Dungeons and Dragons 5th Edition."
                + "\nHave fun and good luck!";
        return inst;
    }
    
    /**
     * Moves to or locks the up direction based on whether the answer was correct.
     * @param theCorrect Whether or not the answer was correct.
     */
    private void choseUp(boolean theCorrect) {
        if (theCorrect) {
            myMaze.moveUp();
        } else {
            myMaze.lockUp();
        }
    }
    
    /**
     * Moves to or locks the down direction based on whether the answer was correct.
     * @param theCorrect Whether or not the answer was correct.
     */
    private void choseDown(boolean theCorrect) {
        if (theCorrect) {
            myMaze.moveDown();
        } else {
            myMaze.lockDown();
        }
    }
    
    /**
     * Moves to or locks the left direction based on whether the answer was correct.
     * @param theCorrect Whether or not the answer was correct.
     */
    private void choseLeft(boolean theCorrect) {
        if (theCorrect) {
            myMaze.moveLeft();
        } else {
            myMaze.lockLeft();
        }
    }
    
    /**
     * Moves to or locks the right direction based on whether the answer was correct.
     * @param theCorrect Whether or not the answer was correct.
     */
    private void choseRight(boolean theCorrect) {
        if (theCorrect) {
            myMaze.moveRight();
        } else {
            myMaze.lockRight();
        }
    }
    
    /**
     * Gets the next question, reseting the iterator if needed.
     */
    private void nextQuestion() {
        if (myQuestionIterator == null || !myQuestionIterator.hasNext()) {
            Collections.shuffle(myQuestionList);
            myQuestionIterator = myQuestionList.iterator();
        }
        myCurrentQuestion = myQuestionIterator.next();
    }
    
}
