package com.fitnesscoachapp;

import java.util.*;
public class Admin extends User {
    private int adminLevel;
    private UUID employeeId;
    public Admin(int adminLevel, UUID employeeId,UUID userId,String email, String password, ROLE role, String firstName, String lastName,
                  String phoneNumber) {
        super(userId,email, password, role, firstName, lastName, phoneNumber);
        this.adminLevel = adminLevel;
        this.employeeId = employeeId;
    }
    public int getAdminLevel() {return adminLevel;}
    public void setAdminLevel(int adminLevel) {this.adminLevel = adminLevel;}
    public UUID getEmployeeId() {return employeeId;}
    public void setEmployeeId(UUID employeeId) {this.employeeId = employeeId;}
    @Override
    public ROLE getRole(){
        return ROLE.ADMIN;
    }
    @Override
    public String toString(){
        return "The admin level is "+this.adminLevel+" and his ID is "+this.employeeId;
    }
}
