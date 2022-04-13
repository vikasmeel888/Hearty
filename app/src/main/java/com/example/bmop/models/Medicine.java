package com.example.bmop.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Medicine implements Parcelable{

    private String medName;
    private String medCompany;
    private String medDose;
    private String medCategory;
    private String medPrice;
    private String medLoc;
    public String uploaderId;
    public String docId;

    public Medicine(){

    }

    public Medicine(String medname, String medcompany, String meddose, String medprice, String medcategory, String medloc, String uploader_id){
        this.medName = medname;
        this.medCompany = medcompany;
        this.medDose = meddose;
        this.medPrice = medprice;
        this.medCategory = medcategory;
        this.medLoc = medloc;
        this.uploaderId = uploader_id;
    }

    protected Medicine(Parcel in) {
        medName = in.readString();
        medCompany = in.readString();
        medDose = in.readString();
        medCategory = in.readString();
        medPrice = in.readString();
        medLoc = in.readString();
        uploaderId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(medName);
        dest.writeString(medCompany);
        dest.writeString(medDose);
        dest.writeString(medCategory);
        dest.writeString(medPrice);
        dest.writeString(medLoc);
        dest.writeString(uploaderId);

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Medicine> CREATOR = new Parcelable.Creator<Medicine>() {
        @Override
        public Medicine createFromParcel(Parcel in) {
            return new Medicine(in);
        }

        @Override
        public Medicine[] newArray(int size) {
            return new Medicine[size];
        }
    };

    @Override
    public String toString() {
        return "Medicine{" +
                ", medName='" + medName + '\'' +
                ", medCompany='" + medCompany + '\'' +
                ", medDose='" + medDose + '\'' +
                ", medCategory='" + medCategory + '\'' +
                ", medPrice='" + medPrice + '\'' +
                ", medLoc='" + medLoc + '\'' +
                ", uploaderId='" + uploaderId + '\'' +
                '}';
    }

    public String getMedName() {
        return medName;
    }
    public void setMedName(String medName) {
        this.medName = medName;
    }


    public String getMedCompany() {
        return medCompany;
    }
    public void setMedCompany(String medCompany) {
        this.medCompany = medCompany;
    }

    public String getMedDose() {
        return medDose;
    }
    public void setMedDose(String medDose) {
        this.medDose = medDose;
    }


    public String getUploaderId() { return uploaderId; }
    public void setUploaderId(String uploaderId) { this.uploaderId = uploaderId; }

    public String getMedPrice() {
        return medPrice;
    }
    public void setMedPrice(String medPrice) {
        this.medPrice = medPrice;
    }

    public String getMedCategory() { return medCategory; }
    public void setMedCategory(String medCategory) {  this.medCategory = medCategory; }


    public String getMedLoc() {  return medLoc;  }
    public void setMedLoc(String medLoc) { this.medLoc = medLoc; }

}

