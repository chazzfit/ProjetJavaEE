package com.demo.Metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.AdminRepository;
import com.demo.dao.ClassRepository;
import com.demo.dao.NewsRepository;
import com.demo.dao.StudentRepository;
import com.demo.dao.TeacherRepository;
import com.demo.entities.Admin;
import com.demo.entities.GroupClass;
import com.demo.entities.News;
import com.demo.entities.Student;
import com.demo.entities.Teacher;

@Service
@Transactional
public class IntranetMetierImplement implements IntranetMetierInterface{

	@Autowired
	private StudentRepository studentRep ; 
	@Autowired
	private TeacherRepository teacherRep;
	@Autowired
	private AdminRepository adminRep;
	@Autowired
	private NewsRepository newsRep;
	@Autowired
	private ClassRepository classRep;
	@Override
	public void createAdmin(String name, String username, String password) {
		// TODO Auto-generated method stub
		Admin admin = adminRep.save(new Admin(name, username, password));
		
	}

	@Override
	public void createStudent(String name, String username, String password, GroupClass studentClass) {
		// TODO Auto-generated method stub
		Student student = studentRep.save(new Student(name, username, password, studentClass));

		
	}

	@Override
	public void createTeacher(String name, String username, String password) {
		// TODO Auto-generated method stub
		Teacher teacher = teacherRep.save(new Teacher(name, username, password));

		
	}

	@Override
	public void createNews(String title, String description, String image, String date, boolean isActive) {
		// TODO Auto-generated method stub
		News news = newsRep.save(new News(title, description, image, date, isActive));
		
		
	}
	
	@Override
	public List<GroupClass> getGroupClassList() {
		return classRep.getGroupClassList();
	}

	@Override
	public List<News> getNewsList() {
		// TODO Auto-generated method stub
		return newsRep.getNewsList();
	}

	@Override
	public void deleteNewsFromTitle(String title) {
		// TODO Auto-generated method stub
		newsRep.deleteNewsFromTitle(title);
		
	}

	@Override
	public News getNewsFromTitle(String title) {
		// TODO Auto-generated method stub
		return newsRep.getNewsFromTitle(title);
	}

	@Override
	public void updateNews(Long id, String title, String description, String image, String date, boolean isActive) {
		// TODO Auto-generated method stub
		System.out.println(title);
		newsRep.updateNews(id, title, description, image, date, isActive);
		
	}

	@Override
	public List<News> getLatestNewsList() {
		// TODO Auto-generated method stub
		return newsRep.getLatestNewsList();
	}

}
