package com.example.chatbot;

public class Message {

    //Attributes
    private String message;
    private String sender;

    //Constructor
    public Message(String message, String sender){
        this.message = message;
        this.sender = sender;
    }

    //Gets and sets
    public String getMessage(){
        return message;
    }

    public String getSender(){
        return sender;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setSender(String sender){
        this.sender = sender;
    }
}
