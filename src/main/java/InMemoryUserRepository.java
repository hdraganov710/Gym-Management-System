import java.util.*;

public class InMemoryUserRepository implements UserRepository {
    private final Map<UUID, User> database = new HashMap<>();
    @Override
    public Optional<User> findById(UUID id) {return Optional.ofNullable(database.get(id));}
    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(database.get(email));
    }
    @Override
    public void save(User user) {
        database.put(user.getUserId(),user);
    }
    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {return Optional.ofNullable(database.get(phoneNumber));}
    @Override
    public Optional<User> deleteById(UUID id) {
        User userToRemove = database.get(id);
        if (userToRemove != null) {
            database.remove(id);
        }
        return Optional.ofNullable(userToRemove);
    }
    @Override
    public List<User> findAll(){
        return new ArrayList<>(database.values());
    }
    @Override
    public Optional<User> update(UUID id,RegistrationRequest updateData){
        User existingUser = database.get(id);
        if (existingUser == null) return Optional.empty();
        if(updateData.firstName != null) existingUser.setFirstName(updateData.firstName);
        if (updateData.lastName != null) existingUser.setLastName(updateData.lastName);
        if (updateData.phoneNumber != null) existingUser.setPhoneNumber(updateData.phoneNumber);

        if(existingUser instanceof Client client && updateData.weight != null)
        {
            client.setWeight(updateData.weight);
        }
        else if(existingUser instanceof Coach coach && updateData.specialty != null){
            coach.setSpecialty(updateData.specialty);
        }
        return Optional.of(existingUser);
    }

}
