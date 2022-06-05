package com.example.gymtopia;

public class Diet {
    private Double age, weight, height;
    private String activity, gender;

    public Diet(Double age, Double weight, Double height, String activity, String gender) {
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.activity = activity;
        this.gender = gender;
    }

    public double calculateCalories(){
        double x= 0;
        if(gender=="Male"){
            x = 10*weight + 6.25*height -5*age + 5;
        }
        else{
            x = 10*weight + 6.25*height -5*age -161;
        }

        if(activity =="Sedentary")x*=1.2;
        else if(activity == "Lightly active")x*=1.375;
        else if(activity == "Moderately activee")x*=1.55;
        else if(activity == "Very active")x*=1.725;
        else x*=1.9;

        return Math.round(x);
    }
    public double calculateProtein(){
        double x= 0;


        if(activity =="Sedentary")x=0.8*weight;
        else if(activity == "Lightly active")x=1.2*weight;
        else if(activity == "Moderately activee")x=1.5*weight;
        else if(activity == "Very active")x=2*weight;
        else x=2.5*weight;
        if(gender=="Male"){
            x += 50;
        }
        return Math.round(x);
    }

    public double calculateCarbs(){
        double x= 0;


        if(activity =="Sedentary")x=2*weight;
        else if(activity == "Lightly active")x=2.5*weight;
        else if(activity == "Moderately activee")x=3*weight;
        else if(activity == "Very active")x=3.5*weight;
        else x=4*weight;
        if(gender=="Male"){
            x += 70;
        }
        return Math.round(x);
    }
    public double calculateFats(){
        double x= 0;

        if(activity =="Sedentary")x=0.6*weight;
        else if(activity == "Lightly active")x=1*weight;
        else if(activity == "Moderately activee")x=1.3*weight;
        else if(activity == "Very active")x=1.8*weight;
        else x=2.2*weight;
        if(gender=="Male"){
            x += 30;
        }
        return Math.round(x);
    }


    public Double getAge() {
        return age;
    }

    public Double getWeight() {
        return weight;
    }

    public Double getHeight() {
        return height;
    }

    public String getActivity() {
        return activity;
    }

    public String getGender() {
        return gender;
    }
}
