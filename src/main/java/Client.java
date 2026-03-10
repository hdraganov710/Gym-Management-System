import java.util.UUID;
public class Client extends User{
    private String weight;
    private int age;
    private GENDER gender;
    public Client(UUID userId, String email, String password, ROLE role, String firstName, String lastName,
                  String phoneNumber, String weight, int age, GENDER gender) {
        super(userId,email, password, role, firstName, lastName,phoneNumber);
        this.weight = weight;
        this.age = age;
        this.gender = gender;
    }
    public String getWeight() {return weight;}
    public void setWeight(String weight) {this.weight = weight;}
    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}
    public GENDER getGender() {return gender;}
    public void setGender(GENDER gender) {this.gender = gender;}
    @Override
    public ROLE getRole() {return ROLE.CLIENT;}
    @Override
    public String toString() {
        return "The client is "+age+"years old and is "+gender;
    }
}
