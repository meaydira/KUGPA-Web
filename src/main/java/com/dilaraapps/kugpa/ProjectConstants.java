package com.dilaraapps.kugpa;

import java.util.HashMap;

public class ProjectConstants {

    private static ProjectConstants instance=null;
    static HashMap<String, Float> gradeMap;
    private ProjectConstants(){

    }

    public static ProjectConstants getInstance(){
        if(instance == null){
            instance = new ProjectConstants();
            initGradeMap();
        }

        return instance;
    }

    public HashMap<String, Float> getGradeMap(){
        return gradeMap;
    }

    private static void initGradeMap(){

        gradeMap = new HashMap<String, Float>();
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
    }


}
