package com.example.gymtopia;

import java.sql.Date;

public class Members extends Person {
    private double weight;
    private double height;
    private double bill;
    private double bmi;
    private Membership membership;
    private Trainer trainer;
    private String trainerName;
    private String sessionName;
    private Date expiry;
    private Classes  session;
    public Members(String name, String PhoneNum,  double weight, double height, Membership
            membership){
        super(name, PhoneNum);
        this.weight=weight;
        this.height=height;
        this.membership=membership;
    }
    public Members(String name, String PhoneNum,   double bmi,  String coach, String session, Date expiry, Double bill){
        super(name, PhoneNum);
        this.bmi = bmi;
        trainerName = coach;
        sessionName = session;
        this.expiry  =expiry;
        this.bill  =bill;
    }

    public void joinClass(Classes classname){
        session=classname;
    }
    public void joinTrainer( Trainer Ptrainer){
        trainer=Ptrainer;
    }

    public double getWeight() {
        return weight;
    }

    public double getBill() {
        return bill;
    }

    public double getBmi() {
        return bmi;
    }

    public Membership getMembership() {
        return membership;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public String getSessionName() {
        return sessionName;
    }

    public Date getExpiry() {
        return expiry;
    }

    public Classes getSession() {
        return session;
    }

    public double getHeight() {
        return height;
    }
    public static double CalculateBMI(double weight, double height){
        return weight*10000/(height*height);
    }
}
