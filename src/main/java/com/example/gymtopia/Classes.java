package com.example.gymtopia;


import java.util.Date;

public class Classes implements Utility{
    private String Name;
    private Date classDay;
    private String slot;
    private Trainer trainer;
    private Members[] members;
    public Classes(String name, Date Day, String timeslot, Trainer trainer){
        this.Name=name;
        this.classDay=Day;
        this.slot=timeslot;
        this.trainer=trainer;

    }

    public String getName() {
        return Name;
    }

    public Date getClassDay() {
        return classDay;
    }

    public String getSlot() {
        return slot;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public Members[] getMembers() {
        return members;
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
