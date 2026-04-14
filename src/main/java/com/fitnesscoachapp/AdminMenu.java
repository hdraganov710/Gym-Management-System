package com.fitnesscoachapp;

import java.util.*;

public class AdminMenu {
    private final Scanner sc;
    private final UserRepository userRepository;
    private final UserService userService;
    private final Admin admin;

    public AdminMenu(Scanner sc, UserRepository userRepository, UserService userService, Admin admin) {
        this.sc = sc;
        this.userRepository = userRepository;
        this.userService = userService;
        this.admin = admin;
    }

    public void showMenu() {
        boolean backToLogin = false;
        while (!backToLogin) {
            System.out.println("---Admin Menu---");
            System.out.println("1. View All Users");
            System.out.println("2. Add New User");
            System.out.println("3. Change Coach Status");
            System.out.println("4. Assign Coach");
            System.out.println("5. Delete User");
            System.out.println("0. Logout ");
            System.out.println("Enter your choice");
            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> viewUsers();
                case 2 -> addUser();
                case 3 -> changeStatus();
                case 4 -> assignCoach();
                case 5 -> deleteUser();
                case 0 -> backToLogin = true;
            }
        }
    }

    public void viewUsers() {
        userRepository.findAll().forEach(user -> System.out.println(user.toString()));
    }

    public void addUser() {
        RegistrationRequest request = new RegistrationRequest();
        System.out.println("--- User Registration ---");
        System.out.print("Email: "); request.setEmail(sc.next());
        System.out.print("First Name: "); request.setFirstName(sc.next());
        System.out.print("Last Name: "); request.setLastName(sc.next());
        System.out.print("Phone Number: "); request.setPhoneNumber(sc.next());
        System.out.print("Password: "); request.setPassword(sc.next());

        System.out.println("1. Coach | 2. Client | 3. Admin");
        int choice = sc.nextInt();
        switch (choice) {
            case 1 -> {
                request.setRole(ROLE.COACH);
                System.out.print("Experience (years): ");
                request.setExperience(sc.nextInt());
            }
            case 2 -> {
                request.setRole(ROLE.CLIENT);
                System.out.print("Age: ");
                request.setAge(sc.nextInt());
            }
            case 3 -> request.setRole(ROLE.ADMIN);
            default -> {
                System.out.println("Invalid choice!");
                return;
            }
        }
        userService.register(request);
        System.out.println("User added successfully!");
    }

    public void changeStatus() {
        System.out.println("--- Change Status ---");
        System.out.println("1. Coach | 2. Client | 3. Admin");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.println("Type in the Coach's UserId:");
                String userId = sc.next();
                sc.nextLine();
                try { UUID.fromString(userId); } catch (IllegalArgumentException e) { System.out.println("Invalid ID format."); break; }
                userRepository.findById(UUID.fromString(userId)).ifPresentOrElse(user -> {
                    if (user instanceof Coach coach) {
                        System.out.println("Updating coach: " + coach.getFirstName());
                        System.out.println("1 - XP, 2 - Specialty: ");
                        int subChoice = sc.nextInt();
                        if (subChoice == 1) {
                            System.out.print("Enter new Experience: ");
                            coach.setExperience(sc.nextInt());
                        } else if (subChoice == 2) {
                            System.out.print("Enter new Specialty: ");
                            coach.setSpecialty(sc.next());
                        }
                        userRepository.save(coach);
                        System.out.println("Coach updated!");
                    } else {
                        System.out.println("That ID does not belong to a coach!");
                    }
                }, () -> System.out.println("User Not Found!"));
            }

            case 2 -> {
                System.out.println("Type in the Client's UserId:");
                String userId = sc.next();
                sc.nextLine();
                try {
                    userRepository.findById(UUID.fromString(userId)).ifPresentOrElse(user -> {
                        if (user instanceof Client client) {
                            System.out.println("Updating client: " + client.getFirstName());
                            System.out.print("Enter New Password: ");
                            client.setPassword(sc.nextLine());
                            userRepository.save(client);
                            System.out.println("Client updated!");
                        } else {
                            System.out.println("That ID does not belong to a client!");
                        }
                    }, () -> System.out.println("User Not Found!"));
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid ID format.");
                }
            }

            case 3 -> {
                System.out.print("Enter Admin ID to update: ");
                String adminIdStr = sc.next();
                sc.nextLine();
                UUID adminId;
                try { adminId = UUID.fromString(adminIdStr); } catch (IllegalArgumentException e) { System.out.println("Invalid ID format."); break; }
                userRepository.findById(adminId).ifPresentOrElse(user -> {
                    if (user instanceof Admin otherAdmin) {
                        System.out.println("1. Level | 2. Password | 3. Phone");
                        int ch = sc.nextInt();
                        switch (ch) {
                            case 1 -> { System.out.print("Level: "); otherAdmin.setAdminLevel(sc.nextInt()); }
                            case 2 -> { System.out.print("Password: "); otherAdmin.setPassword(sc.next()); }
                            case 3 -> { System.out.print("Phone: "); otherAdmin.setPhoneNumber(sc.next()); }
                        }
                        userRepository.save(otherAdmin);
                    }
                }, () -> System.out.println("Admin not found."));
            }
        }
    }

    public void assignCoach() {
        System.out.println("--- Assign Coach ---");
        System.out.println("Enter the userId you want to change the assigned coach to: ");
        String userId = sc.next();
        sc.nextLine();
        UUID clientUuid;
        try { clientUuid = UUID.fromString(userId); } catch (IllegalArgumentException e) { System.out.println("Invalid ID format."); return; }
        userRepository.findById(clientUuid).ifPresentOrElse(user -> {
            if (user instanceof Client client) {
                System.out.println("Updating client: " + client.getFirstName());
                System.out.println("Current coach is: " + client.getAssignedCoach());
                System.out.println("Enter the new coach ID: ");
                try {
                    client.setAssignedCoach(UUID.fromString(sc.nextLine()));
                    userRepository.save(client);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid coach ID format.");
                }
            }
        }, () -> System.out.println("Invalid userId"));
    }

    public void deleteUser() {
        System.out.println("--- Delete User ---");
        System.out.println("Enter the userId you want to delete: ");
        String userId = sc.next();
        System.out.println("Are you sure?: (y/n) ");
        String answer = sc.next();
        if (answer.equalsIgnoreCase("y")) {
            try {
                userService.deleteUser(admin.getUserId(), UUID.fromString(userId));
                System.out.println("User deleted.");
            } catch (UserNotFoundException | UnauthorizedException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid ID format.");
            }
        } else {
            System.out.println("Bye.");
        }
    }
}