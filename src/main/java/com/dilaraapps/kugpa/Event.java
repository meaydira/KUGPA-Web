package com.dilaraapps.kugpa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Event {


    String CourseCode;
    String Component;
    String Section;
    String classNumber;
    String courseStartHour;
    String courseEndHour;
    ArrayList<String> courseDays;
    String Instructor;
    String location;
    String CourseStartEnd;

    public Event(String courseCode, String component, String section, String classNumber, String courseTime,
                 String instructor, String location, String courseStartEnd) {
        super();
        CourseCode = courseCode;
        Component = component;
        Section = section;
        this.classNumber = classNumber;
        setCourseTime(courseTime);
        Instructor = instructor;
        this.location = location;
        CourseStartEnd = courseStartEnd;
    }


    @Override
    public String toString() {
        return "Event [CourseCode=" + CourseCode + ", Component=" + Component + ", Section=" + Section
                + ", classNumber=" + classNumber + ", courseStartHour=" + courseStartHour + ", courseEndHour="
                + courseEndHour + ", courseDays=" + courseDays + ", Instructor=" + Instructor + ", location=" + location
                + ", CourseStartEnd=" + CourseStartEnd + "]";
    }


    public void setCourseTime(String courseSchedule) {
        //format : MoWe 5:30PM - 6:45PM;

        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mmaa");

        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");


        StringTokenizer st = new StringTokenizer(courseSchedule, " ");

        setDays(st.nextToken());

        try {
            String start = st.nextToken();
            //System.out.println(date24Format.format(date12Format.parse(start)));
            this.courseStartHour = date24Format.format(date12Format.parse(start));
            st.nextToken();
            String end = st.nextToken();
            //System.out.println(date24Format.format(date12Format.parse(end)));
            this.courseEndHour = date24Format.format(date12Format.parse(end));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public void setDays(String daysUnformatted) {

        this.courseDays = new ArrayList<String>();
        int count = daysUnformatted.length() / 2;
        //System.out.println(daysUnformatted);
        for (int i = 0; i < count; i++) {
            String temp = daysUnformatted.substring(2 * i, 2 * (i + 1));
            // for i = 0 , 0-1
            // for i=1 , 2,3

            if (temp.equals("Mo")) {
                this.courseDays.add("Monday");
            } else if (temp.equals("Tu")) {
                this.courseDays.add("Tuesday");
            } else if (temp.equals("We")) {
                this.courseDays.add("Wednesday");
            } else if (temp.equals("Th")) {
                this.courseDays.add("Thursday");
            } else if (temp.equals("Fr")) {
                this.courseDays.add("Friday");
            }
        }
		
	/*	for (String day: courseDays) {
			System.out.println(day);
		}  */


    }


    public void printCourseTime() {
        for (String day : this.courseDays) {
            System.out.println(day);
        }

        System.out.println("Starts at : " + this.courseStartHour);
        System.out.println("Ends at : " + this.courseStartHour);
    }


    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String courseCode) {
        CourseCode = courseCode;
    }

    public String getComponent() {
        return Component;
    }

    public void setComponent(String component) {
        Component = component;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public String getCourseStartHour() {
        return courseStartHour;
    }

    public void setCourseStartHour(String courseStartHour) {
        this.courseStartHour = courseStartHour;
    }

    public String getCourseEndHour() {
        return courseEndHour;
    }

    public void setCourseEndHour(String courseEndHour) {
        this.courseEndHour = courseEndHour;
    }

    public ArrayList<String> getCourseDays() {
        return courseDays;
    }

    public void setCourseDays(ArrayList<String> courseDays) {
        this.courseDays = courseDays;
    }

    public String getInstructor() {
        return Instructor;
    }

    public void setInstructor(String instructor) {
        Instructor = instructor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCourseStartEnd() {
        return CourseStartEnd;
    }

    public void setCourseStartEnd(String courseStartEnd) {
        CourseStartEnd = courseStartEnd;
    }


}
