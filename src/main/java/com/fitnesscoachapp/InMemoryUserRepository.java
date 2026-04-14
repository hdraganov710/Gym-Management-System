package com.fitnesscoachapp;

import java.util.*;

public class InMemoryUserRepository implements UserRepository {
    private final Map<UUID, User> database = new HashMap<>();

    @Override
    public Optional<User> findById(UUID id) { return Optional.ofNullable(database.get(id)); }

    @Override
    public Optional<User> findByEmail(String email) {
        return database.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public void save(User user) { database.put(user.getUserId(), user); }

    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return database.values().stream()
                .filter(user -> user.getPhoneNumber().equals(phoneNumber))
                .findFirst();
    }

    @Override
    public Optional<User> deleteById(UUID id) {
        User userToRemove = database.get(id);
        if (userToRemove != null) {
            database.remove(id);
        }
        return Optional.ofNullable(userToRemove);
    }

    @Override
    public List<User> findAll() { return new ArrayList<>(database.values()); }

    @Override
    public Optional<User> update(UUID id, RegistrationRequest updateData) {
        User existingUser = database.get(id);
        if (existingUser == null) return Optional.empty();
        if (updateData.getFirstName() != null) existingUser.setFirstName(updateData.getFirstName());
        if (updateData.getLastName() != null) existingUser.setLastName(updateData.getLastName());
        if (updateData.getPhoneNumber() != null) existingUser.setPhoneNumber(updateData.getPhoneNumber());

        if (existingUser instanceof Client client && updateData.getWeight() != null) {
            client.setWeight(updateData.getWeight());
        } else if (existingUser instanceof Coach coach && updateData.getSpecialty() != null) {
            coach.setSpecialty(updateData.getSpecialty());
        }
        return Optional.of(existingUser);
    }
}