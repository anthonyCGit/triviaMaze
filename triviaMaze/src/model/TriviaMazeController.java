package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controls the trivia maze program and displays basic output.
 * @author ajc77
 * @version 3/11/2022
 */
public class TriviaMazeController {
    
    /**
     * The file to save and load the trivia maze from.
     */
    private static final String SAVE_FILE = "triviaSave.txt";
    
    /**
     * The option number of the move process.
     */
    private static final int CHOICE_MOVE = 1;
    
    /**
     * The option number of the save process.
     */
    private static final int CHOICE_SAVE = 2;
    
    /**
     * The option number of the load process.
     */
    private static final int CHOICE_LOAD = 3;
    
    /**
     * The option number of displaying about.
     */
    private static final int CHOICE_ABOUT = 4;
    
    /**
     * The option number of displaying the instructions.
     */
    private static final int CHOICE_INST = 5;
    
    /**
     * The option number of the cheats menu.
     */
    private static final int CHOICE_CHEATS = 6;
    
    /**
     * The option number of exiting the program.
     */
    private static final int CHOICE_EXIT = 0;
    
    /**
     * Starts the trivia maze program.
     * @param theArgs Command line arguments. (Not used in this program)
     */
    public static void main(String[] theArgs) {
            
        TriviaMaze trivia = new TriviaMaze();
        Scanner input = new Scanner(System.in);
        
        //trivia.toggleMazeForcedMove();
        //trivia.toggleAnswerDisplay();
        while(!trivia.getFinishState() && trivia.getSolvableState()) {
            System.out.println("----------------");
            System.out.println("D&D 5E Trivia Maze\n");
            System.out.println(trivia.getMazeBoard() + "\n");
            
            //Performs the users choice of action.
            int choice = getChoice(input);
            
            if (choice == CHOICE_MOVE) {
                move(input, trivia);
            } else if (choice == CHOICE_SAVE) {
                saveToFile(trivia);
                System.out.println("Successfully saved.");
            } else if (choice == CHOICE_LOAD) {
                if (new File(SAVE_FILE).exists()) {
                    trivia = loadFromFile();
                    System.out.println("Successfully loaded.");
                    System.out.println("Press ENTER to continue...");
                    input.nextLine();
                } else {
                    System.out.println("No save file exists.");
                    System.out.println("Press ENTER to continue...");
                    input.nextLine();
                }                
            } else if (choice == CHOICE_ABOUT) {
                System.out.println(trivia.getAboutText());
                System.out.println("Press ENTER to continue...");
                input.nextLine();
            } else if (choice == CHOICE_INST) {
                System.out.println(trivia.getInstructionsText());
                System.out.println("Press ENTER to continue...");
                input.nextLine();
            } else if (choice == CHOICE_CHEATS) {
                cheatMenu(input, trivia);
            } else if (choice == CHOICE_EXIT) {
                System.out.println("Press ENTER to close...");
                input.nextLine();
                input.close();
                System.exit(0);
            }
            
            System.out.println();
            
            //Retry or exit if a win/loss condition is met
            if (!trivia.getSolvableState()) {
                System.out.println("LOSE: You've locked yourself in!");
                if (retryPrompt(input) == 1) {
                    trivia = new TriviaMaze();
                }
            } else if (trivia.getFinishState()) {
                System.out.println("WIN: Congratulations! You've made it to the end!");
                if (retryPrompt(input) == 1) {
                    trivia = new TriviaMaze();
                }
            }
        }
        
        System.out.println("Press ENTER to close...");
        input.nextLine();
        input.close();
        
    }
    
