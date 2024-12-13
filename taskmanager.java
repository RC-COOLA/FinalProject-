import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskManager.java {

    // Array to store completed tasks (fixed size for simplicity)
    static String[] completedTasks = new String[10];
    // ArrayList to store pending tasks
    static ArrayList<String> pendingTasks = new ArrayList<>();
    // Scanner for user input
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadData();
        menu();
    }

    // Menu-driven interface
    public static void menu() {
    System.out.println("Task Manager Menu:");
    System.out.println("1. View Pending Tasks");
    System.out.println("2. Add Task");
    System.out.println("3. Complete Task");
    System.out.println("4. View Completed Tasks");
    System.out.println("5. Exit");
    int choice = getIntInput("Please choose an option (1-5): ");

        switch (choice) {
    case 1:
                viewPendingTasks();
                break;
        case 2:
                addTask();
                break;
        case 3:
                completeTask();
                break;
           case 4:
                viewCompletedTasks();
                break;
        case 5:
                saveData();
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                menu();
        }
    }

    // Here are the pending task loading
    public static void viewPendingTasks() {
        System.out.println("Pending Tasks:");
        if (pendingTasks.isEmpty()) {
            System.out.println("No pending tasks.");
        } else {
            for (int i = 0; i < pendingTasks.size(); i++) {
                System.out.println((i + 1) + ". " + pendingTasks.get(i));
            }
        }
        menu();
    }

    // Add a new task to pending tasks
    public static void addTask() {
        System.out.print("Enter task description: ");
        String task = scanner.nextLine();
        pendingTasks.add(task);
        System.out.println("Task added.");
        menu();
    }
    public static void completeTask() {
        viewPendingTasks();
        int taskIndex = getIntInput("Enter the number of the task to complete: ") - 1;

        try {
            if (taskIndex < 0 || taskIndex >= pendingTasks.size()) {
                throw new IndexOutOfBoundsException("Invalid task number.");
            }
            String task = pendingTasks.remove(taskIndex);
            addCompletedTask(task);
            System.out.println("Task completed.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
        menu();
    }

    // Add completed task to the array
    public static void addCompletedTask(String task) {
        for (int i = 0; i < completedTasks.length; i++) {
            if (completedTasks[i] == null) {
                completedTasks[i] = task;
                return;
            }
        }
        System.out.println("Completed tasks list is full.");
    }

    // View completed tasks
    public static void viewCompletedTasks() {
        System.out.println("Completed Tasks:");
        boolean hasCompletedTasks = false;
        for (String task : completedTasks) {
            if (task != null) {
                System.out.println(task);
                hasCompletedTasks = true;
            }
        }
        if (!hasCompletedTasks) {
         System.out.println("No completed tasks.");
        }
        menu();
    }
    public static int getIntInput(String prompt) {
        int value = -1;
        while (value == -1) {
            try {
                System.out.print(prompt);
                value = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }

    // Save the pending tasks and completed tasks to files
    public static void saveData() {
        try {
            // Save pending tasks
            BufferedWriter writer = new BufferedWriter(new FileWriter("pendingTasks.txt"));
            for (String task : pendingTasks) {
                writer.write(task + "\n");
            }
            writer.close();

            // Here is hwere I Save the completed tasks 
            writer = new BufferedWriter(new FileWriter("completedTasks.txt"));
            for (String task : completedTasks) {
                if (task != null) {
                    writer.write(task + "\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load the pending tasks and completed tasks from files
    public static void loadData() {
        try {
            // Load pending tasks
            BufferedReader reader = new BufferedReader(new FileReader("pendingTasks.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                pendingTasks.add(line);
            }
            reader.close();

            // Load completed tasks
            reader = new BufferedReader(new FileReader("completedTasks.txt"));
            int index = 0;
            while ((line = reader.readLine()) != null && index < completedTasks.length) {
                completedTasks[index++] = line;
            }
            reader.close();
        } catch (IOException e) {
         System.out.println("Error loading data: " + e.getMessage());
        }
    }
}
