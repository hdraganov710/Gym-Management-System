package com.fitnesscoachapp;

import java.util.*;

public class CoachMenu {
    private final Scanner sc;
    private final Coach coach;
    private final UserRepository userRepository;

    public CoachMenu(Scanner sc, Coach coach, UserRepository userRepository) {
        this.sc = sc;
        this.coach = coach;
        this.userRepository = userRepository;
    }

    public void showMenu() {
        boolean backToLogin = false;
        while (!backToLogin) {
            System.out.println("---CoachMenu---");
            System.out.println("Welcome, coach " + coach.getFirstName());
            System.out.println("1. View My Profile");
            System.out.println("2. View My Clients");
            System.out.println("3. Manage Workout Plan");
            System.out.println("0. Logout");
            System.out.print("Please enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> viewProfile();
                case 2 -> viewClients();
                case 3 -> createWorkout();
                case 0 -> backToLogin = true;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void viewProfile() {
        System.out.println("--- My Profile ---");
        System.out.println("Name:       " + coach.getFirstName() + " " + coach.getLastName());
        System.out.println("Email:      " + coach.getEmail());
        System.out.println("Phone:      " + coach.getPhoneNumber());
        System.out.println("Experience: " + coach.getExperience() + " years");
        System.out.println("Specialty:  " + (coach.getSpecialty() != null ? coach.getSpecialty() : "Not set"));
    }

    public void viewClients() {
        System.out.println("--- Your Assigned Clients ---");
        List<Client> myClients = getMyClients();
        if (myClients.isEmpty()) {
            System.out.println("No clients assigned to you yet.");
        } else {
            for (int i = 0; i < myClients.size(); i++) {
                Client c = myClients.get(i);
                System.out.println((i + 1) + ". " + c.getFirstName() + " " + c.getLastName() + " (ID: " + c.getUserId() + ")");
            }
        }
    }

    public void createWorkout() {
        List<Client> myClients = getMyClients();
        if (myClients.isEmpty()) {
            System.out.println("No clients assigned to you.");
            return;
        }

        System.out.println("--- Select a Client ---");
        for (int i = 0; i < myClients.size(); i++) {
            Client c = myClients.get(i);
            System.out.println((i + 1) + ". " + c.getFirstName() + " " + c.getLastName());
        }
        System.out.print("Enter number: ");
        int pick = sc.nextInt();
        sc.nextLine();
        if (pick < 1 || pick > myClients.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        Client client = myClients.get(pick - 1);
        handleWorkoutCreation(client);
    }

    public void handleWorkoutCreation(Client client) {
        if (client.getwP() == null) {
            client.setwP(new WorkoutWeek());
        }
        System.out.print("Enter the day name (e.g. Monday): ");
        String dayName = sc.nextLine();

        WorkoutDay targetDay = client.getwP().getDays().stream()
                .filter(day -> day.getDayName().equalsIgnoreCase(dayName))
                .findFirst()
                .orElse(null);

        if (targetDay == null) {
            targetDay = new WorkoutDay(dayName, null);
            client.getwP().addDay(targetDay);
            System.out.println("New day '" + dayName + "' created.");
        }

        System.out.println("1. Add exercise");
        System.out.println("2. Remove exercise");
        System.out.print("Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            System.out.print("Enter exercise to add: ");
            targetDay.addExercise(sc.nextLine());
            System.out.println("Exercise added to " + dayName + ".");
        } else if (choice == 2) {
            List<String> exercises = targetDay.getExercises();
            if (exercises.isEmpty()) {
                System.out.println("No exercises on " + dayName + ".");
            } else {
                System.out.println("--- Exercises on " + dayName + " ---");
                for (int i = 0; i < exercises.size(); i++) {
                    System.out.println((i + 1) + ". " + exercises.get(i));
                }
                System.out.print("Enter number to remove: ");
                int idx = sc.nextInt();
                sc.nextLine();
                if (idx >= 1 && idx <= exercises.size()) {
                    String removed = exercises.remove(idx - 1);
                    System.out.println("Removed: " + removed);
                } else {
                    System.out.println("Invalid selection.");
                }
            }
        } else {
            System.out.println("Invalid choice.");
        }

        userRepository.save(client);
    }

    private List<Client> getMyClients() {
        return userRepository.findAll().stream()
                .filter(user -> user instanceof Client)
                .map(user -> (Client) user)
                .filter(client -> coach.getUserId().equals(client.getAssignedCoach()))
                .toList();
    }
}
