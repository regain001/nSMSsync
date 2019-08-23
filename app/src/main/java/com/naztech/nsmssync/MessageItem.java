package com.naztech.nsmssync;

public class MessageItem {
    private String message;
    private String number;
    private String Date;
    private String Status;

    public MessageItem(String message, String number, String date, String status) {
        this.message = message;
        this.number = number;
        Date = date;
        Status = status;
    }

    public void changeMessage(String msg){
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
