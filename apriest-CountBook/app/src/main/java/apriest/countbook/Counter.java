package apriest.countbook;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Alex on 2017-09-28.
 */

public class Counter implements Parcelable, Serializable {

    private int currentValue;
    private int initialValue;
    private String dateString;
    private String name;
    private String comment;

    public Counter() {
        this.initialValue = 0;
        this.currentValue = 0;
        this.dateString = this.initializeDate();
        this.name = "Hold To Edit";
        this.comment = "";
    }

    public int getCurrentValue() {
        return this.currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public int getInitialValue() {
        return this.initialValue;
    }

    public void setInitialValue(int initialValue) {
        this.initialValue = initialValue;
    }

    public String initializeDate() {    //generates date and turns it into YYYY-MM-DD
        Date date = new Date();
        String theString = String.valueOf(date.getYear() + 1900) + "-";
        theString += String.format("%02d", date.getMonth() + 1) + "-";
        theString += String.format("%02d", date.getDate());
        return theString;
    }

    public String getDateString() {
        return this.dateString;
    }

    public void setDateString(String dateString){
        this.dateString = dateString;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void resetValue() {
        this.setCurrentValue(this.getInitialValue());
    }

    public void increment() {
        this.setCurrentValue(this.getCurrentValue() + 1);
        this.setDateString(this.initializeDate());
    }

    public void decrement() {
        if (this.getCurrentValue() > 0) {
            this.setCurrentValue(this.getCurrentValue() - 1);
            this.setDateString(this.initializeDate());
        }
    }

    //https://developer.android.com/reference/android/os/Parcelable.html
    public void writeToParcel(Parcel dest, int flags) {     //stores data in string array
        dest.writeStringArray(new String[]{String.valueOf(this.initialValue), String.valueOf(this.currentValue),
                this.dateString, this.name, this.comment});

    }

    public int describeContents() {
        return 0;
    }   //to implement Parcelable

    public static final Parcelable.Creator<Counter> CREATOR
            = new Parcelable.Creator<Counter>() {
        public Counter createFromParcel(Parcel in) {
            return new Counter(in);
        }

        public Counter[] newArray(int size) {
            return new Counter[size];
        }
    };

    private Counter(Parcel in) {    //Parcelable constructor
        String[] info = new String[5];
        in.readStringArray(info);
        this.initialValue = Integer.parseInt(info[0]);
        this.currentValue = Integer.parseInt(info[1]);
        this.dateString = info[2];
        this.name = info[3];
        this.comment = info[4];
    }

    @Override
    public String toString() {
        return this.getName() + "\n" + this.getDateString() + "\n" + String.valueOf(this.getCurrentValue());
    }

}
