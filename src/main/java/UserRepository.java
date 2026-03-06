import java.util.*;
public interface UserRepository {

     void save(User user);
     Optional<User> findById(UUID id);
     Optional<User> findByEmail(String email);
     Optional<User> findByPhoneNumber(String phoneNumber);
     Optional<User> deleteById(UUID id);
     List<User> findAll();
     Optional<User> update(UUID id,RegistrationRequest updateData);
}
