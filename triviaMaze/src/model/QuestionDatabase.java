package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.sqlite.SQLiteDataSource;

/**
 * 
 * @author professor
 * @author ajc77
 * @author teammate1
 * 
 * Simple class to demonstrate SQLite connectivity
 * 1) create connection
 * 2) add a table
 * 3) wipe the table so that previous entries are not duplicated
 * 4) add entries to the table
 * 5) query the table for its contents
 * 6) display the results
 * 
 * NOTE: any interactions with a database should utilize a try/catch
 * since things can go wrong
 * 
 * @see <a href="https://shanemcd.org/2020/01/24/how-to-set-up-sqlite-with-jdbc-in-eclipse-on-windows/">
https://shanemcd.org/2020/01/24/how-to-set-up-sqlite-with-jdbc-in-eclipse-on-windows/</a>
 *
 */
public class QuestionDatabase implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 7423022287362250555L;

    private SQLiteDataSource myDataSource;
    
    private List<Question> myQuestionList;
    
    public QuestionDatabase(String thefileName) throws FileNotFoundException {
        //establish connection (creates db file if it does not exist :-)
        try {
            myDataSource = new SQLiteDataSource();
            myDataSource.setUrl("jdbc:sqlite:questions.db");
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(0);
        }

        // wipe database before insertion
        String query = "DELETE FROM questions";
        try (Connection conn = myDataSource.getConnection();
                Statement stmt = conn.createStatement();) {
        	stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        
        //now create a table
        query = "CREATE TABLE IF NOT EXISTS questions ( " +
                "TYPE TEXT NOT NULL, " +
                "QUESTION TEXT NOT NULL, " +
                "CORRECT TEXT NOT NULL, " +
                "ANSWER1 TEXT NOT NULL, " +
                "ANSWER2 TEXT, " +
                "ANSWER3 TEXT, " +
                "ANSWER4 TEXT )";
        try ( Connection conn = myDataSource.getConnection();
                Statement stmt = conn.createStatement(); ) {
              stmt.executeUpdate(query);
          } catch ( SQLException e ) {
              e.printStackTrace();
              System.exit( 0 );
          }
        
        // fill myDataSource from txt file
        try (Connection conn = myDataSource.getConnection();
              Statement stmt = conn.createStatement();) {
            Scanner input = new Scanner(new File(thefileName));
            while (input.hasNextLine()) {
                query = input.nextLine();
                stmt.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        
        //Prepare the list to add questions to
        myQuestionList = new ArrayList<Question>();
        
        //now query the database table for all its contents and display the results
        query = "SELECT * FROM questions ORDER BY RANDOM()";

        try (Connection conn = myDataSource.getConnection();
              Statement stmt = conn.createStatement();) {
            
            ResultSet rs = stmt.executeQuery(query);
            
            //walk through each 'row' of results, grab data by column/field name
            // and print it

            while (rs.next()) {
                String type = rs.getString("TYPE");
                String question = rs.getString("QUESTION");
                String correct = rs.getString("CORRECT");
                String answer1 = rs.getString("ANSWER1");
                String answer2 = rs.getString("ANSWER2");
                String answer3 = rs.getString("ANSWER3");
                String answer4 = rs.getString("ANSWER4");
                
                if (type.equals("MC")) {
                    ArrayList<String> answers = new ArrayList<String>();
                    if (!answer1.equals("NULL")) {
                        answers.add(answer1);
                    }
                    if (!answer2.equals("NULL")) {
                        answers.add(answer2);
                    }
                    if (!answer3.equals("NULL")) {
                        answers.add(answer3);
                    }
                    if (!answer4.equals("NULL")) {
                        answers.add(answer4);
                    }
                    myQuestionList.add(new MultipleChoice(question, answers.toArray(String[]::new), correct));
                } else {
                    myQuestionList.add(new ShortAnswer(question, correct));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    public List<Question> getQuestionList() {
        return myQuestionList;
    }
    
}