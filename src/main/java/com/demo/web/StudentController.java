package com.demo.web;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.Metier.IntranetMetierInterface;
import com.demo.dao.CourseRepository;
import com.demo.entities.*;
import com.demo.staticClasses.*;


@Controller
@RequestMapping("student")

class StudentController {
	@Autowired
	private IntranetMetierInterface interfaceMetier;
	@Autowired
	private CourseRepository courseRep;
	
	public String message = "student";
	public Long id;
	@RequestMapping(value={"/home", "/"})
	public String empty(Model model , HttpServletRequest request, HttpServletResponse response)
	{
		model.addAttribute("profile", message) ;
		
		Cookie[] cookies = request.getCookies();
		String nameCookie = "intranetEsmeCookie";
		String pathLink = "homeLogged";

		if (!cookies.equals(null)) {
			Cookie actualCookie = null;
			for (int i = 0; i < cookies.length; i++) {
				String name = cookies[i].getName();
				String value = cookies[i].getValue();
				// Cookie recherché : partage dans l’objet request
				if (name.equals(nameCookie)) {
					System.out.println("get cookie" + name);
					actualCookie = cookies[i];
					// pathLink =
					break;
				}
			}

			if (actualCookie != null) {
				this.id = Long.valueOf(actualCookie.getValue().split("-")[1]).longValue();

			}
			else {
				pathLink =  "redirect:/home";
			}
		}
		
		
		List<News> newsList = new ArrayList<>();
		for (News news : interfaceMetier.getLatestNewsList())
			newsList.add(news);
		
		model.addAttribute("news", newsList);
		return pathLink; 
	}
		
	@RequestMapping("/news")
	public String news(Model model )
	{
		model.addAttribute("profile", message) ;
		List<News> newsList = new ArrayList<>();
		for (News news : interfaceMetier.getNewsList())
			newsList.add(news);
		
		model.addAttribute("news", newsList);
		return "news" ; 
	}
	
	@RequestMapping("/marks")
	public String marks(Model model )
	{
		List<Mean> meanList = new ArrayList<>();
		List<String> dayList = new ArrayList<>();

		for(int i = 0; i<6; i++) {
			for(int j =0; j<5; j++) {
				dayList.add("Day " + i + " timeSlot " + j );
			}
		}
		System.out.println(dayList);

		for (Mean mean : interfaceMetier.getStudentsMeanFromId(this.id)) {
			meanList.add(mean);
		}
		model.addAttribute("meanList", meanList) ;

		model.addAttribute("profile", message) ;
		return "marks" ; 
	}
	
