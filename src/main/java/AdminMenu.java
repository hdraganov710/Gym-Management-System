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
        sc.nextLine();

        switch(choice) {
            case 1 -> {
                System.out.println("Type in the Coach's UserId:");
                String userId = sc.next();
                userRepository.findById(UUID.fromString(userId)).ifPresentOrElse(user -> {
                    if(user instanceof Coach coach){
                        System.out.println("Updating coach: " + coach.getFirstName());
                        System.out.println("1 - XP, 2 - Specialty: ");
                        int subChoice = sc.nextInt();

                        if(subChoice == 1) {
                            System.out.print("Enter new Experience: ");
                            coach.setExperience(sc.nextInt());
                        } else if(subChoice == 2) {
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
                userRepository.findById(UUID.fromString(userId)).ifPresentOrElse(user -> {
                    if(user instanceof Client client){
                        System.out.println("Updating client: " + client.getFirstName());
                        System.out.println("1 - Assigned Coach, 2 - Password");
                        int subChoice = sc.nextInt();
                        sc.nextLine();

                        if(subChoice == 1) {
                            System.out.print("Enter Coach Email: ");
                            client.setAssignedCoach(sc.nextLine());
                        } else if(subChoice == 2) {
                            System.out.print("Enter New Password: ");
                            client.setPassword(sc.nextLine());
                        }
                        userRepository.save(client);
                        System.out.println("Client updated!");
                    }
                }, () -> System.out.println("User Not Found!"));
            }

            case 3 -> {
                // Your Admin logic is already good, just added '->' for consistency!
                System.out.print("Enter Admin ID to update: ");
                UUID adminId = UUID.fromString(sc.next());
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
        userRepository.findById(UUID.fromString(userId)).ifPresentOrElse(user -> {
            if(user instanceof Client client){
                System.out.println("Updating client: " + client.getFirstName());
                System.out.println("Current coach is: " + client.getAssignedCoach());
                System.out.println("Enter the new coach you want to assing: ");
                String newCoach = sc.nextLine();
                client.setAssignedCoach(newCoach);
                userRepository.save(client);
                sc.nextLine();
            }
        } ,()-> System.out.println("Invalid userId"));
    }
    public void deleteUser()
    {
        System.out.println("--- Delete User ---");
        System.out.println("Enter the userId you want to delete: ");
        String userId = sc.next();
        System.out.println("Are you sure?: (y/n) ");
        String answer = sc.next();
        if(answer.equalsIgnoreCase("y"))
        {
            userRepository.deleteById(UUID.fromString(userId));
        }
        else{System.out.println("Bye.");}
    }

    }
