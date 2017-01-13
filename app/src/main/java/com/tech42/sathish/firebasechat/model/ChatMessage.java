package com.tech42.sathish.firebasechat.model;

import com.google.firebase.database.Exclude;

public class ChatMessage {

    private String message;
    private String imageurl;
    private String sender;
    private String recipient;
    private String time;

    private int mRecipientOrSenderStatus;

    public ChatMessage() {
    }

    public ChatMessage(String message, String imageurl, String sender, String recipient, String time) {
        this.message = message;
        this.recipient = recipient;
        this.sender = sender;
        this.imageurl = imageurl;
        this.time = time;
    }

    public void setRecipientOrSenderStatus(int recipientOrSenderStatus) {
        this.mRecipientOrSenderStatus = recipientOrSenderStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getRecipient(){
        return recipient;
    }

    public String getSender(){
        return sender;
    }

    public String getImageurl(){return imageurl;}

    public String getTime(){return time;}

    @Exclude
    public int getRecipientOrSenderStatus() {
        return mRecipientOrSenderStatus;
    }
}
