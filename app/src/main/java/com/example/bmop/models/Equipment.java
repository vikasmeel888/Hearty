package com.example.bmop.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Equipment implements Parcelable {
    private String equipName;
    private String equipCompany;
    private String equipQuantity;
    private String equipPrice;
    private String equipCategory;
    private String equipLoc;
    public String equip_uploaderId;
    public String docId;

    public Equipment(String equipname, String equipcompany, String equipquantity, String equipprice, String equipcategory, String equiploc, String equip_uploaderid) {
        this.equipName = equipname;
        this.equipCompany = equipcompany;
        this.equipQuantity = equipquantity;
        this.equipPrice = equipprice;
        this.equipLoc = equiploc;
        this.equipCategory = equipcategory;
        this.equip_uploaderId = equip_uploaderid;
    }
    public Equipment(){

    }

    protected Equipment(Parcel in) {
        equipName = in.readString();
        equipCompany = in.readString();
        equipQuantity = in.readString();
        equipPrice = in.readString();
        equipCategory = in.readString();
        equipLoc = in.readString();
        equip_uploaderId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(equipName);
        dest.writeString(equipCompany);
        dest.writeString(equipQuantity);
        dest.writeString(equipPrice);
        dest.writeString(equipCategory);
        dest.writeString(equipLoc);
        dest.writeString(equip_uploaderId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Equipment> CREATOR = new Parcelable.Creator<Equipment>() {
        @Override
        public Equipment createFromParcel(Parcel in) {
            return new Equipment(in);
        }

        @Override
        public Equipment[] newArray(int size) {
            return new Equipment[size];
        }
    };

    @Override
    public String toString() {
        return "Medicine{" +
                ", equipName='" + equipName + '\'' +
                ", equipCompany='" + equipCompany + '\'' +
                ", equipQuantity='" + equipQuantity + '\'' +
                ", equipCategory=" + equipCategory +
                ", equipPrice='" + equipPrice + '\'' +
                ", equipLoc='" + equipLoc + '\'' +
                ", equip_uploaderId='" + equip_uploaderId + '\'' +
                '}';
    }

    public String getEquipName() {
        return equipName;
    }
    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public String getEquipCompany() {
        return equipCompany;
    }
    public void setEquipCompany(String equipCompany) {
        this.equipCompany = equipCompany;
    }

    public String getEquipQuantity() {
        return equipQuantity;
    }
    public void setEquipQuantity(String equipQuantity) {
        this.equipQuantity = equipQuantity;
    }

    public String getEquipPrice() {
        return equipPrice;
    }
    public void setEquipPrice(String equipPrice) {
        this.equipPrice = equipPrice;
    }

    public String getEquip_uploaderId() { return equip_uploaderId; }
    public void setEquip_uploaderId(String equip_uploaderId) { this.equip_uploaderId = equip_uploaderId; }

    public String getEquipCategory() { return equipCategory;  }
    public void setEquipCategory(String equipCategory) { this.equipCategory = equipCategory; }

    public String getEquipLoc() {  return equipLoc; }
    public void setEquipLoc(String equipLoc) { this.equipLoc = equipLoc; }

}