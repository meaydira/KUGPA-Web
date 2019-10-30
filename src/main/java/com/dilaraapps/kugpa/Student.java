package com.dilaraapps.kugpa;


import java.text.SimpleDateFormat;
import java.util.*;

public class Student {
    String userName;


    String nameSurname;
    ArrayList<Course> coursesTaken;
    HashMap<String, LinkedList<Course>> coursesPerTerm;
    Float cumulativeGPA;
    HashMap<String, Float> SPAPerTerm;
    ArrayList<String> terms;
    ArrayList<Event> events;
    ArrayList<Student> friends;


    public String getUserName() {
        return userName;
    }

    public ArrayList<String> getTerms() {
        return this.terms;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Course> getCoursesTaken() {
        return coursesTaken;
    }

    public void setCoursesTaken(ArrayList<Course> coursesTaken) {
        this.coursesTaken = coursesTaken;
    }

    public void addEvent(Event e) {
        this.events.add(e);
    }

    public HashMap<String, LinkedList<Course>> getCoursesPerTerm() {
        return coursesPerTerm;
    }

    public void setCoursesPerTerm(HashMap<String, LinkedList<Course>> coursesPerTerm) {
        this.coursesPerTerm = coursesPerTerm;
    }

    public Float getCumulativeGPA() {
        return cumulativeGPA;
    }

    public void setCumulativeGPA(Float cumulativeGPA) {
        this.cumulativeGPA = cumulativeGPA;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public Student(String userName, Float cumulativeGPA) {
        this.userName = userName;
        this.cumulativeGPA = cumulativeGPA;
        this.coursesTaken = new ArrayList<Course>();
        initializeHashMap();
        this.events = new ArrayList<Event>();
        terms = new ArrayList<String>();
        friends = new ArrayList<Student>();
    }

    public Student(String userName, Float cumulativeGPA, ArrayList<Course> courses) {
        this.userName = userName;
        this.cumulativeGPA = cumulativeGPA;
        this.coursesTaken = courses;
        friends = new ArrayList<Student>();
        initializeHashMap();
    }

    public void initializeHashMap() {
        coursesPerTerm = new HashMap<String, LinkedList<Course>>();

        for (Course c : this.coursesTaken) {
            if (coursesPerTerm.containsKey(c.CourseTerm)) {
                LinkedList<Course> temp = coursesPerTerm.get(c.CourseTerm);
                temp.add(c);
                coursesPerTerm.put(c.CourseTerm, temp);
            } else {
                LinkedList<Course> temp = new LinkedList<Course>();
                temp.add(c);
                coursesPerTerm.put(c.CourseTerm, temp);
            }
        }
    }

    public void addFriend(Student e) {
        this.friends.add(e);
    }

    public void setTerms(ArrayList<String> terms) {
        this.terms = terms;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Student> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<Student> friends) {
        this.friends = friends;
    }

    public Float getTotalCredits() {
        float total = 0;

        for (Course c : coursesTaken) {
            if (c.isIncludedInGpa())
                total += c.CourseCredit;
        }

        return total;
    }

    public HashMap<String, ArrayList<Event>> getEventsForDay() {
        HashMap<String, ArrayList<Event>> map = new HashMap<String, ArrayList<Event>>();
        ArrayList<Event> mo = new ArrayList<Event>();
        ArrayList<Event> we = new ArrayList<Event>();
        ArrayList<Event> tue = new ArrayList<Event>();
        ArrayList<Event> thu = new ArrayList<Event>();
        ArrayList<Event> fri = new ArrayList<Event>();
        for (Event e : this.events) {
            for (String day : e.courseDays) {
                if (day.equals("Monday")) {
                    mo.add(e);
                } else if (day.equals("Tuesday")) {
                    tue.add(e);
                } else if (day.equals("Wednesday")) {
                    we.add(e);
                } else if (day.equals("Thursday")) {
                    thu.add(e);
                } else if (day.equals("Friday")) {
                    fri.add(e);
                }
            }
        }

        map.put("Monday", mo);
        map.put("Tuesday", tue);
        map.put("Wednesday", we);
        map.put("Thursday", thu);
        map.put("Friday", fri);

        return map;
    }

    public Float calculateGPA() {
        Float weightSum = (float) 0;
        Float credits = (float) 0;
        for (Course c : this.coursesTaken) {
            if (c.isIncludedInGpa() && c.getNumericalValueOfGrade() != null) {
                weightSum += c.CourseCredit * c.getNumericalValueOfGrade();
                credits += c.CourseCredit;
            }
        }

        return weightSum / credits;

    }

    public Float calculateSPA(String term) {

        Float weightSum = (float) 0;
        Float credits = (float) 0;
        for (Course c : this.coursesTaken) {
            if (c.isIncludedInGpa() && c.getNumericalValueOfGrade() != null) {

                if (c.getCourseTerm().equals(term)) {

                    weightSum += c.CourseCredit * c.getNumericalValueOfGrade();
                    credits += c.CourseCredit;
                    // System.out.println(c.toString());
                }
            }
        }

        // System.out.println("weight sum is " + weightSum);
        // System.out.println("totalCredits are " + credits);
        return weightSum / credits;

    }

    public Event getEventForCurrentTime(Date time) {
        SimpleDateFormat dayFormatter = new SimpleDateFormat("EEEE");
        // the day of the week spelled out completely
        String day = dayFormatter.format(time);
        System.out.println(dayFormatter.format(time));
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        String hourAndMinute = timeFormatter.format(time);
        System.out.println(timeFormatter.format(time));

        for (Event e : this.getEventsForDay().get(day)) {
            //System.out.println("comparing with " + day + " , "+hourAndMinute + " . and " + e.courseStartHour );

            if (compareHM(e.courseStartHour, hourAndMinute) < 1
                    && compareHM(hourAndMinute, e.courseEndHour) < 1) {
                //System.out.println("comparison returned true");
                return e;
            }
        }

        return null;

    }

    public boolean isFreeAt(Date time) {
        if (getEventForCurrentTime(time) == null) {
            System.out.println("Student is free at " + time.toString());
            return true;
        } else {
            System.out.println("Student is busy at " + time.toString());
            return false;
        }
    }


    public int compareHM(String t1, String t2) {
        StringTokenizer tokenizer1 = new StringTokenizer(t1, ":");
        StringTokenizer tokenizer2 = new StringTokenizer(t2, ":");

        int hour1 = Integer.parseInt(tokenizer1.nextToken());

        int hour2 = Integer.parseInt(tokenizer2.nextToken());
        //System.out.println("step 3");
        int minute1 = Integer.parseInt(tokenizer1.nextToken());
        //System.out.println("step 4");
        int minute2 = Integer.parseInt(tokenizer2.nextToken());
        if (hour1 > hour2) {
            //	System.out.println(t1 + "is larger  than" + t2);
            return 1;
        } else if (hour1 == hour2) {
            if (minute1 > minute2) {
                //	System.out.println(t1 + "is larger  than" + t2);
                return 1;
            } else if (minute1 == minute2) {
                //	System.out.println(t1 + "is equal to" + t2);
                return 0;
            } else return -1;
        } else return -1;

    }

    public void addCourse(Course c) {

        if (!c.isIncludedInGpa()) {
            c.setIncludedInGPA("N");
        }

        this.coursesTaken.add(c);
        if (!terms.contains(c.getCourseTerm())) {

            terms.add(c.getCourseTerm());
            Collections.sort(terms, new Comparators());

        }

        if (coursesPerTerm.keySet().contains(c.CourseTerm)) {
            this.coursesPerTerm.get(c.CourseTerm).add(c);
        } else {

            LinkedList<Course> temp = new LinkedList<Course>();
            temp.add(c);
            this.coursesPerTerm.put(c.getCourseTerm(), temp);
        }

    }

    public HashMap<String, Float> getSPAForAll() {
        SPAPerTerm = new HashMap<String, Float>();
        initializeHashMap();

        for (String term : this.coursesPerTerm.keySet()) {
            Float weightSum = (float) 0;
            Float credits = (float) 0;
            LinkedList<Course> courses = coursesPerTerm.get(term);
            for (Course c : courses) {
                //  System.out.println(c.toString());
                if (c.isIncludedInGpa() && c.getNumericalValueOfGrade() != null) {

                    weightSum += c.CourseCredit * c.getNumericalValueOfGrade();
                    credits += c.CourseCredit;

                }
            }

            // System.out.println("SPA for " + term + " is " + weightSum / credits);

            SPAPerTerm.put(term, weightSum / credits);


        }

        return SPAPerTerm;


    }

    public ArrayList<String> getNonNullSPATerms() {

        ArrayList<String> termList = new ArrayList<String>();
        HashMap<String, Float> SPAMap = this.getSPAForAll();

        for (String term : SPAMap.keySet()) {
            if (SPAMap.get(term).toString().equals("NaN")) {
                termList.remove(term);
            }

        }
        return termList;
    }

    @Override
    public String toString() {

        String s = "Student [userName=" + userName + "cumulativeGPA=" + cumulativeGPA + " \n";
        for (Course c : coursesTaken) {
            s += c.CourseCode + " " + c.CourseName + " " + c.Grade + " " + c.CourseTerm + " " + " "
                    + (c.isIncludedInGpa() ? "INCLUDED" : "NOT INCLUDED") + "\n";
        }

        return s;
    }


    public void swap(int index1, int index2) {
        Course temp = this.coursesTaken.get(index1);
        Course temp2 = this.coursesTaken.get(index2);
        this.coursesTaken.remove(index1);
        this.coursesTaken.add(index1, temp2);
        this.coursesTaken.remove(index2);
        this.coursesTaken.add(index2, temp);
    }

}


