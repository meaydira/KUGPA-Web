package com.dilaraapps.kugpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



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

}