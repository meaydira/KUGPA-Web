package com.dilaraapps.kugpa;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
public class KUGPAGetGradesController {

    GpaCalculator gpaCalculator;



    @Autowired
    public KUGPAGetGradesController(GpaCalculator gpaCalculator) {
        super();
        this.gpaCalculator = gpaCalculator;

    }

    @PostMapping("/loginUser")
    public ResponseEntity getGrades(@RequestParam(value="username") String username, @RequestParam(value="passw") String password) {

        Student current = null;
        try {

            current = gpaCalculator.getStudent(username, password);
            // System.out.println(current.getSPAForAll().keySet().toString());


     /*       model.addAttribute("courses", current.getCoursesTaken());
            model.addAttribute("nameSurname", current.getNameSurname());
            model.addAttribute("courseList", current.getCoursesPerTerm());// Hashmap<String,List<Course>>
            model.addAttribute("SPAList", current.getSPAForAll()); //HashMap <String,Float>
            model.addAttribute("termIncluded", current.getNonNullSPATerms()); //ArrayList -String
            model.addAttribute("terms", current.getTerms()); //ArrayList -String
            model.addAttribute("gpa", current.getCumulativeGPA());
            model.addAttribute("totalCredit", current.getTotalCredits());  */

            return ResponseEntity.ok(current);
        } catch (Exception e) {
           // model.addAttribute("errorMessage", e.getMessage());
            System.out.println(e);
            return ResponseEntity.ok(current);
        }

    }

    @PostMapping(value ="/loginU")
    public ResponseEntity<Student> loginUser(@RequestBody User user) {

        Student current = null;
        try {
            String username = user.getUsername();
            String password = user.getPassw();
         /*   if(requestBody.has("username")){
                username = requestBody.get("username").toString();
            }
            if(requestBody.has("passw")){
                username = requestBody.get("passw").toString();
            }  */
            current = gpaCalculator.getStudent(username, password);
            // System.out.println(current.getSPAForAll().keySet().toString());


     /*       model.addAttribute("courses", current.getCoursesTaken());
            model.addAttribute("nameSurname", current.getNameSurname());
            model.addAttribute("courseList", current.getCoursesPerTerm());// Hashmap<String,List<Course>>
            model.addAttribute("SPAList", current.getSPAForAll()); //HashMap <String,Float>
            model.addAttribute("termIncluded", current.getNonNullSPATerms()); //ArrayList -String
            model.addAttribute("terms", current.getTerms()); //ArrayList -String
            model.addAttribute("gpa", current.getCumulativeGPA());
            model.addAttribute("totalCredit", current.getTotalCredits());  */

            return ResponseEntity.ok(current);
        } catch (Exception e) {
            // model.addAttribute("errorMessage", e.getMessage());
            System.out.println(e);
            return ResponseEntity.ok(current);
        }

    }

}