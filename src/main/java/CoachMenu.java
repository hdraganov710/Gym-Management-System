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
    public void showMenu(){
        boolean backToLogin = false;
        while(!backToLogin){
            System.out.println("---CoachMenu---");
            System.out.println("Welcome, coach " + coach.getFirstName());
            System.out.println("1. View my clients:  ");
            System.out.println("2. Create Workout Plan:  ");
            System.out.println("0. Logout");
            System.out.println("Please enter your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                   viewClients();
                   break;
                case 2:
                    createWorkout();
                    break;
                    case 0:
                        backToLogin = true;
                        break;

                        default:
                            System.out.println("Invalid choice");

            }
        }
    }
    public void viewClients(){
        UUID currCoach = coach.getUserId();
        System.out.println("---Your assigned clients---");
        List<Client> myClients = userRepository.findAll().stream()
                .filter(user -> user instanceof Client)
                .map(user -> (Client) user)
                .filter(client -> currCoach.equals(client.getAssignedCoach()))
                .toList();
        if (myClients.isEmpty()) {
            System.out.println("No clients assigned to you yet.");
        } else {
            myClients.forEach(c -> System.out.println("- " + c.getFirstName() + " " + c.getLastName()));
        }
    }
    public void createWorkout(){

    }
}
