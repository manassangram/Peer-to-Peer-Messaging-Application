package com.example.manassangram.myapplication5;

public class Contact {

    //private variables
    public int _id;
    public String _name;
    public String _phone_number;
    public String chatFile;

    // Empty constructor
    public Contact()
    {

    }



    public Contact(int id, String name, String phonenumber)
    {
        this._id = id;
        this._name = name;
        this._phone_number = phonenumber;
        this.chatFile = _phone_number+".txt";

    }

    public Contact(int id, String name, String _phone_number, String ipAddress){
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
        this.chatFile = _phone_number+".txt";

    }


    public Contact(String name, String _phone_number, String ipAddress){
        this._name = name;
        this._phone_number = _phone_number;
        this.chatFile = _phone_number+".txt";

    }

    // constructor
    public Contact(String name, String _phone_number){
        this._name = name;
        this._phone_number = _phone_number;
        this.chatFile = _phone_number+".txt";

    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){

        this._name = name;
        setPhoneNumber(_phone_number+".txt");
    }

    // getting phone number
    public String getPhoneNumber(){
        return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }

    public String getchatFile(){
        return this.chatFile;
    }

    public void setchatFile(String chatFile){
        this.chatFile = chatFile;
    }


}
