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

    public Record(int id, Calendar date, String commitId, String description, int durationHours, int durationMinutes) {

        this.id = id;
        this.commitId = commitId;
        this.date = date;
        this.description = description;
        this.durationHours = durationHours;
        this.durationMinutes = durationMinutes;

    }

    public int getId() {

        return id;

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
