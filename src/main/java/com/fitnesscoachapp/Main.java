package com.fitnesscoachapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;
import java.util.UUID;

@SpringBootApplication
public class Main implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner sc = new Scanner(System.in);
        UserRepository repo = new InMemoryUserRepository();
        UserService userService = new UserService(repo);
        boolean flag = true;
        while (flag) {
            System.out.println("\n--- GYM MANAGEMENT SYSTEM ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();
            try {
                switch (choice) {
                    case "1" -> handleRegistration(userService, sc);
                    case "2" -> handleLogin(userService, sc, repo);
                    case "3" -> flag = false;
                }
            } catch (WrongCredentialsException | UserNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void handleRegistration(UserService userService, Scanner sc) {
        System.out.println("---New user Registration---");
        RegistrationRequest request = new RegistrationRequest();
        System.out.print("Email: ");
        request.setEmail(sc.nextLine());
        System.out.print("Password: ");
        request.setPassword(sc.nextLine());
        System.out.print("First Name: ");
        request.setFirstName(sc.nextLine());
        System.out.print("Last Name: ");
        request.setLastName(sc.nextLine());
        System.out.print("Phone Number: ");
        request.setPhoneNumber(sc.nextLine());
        System.out.print("Role: ");
        while (true) {
            try {
                String role = sc.nextLine();
                request.setRole(ROLE.valueOf(role.trim().toUpperCase()));
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Role!");
            }
        }
        if (ROLE.ADMIN == request.getRole()) {
            System.out.println("You are an Administrator!");
            System.out.print("Enter your level: ");
            request.setAdminLevel(sc.nextInt());
            sc.nextLine();
            System.out.print("Enter your employeeID: ");
            request.setEmployeeId(UUID.fromString(sc.nextLine()));
        } else if (ROLE.COACH == request.getRole()) {
            System.out.println("You are a Coach!");
            System.out.print("Enter your experience: ");
            request.setExperience(sc.nextInt());
            sc.nextLine();
            System.out.print("Enter your specialty: ");
            request.setSpecialty(sc.nextLine());
        } else if (ROLE.CLIENT == request.getRole()) {
            System.out.println("You are a Client!");
            System.out.print("Enter your age: ");
            request.setAge(sc.nextInt());
            System.out.print("Enter your gender (1 - Male / 2 - Female): ");
            request.setGender(GENDER.fromInt(sc.nextInt()));
            sc.nextLine();
        }
        userService.register(request);
    }

    private static void handleLogin(UserService userService, Scanner sc, UserRepository repo) {
        System.out.println("--- Login System ---");
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        User user = userService.login(email, password);
        System.out.println("Successful login! Welcome, " + user.getFirstName());
        if (user instanceof Admin admin) {
            new AdminMenu(sc, repo, userService, admin).showMenu();
        } else if (user instanceof Coach coach) {
            new CoachMenu(sc, coach, repo).showMenu();
        } else if (user instanceof Client client) {
            new ClientMenu(sc, client, userService, repo).showMenu();
        }
    }
}