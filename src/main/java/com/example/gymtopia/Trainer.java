package com.example.gymtopia;

import java.util.Date;

public class Trainer extends Person {
    private Members[] members;
    private Classes[] classes;
    private String username;
    private String password;
    private int numclasses;
    private double salary;

    public Trainer(String name, String PhoneNum){
        super(name, PhoneNum);

    }


    public Trainer(String name, String PhoneNum, String username, String password, int numclasses, double salary) {
        super(name, PhoneNum);
        this.username = username;
        this.password = password;
        this.numclasses = numclasses;
        this.salary = salary;
    }


    public int getNumclasses() {
        return numclasses;
    }

    public static double calculateSalary(int numclasses){
        return 1500 + numclasses*300;
    }

    public double getSalary() {
        return salary;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Members[] getMembers() {
        return members;
    }

    public Classes[] getClasses() {
        return classes;
    }
    public float Calculatesalary(){
        return 1000;
    }

}
