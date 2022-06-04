package com.example.gymtopia;

import java.util.Date;

public abstract class Person implements Utility{
    private String Name;
    private String PhoneNumber;


    public  Person(String name, String PhoneNum){
        this.Name=name;
        this.PhoneNumber=PhoneNum;

    }

    public String getName() {
        return Name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }




    @Override
    public void ADD() {

    }

    @Override
    public void UPDATE() {

    }

    @Override
    public void REMOVE() {

    }


}
