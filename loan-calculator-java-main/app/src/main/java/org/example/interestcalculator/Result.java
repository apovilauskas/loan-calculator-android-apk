package org.example.interestcalculator;

import java.util.ArrayList;

public class Result {
    ArrayList<Payment> list;
    String message;

    public Result(ArrayList<Payment> list, String message) {
        this.list = list;
        this.message = message;
    }

    public ArrayList<Payment> getList() {
        return list;
    }

    public void setList(ArrayList<Payment> list) {
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
