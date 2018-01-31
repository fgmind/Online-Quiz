package teacher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class OnlineQuiz {

	private String dbUrl = "jdbc:mysql://localhost:3306/onlineQuiz?useSSL=false";
	private String user = "root";
	private String password = "root";
	
	private Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		
		OnlineQuiz oq = new OnlineQuiz();
		
		boolean exit = false;
		
		while (exit == false){
			System.out.println("\n");
			System.out.println("1. Take quiz (check)");
			System.out.println("2. Add question");
			System.out.println("3. Add student");
			System.out.println("4. View Results");
			System.out.println("0. Exit System");
			
			System.out.println("\nEnter your choice");
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			
			switch(choice){
			
			case 1:
				oq.takeQuiz();
				break;
				
			case 2:
				oq.addQuestion();
				break;
				
			case 3:
				oq.addStudent();
				break;
				
			case 4:
				oq.viewResults();
				break;
			
			case 0:
				exit = true;
				break;
				
			default:
				System.out.println("Error! Choice should be a number between 1 and 4! (or 0 to exit)");
				exit = false;
			}
		}
		
		System.out.println("\nGoodbye!");
	}

	
	
	private void takeQuiz(){
		
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
				int tempScore = scanner.nextInt();
				
				if (tempScore == myRs.getInt("answer")){
					score++;
				}
				
				quesCount++;
			}
			System.out.println("\n\n\n\n" + "Your score is:" + score + " out of " + quesCount);
			
//			5. Close statement & connection
			myStmt.close();
			myConn.close();

			
		}
		catch (Exception e) {
			e.getStackTrace();
		}
		
	}
	
	
	private void addQuestion(){

		try {
//			1. Establishing Connection
			Connection myConn = DriverManager.getConnection(dbUrl, user, password);
//			2. Create a statement
			Statement myStmt = myConn.createStatement();

//			Creating Question and answers
			System.out.println("Enter Question:");
			String question = scanner.nextLine();
			
			System.out.println("option 1: ");
			String optionA = scanner.nextLine();
			
			System.out.println("option 2: ");
			String optionB = scanner.nextLine();
			
			System.out.println("option 3: ");
			String optionC = scanner.nextLine();
			
			System.out.println("option 4: ");
			String optionD = scanner.nextLine();
			
			System.out.println("answer: (1-4) ");
			int answer = scanner.nextInt();
			
//			3. create mySql command / query
			String sql = "insert into quizQuestions"
						+ "(question, optionA, optionB, optionC, optionD, answer)"
						+ "values"
						+ "('" + question + "',"
						+ "'" + optionA + "',"
						+ "'" + optionB + "',"
						+ "'" + optionC + "',"
						+ "'" + optionD + "',"
						+ answer +");";

//			4. send command to mySql
			myStmt.executeUpdate(sql);
			
			System.out.println("insert complete!");
			
//			5. Close statement & connection
			myStmt.close();
			myConn.close();
			
		}
		catch (Exception e) {
			e.getStackTrace();
		}
		
	}


	private void viewResults(){
		
		try {
//			1. Establishing Connection
			Connection myConn = DriverManager.getConnection(dbUrl, user, password);
//			2. Create a statement
			Statement myStmt = myConn.createStatement();
//			3. Execute the query
			ResultSet myRs = myStmt.executeQuery("select * from students");
//			4. Process the resultSet object

			System.out.println("Student ID\tresults");
			while(myRs.next()){
				System.out.print(myRs.getString("id"));
				System.out.print("\t\t");
				System.out.print(myRs.getString("results"));
				System.out.print("\n");

			}

			
//			5. Close statement & connection
			myStmt.close();
			myConn.close();

			
		}
		catch (Exception e) {
			e.getStackTrace();
		}
	
		
	}
	

	private void addStudent(){
		try {
//			1. Establishing Connection
			Connection myConn = DriverManager.getConnection(dbUrl, user, password);
//			2. Create a statement
			Statement myStmt = myConn.createStatement();
			
			System.out.println("Student's First Name: ");
			String firstName = scanner.next();
			System.out.println("Student's Last Name: ");
			String lastName = scanner.next();
			System.out.println("Student's email address: ");
			String email = scanner.next();
			System.out.println("Student's phone number: ");
			int phone = scanner.nextInt();
			
//			3. create mySql command / query
			String sql = "insert into students"
						+ "(firstName, lastName, email, phone)"
						+ "values"
						+ "('" + firstName + "',"
						+ "'" + lastName + "',"
						+ "'" + email + "',"
						+ phone + ");";

//			4. send command to mySql
			myStmt.executeUpdate(sql);
			
			System.out.println("student successfully added!");
			
//			5. Close statement & connection
			myStmt.close();
			myConn.close();
			
		}
		catch (Exception e) {
			e.getStackTrace();
		}
	
	}
}


