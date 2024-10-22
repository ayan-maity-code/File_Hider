package views;

import dao.DataDao;
import model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private final String email;
    private final Scanner scanner; // Scanner as a class-level variable

    UserView(String email) {
        this.email = email;
        this.scanner = new Scanner(System.in); // Initialize the scanner here
    }

    public void home() {
        while (true) {
            System.out.println("Welcome " + this.email);
            System.out.println("Press 1 to show hidden files");
            System.out.println("Press 2 to hide a new file");
            System.out.println("Press 3 to unhide a file");
            System.out.println("Press 0 to exit");

            int ch;
            try {
                ch = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue; // Go back to the start of the loop
            }

            switch (ch) {
                case 1 -> {
                    try {
                        List<Data> files = DataDao.getAllFiles(this.email);
                        System.out.println("Id - File Name");
                        for (Data file : files) {
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                    } catch (SQLException e) {
                        System.out.println("Error retrieving files: " + e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("Enter the file path");
                    String path = scanner.nextLine();
                    File f = new File(path);

                    // Check if the file exists
                    if (!f.exists()) {
                        System.out.println("File not found: " + f.getAbsolutePath());
                        return; // Exit the case early
                    }

                    Data file = new Data(0, f.getName(), path, this.email);
                    try {
                        int result = DataDao.hideFile(file); // Capture the return value
                        if (result > 0) {
                            System.out.println("File hidden successfully.");
                        } else {
                            System.out.println("No file was hidden.");
                        }
                    } catch (SQLException | IOException e) {
                        System.out.println("Error hiding file: " + e.getMessage());
                    }
                }

                case 3 -> {
                    try {
                        List<Data> files = DataDao.getAllFiles(this.email);
                        System.out.println("Id - File Name");
                        for (Data file : files) {
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                        System.out.println("Enter the id to unhide the file");
                        int id = Integer.parseInt(scanner.nextLine());
                        boolean isValidId = false;
                        for (Data file : files) {
                            if (file.getId() == id) {
                                isValidId = true;
                                break;
                            }
                        }
                        if (isValidId) {
                            DataDao.unhide(id);
                            System.out.println("File unhidden successfully.");
                        } else {
                            System.out.println("Wrong Id");
                        }
                    } catch (SQLException | IOException e) {
                        System.out.println("Error during unhide operation: " + e.getMessage());
                    }
                }
                case 0 -> {
                    System.out.println("Exiting...");
                    scanner.close(); // Close the scanner
                    System.exit(0); // Exit the application
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
