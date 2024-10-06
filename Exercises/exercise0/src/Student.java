//Student class

import java.util.*;


class Student {
    private String name;
    private boolean partTime;
    private Vector<Integer> examScores; // Vector to store exam scores
    private Vector<Integer> quizScores; // Vector of vectors for quiz scores

    // Constructor to initialize the student and quiz scores
    public Student(String name, boolean partTime) {
        this.name = name;
        this.partTime = partTime;
        this.examScores = new Vector<>();
        this.quizScores = new Vector<>(15); // For 20 students


        for (int j = 0; j < 15; j++) {
            quizScores.add((int) (Math.random() * 100));// Add random quiz scores
        }

    }

    public String getName() {
        return name;
    }

    public boolean isPartTime() {
        return partTime;
    }

    public int getQuizScoreForSession(int sessionIndex) {
        return quizScores.get(sessionIndex); // Return quiz score for the session
    }
}

// Full-time student class
class Full_Time extends Student {
    private Vector<Integer> examScores; // Vector to store two exam scores

    // Constructor
    public Full_Time(String name) {
        super(name, false); // Full-time students are not part-time
        this.examScores = new Vector<>(2); // Full-time students have 2 exam scores
        // Assign random scores for example
        examScores.add((int) (Math.random() * 100));
        examScores.add((int) (Math.random() * 100));
    }

    public void printExamScores() {
        System.out.println("The exam scores of full-time student " + getName() + " are: " + examScores);
    }
}

// Part-time student class
class Part_Time extends Student {
    // Constructor
    public Part_Time(String name) {
        super(name, true); // Part-time students
    }

    public void nameOfPartTime() {
        System.out.println("Name of the part-time student: " + getName());
    }
}

class Session {
    private Vector<Student> students; // Holds the students in the session

    // Constructor
    public Session() {
        students = new Vector<>(20); // Initialize the vector for 20 students
    }

    public void addStudent(Student student) {
        if (students.size() < 20) {
            students.add(student);
        } else {
            System.out.println("Session is full. Cannot add more students.");
        }
    }

    public Vector<Student> getStudents() {
        return students;
    }

    // Method to calculate average quiz scores for a specific session
    public void calculateAverageQuizScoreForSession(int sessionIndex) {
        double totalScore = 0;
        int numStudents = students.size();

        for (Student student : students) {
            totalScore += student.getQuizScoreForSession(sessionIndex);
        }

        double averageScore = totalScore / numStudents;
        System.out.printf("Average quiz score for session %d is: %.2f%n", sessionIndex + 1, averageScore);
    }

    // Method to show quiz scores for a specific session
    public void quizScoreOneSession(int sessionIndex) {

        // Create a list of pairs (student name, quiz score) for the session
        List<Map.Entry<String, Integer>> scoresAndNames = new ArrayList<>();

        // Collect quiz scores and names for the specified session
        for (Student student : students) {
            scoresAndNames.add(new AbstractMap.SimpleEntry<>(student.getName(), student.getQuizScoreForSession(sessionIndex)));
        }

        // Sort the list by quiz score
        scoresAndNames.sort(Map.Entry.comparingByValue());

        // Print the sorted quiz scores and corresponding student names
        System.out.println("Sorted quiz scores and student names for session " + (sessionIndex + 1) + " in ascending order:");
        for (Map.Entry<String, Integer> entry : scoresAndNames) {
            System.out.println("Student: " + entry.getKey() + ", Score: " + entry.getValue());
        }
    }
}