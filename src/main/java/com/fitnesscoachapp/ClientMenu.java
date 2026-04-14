package com.fitnesscoachapp;

import java.util.Scanner;

public class ClientMenu {
    private final Scanner sc;
    private final Client client;
    private final UserService userService;
    private final UserRepository userRepository;

    public ClientMenu(Scanner sc, Client client, UserService userService, UserRepository userRepository) {
        this.sc = sc;
        this.client = client;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public void showMenu() {
        boolean backToLogin = false;
        while (!backToLogin) {
            System.out.println("---Client Menu---");
            System.out.println("Welcome, " + client.getFirstName());
            System.out.println("1. View My Profile");
            System.out.println("2. View Workout Plan");
            System.out.println("3. Update Profile");
            System.out.println("0. Logout");
            System.out.print("Please enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> viewProfile();
                case 2 -> viewWorkoutPlan();
                case 3 -> updateProfile();
                case 0 -> backToLogin = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void viewProfile() {
        System.out.println("--- My Profile ---");
        System.out.println("Name:           " + client.getFirstName() + " " + client.getLastName());
        System.out.println("Email:          " + client.getEmail());
        System.out.println("Phone:          " + client.getPhoneNumber());
        System.out.println("Age:            " + client.getAge());
        System.out.println("Weight:         " + (client.getWeight() != null ? client.getWeight() : "Not set"));
        System.out.println("Gender:         " + client.getGender());
        String coachName = "None";
        if (client.getAssignedCoach() != null) {
            coachName = userRepository.findById(client.getAssignedCoach())
                    .map(u -> u.getFirstName() + " " + u.getLastName())
                    .orElse("Unknown");
        }
        System.out.println("Assigned Coach: " + coachName);
    }

    private void viewWorkoutPlan() {
        System.out.println("--- My Workout Plan ---");
        if (client.getwP() == null || client.getwP().getDays().isEmpty()) {
            System.out.println("No workout plan assigned yet.");
            return;
        }
        for (WorkoutDay day : client.getwP().getDays()) {
            System.out.println(day);
        }
    }

    private void updateProfile() {
        System.out.println("--- Update Profile ---");
        System.out.println("1. Change Password");
        System.out.println("2. Update Weight");
        System.out.println("3. Update First Name");
        System.out.println("4. Update Last Name");
        System.out.println("5. Update Phone Number");
        System.out.print("Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1 -> {
                System.out.print("Current password: ");
                String oldPassword = sc.nextLine();
                System.out.print("New password: ");
                String newPassword = sc.nextLine();
                try {
                    userService.updateProfile(client.getUserId(), oldPassword, newPassword, null);
                } catch (UnauthorizedException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case 2 -> {
                System.out.print("New weight (kg): ");
                try {
                    Double newWeight = Double.parseDouble(sc.nextLine());
                    userService.updateProfile(client.getUserId(), null, null, newWeight);
                    System.out.println("Weight updated.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid weight. Please enter a number.");
                }
            }
            case 3 -> {
                System.out.print("New first name: ");
                client.setFirstName(sc.nextLine());
                userRepository.save(client);
                System.out.println("First name updated.");
            }
            case 4 -> {
                System.out.print("New last name: ");
                client.setLastName(sc.nextLine());
                userRepository.save(client);
                System.out.println("Last name updated.");
            }
            case 5 -> {
                System.out.print("New phone number: ");
                client.setPhoneNumber(sc.nextLine());
                userRepository.save(client);
                System.out.println("Phone number updated.");
            }
            default -> System.out.println("Invalid choice.");
        }
    }
}