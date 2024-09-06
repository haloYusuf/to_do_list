package com.example.todolist.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Content implements Parcelable{

    private int id;
    private String data;

    public Content(){

    }

    private Content(Parcel in) {
        id = in.readInt();
        data = in.readString();
    }

    public static final Creator<Content> CREATOR = new Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel in) {
            return new Content(in);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.data);
    }
}
