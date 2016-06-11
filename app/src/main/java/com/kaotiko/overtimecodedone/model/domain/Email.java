package com.kaotiko.overtimecodedone.model.domain;

public class Email {


    private long id;
    private String email;
    private boolean readyToEdit;
    private boolean selected;

    public Email(long id, String email) {

        this.id = id;
        this.email = email;
        readyToEdit = false;
        selected = false;

    }

    public void setSelected(boolean selected) {

        this.selected = selected;

    }

    public void setReadyToEdit(boolean readyToEdit) {

        this.readyToEdit = readyToEdit;

    }

    public boolean isReadyToEdit() {

        return readyToEdit;

    }

    public boolean isSelected() {

        return selected;

    }

    public void setId(long id) {

        this.id = id;

    }

    public long getId() {

        return id;

    }

    public void setEmail(String email) {

        this.email = email;

    }

    public String getEmail() {

        return email;

    }

}
