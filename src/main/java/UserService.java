import java.util.*;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Invalid email or password"));
        if (!user.getPassword().equals(password)) {
            throw new UserNotFoundException("Invalid email or password");
        }
        return user;
    }

    public User register(RegistrationRequest request) {
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        if (userRepository.findByEmail(request.email).isPresent() || userRepository.findByPhoneNumber(request.email).isPresent()) {
            throw new AlreadyTakenUsernameorEmailException("One of the credentials is already taken!");
        }
        User newUser;

        switch (request.role) {

            case ROLE.ADMIN:
                newUser = new Admin(request.adminLevel, request.employeeId, UUID.randomUUID(), request.email,
                        request.password, ROLE.ADMIN, request.firstName, request.lastName, request.phoneNumber);
                break;
            case ROLE.COACH:
                newUser = new Coach(UUID.randomUUID(), request.email, request.password,
                        ROLE.COACH, request.firstName, request.lastName, request.phoneNumber, request.experience,
                        request.specialty);
                break;
            case ROLE.CLIENT:
                newUser = new Client(UUID.randomUUID(), request.email, request.password,
                        ROLE.CLIENT, request.firstName, request.lastName, request.phoneNumber, request.weight,
                        request.age, request.gender,request.assignedCoach);

                break;
            default:
                throw new UserNotFoundException("Invalid role");
        }
        userRepository.save(newUser);
        return newUser;
    }

    public void deleteUser(UUID employeeId, UUID userId) {
        User user = userRepository.findById(employeeId).orElseThrow(()
                -> new UserNotFoundException("Admin not found!"));
        if (!user.getRole().equals(ROLE.ADMIN)) {
            throw new UnauthorizedException("Only admins can delete users");
        }
        userRepository.deleteById(userId);
    }
    public List<User> getAllUsersByRole(ROLE role) {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream().filter(user -> user.getRole() == role)
                .toList();
    }
    public void updateProfile(UUID id, String oldPassword, String newPassword, String newWeight) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException("There is no such user!"));
        if (newPassword != null && !newPassword.isEmpty()) {
            if(!user.getPassword().equals(oldPassword)) {
                throw new UnauthorizedException("Passwords do not match!");
            }
            if(user.getPassword().equals(newPassword))
            {
               throw new UnauthorizedException("The new password should not be the same as the old one!");
            }
            user.setPassword(newPassword);
            System.out.println("Your password has been changed");
        }
        if(newWeight != null && !newWeight.isEmpty()) {
            if(user instanceof Client client) {
                client.setWeight(newWeight);
            }
        }
        userRepository.save(user);

    }
}

