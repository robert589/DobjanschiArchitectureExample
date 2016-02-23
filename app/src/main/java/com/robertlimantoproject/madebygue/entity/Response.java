package com.robertlimantoproject.madebygue.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 24/7/2015.
 */
public class Response implements Parcelable {


    public int status;

    public String message;

    public Parcelable object;

    public Parcelable[] objectList;

    public Response(int status, String message){

        this.status = status;

        this.message = message;
    }

    public Response(int status, String message, Parcelable object){

        this.status = status;

        this.message = message;

        this.object = object;
    }

    public Response(int status, String message, Parcelable[] objectList){

        this.status = status;

        this.message = message;

        this.objectList = objectList;
    }

    /*
   //    PARCELABLE IMPLEMENTATION
   */
    public void writeToParcel(Parcel out, int flags) {

        out.writeInt(status);
        out.writeString(message);
        out.writeParcelable(object, flags);
        out.writeParcelableArray(objectList, flags);
    }

    public int describeContents() {
        return 0;
    }

    private Response(Parcel in) {
        this.status = in.readInt();
        this.message = in.readString();
        this.object = (Parcelable) in.readParcelable(Parcelable.class.getClassLoader());
        this.objectList = in.readParcelableArray(Parcelable.class.getClassLoader());
    }

    public static final Parcelable.Creator<Response> CREATOR = new Parcelable.Creator<Response>() {
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        public Response[] newArray(int size) {
            return new Response[size];
        }
    };

    /**
     * GETTER AND SETTER
     * @return
     */
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Parcelable object) {
        this.object = object;
    }

    public Parcelable[] getObjectList() {
        return objectList;
    }

    public void setObjectList(Parcelable[] objectList) {
        this.objectList = objectList;
    }
}
