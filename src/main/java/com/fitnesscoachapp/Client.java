package com.fitnesscoachapp;

import java.util.UUID;

public class Client extends User {
    private Double weight;
    private int age;
    private GENDER gender;
    private UUID assignedCoach;
    private WorkoutWeek wWeek;

    public Client(UUID userId, String email, String password, ROLE role, String firstName, String lastName,
                  String phoneNumber, Double weight, int age, GENDER gender, UUID assignedCoach, WorkoutWeek wWeek) {
        super(userId, email, password, role, firstName, lastName, phoneNumber);
        this.weight = weight;
        this.age = age;
        this.gender = gender;
        this.assignedCoach = assignedCoach;
        this.wWeek = wWeek;
    }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public GENDER getGender() { return gender; }
    public void setGender(GENDER gender) { this.gender = gender; }
    public UUID getAssignedCoach() { return assignedCoach; }
    public void setAssignedCoach(UUID assignedCoach) { this.assignedCoach = assignedCoach; }
    public WorkoutWeek getwP() { return wWeek; }
    public void setwP(WorkoutWeek wWeek) { this.wWeek = wWeek; }

    @Override
    public ROLE getRole() { return ROLE.CLIENT; }

    @Override
    public String toString() {
        return "The client is " + age + " years old and is " + gender;
    }
}