    /**
     * Performs the move process for the trivia maze.
     * @param theInput The scanner for the program.
     * @param theTriviaMaze The trivia maze to perform on.
     */
    private static void move(Scanner theInput, TriviaMaze theTriviaMaze) {
        boolean loop = true;
        
        //Continues the move operation until the user chooses to exit
        while (loop) {
            System.out.println("\n----------------");
            System.out.println(theTriviaMaze.getMazeBoard() + "\n");
            String direction = chooseDirection(theInput, theTriviaMaze);
            
            if (direction.equals("exit")) {
                loop = false;
            } else {
                //Prompt the question
                System.out.println();
                System.out.println(theTriviaMaze.getCurrentQuestion().getQuestion());
                
                //Display the answer if specified
                if (theTriviaMaze.getAnswerDisplay()) {
                    System.out.println("Correct Answer: " + theTriviaMaze.getCorrectAnswer());
                }
                
                //Display multiple choice options and get multiple choice selection
                if (theTriviaMaze.getCurrentQuestion().getType().equals("Multiple Choice")) {
                    String[] answers = ((MultipleChoice) (theTriviaMaze.getCurrentQuestion())).getAnswers();
                    for (int i = 0; i < answers.length; i++) {
                        System.out.println("[" + (i + 1) + "]: " + answers[i]);
                    }
                    System.out.print("Answer (enter number of choice): ");
                    theTriviaMaze.setCurrentAnswer(answers[getAnswerChoice(theInput, answers.length) - 1]);
                } else { //Get short-answer answer
                    System.out.print("Answer: ");
                    theTriviaMaze.setCurrentAnswer(theInput.nextLine());
                }
                
                //Sets the correctness of the answer and attempts to move
                if (theTriviaMaze.updateCorrectness()) {
                    System.out.println("Correct!");
                } else {
                    System.out.println("Incorrect!");
                }
                theTriviaMaze.move(direction);
            }
            
            if (theTriviaMaze.getFinishState() || !theTriviaMaze.getSolvableState()) {
                loop = false;
            }
            
        }
        
    }
    
    /**
     * Gets the direction to move.
     * @param theInput The scanner for the program.
     * @param theTriviaMaze The trivia maze to perform on.
     * @return The direction to move.
     */
    private static String chooseDirection(Scanner theInput, TriviaMaze theTriviaMaze) {
        boolean loop = true;
        String direction = "";
        
        //Update the available directions
        theTriviaMaze.updateOpenDirections();
        ArrayList<String> availableDirections = new ArrayList<String>();
        if (theTriviaMaze.getUpOpen() || theTriviaMaze.getMazeForcedMove()) {
            availableDirections.add("up");
        }
        if (theTriviaMaze.getDownOpen() || theTriviaMaze.getMazeForcedMove()) {
            availableDirections.add("down");
        }
        if (theTriviaMaze.getRightOpen() || theTriviaMaze.getMazeForcedMove()) {
            availableDirections.add("right");
        }
        if (theTriviaMaze.getLeftOpen() || theTriviaMaze.getMazeForcedMove()) {
            availableDirections.add("left");
        }
        
        //Prompts until a direction is chosen
        while (loop) {
            System.out.println("Available directions: " + availableDirections.toString().toUpperCase());
            System.out.print("Choose a direction or type \"EXIT\": ");
            direction = theInput.nextLine();
            if (availableDirections.contains(direction.toLowerCase()) || direction.toLowerCase().equals("exit")) {
                loop = false;
            } else {
                System.out.println("\nPlease enter an available direction.");
            }
        }
        
        return direction;
    }
    
    /**
     * Saves the trivia maze to a file.
     * @param theTriviaMaze The trivia maze to save.
     */
    private static void saveToFile(TriviaMaze theTriviaMaze) {
        try { 
            
            // Saving of object in a file 
            FileOutputStream file = new FileOutputStream(SAVE_FILE); 
            ObjectOutputStream out = new ObjectOutputStream(file); 
            // Method for serialization of object 
            out.writeObject(theTriviaMaze); 
            out.close(); 
            file.close(); 
        } catch (IOException ex) { 
            System.out.println("IOException is caught while saving"); 
            ex.printStackTrace();
        } 
    }
    
