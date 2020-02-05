package com.dilaraapps.kugpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {
    GpaCalculator gpaCalculator;
	
/*	@ModelAttribute("courseList")
	private ArrayList<Course> getCourseList(String username, String passw) {
	    return this.gpaCalculator.getStudent(username, passw).getCoursesTaken();
	}*/



    public UserController(GpaCalculator gpaCalculator) {
        super();
        this.gpaCalculator = gpaCalculator;
    }

    @GetMapping("/")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "index";
    }
	


	/*    @PostMapping("/login")
	    public String formSubmit(@ModelAttribute User user, Model model) {

	    	Student current = gpaCalculator.getStudent(user.getUsername(), user.getPassw());
	    	System.out.println("current gpa is :"+ current.getCumulativeGPA());
	    	model.addAttribute("GPA", current.getCumulativeGPA());

	        return "result";
	    } */

    @PostMapping("/login")
    public String formSubmit(@ModelAttribute User user, Model model) {

        Student current = null;
        try {

            current = gpaCalculator.getStudent(user.getUsername(), user.getPassw());
            // System.out.println(current.getSPAForAll().keySet().toString());


            model.addAttribute("courses", current.getCoursesTaken());
            model.addAttribute("nameSurname", current.getNameSurname());
            model.addAttribute("courseList", current.getCoursesPerTerm());// Hashmap<String,List<Course>>
            model.addAttribute("SPAList", current.getSPAForAll()); //HashMap <String,Float>
            model.addAttribute("termIncluded", current.getNonNullSPATerms()); //ArrayList -String
            model.addAttribute("terms", current.getTerms()); //ArrayList -String
            model.addAttribute("gpa", current.getCumulativeGPA());
            model.addAttribute("totalCredit", current.getTotalCredits());
            return "report";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            System.out.println(e);
            return "index";
        }

    }
	    
	  /* @PostMapping("/schedule")
		public String getSchedule(@ModelAttribute User user, Model model){
		   Student current = null;
		   try {
			   current = gpaCalculator.getStudent(user.getUsername(), user.getPassw());
		   } catch (Exception e) {
			   model.addAttribute("errorMessage", e.getMessage());
			   return "form";
		   }

		   ArrayList<String> days = new ArrayList<String>();
	        days.add("Monday");
	        days.add("Tuesday");
	        days.add("Wednesday");
	        days.add("Thursday");
	        days.add("Friday");
	        model.addAttribute("eventsForDay", current.getEventsForDay());//  Hashmap<String,List<Event>>
	        
	        for(String key : current.getEventsForDay().keySet() ) {
	        	 for(Event e : current.getEventsForDay().get(key) ) {
	     	        System.out.println(e.CourseCode);
	 	        }
	        }
	        model.addAttribute("days", days );
			    return "schedule";

		}   */
		
		/*   @PostMapping("/schedule")
		    public String showSchedule(@ModelAttribute User user, Model model) {
		    
		    	Student current = gpaCalculator.getStudent(user.getUsername(), user.getPassw());
		        model.addAttribute("eventsForDay", current.getEventsForDay());//  Hashmap<String,List<Event>>
		        ArrayList<String> days = new ArrayList<String>();
		        days.add("Monday");
		        days.add("Tuesday");
		        days.add("Wednesday");
		        days.add("Thursday");
		        days.add("Friday");
		        
		        model.addAttribute("days", days );
		     
		        return "schedule";
		    }  */
}


     
