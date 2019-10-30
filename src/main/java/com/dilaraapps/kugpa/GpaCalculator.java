package com.dilaraapps.kugpa;

public interface GpaCalculator {

    public Student getStudent(String username, String passw) throws Exception;

    public Student extractStudent(String userid, String passw) throws Exception;
}
