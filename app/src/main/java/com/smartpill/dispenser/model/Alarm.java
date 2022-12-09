package com.smartpill.dispenser.model;

import com.google.gson.annotations.SerializedName;

public class Alarm {

    @SerializedName("alarm_id")
    private String alarmId;

    @SerializedName("alarm_name")
    private String alarmName;

    @SerializedName("alarm_time")
    private String alarmTime;

    public Alarm(String alarmId, String alarmName, String alarmTime) {
        this.alarmId = alarmId;
        this.alarmName = alarmName;
        this.alarmTime = alarmTime;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }
}
