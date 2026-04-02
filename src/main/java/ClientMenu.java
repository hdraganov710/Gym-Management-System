import java.util.Scanner;

public class ClientMenu {
    private final Scanner sc;
    private final Client client;
    private final UserRepository userRepository;

    public ClientMenu(Scanner sc, Client client, UserRepository userRepository) {
        this.sc = sc;
        this.client = client;
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
        System.out.println("Assigned Coach: " + (client.getAssignedCoach() != null ? client.getAssignedCoach() : "None"));
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
        UserService userService = new UserService(userRepository);
        System.out.println("--- Update Profile ---");
        System.out.println("1. Change Password");
        System.out.println("2. Update Weight");
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
                System.out.print("New weight: ");
                String newWeight = sc.nextLine();
                userService.updateProfile(client.getUserId(),
                        null, null, newWeight);
                System.out.println("Weight updated.");
            }
            default -> System.out.println("Invalid choice.");
        }
    }
}
