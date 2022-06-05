package com.example.gymtopia;


import java.util.Date;

public class Classes implements Utility{
    private String Name;
    private Date classDay;
    private String weekday;
    private String slot;
    private Trainer trainer;
    private String coachName;
    private Members[] members;
    private int memberNumber;
    public Classes(String name, Date Day, String timeslot, Trainer trainer){
        this.Name=name;
        this.classDay=Day;
        this.slot=timeslot;
        this.trainer=trainer;

    }

    public Classes(String name, String weekday, String slot, String coachName, int memberNumber) {
        Name = name;
        this.weekday = weekday;
        this.slot = slot;
        this.coachName = coachName;
        this.memberNumber = memberNumber;
    }

    public String getWeekday() {
        return weekday;
    }

    public String getCoachName() {
        return coachName;
    }

    public int getMemberNumber() {
        return memberNumber;
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
