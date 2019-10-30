package com.dilaraapps.kugpa;

import javax.persistence.Entity;

@Entity
public class User {

    private String username;
    private String passw;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }


}
