package com.example.dongwoo.produnt;

public class User {
    private String name;
    private int cash;
    private String mac;
    private float rate;
    private double x;
    private double y;
    private String uid;
    private String phoneNumber;
    private String provider;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getCash(){
        return cash;
    }
    public void setCash(int cash){
        this.cash = cash;
    }

    public float getRate(){
        return rate;
    }
    public void setRate(float rate){
        this.rate = rate;
    }

    public double getX(){
        return x;
    }
    public void setX(double x){
        this.x = x;
    }

    public double getY(){
        return y;
    }
    public void setY(double y){
        this.y = y;
    }

    public String getUid(){
        return uid;
    }
    public void setUid(String uid){
        this.uid = uid;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getProvider(){
        return provider;
    }
    public void setProvider(String provider){
        this.provider = provider;
    }

    @Override
    public String toString() {
        return name+" "+ cash +" "+ mac +" "+ rate +" "+ x +" "+ y +" "+ uid +" "+ phoneNumber +" "+ provider;
    }
}