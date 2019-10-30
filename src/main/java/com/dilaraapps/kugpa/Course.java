package com.dilaraapps.kugpa;


import java.time.Duration;
import java.util.Comparator;
import java.util.HashMap;

public class Course {


    HashMap<String, Float> gradeMap = new HashMap<String, Float>();


    String CourseName;
    String CourseCode;
    String CourseTerm;
    String Grade;
    Float CourseCredit;
    String includedInGPA;
    Duration time;


    public Course(String name, String code, String term, String Grade, String Credit, String included) {
        gradeMap.put("A+", (float) 4.3);
        gradeMap.put("A", (float) 4.0);
        gradeMap.put("A-", (float) 3.7);
        gradeMap.put("B+", (float) 3.3);
        gradeMap.put("B", (float) 3.0);
        gradeMap.put("B-", (float) 2.7);
        gradeMap.put("C+", (float) 2.3);
        gradeMap.put("C", (float) 2.0);
        gradeMap.put("C-", (float) 1.7);
        gradeMap.put("D+", (float) 1.3);
        gradeMap.put("D", (float) 1.0);
        gradeMap.put("F", (float) 0);
        gradeMap.put("TA+", (float) 4.3);
        gradeMap.put("TA", (float) 4.0);
        gradeMap.put("TA-", (float) 3.7);
        gradeMap.put("TB+", (float) 3.3);
        gradeMap.put("TB", (float) 3.0);
        gradeMap.put("TB-", (float) 2.7);
        gradeMap.put("TC+", (float) 2.3);
        gradeMap.put("TC", (float) 2.0);
        gradeMap.put("TC-", (float) 1.7);
        gradeMap.put("TD+", (float) 1.3);
        gradeMap.put("TD", (float) 1.0);
        gradeMap.put("TF", (float) 0);


        this.CourseName = name;
        this.CourseCode = code;
        this.Grade = Grade;
        this.CourseTerm = term;
        this.CourseCredit = Float.parseFloat(Credit);
        this.includedInGPA = included;


    }


    public HashMap<String, Float> getGradeMap() {
        return gradeMap;
    }


    public void setGradeMap(HashMap<String, Float> gradeMap) {
        this.gradeMap = gradeMap;
    }


    public String getCourseName() {
        return CourseName;
    }


    public void setCourseName(String courseName) {
        CourseName = courseName;
    }


    public String getCourseCode() {
        return CourseCode;
    }


    public void setCourseCode(String courseCode) {
        CourseCode = courseCode;
    }


    public String getCourseTerm() {
        return CourseTerm;
    }


    public void setCourseTerm(String courseTerm) {
        CourseTerm = courseTerm;
    }


    public String getGrade() {
        return Grade;
    }


    public void setGrade(String grade) {
        Grade = grade;
    }


    public Float getCourseCredit() {
        return CourseCredit;
    }


    public void setCourseCredit(Float courseCredit) {
        CourseCredit = courseCredit;
    }


    public String getIncludedInGPA() {
        return includedInGPA;
    }


    public void setIncludedInGPA(String includedInGPA) {
        this.includedInGPA = includedInGPA;
    }


    public boolean isIncludedInGpa() {
        if (this.includedInGPA.equals("N")) {
            return false;
        } else if (this.CourseCode.substring(0, 3).equals("ELC")) {
            return false;
        } else return true;
    }


    public Float getNumericalValueOfGrade() {
        if (gradeMap.containsKey(this.Grade)) {
            return gradeMap.get(Grade);
        } else return null;
    }

    public int getTerm() {
        if (this.CourseTerm.contains("Spring")) {
            return 1;
        } else if (this.CourseTerm.contains("Summer")) {
            return 2;
        } else return 3;
    }


    @Override
    public String toString() {

        String s = CourseCode + " " + CourseName + " " + Grade + " " + CourseTerm + " " + " " + (isIncludedInGpa() ? "INCLUDED" : "NOT INCLUDED");
        return s;
    }








	





 

	
	
/*	A+	4.30
	A	4.00
	A-	3.70
	B+	3.30
	B	3.00
	B-	2.70
	C+	2.30
	C	2.00
	C-	1.70
	D+	1.30
	D	1.00
	F	0.00
*/
}


class Comparators implements Comparator<String> {

    @Override
    public int compare(String s1, String s2) {
        // TODO Auto-generated method stub


        if (s1.equals(s2)) return 0;
        int year1 = Integer.parseInt(s1.substring(s1.length() - 4, s1.length()));
        int year2 = Integer.parseInt(s2.substring(s2.length() - 4, s2.length()));
        if (year1 < year2) return -1;
        if (year2 < year1) return 1;

        if (getTerm(s1) < getTerm(s2)) {
            return -1;
        } else return 1;
    }

    public int getTerm(String t) {
        if (t.contains("Spring")) {
            return 1;
        } else if (t.contains("Summer")) {
            return 2;
        } else return 3;
    }


}

