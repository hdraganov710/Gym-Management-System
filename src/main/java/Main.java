import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserRepository repo = new InMemoryUserRepository();
        UserService userService = new UserService(repo);

        while (true) {
            System.out.println("\n--- GYM MANAGEMENT SYSTEM ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();
            try {
                switch (choice) {
                    case "1":
                        handleRegistration(userService, sc);
                }
            }
        }

    }
    private static void handleRegistration(UserService userService, Scanner sc) {
        System.out.print("---New user Registration---");
        RegistrationRequest request = new RegistrationRequest();
        System.out.print("Email: ");
        request.email = sc.nextLine();
        System.out.print("Password: ");
        request.password = sc.nextLine();
        System.out.print("First Name: ");
        request.firstName = sc.nextLine();
        System.out.print("Last Name: ");
        request.lastName = sc.nextLine();
        System.out.print("Age: ");
        request.phoneNumber = sc.nextLine();
        System.out.print("Role: ");
        while (true) {
            try {
                String role = sc.nextLine();
                request.role = ROLE.valueOf(role.trim().toUpperCase());
                break;
                }
            catch (IllegalArgumentException e) {
                System.out.println("Invalid Role!");
            }
        }
        if(ROLE.ADMIN == request.role)
        {
            System.out.println("You are an Administrator!");
            System.out.print("Enter your level: ");
            request.adminLevel = sc.nextInt();
            System.out.print("Enter your employeeID: ");
            request.employeeId = UUID.fromString(sc.nextLine());

        }

    }
}
