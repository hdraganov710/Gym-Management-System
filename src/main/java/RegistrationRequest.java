import org.example.GENDER;

import java.util.UUID;

public class RegistrationRequest {
    //User parameters

    public  String email;
    public String password;
    public ROLE role;
    public String firstName;
    public String lastName;
    public String phoneNumber;
    //Coach parameters
    public int experience;
    public String specialty;
    //Client parameters
    public String weight;
    public String age;
    public GENDER gender;
    public int adminLevel;
    public UUID employeeId;
    public RegistrationRequest() {}

}
