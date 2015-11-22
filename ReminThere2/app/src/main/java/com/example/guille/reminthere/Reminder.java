package com.example.guille.reminthere;

import java.io.Serializable;

/*
 * Created by Guille on 13/11/15.
 */
public class Reminder implements Serializable{

    private static final long serialVersionUID = 0L;

    private String message;
    private String location;
    private boolean location_active;
    private String alarm;
    private boolean alarm_active;
    private boolean checked = false;

    //no sabem si es necesari per tots
    public Reminder(String message) {
        this.message = message;
    }

    public String  getMessage()       { return message; }
    public String getLocation()     {return location;}
    public String getAlarm()          {return alarm;}

    public boolean isAlarm_active() {return alarm_active;}
    public boolean isLocation_active() {return location_active;}
    public boolean isChecked()     { return checked; }

    public void    toggleChecked() { checked = !checked; }

    public void setCheckState(boolean state){
        checked = state;
    }


}
