package asistencia;

import java.util.Scanner;

public class AsistenciaEstudiantes {
	
	    // Constants
	    private static final int DAYS_WEEK = 5;
	    private static final int NUM_STUDENTS = 4;

	    // students
	    private static final String[] STUDENTS = {"Anna", "Luis", "Martha", "Peter"};

	    // Attendance matrix
	    private static String[][] attendance = new String[NUM_STUDENTS][DAYS_WEEK];

	    private static Scanner input = new Scanner(System.in);

	    public static void main(String[] args) {
	        boolean dataFilled = false;
	        int option;

	        // First step: register attendance
	        registerAttendance();
	        dataFilled = true;

	        // Menu available after registration
	        do {
	            System.out.println("\n=== MAIN MENU ===");
	            System.out.println("1. View individual attendance");
	            System.out.println("2. View general summary");
	            System.out.println("3. Re-register attendance (reset data)");
	            System.out.println("4. Exit");
	            System.out.print("Choose an option: ");
	            option = input.nextInt();
	            input.nextLine(); // clear buffer

	            if (!dataFilled) {
	                System.out.println("⚠ You must register attendance first.");
	                continue;
	            }

	            switch (option) {
	                case 1 -> viewIndividualAttendance();
	                case 2 -> viewGeneralSummary();
	                case 3 -> {
	                    resetAttendance();
	                    registerAttendance(); // enter data again
	                }
	                case 4 -> System.out.println("👋 Exiting the system...");
	                default -> System.out.println("⚠ Invalid option. Try again.");
	            }
	        } while (option != 4);

	        input.close();
	    }

	    // Register attendance for all students
	    private static void registerAttendance() {
	        System.out.println("\n=== Attendance Registration (P = Present, A = Absent) ===");
	        for (int i = 0; i < NUM_STUDENTS; i++) {
	            for (int j = 0; j < DAYS_WEEK; j++) {
	                String status; 
	                while (true) {
	                    System.out.print(STUDENTS[i] + " on Day " + (j + 1) + " (P/A): ");
	                    status = input.nextLine().trim().toUpperCase(); // .trim removes spaces before/after the text .toUpperCaes converts everything to uppercase little a is now a big A
	                    
	                    if (status.equals("P") || status.equals("A")) {
	                        attendance[i][j] = status;
	                        break;
	                    } else {
	                        System.out.println("Invalid input. Only 'P' or 'A' are allowed.");
	                    }
	                }
	            }
	        }
	        System.out.println("Attendance successfully registered.");
	    }

	    // View individual student attendance
	    private static void viewIndividualAttendance() {
	        System.out.println("\n=== Individual Attendance ===");
	        for (int i = 0; i < NUM_STUDENTS; i++) {
	            System.out.println((i + 1) + ". " + STUDENTS[i]);
	        }
	        System.out.print("Choose a student: ");
	        int idx = input.nextInt() - 1;
	        input.nextLine(); // clear buffer

	        if (idx >= 0 && idx < NUM_STUDENTS) {
	            System.out.println("\nAttendance for " + STUDENTS[idx] + ":");
	            for (int j = 0; j < DAYS_WEEK; j++) {
	                System.out.println("Day " + (j + 1) + ": " + attendance[idx][j]);
	            }
	        } else {
	            System.out.println("⚠ Invalid student.");
	        }
	    }

	    // View general summary
	    private static void viewGeneralSummary() {
	        System.out.println("\n=== General Attendance Summary ===");

	        // Totals per student
	        for (int i = 0; i < NUM_STUDENTS; i++) {
	            int presentCount = 0, absentCount = 0;
	            for (int j = 0; j < DAYS_WEEK; j++) {
	                if (attendance[i][j].equals("P")) presentCount++;
	                else absentCount++;
	            }
	            System.out.println(STUDENTS[i] + " -> Present: " + presentCount + ", Absent: " + absentCount);
	        }

	        // Students who attended all days
	        System.out.println("\n✔ Students who attended all days:");
	        boolean found = false;
	        for (int i = 0; i < NUM_STUDENTS; i++) {
	            boolean allPresent = true;
	            for (int j = 0; j < DAYS_WEEK; j++) {
	                if (attendance[i][j].equals("A")) {
	                    allPresent = false;
	                    break;
	                }
	            }
	            if (allPresent) {
	                System.out.println("- " + STUDENTS[i]);
	                found = true;
	            }
	        }
	        if (!found) System.out.println("None");

	        // Days with the most absences
	        System.out.println("\n📌 Day(s) with the most absences:");
	        int maxAbsences = 0;
	        int[] absencesPerDay = new int[DAYS_WEEK];

	        for (int j = 0; j < DAYS_WEEK; j++) {
	            int absents = 0;
	            for (int i = 0; i < NUM_STUDENTS; i++) {
	                if (attendance[i][j].equals("A")) absents++;
	            }
	            absencesPerDay[j] = absents;
	            if (absents > maxAbsences) maxAbsences = absents;
	        }

	        for (int j = 0; j < DAYS_WEEK; j++) {
	            if (absencesPerDay[j] == maxAbsences) {
	                System.out.println("- Day " + (j + 1) + " with " + maxAbsences + " absences");
	            }
	        }
	    }

	    // Reset attendance
	    private static void resetAttendance() {
	        attendance = new String[NUM_STUDENTS][DAYS_WEEK];
	        System.out.println("🔄 Attendance records have been cleared.");
	    }
	}