    /**
     * Loads and returns a previously saved trivia maze.
     * @return The loaded trivia maze.
     */
    private static TriviaMaze loadFromFile() {
        TriviaMaze trivia = null;
        
        try { 
            
            // Reading the object from a file 
            FileInputStream file = new FileInputStream(SAVE_FILE); 
            ObjectInputStream in = new ObjectInputStream(file); 
  
            // Method for deserialization of object 
            trivia = (TriviaMaze)in.readObject(); 
  
            in.close(); 
            file.close(); 
        } catch (IOException ex) { 
            System.out.println("IOException is caught while loading"); 
        } catch (ClassNotFoundException ex) { 
            System.out.println("ClassNotFoundException" + 
                                " is caught while loading"); 
        }
        return trivia;
    }
    
    /**
     * A menu that enables and disables cheats for the trivia maze.
     * @param theInput The scanner for the program.
     * @param theTriviaMaze The trivia maze to perform on.
     */
    private static void cheatMenu(Scanner theInput, TriviaMaze theTriviaMaze) {
        boolean loop = true;
        int choice = 0;
        
        //Prompts the user to enable/disable cheats until they choose to exit
        while(loop) {
            System.out.println("\nCHEATS"
                    + "\n [1]: Toggle Force Move Through Blocked Doors: " + theTriviaMaze.getMazeForcedMove()
                    + "\n [2]: Toggle Display Correct Answer: " + theTriviaMaze.getAnswerDisplay()
                    + "\n [0]: Exit");
            System.out.print("Enter the number of your choice: ");
            String choiceText = theInput.nextLine();
            try {
                choice = Integer.parseInt(choiceText);
                
                if (choice == 0) {
                    loop = false;
                } else if (choice == 1) {
                    theTriviaMaze.toggleMazeForcedMove();
                    System.out.println("Force Move Through Blocked Doors is now set to: " + theTriviaMaze.getMazeForcedMove());
                } else if (choice == 2) {
                    theTriviaMaze.toggleAnswerDisplay();
                    System.out.println("Display Correct Answer is now set to: " + theTriviaMaze.getAnswerDisplay());
                } else {
                    System.out.println("Please choose one of the available options.\n");
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.\n");
            }
            
        }
        
    }
    
    /**
     * Gets the main menu choice of the user.
     * @param theInput The scanner for the program.
     * @return The menu choice of the user.
     */
    private static int getChoice(Scanner theInput) {
        boolean loop = true;
        int choice = 0;
        
        while(loop) {
            System.out.println("OPTIONS"
                    + "\n [1]: Move"
                    + "\n [2]: Save"
                    + "\n [3]: Load"
                    + "\n [4]: About"
                    + "\n [5]: Instructions"
                    + "\n [6]: Cheats"
                    + "\n [0]: Exit");
            System.out.print("Enter the number of your choice: ");
            String choiceText = theInput.nextLine();
            try {
                choice = Integer.parseInt(choiceText);
                
                if (0 <= choice && choice <= 6) {
                    loop = false;
                } else {
                    System.out.println("Please choose one of the available options.\n");
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.\n");
            }
        }
        
        return choice;
    }
    
    /**
     * Gets the users choice out of a specified number of options.
     * @param theInput The scanner for the program.
     * @param totalChoices The total number of options.
     * @return The user's choice of the options.
     */
    private static int getAnswerChoice(Scanner theInput, int totalChoices) {
        boolean loop = true;
        int choice = 0;
        
        while(loop) {
            String choiceText = theInput.nextLine();
            try {
                choice = Integer.parseInt(choiceText);
                
                if (1 <= choice && choice <= totalChoices) {
                    loop = false;
                } else {
                    System.out.println("Please choose one of the available options.\n");
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.\n");
            }
        }
        
        return choice;
    }
    
    /**
     * Prompts the user on whether to restart or exit.
     * @param theInput The total number of options.
     * @return The user's choice of option.
     */
    private static int retryPrompt(Scanner theInput) {
        int numberOfOptions = 2;
        System.out.println("Would you like to restart or exit?");
        System.out.println("[1]: Restart"
                + "\n[2]: Exit");
        System.out.print("Choice (enter number): ");
        return getAnswerChoice(theInput, numberOfOptions);
    }
    
}
