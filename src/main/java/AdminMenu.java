import java.util.*;

public class AdminMenu {
    private final Scanner sc;
    private final Admin admin;
    private final UserRepository userRepository;

    public AdminMenu(Scanner sc, Admin admin, UserRepository userRepository) {
        this.sc = sc;
        this.admin = admin;
        this.userRepository = userRepository;
    }

    public void showMenu() {
        System.out.println("---Admin Menu---");
        System.out.println("1. View All Users");
        System.out.println("2. Add New User");
        System.out.println("3. Change Coach Status");
        System.out.println("4. Assign Coach");
        System.out.println("5. Delete User");
        System.out.println("0. Logout ");
        System.out.println("Enter your choice");
        int choice = sc.nextInt();
        boolean backToLogin = false;
        while (!backToLogin) {
            switch (choice) {
                case 1:
                    viewUsers();
                    break;
                case 2:
                    addUser();
                    break;
                case 3:
                    changeStatus();
                    break;
                case 4:
                    assignCoach();
                    break;
                case 5:
                    deleteUser();
                    break;
                case 0:
                    backToLogin = true;
                    break;
            }
        }
    }
    public void viewUsers() {
        userRepository.findAll().forEach(user -> System.out.println(user.toString()));
    }
    public void addUser() {
        RegistrationRequest request = new RegistrationRequest();
        UserService userService = new UserService(userRepository);
        System.out.println("--- User Registration ---");
        System.out.print("Email: "); request.setEmail(sc.next());
        System.out.print("First Name: "); request.setFirstName(sc.next());
        System.out.print("Last Name: "); request.setLastName(sc.next());
        System.out.print("Password: "); request.setPassword(sc.next());

        System.out.println("1. Coach | 2. Client | 3. Admin");
        int choice = sc.nextInt();
        switch(choice) {
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
        switch(choice) {
            case 1 -> {
                System.out.println("Type in the Coach's UserId:");
                String userId = sc.next();
                userRepository.findById(UUID.fromString(userId)).ifPresentOrElse(user -> {
                    if(user instanceof Coach coach){
                        System.out.println("Updating coach: "+coach.getFirstName());
                        System.out.println("Choose what you want to update(1-XP, 2-Specialty: ");
                        if(sc.nextInt() == 1) {
                            coach.setExperience(sc.nextInt());
                            userRepository.save(coach);
                            System.out.println("Coach updated!");
                        }
                        else if(sc.nextInt() == 2) {
                            coach.setSpecialty(sc.next());
                            userRepository.save(coach);
                            System.out.println("Coach updated!");
                        }
                        else {
                            System.out.println("Invalid choice!");
                        }
                    }
                    else {
                        System.out.println("That Id does not belong to a coach!");
                    }
                },() -> System.out.println("User Not Found!"));

            }
        }
        }
        
    }
