package com.fitnesscoachapp;

import java.util.UUID;

public class RegistrationRequest {

    private String email;
    private String password;
    private ROLE role;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    private int experience;
    private String specialty;

    private Double weight;
    private int age;
    private GENDER gender;
    private UUID assignedCoachId;
    private WorkoutWeek wWeek;

    private int adminLevel;
    private UUID employeeId;

    public RegistrationRequest() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public ROLE getRole() { return role; }
    public void setRole(ROLE role) { this.role = role; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public int getExperience() { return experience; }
    public void setExperience(int experience) { this.experience = experience; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public GENDER getGender() { return gender; }
    public void setGender(GENDER gender) { this.gender = gender; }

    public UUID getAssignedCoachId() { return assignedCoachId; }
    public void setAssignedCoachId(UUID assignedCoachId) { this.assignedCoachId = assignedCoachId; }

    public WorkoutWeek getwWeek() { return wWeek; }
    public void setwWeek(WorkoutWeek wWeek) { this.wWeek = wWeek; }

    public int getAdminLevel() { return adminLevel; }
    public void setAdminLevel(int adminLevel) { this.adminLevel = adminLevel; }

    public UUID getEmployeeId() { return employeeId; }
    public void setEmployeeId(UUID employeeId) { this.employeeId = employeeId; }
}