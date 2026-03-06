import java.util.*;
public abstract class User {
    private final UUID userId;
    private String email;
    private String password;
    private ROLE role;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    public User(UUID userId,String email, String password, ROLE role, String firstName, String lastName,
                String phoneNumber)throws InvalidEmailException {
        this.email = email;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
    }
    public UUID getUserId() {return userId;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public void setRole(ROLE role) {this.role = role;}
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public abstract ROLE getRole();
    @Override
    public String toString() {
        return "The user's name is "+firstName+" "+lastName;
    }

}
