package com.github.jroperto.gaebase.model;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

public class User {

    @Id
    private String email;
    @Index
    private String lastName;
    @Index
    private String firstName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
