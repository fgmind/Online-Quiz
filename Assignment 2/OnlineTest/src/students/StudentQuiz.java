package students;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class StudentQuiz {
	
	private String dbUrl = "jdbc:mysql://localhost:3306/onlineQuiz?useSSL=false";
	private String user = "student";
	private String password = "";
	
	private boolean actualStudent = false;
	private Scanner answer = new Scanner(System.in);
	private int studentID = 0;

	public static void main(String[] args) {


		StudentQuiz studentQuiz = new StudentQuiz();
		
		studentQuiz.validateUser();
		
		studentQuiz.takeQuiz();

	}
	
	private void validateUser(){
		
		System.out.println("Please enter your Student ID: ");
		studentID = answer.nextInt();

		try {
//			1. Establishing Connection
			Connection myConn = DriverManager.getConnection(dbUrl, user, password);
//			2. Create a statement
			Statement myStmt = myConn.createStatement();
//			3. Execute the query
			ResultSet myRs = myStmt.executeQuery("select * from students");
//			4. Process the resultSet object
			while(myRs.next()){
				if (myRs.getInt("id") == studentID){
					System.out.println("Welcome " + myRs.getString("firstName"));
					System.out.println("The test will start shortly");
					actualStudent = true;					
				}
			}
			if (actualStudent == false){
				System.out.println("Sorry an error occured! Try logging in again");
				System.exit(-1);
			}

//			5. Close statement & connection
			myStmt.close();
			myConn.close();

		}
		catch (Exception e) {
			e.getStackTrace();
		}
	}

	void takeQuiz(){
		
		int score = 0;
		int quesCount = 0;

		try {
//			1. Establishing Connection
			Connection myConn = DriverManager.getConnection(dbUrl, user, password);
//			2. Create a statement
			Statement myStmt = myConn.createStatement();
//			3. Execute the query
			ResultSet myRs = myStmt.executeQuery("select * from quizQuestions");
//			4. Process the resultSet object
			while(myRs.next()){
				System.out.println("\n" + myRs.getString("id") + ". " + myRs.getString("question"));
				System.out.println("\t1. " + myRs.getString("optionA"));
				System.out.println("\t2. " + myRs.getString("optionB"));
				System.out.println("\t3. " + myRs.getString("optionC"));
				System.out.println("\t4. " + myRs.getString("optionD"));
				
				System.out.println("Your answer: ");
				int tempScore = answer.nextInt();
				
				if (tempScore == myRs.getInt("answer")){
					score++;
				}
				
				quesCount++;
			}
			System.out.println("\n\n\n\n" + "Your score is:" + score + " out of " + quesCount);

			String sqlAddResult = "UPDATE students SET results = " + (double)score/quesCount*100 + " WHERE id =" + studentID + ";";
			myStmt.executeUpdate(sqlAddResult);
			
//			5. Close statement & connection
			myStmt.close();
			myConn.close();
			
		}
		catch (Exception e) {
			e.getStackTrace();
		}
		
	}
}
