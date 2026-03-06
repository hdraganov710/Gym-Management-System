import java.util.UUID;

public class Coach extends User {
    private int experience;
    private String specialty;
    public Coach(UUID userId, String email, String password, ROLE role, String firstName, String lastName,
                 String phoneNumber, int experience, String specialty) {
        super(userId,email, password, role, firstName, lastName, phoneNumber);
        this.experience = experience;
        this.specialty = specialty;
    }
    public int getExperience() {return experience;}
    public void setExperience(int experience) {this.experience = experience;}
    public String getSpecialty() {return specialty;}
    public void setSpecialty(String specialty) {this.specialty = specialty;}
    @Override
    public ROLE getRole() {return ROLE.COACH;}
    @Override
    public String toString(){
        return "This coach has "+this.experience+" experience and his specialty is "+this.specialty;
    }
}
