package com.kaotiko.overtimecodedone.model.domain;

import java.util.Calendar;
import java.util.Date;

public class Record {

    private int id;
    private Calendar date;
    private String commitId;
    private String description;
    private int durationHours;
    private int durationMinutes;
    private boolean isSet;

    public Record(int id, Calendar date, String commitId, String description, int durationHours, int durationMinutes) {

        isSet = false;
        this.id = id;
        this.commitId = commitId;
        this.date = date;
        this.description = description;
        this.durationHours = durationHours;
        this.durationMinutes = durationMinutes;

    }

    public void setSet(boolean set) {

        isSet = set;

    }

    public boolean getIsSet() {

        return isSet;

    }

    public int getId() {

        return id;

    }

    public void setDate(Calendar date) {

        this.date = date;

    }

    public void setCommitId(String commitId) {

        this.commitId = commitId;

    }

    public void setDescription(String description) {

        this.description = description;

    }

    public void setDurationHours(int durationHours) {

        this.durationHours = durationHours;

    }

    public void setDurationMinutes(int durationMinutes) {

        this.durationMinutes = durationMinutes;

    }

    public Calendar getDate() {

        return date;

    }

    public int getDurationHours() {

        return durationHours;

    }

    public int getDurationMinutes() {

        return durationMinutes;

    }

    public String getCommitId() {

        return commitId;

    }

    public String getDescription() {

        return description;

    }

}
