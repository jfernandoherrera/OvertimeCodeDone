package com.kaotiko.overtimecodedone.model.domain;

import java.util.Calendar;

public class Record {

    private long id;
    private Calendar date;
    private String commitId;
    private String description;
    private int durationHours;
    private int durationMinutes;
    private boolean isProgrammaticallyChanged;
    private boolean isWaitingSave;
    private boolean onEditedDate;

    public Record(int id, Calendar date, String commitId, String description, int durationHours, int durationMinutes) {

        isProgrammaticallyChanged = true;
        isWaitingSave = false;
        onEditedDate = false;
        this.id = id;
        this.commitId = commitId;
        this.date = date;
        this.description = description;
        this.durationHours = durationHours;
        this.durationMinutes = durationMinutes;

    }

    public void setId(long id) {

        this.id = id;

    }

    public void setOnEditedDate(boolean onEditedDate) {

        this.onEditedDate = onEditedDate;

    }

    public boolean getOnEditedDate() {

        return onEditedDate;

    }

    public void setIsProgrammaticallyChanged(boolean set) {

        isProgrammaticallyChanged = set;

    }

    public void setWaitingSave(boolean waitingSave) {

        isWaitingSave = waitingSave;

    }

    public boolean getIsWaitingSave() {

        return isWaitingSave;

    }

    public boolean getIsProgrammaticallyChanged() {

        return isProgrammaticallyChanged;

    }

    public long getId() {

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
