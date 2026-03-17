import java.util.UUID;

public class RegistrationRequest {

    public  String email;
    public String password;
    public ROLE role;
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public int experience;
    public String specialty;
    public String weight;
    public int age;
    public GENDER gender;
    public int adminLevel;
    public UUID employeeId;
    public String assignedCoach;
    public WorkoutWeek wWeek;

    public RegistrationRequest() {}

}
