package com.example.bmop.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

public class Message implements Parcelable {

    private String message;
    private String userId;
    private Timestamp sentOn;
    private String destId;

    public Message(String message, String userId, Timestamp sentOn) {
        this.message = message;
        this.userId = userId;
        this.sentOn = sentOn;

    }
    public Message(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getSentOn() {
        return sentOn;
    }

    public void setSentOn(Timestamp sentOn) {
        this.sentOn = sentOn;
    }

    public String getDestId() {
        return destId;
    }

    public void setDestId(String destId) {
        this.destId = destId;
    }

       protected Message(Parcel in) {
        message = in.readString();
        userId = in.readString();
        sentOn = (Timestamp) in.readValue(Timestamp.class.getClassLoader());
        destId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeString(userId);
        dest.writeValue(sentOn);
        dest.writeString(destId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
