package com.fitnesscoachapp;

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
            throw new WrongCredentialsException("Invalid email or password");
        }
        return user;
    }

    public User register(RegistrationRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()
                || userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new AlreadyTakenUsernameorEmailException("One of the credentials is already taken!");
        }
        User newUser;

        switch (request.getRole()) {
            case ROLE.ADMIN:
                newUser = new Admin(request.getAdminLevel(), request.getEmployeeId(), UUID.randomUUID(),
                        request.getEmail(), request.getPassword(), ROLE.ADMIN,
                        request.getFirstName(), request.getLastName(), request.getPhoneNumber());
                break;
            case ROLE.COACH:
                newUser = new Coach(UUID.randomUUID(), request.getEmail(), request.getPassword(),
                        ROLE.COACH, request.getFirstName(), request.getLastName(), request.getPhoneNumber(),
                        request.getExperience(), request.getSpecialty());
                break;
            case ROLE.CLIENT:
                newUser = new Client(UUID.randomUUID(), request.getEmail(), request.getPassword(),
                        ROLE.CLIENT, request.getFirstName(), request.getLastName(), request.getPhoneNumber(),
                        request.getWeight(), request.getAge(), request.getGender(),
                        request.getAssignedCoachId(), request.getwWeek());
                break;
            default:
                throw new UserNotFoundException("Invalid role");
        }
        userRepository.save(newUser);
        return newUser;
    }

    public void deleteUser(UUID adminId, UUID userId) {
        User user = userRepository.findById(adminId)
                .orElseThrow(() -> new UserNotFoundException("Admin not found!"));
        if (!user.getRole().equals(ROLE.ADMIN)) {
            throw new UnauthorizedException("Only admins can delete users");
        }
        userRepository.deleteById(userId);
    }

    public List<User> getAllUsersByRole(ROLE role) {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == role)
                .toList();
    }

    public void updateProfile(UUID id, String oldPassword, String newPassword, Double newWeight) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("There is no such user!"));
        if (newPassword != null && !newPassword.isEmpty()) {
            if (!user.getPassword().equals(oldPassword)) {
                throw new UnauthorizedException("Passwords do not match!");
            }
            if (user.getPassword().equals(newPassword)) {
                throw new UnauthorizedException("The new password should not be the same as the old one!");
            }
            user.setPassword(newPassword);
            System.out.println("Your password has been changed");
        }
        if (newWeight != null) {
            if (user instanceof Client client) {
                client.setWeight(newWeight);
            }
        }
        userRepository.save(user);
    }
}