	@RequestMapping(value = "/timeTable", method = RequestMethod.GET)
	public String postTimeTableManage(Model model) {

		Boolean visualize = true;
		// id of the teacher selected to find the teacher course list
		Student student = interfaceMetier.getStudentFromId(this.id);
		GroupClass classStudent = student.getStudentClass();
		List<Course> courseListByStudent = interfaceMetier.getCourseListByClass(classStudent.getClassId());
		
		// initiate variable
		List<Teacher> teachers = interfaceMetier.getTeacherList();
		List<String> teachersNames = new ArrayList<>();
		List<String> classNameList = new ArrayList<>();
		List<String> courseNameList = new ArrayList<>();
		List<String> courseDayList = new ArrayList<>();
		List<String> hoursDayList = new ArrayList<>();
		List<String> courseNameListByClass = new ArrayList<>();
		List<String> courseDayListByClass = new ArrayList<>();
		List<String> courseHoursListByTeacher = new ArrayList<>();
		List<String> courseClassNameListByClass = new ArrayList<>();
		List<String> courseSortedByDay = new ArrayList<>();
		List<TimeSlot> timeSlotList = new ArrayList<>();

		//
		List<String> heightAM = new ArrayList<>();
		List<String> tenAM = new ArrayList<>();
		List<String> noon = new ArrayList<>();
		List<String> twoPM = new ArrayList<>();
		List<String> fourPM = new ArrayList<>();
		List<String> sixPM = new ArrayList<>();
		List<String> days = new ArrayList<>();

		// hours list
		hoursDayList.add("08AM");
		hoursDayList.add("10AM");
		hoursDayList.add("12AM");
		hoursDayList.add("2PM");
		hoursDayList.add("4PM");
		hoursDayList.add("6PM");
		// day list
		courseDayList.add("Monday");
		courseDayList.add("Tuesday");
		courseDayList.add("Wednesday");
		courseDayList.add("Thursday");
		courseDayList.add("Friday");

		// Course name List
		courseNameList.add("Ember");
		courseNameList.add("J2EE");
		courseNameList.add("BDD");
		courseNameList.add("DEVOPS");
		courseNameList.add("ART");

		// Days list
		days.add("monday");
		days.add("tuesday");
		days.add("wednesday");
		days.add("thursday");
		days.add("friday");

		// get all class name
		for (GroupClass groupClass : interfaceMetier.getGroupClassList())
			classNameList.add(groupClass.getName());
		for (Teacher teacher : teachers) {
			teachersNames.add(teacher.getName() + " " + teacher.getUsername());
		}
		// get all course name of the teacher
		for (Course course : courseListByStudent)
			courseNameListByClass.add(course.getName());

		// get all course name of the teacher
		for (Course course : courseListByStudent)
			courseDayListByClass.add(course.getDay());

		// get all course name of the teacher
		for (Course course : courseListByStudent)
			courseHoursListByTeacher.add(course.getHours());

		// get all course name of the teacher
		for (Course course : courseListByStudent)
			courseClassNameListByClass.add(course.getClass().getName());

		courseListByStudent.sort(Comparator.comparing(Course::getHours));
		
			for (Course course : courseListByStudent) {
				courseSortedByDay.add(course.getDay() + "-" + course.getHours() + "-" + course.getName() + "-");
				System.out.println("courseday  " + course);
				if (course.getDay().equals("Monday")) {
					System.out.println("courseday  ");

					timeSlotList.add(new TimeSlot(course.getHours(), course.getName()+" "+course.getTeacher().getName() + "-" + course.getHours(), null,
							null, null, null));
				}
				if (course.getDay().equals("Tuesday")) {
					System.out.println("courseday  ");

					timeSlotList.add(new TimeSlot(course.getHours(), null, course.getName()+" "+course.getTeacher().getName() + "-" + course.getHours(),
							null, null, null));
				}
				if (course.getDay().equals("Wednesday")) {
					System.out.println("courseday  ");

					timeSlotList.add(new TimeSlot(course.getHours(), null, null,
							course.getName()+" "+course.getTeacher().getName() + "-" + course.getHours(), null, null));
				}
				if (course.getDay().equals("Thursday")) {
					System.out.println("courseday  ");

					timeSlotList.add(new TimeSlot(course.getHours(), null, null, null,
							course.getName()+" "+course.getTeacher().getName() + "-" + course.getHours(), null));
				}
				if (course.getDay().equals("Friday")) {
					System.out.println("courseday  ");

					timeSlotList.add(new TimeSlot(course.getHours(), null, null, null, null,
							course.getName()+" "+course.getTeacher().getName() + "-" + course.getHours()));
				}

			}
			timeSlotList.sort(Comparator.comparing(TimeSlot::getName));

			for (String day : days) {
				heightAM.add(day);
				tenAM.add(day);
				noon.add(day);
				twoPM.add(day);
				fourPM.add(day);
				sixPM.add(day);
			}
			for (TimeSlot slot : timeSlotList) {
				for (String item : heightAM) {
					if (item.equals("monday") && slot.getMonday() != null && slot.getMonday().split("-")[1].equals("08AM")) {
						heightAM.set(0,slot.getMonday()+"monday");
					}
					if (item.equals("tuesday") && slot.getTuesday() != null && slot.getTuesday().split("-")[1].equals("08AM")) {
						heightAM.set(1,slot.getTuesday()+"tuesday");
					}
					
					if (item.equals("wednesday") && slot.getWednesday() != null && slot.getWednesday().split("-")[1].equals("08AM")) {
						heightAM.set(2,slot.getWednesday()+"wednesday");
					}
					
					if (item.equals("thursday") && slot.getThursday() != null && slot.getThursday().split("-")[1].equals("08AM")) {
						heightAM.set(3,slot.getThursday()+"thursday");
					}
					
					if (item.equals("friday") && slot.getFriday() != null && slot.getFriday().split("-")[1].equals("08AM")) {
						heightAM.set(4,slot.getFriday()+"friday");
					}
				}
			}
			for (TimeSlot slot : timeSlotList) {
				for (String item : tenAM) {
					if (item.equals("monday") && slot.getMonday() != null && slot.getMonday().split("-")[1].equals("10AM")) {
						tenAM.set(0,slot.getMonday()+"monday");
					}
					
					if (item.equals("tuesday") && slot.getTuesday() != null && slot.getTuesday().split("-")[1].equals("10AM")) {
						tenAM.set(1,slot.getTuesday()+"tuesday");
					}
					
					if (item.equals("wednesday") && slot.getWednesday() != null && slot.getWednesday().split("-")[1].equals("10AM")) {
						tenAM.set(2,slot.getWednesday()+"wednesday");
					}
					
					if (item.equals("thursday") && slot.getThursday() != null && slot.getThursday().split("-")[1].equals("10AM")) {
						tenAM.set(3,slot.getThursday()+"thursday");
					}
					
					if (item.equals("friday") && slot.getFriday() != null && slot.getFriday().split("-")[1].equals("10AM")) {
						tenAM.set(4,slot.getFriday()+"friday");
					}
				}
			}
			for (TimeSlot slot : timeSlotList) {
				for (String item : noon) {
					if (item.equals("monday") && slot.getMonday() != null && slot.getMonday().split("-")[1].equals("12AM")) {
						noon.set(0,slot.getMonday()+"monday");
					}
					
					if (item.equals("tuesday") && slot.getTuesday() != null && slot.getTuesday().split("-")[1].equals("12AM")) {
						noon.set(1,slot.getTuesday()+"tuesday");
					}
					
					if (item.equals("wednesday") && slot.getWednesday() != null && slot.getWednesday().split("-")[1].equals("12AM")) {
						noon.set(2,slot.getWednesday()+"wednesday");
					}
					
					if (item.equals("thursday") && slot.getThursday() != null && slot.getThursday().split("-")[1].equals("12AM")) {
						noon.set(3,slot.getThursday()+"thursday");
					}
					
					if (item.equals("friday") && slot.getFriday() != null && slot.getFriday().split("-")[1].equals("12AM")) {
						noon.set(4,slot.getFriday()+"friday");
					}
				}
			}
			for (TimeSlot slot : timeSlotList) {
				for (String item : twoPM) {
					if (item.equals("monday") && slot.getMonday() != null && slot.getMonday().split("-")[1].equals("2PM")) {
						twoPM.set(0,slot.getMonday()+"monday");
					}
					
					if (item.equals("tuesday") && slot.getTuesday() != null && slot.getTuesday().split("-")[1].equals("2PM")) {
						twoPM.set(1,slot.getTuesday()+"tuesday");
					}
					
					if (item.equals("wednesday") && slot.getWednesday() != null && slot.getWednesday().split("-")[1].equals("2PM")) {
						twoPM.set(2,slot.getWednesday()+"wednesday");
					}
					
					if (item.equals("thursday") && slot.getThursday() != null && slot.getThursday().split("-")[1].equals("2PM")) {
						twoPM.set(3,slot.getThursday()+"thursday");
					}
					
					if (item.equals("friday") && slot.getFriday() != null && slot.getFriday().split("-")[1].equals("2PM")) {
						twoPM.set(4,slot.getFriday()+"friday");
					}
				}
			}
			for (TimeSlot slot : timeSlotList) {
				for (String item : fourPM) {
					if (item.equals("monday") && slot.getMonday() != null && slot.getMonday().split("-")[1].equals("4PM")) {
						fourPM.set(0,slot.getMonday()+"monday");
					}
					
					if (item.equals("tuesday") && slot.getTuesday() != null && slot.getTuesday().split("-")[1].equals("4PM")) {
						fourPM.set(1,slot.getTuesday()+"tuesday");
					}
					
					if (item.equals("wednesday") && slot.getWednesday() != null && slot.getWednesday().split("-")[1].equals("4PM")) {
						fourPM.set(2,slot.getWednesday()+"wednesday");
					}
					
					if (item.equals("thursday") && slot.getThursday() != null && slot.getThursday().split("-")[1].equals("4PM")) {
						fourPM.set(3,slot.getThursday()+"thursday");
					}
					
					if (item.equals("friday") && slot.getFriday() != null && slot.getFriday().split("-")[1].equals("4PM")) {
						fourPM.set(4,slot.getFriday()+"friday");
					}
				}
			}
			for (TimeSlot slot : timeSlotList) {
				for (String item : sixPM) {
					if (item.equals("monday") && slot.getMonday() != null && slot.getMonday().split("-")[1].equals("6PM")) {
						sixPM.set(0,slot.getMonday()+"monday");
					}
					
					if (item.equals("tuesday") && slot.getTuesday() != null && slot.getTuesday().split("-")[1].equals("6PM")) {
						sixPM.set(1,slot.getTuesday()+"tuesday");
					}
					
					if (item.equals("wednesday") && slot.getWednesday() != null && slot.getWednesday().split("-")[1].equals("6PM")) {
						sixPM.set(2,slot.getWednesday()+"wednesday");
					}
					
					if (item.equals("thursday") && slot.getThursday() != null && slot.getThursday().split("-")[1].equals("6PM")) {
						sixPM.set(3,slot.getThursday()+"thursday");
					}
					
					if (item.equals("friday") && slot.getFriday() != null && slot.getFriday().split("-")[1].equals("6PM")) {
						sixPM.set(4,slot.getFriday()+"friday");
					}
				}
			}

		

//		for (Course slot : courseListByStudent) {
//
//			System.out.println("yolo  " + slot.getName()+ student.getName());
//
//		}

		hoursDayList.sort(String::compareToIgnoreCase);
		for (String hours : hoursDayList)
			System.out.println(hours);
		// set all attributes to the jsps
		model.addAttribute("teachers", teachersNames);
		model.addAttribute("classNameList", classNameList);
		model.addAttribute("courseNameList", courseNameList);
		model.addAttribute("courseDayList", courseDayList);
		model.addAttribute("hoursDayList", hoursDayList);
		// permit to hidden or not the display
		model.addAttribute("visualize", visualize);
		model.addAttribute("profile", message);
		model.addAttribute("courseListByTeacher", courseListByStudent);
		model.addAttribute("courseDayListByTeacher", courseDayListByClass);
		model.addAttribute("courseHoursListByTeacher", courseHoursListByTeacher);
		model.addAttribute("courseClassNameListByTeacher", courseClassNameListByClass);
		model.addAttribute("courseSortedByDay", courseSortedByDay);
		model.addAttribute("timeSlotList", timeSlotList);
		model.addAttribute("heightAM", heightAM);
		model.addAttribute("tenAM", tenAM);
		model.addAttribute("noon", noon);
		model.addAttribute("twoPM", twoPM);
		model.addAttribute("fourPM", fourPM);
		model.addAttribute("sixPM", sixPM);


		return "timeTable";
	}


}
