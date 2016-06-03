package com.kaotiko.overtimecodedone.model.domain;

public class Email {


    private long id;
    private String email;

    public Email(long id, String email) {

        this.id = id;
        this.email = email;

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
