import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    //This part is to generate names for students
    public static String generateRandomName(int minLength, int maxLength) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        int length = random.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder name = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            name.append(chars.charAt(random.nextInt(chars.length())));
        }

        return name.toString();
    }
    public static void main(String[] args) throws IOException {
        Session session = new Session(); // Create a session to hold students

        for (int i = 0; i < 10; i++) {
            String randomFullName = generateRandomName(3, 10); // Random name for full-time student
            String randomPartName = generateRandomName(3, 10); // Random name for part-time student
            session.addStudent(new Full_Time(randomFullName));
            session.addStudent(new Part_Time(randomPartName));
        }

        // Call public methods for each student
        for (Student student : session.getStudents()) {
            if (student instanceof Full_Time) {
                Full_Time fullTimeStudent = (Full_Time) student;
                fullTimeStudent.printExamScores(); // Print exam scores for full-time students
            } else if (student instanceof Part_Time) {
                Part_Time partTimeStudent = (Part_Time) student;
                partTimeStudent.nameOfPartTime(); // Print name of part-time students
            }
        }

        // Get user input for the session number
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the session number (1 to 15): ");
        int sessionNumber = scanner.nextInt();

        // Validate input
        if (sessionNumber < 1 || sessionNumber > 15) {
            System.out.println("Invalid session number. Please enter a number between 1 and 15.");
        } else {
            // Calculate average quiz score for the specified session
            System.out.println("Calculating average quiz scores for session " + sessionNumber + ":");
            session.calculateAverageQuizScoreForSession(sessionNumber - 1); // Convert to 0-based index

            // Print quiz scores for the specified session
            System.out.println("Quiz scores for session " + sessionNumber + ":");
            session.quizScoreOneSession(sessionNumber - 1); // Convert to 0-based index
        }

        scanner.close(); // Close the scanner
    }
}
