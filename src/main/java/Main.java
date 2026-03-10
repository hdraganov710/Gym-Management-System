import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
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
                    case "1":
                        handleRegistration(userService, sc);
                        break;
                        case "2":
                            handleLogin(userService,sc);
                            break;
                            case "3":
                                flag = false;
                                break;


                }
            }
            catch(WrongCredentialsException e)
            {
                System.out.println("Wrong credentials!" + e.getMessage());
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
        else if(ROLE.COACH == request.role)
        {
            System.out.println("You are an Coach!");
            System.out.print("Enter your experience: ");
            request.experience = sc.nextInt();
            System.out.print("Enter your specialty: ");
            request.specialty = sc.nextLine();
        }
        else if(ROLE.CLIENT == request.role)
        {
            System.out.println("You are an Client!");
            System.out.print("Enter your age: ");
            request.age = sc.nextInt();
            System.out.print("Enter your gender(1 - Male / 2 - Female: ");
            request.gender = GENDER.fromInt(sc.nextInt());
        }
        userService.register(request);
    }
    private static void handleLogin(UserService userService, Scanner sc) {
        System.out.println("--- Login System ---");
        RegistrationRequest request = new RegistrationRequest();
        UserRepository repo = new InMemoryUserRepository();
        System.out.print("Email: ");
        request.email = sc.nextLine();
        repo.findByEmail(request.email)
                .ifPresentOrElse(
                        user -> {
                            System.out.println("User found with email: " + request.email);
                            System.out.print("Enter your password: ");
                            request.password = sc.nextLine();
                            if(user.getPassword().equals(request.password))
                            {
                                System.out.println("Successful login! Welcome, " + user.getFirstName());

                            }
                            else {System.out.println("Invalid password!");}
                        },
                        () -> {System.out.println("Error: No user found with these credentials!");});

        userService.login(request.email, request.password);
    }

}
