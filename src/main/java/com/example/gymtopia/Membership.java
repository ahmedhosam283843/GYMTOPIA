package com.example.gymtopia;


import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.DAYS;

public class Membership implements Utility {
    private String membershipType;
    private Date ExpiryDate;

    private Classes session;

    public Membership(Date expiryDate, Classes session) {
        ExpiryDate = expiryDate;
        this.session = session;
    }

    public String getMembershipType() {
        return membershipType;
    }



    public Date getExpiryDate() {
        return ExpiryDate;
    }

    public float getBill() {
        long diff = ExpiryDate.getTime() - (new Date()).getTime();
        float days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        if(session.getName()=="gymnastics")
            return Math.max(0, days*10);
        else if (session.getName()=="zomba")
            return Math.max(0,days*15);
        else if(session.getName()=="Kick boxing")
            return Math.max(0,days*12);
        else if(session.getName()=="Karate")
            return Math.max(0,days*8);
        else return Math.max(0,days*5);

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
