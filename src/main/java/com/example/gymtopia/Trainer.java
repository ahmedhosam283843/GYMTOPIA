package com.example.gymtopia;

import java.util.Date;

public class Trainer extends Person {
    private Members[] members;
    private Classes[] classes;
    public Trainer(String name, String PhoneNum){
        super(name, PhoneNum);

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
