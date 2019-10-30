package com.dilaraapps.kugpa;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class gpa_calculator implements GpaCalculator {
	

	/*public gpa_calculator() {
			
			
			
			Student user = extractGrades("DETOPRAK13","Scarface12-");
			System.out.println("Student has been successfully extracted");
			System.out.println("Calculated GPA is " + user.calculateGPA());
			
			// TODO Auto-generated method stub
			
			user.sortCourses();
			
			System.out.println(user.toString());
			
			user.calculateSPAForAll(); 
			
		} */

    public Student getStudent(String username, String passw) throws Exception {

        Student user = null;
        try {
            user = extractStudent(username, passw);
            System.out.println("Student has been successfully extracted");
            System.out.println("Calculated GPA is " + user.calculateGPA());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }


        //	System.out.println(user.toString());

        //	user.calculateSPAForAll();
        return user;

    }


    public Student extractStudent(String userid, String passw) throws Exception {
        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_60);
        Student stu = null;
        //
        //webClient.getOptions().setJavaScriptEnabled(true);
        //    final WebClient webClient = new WebClient();
        // webClient.setJavaScriptTimeout(10000);
        // webClient.set
        try {
            System.out.println("Querying");

            webClient.setAjaxController(new AjaxController() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                @Override
                public boolean processSynchron(HtmlPage page, WebRequest request, boolean async) {
                    return true;
                }
            });

            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);

            HtmlPage page = (HtmlPage) webClient.getPage("https://kusis.ku.edu.tr/psp/ps/?cmd=login&languageCd=ENG&");


            HtmlForm form = page.getFormByName("login");


            System.out.println("login form has been successfully retrieved");

            form.getInputByName("userid").setValueAttribute(userid);
            HtmlInput passWordInput = form.getInputByName("pwd");
            passWordInput.removeAttribute("KU Net Password");
            passWordInput.setValueAttribute(passw);


            page = form.getInputByValue("Sign In").click();

            List<HtmlDivision> alert = page.getByXPath("//div[contains(@class, 'alert')]");
            //System.out.println(alert.get(0).asText());

            if (alert != null && alert.size() > 0) {
                String span = alert.get(0).asText();
                throw new Exception(span);
            }


            page = webClient.getPage("https://kusis.ku.edu.tr/psp/ps/EMPLOYEE/SA/c/SA_LEARNER_SERVICES.SSS_MY_CRSEHIST.GBL?FolderPath=PORTAL_ROOT_OBJECT.CO_EMPLOYEE_SELF_SERVICE.HCCC_ACADEMIC_RECORDS.HC_SSS_MY_CRSEHIST_GBL&IsFolder=false&IgnoreParamTempl=FolderPath%2cIsFolder");

            //TODO: ADD CUMULATIVE GPA TO PAGE
            HtmlSpan cum_gpa = (HtmlSpan) page.getElementById("KU_AH_DERIVED_CUM_GPA");
            HtmlPage framePage = (HtmlPage) page.getFrameByName("TargetContent").getEnclosedPage();
            HtmlSpan a = (HtmlSpan) framePage.getByXPath("//span[contains(@id, 'KU_AH_DERIVED_CUM_GPA')]").get(0);


            System.out.println(page.asText());

            stu = new Student(userid, Float.parseFloat(a.asText()));

            HtmlSpan nameSpan = (HtmlSpan) framePage.getByXPath("//span[contains(@id, 'DERIVED_SSTSNAV_PERSON_NAME')]").get(0);
            stu.setNameSurname(nameSpan.asText());


            for (int i = 0; i < framePage.getByXPath("//span[contains(@id, 'CRSE_NAME')]").size(); i++) {
                HtmlSpan code = (HtmlSpan) framePage.getByXPath("//span[contains(@id, 'CRSE_NAME')]").get(i);
                HtmlSpan term = (HtmlSpan) framePage.getByXPath("//span[contains(@id, 'CRSE_TERM')]").get(i);
                HtmlSpan fullname = (HtmlSpan) framePage.getByXPath("//span[contains(@id, 'CRSE_LINK')]").get(i);
                HtmlSpan grade = (HtmlSpan) framePage.getByXPath("//span[contains(@id, 'CRSE_GRADE')]").get(i);
                HtmlSpan unit = (HtmlSpan) framePage.getByXPath("//span[contains(@id, 'CRSE_UNITS')]").get(i);
                HtmlSpan included = (HtmlSpan) framePage.getByXPath("//span[contains(@id, 'KU_AH_DERIVED_INCLUDE_IN_GPA')]").get(i);

                stu.addCourse(new Course(fullname.asText(), code.asText(), term.asText(), grade.asText(), unit.asText(), included.asText()));
                //String name,String code, String term, String Grade ,String Credit,String included
            }

            stu.calculateGPA();
            stu.setCumulativeGPA(Float.parseFloat(a.asText()));


            // ---------------------------------* WEEKLY SCHEDULE *-----------------------


            HtmlPage confirmed = (HtmlPage) webClient.getPage("https://kusis.ku.edu.tr/psc/ps/EMPLOYEE/SA/c/SSR_PROG_ENRL_SS.SSR_SS_MY_CLASSES.GBL?FolderPath=PORTAL_ROOT_OBJECT.CO_EMPLOYEE_SELF_SERVICE.HCCC_PE_STUDENT.HC_SSR_SS_MY_CLASSES&amp;IsFolder=false&amp;IgnoreParamTempl=FolderPath%2cIsFolder&amp;PortalActualURL=https%3a%2f%2fkusis.ku.edu.tr%2fpsc%2fps%2fEMPLOYEE%2fSA%2fc%2fSSR_PROG_ENRL_SS.SSR_SS_MY_CLASSES.GBL&amp;PortalContentURL=https%3a%2f%2fkusis.ku.edu.tr%2fpsc%2fps%2fEMPLOYEE%2fSA%2fc%2fSSR_PROG_ENRL_SS.SSR_SS_MY_CLASSES.GBL&amp;PortalContentProvider=SA&amp;PortalCRefLabel=My%20Confirmed%20Enrollments&amp;PortalRegistryName=EMPLOYEE&amp;PortalServletURI=https%3a%2f%2fkusis.ku.edu.tr%2fpsp%2fps%2f&amp;PortalURI=https%3a%2f%2fkusis.ku.edu.tr%2fpsc%2fps%2f&amp;PortalHostNode=SA&amp;NoCrumbs=yes&amp;PortalKeyStruct=yes");

            for (int i = 0; i < confirmed.getByXPath("//div[contains(@id, 'win0divDERIVED_REGFRM1_ }DESCR20$')]").size(); i++) {
                HtmlDivision div = (HtmlDivision) confirmed.getByXPath("//div[contains(@id, 'win0divDERIVED_REGFRM1_DESCR20$')]").get(i);

                String cName = ((HtmlTableDataCell) div.getByXPath("//td[contains(@class, 'PAGROUPDIVIDER')]").get(i)).asText();
                String temp = "trCLASS_MTG_VW$" + i;
                for (int j = 0; j < div.getByXPath("//tr[contains(@id, '" + temp + "')]").size(); j++) {
                    HtmlTableRow tr = (HtmlTableRow) div.getByXPath("//tr[contains(@id, '" + temp + "_row" + (j + 1) + "')]").get(0);

                    String classNumber = tr.getCell(0).asText();
                    String section = tr.getCell(1).asText();
                    String component = tr.getCell(2).asText();
                    String sched = tr.getCell(3).asText();
                    String location = tr.getCell(4).asText();
                    String instructor = tr.getCell(5).asText();
                    String dates = tr.getCell(6).asText();


                    Event tempevent = new Event(cName, component, section, classNumber, sched,
                            instructor, location, dates);
                    stu.addEvent(tempevent);
                    // System.out.println(tempevent.toString());

                }
            }


            SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
            String dateInString = "Monday, Oct 7, 2019 11:10:56 PM";


            try {

                Date formatted = formatter.parse(dateInString);
                Date date = Calendar.getInstance().getTime();
                stu.isFreeAt(date);
                System.out.println(date);
                System.out.println(formatter.format(date));

            } catch (ParseException e) {
                e.printStackTrace();
            }


        } catch (final FailingHttpStatusCodeException e) {
            System.out.println("One");
            e.printStackTrace();
        } catch (final MalformedURLException e) {
            System.out.println("Two");
            e.printStackTrace();
        } catch (final IOException e) {
            System.out.println("Three");
            e.printStackTrace();
        } catch (final Exception e) {
            System.out.println("Four");
            throw e;
        }
        webClient.close();
        System.out.println("Finished");
        return stu;
    }


}



