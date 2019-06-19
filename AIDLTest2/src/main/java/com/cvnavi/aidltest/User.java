package com.cvnavi.aidltest;

import android.os.Parcel;
import android.os.Parcelable;

public final class User implements Parcelable {
    public String name;
    public int age;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(age);
    }

    public static final Creator<User> CREATOR = new
            Creator<User>() {
                public User createFromParcel(Parcel in) {
                    return new User(in);
                }

                public User[] newArray(int size) {
                    return new User[size];
                }
            };

    public User() {

    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private User(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
