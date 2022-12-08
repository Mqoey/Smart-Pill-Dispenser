package com.smartpill.dispenser.model;

import com.google.gson.annotations.SerializedName;

public class Pill {

    @SerializedName("pill_id")
    private String pillId;

    @SerializedName("pill_name")
    private String pillName;

    @SerializedName("pill_description")
    private String pillDescription;

    @SerializedName("pill_consumption")
    private String pillConsumption;

    @SerializedName("pill_image")
    private String pillImage;


    public Pill(String pillId, String pillName, String pillDescription, String pillConsumption, String pillImage) {
        this.pillId = pillId;
        this.pillName = pillName;
        this.pillDescription = pillDescription;
        this.pillConsumption = pillConsumption;
        this.pillImage = pillImage;
    }

    public String getPillId() {
        return pillId;
    }

    public void setPillId(String pillId) {
        this.pillId = pillId;
    }

    public String getPillName() {
        return pillName;
    }

    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    public String getPillDescription() {
        return pillDescription;
    }

    public void setPillDescription(String pillDescription) {
        this.pillDescription = pillDescription;
    }

    public String getPillConsumption() {
        return pillConsumption;
    }

    public void setPillConsumption(String pillConsumption) {
        this.pillConsumption = pillConsumption;
    }

    public String getPillImage() {
        return pillImage;
    }

    public void setPillImage(String pillImage) {
        this.pillImage = pillImage;
    }
}
