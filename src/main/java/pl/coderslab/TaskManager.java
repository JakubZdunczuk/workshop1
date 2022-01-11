package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println(ConsoleColors.BLUE + "Please select an option" + ConsoleColors.RESET + "\nadd\nremove\nlist\nexit");
            String select = scanner.nextLine();
            switch (select) {
                case "add" -> addTask();
                case "remove" -> removeTask();
                case "list" -> showList();
                case "exit" -> System.out.println(ConsoleColors.RED + "Bye!");
                default -> System.out.println("Please select a correct option.");
            }
            if (select.equals("exit")) {
                break;
            }
        }
    }


    public static void addTask() {
        Scanner scanner = new Scanner(System.in);
        StringBuilder builder = new StringBuilder();
        System.out.println("Please add task description:");
        builder.append(scanner.nextLine()).append(", ");
        System.out.println("Please add task due date (yyyy-mm-dd):");
        builder.append(scanner.nextLine()).append(", ");
        System.out.println("Is your task important: true/false");
        builder.append(scanner.nextLine());
        try (FileWriter fileadd = new FileWriter("tasks.csv", true)) {
            fileadd.append("\n" + builder);
        } catch (IOException ex) {
            System.out.println("Error: cannot overwrite a file");
        }
    }

    public static void removeTask() {
        File file = new File("tasks.csv");
        List<String> list = new ArrayList<>();
        try {
            Scanner fileScan = new Scanner(file);
            while (fileScan.hasNextLine()) {
                list.add(fileScan.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Brak pliku.");
        }
        System.out.println("Which task do you want to remove?");
        showList();
        Scanner consoleScanner = new Scanner(System.in);
        int removeIndex = -1;
        while (true) {
            try {
                removeIndex = Integer.parseInt(consoleScanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("This is not a number! ");
            }
            if (removeIndex >= 0 && removeIndex < list.size()) {
                list.remove(removeIndex-1);
                try (FileWriter fileadd = new FileWriter("tasks.csv", false)) {

                    for(String a:list) {
                        fileadd.write(a + "\n");
                    }
                    System.out.println(ConsoleColors.RED +"Successfully removed!");
                } catch (IOException ex) {
                    System.out.println("Error: cannot overwrite a file");
                }
                break;
            } else {
                System.out.print("Enter a valid number: ");
            }
        }
    }

    public static void showList() {
        File file = new File("tasks.csv");
        try {
            Scanner scan = new Scanner(file);
            int i = 1;
            while (scan.hasNextLine()) {
                System.out.println(i + ". " + scan.nextLine());
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Brak pliku.");
        }

    }

}
