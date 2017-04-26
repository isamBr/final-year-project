package com.example.sniketn_pc.diabeticmanagement;

/**
 * Created by Sniketn-Pc on 20/02/2017.
 */


public class UserInputs {
    private String day;
    private String time;
    private String input_glucose;
    private String input_insulin;
    private String input_note;


    public UserInputs(String day,String time,String input_glucose, String input_insulin, String input_note) {
        this.setDay(day);
        this.setTime(time);
        this.setInput_glucose(input_glucose);
        this.setInput_insulin(input_insulin);
        this.setInput_note(input_note);


    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getInput_glucose() {
        return input_glucose;
    }

    public void setInput_glucose(String input_glucose) {
        this.input_glucose = input_glucose;
    }

    public String getInput_insulin() {
        return input_insulin;
    }

    public void setInput_insulin(String input_insulin) {
        this.input_insulin = input_insulin;
    }

    public String getInput_note() {
        return input_note;
    }

    public void setInput_note(String input_note) {
        this.input_note = input_note;
    }
}
