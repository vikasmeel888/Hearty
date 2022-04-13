package com.example.bmop.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Blood implements Parcelable {

    private String patientName;
    private String age;
    private String sex;
    private String bloodGroup;
    private String bloodLocation;
    private String bloodUnits;
    public String blood_uploaderId;
    public String docId;

    public Blood(String patientname, String patientage, String patientsex, String bloodgroup, String bloodlocation, String bloodunit, String blood_uploaderid) {
        this.patientName = patientname;
        this.age = patientage;
        this.sex = patientsex;
        this.bloodGroup = bloodgroup;
        this.bloodLocation = bloodlocation;
        this.bloodUnits = bloodunit;
        this.blood_uploaderId = blood_uploaderid;
    }
    public Blood(){

    }

    protected Blood(Parcel in) {
        patientName = in.readString();
        age = in.readString();
        sex = in.readString();
        bloodGroup = in.readString();
        bloodLocation = in.readString();
        bloodUnits = in.readString();
        blood_uploaderId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(patientName);
        dest.writeString(age);
        dest.writeString(sex);
        dest.writeString(bloodGroup);
        dest.writeString(bloodLocation);
        dest.writeString(bloodUnits);
        dest.writeString(blood_uploaderId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Blood> CREATOR = new Parcelable.Creator<Blood>() {
        @Override
        public Blood createFromParcel(Parcel in) {
            return new Blood(in);
        }

        @Override
        public Blood[] newArray(int size) {
            return new Blood[size];
        }
    };

    @Override
    public String toString() {
        return "Blood{" +
                ", patientName='" + patientName + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", bloodLocation='" + bloodLocation + '\'' +
                ", bloodUnits='" + bloodUnits + '\'' +
                ", blood_uploaderId='" + blood_uploaderId + '\'' +
                '}';
    }

    public String getBloodGroup() {
        return bloodGroup;
    }
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getBloodLocation() {
        return bloodLocation;
    }
    public void setBloodLocation(String bloodLocation) {
        this.bloodLocation = bloodLocation;
    }

    public String getBlood_uploaderId() {
        return blood_uploaderId;
    }
    public void setBlood_uploaderId(String blood_uploaderId) { this.blood_uploaderId = blood_uploaderId; }

    public String getBloodUnits() { return bloodUnits; }
    public void setBloodUnits(String bloodUnits) { this.bloodUnits = bloodUnits;  }

    public String getPatientName() { return patientName;    }
    public void setPatientName(String patientName) {  this.patientName = patientName;    }

    public String getAge() {  return age;    }
    public void setAge(String age) { this.age = age;    }

    public String getSex() {  return sex; }
    public void setSex(String sex) {  this.sex = sex; }
}