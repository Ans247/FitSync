package com.example.fitsync;

public class Message {

    public static String SENT_BY_ME ="me";
    public static String SENT_BY_BOT ="bot";


    String Message;
    String sentBy;

    public static String getSentByMe() {
        return SENT_BY_ME;
    }

    public static void setSentByMe(String sentByMe) {
        SENT_BY_ME = sentByMe;
    }

    public static String getSentByBot() {
        return SENT_BY_BOT;
    }

    public static void setSentByBot(String sentByBot) {
        SENT_BY_BOT = sentByBot;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public Message(String message, String sentBy) {
        Message = message;
        this.sentBy = sentBy;
    }
}
