package com.lp.teacher.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lp.model.CAASPPScores;
import com.lp.model.CAASPPTypes;
import com.lp.model.Grade;
import com.lp.model.School;
import com.lp.model.Student;
import com.lp.model.StudentClass;
import com.lp.model.Teacher;
import com.lp.teacher.dao.CAASPPDAO;

public class CAASPPServiceImpl implements CAASPPService {
	@Autowired
	private CAASPPDAO caasppdao;

	@Override
	public boolean addCAASPPScores(InputStream inputStream) {
		HashSet<CAASPPScores> caasppScores = parseFile(inputStream);
		return caasppdao.addCAASPPScores(caasppScores);
	}

	private HashSet<CAASPPScores> parseFile(InputStream inputStream) {
		HashMap<Long, Student> studentHashMap = getActiveStudents();		
		HashMap<Long, Teacher> teacherHashMap = getActiveTeachers();
		HashMap<Long, HashMap<Long, Grade>> gradesHashMap = getActiveGrades();
		HashMap<Long, HashMap<String, StudentClass>> classesHashMap = getActiveClasses();
		HashMap<String, School> schoolHashMap = getSchools();
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
		CAASPPTypes elaType = new CAASPPTypes(1);
		CAASPPTypes mathType = new CAASPPTypes(2);
		HashSet<CAASPPScores> caasppScores = new HashSet<CAASPPScores>();
		
		try {
			br = new BufferedReader(new InputStreamReader(inputStream));
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] dataLine = line.split(cvsSplitBy);		
				HashMap<Long, Grade> schoolGrades = null;
				HashMap<String, StudentClass> schoolClasses = null;
				School school = null;
				Teacher teacher = null;
				Grade currentGrade = null;
				StudentClass currentClass = null;
				if(!dataLine[0].equals("StudentId") && !dataLine[0].equals("NA") && !dataLine[1].equals("NA") && !dataLine[2].equals("NA") && !dataLine[3].equals("NA")
						 && !dataLine[5].equals("NA") && !dataLine[7].equals("NA") && !dataLine[8].equals("NA") && !dataLine[0].isEmpty() && !dataLine[1].isEmpty() && !dataLine[2].isEmpty() && 
						 !dataLine[3].isEmpty() && !dataLine[5].isEmpty() && !dataLine[7].isEmpty() && !dataLine[8].isEmpty()  ){
				Student student = studentHashMap.get(Long.valueOf(dataLine[0]));
				teacher = teacherHashMap.get(Long.valueOf(dataLine[3]));
				school = schoolHashMap.get(dataLine[1].toString().trim());				
				if(school != null)
				{
					schoolGrades = gradesHashMap.get(school.getSchoolId());
					schoolClasses = classesHashMap.get(school.getSchoolId());
				}
				if(schoolGrades !=null && !schoolGrades.isEmpty()){
					currentGrade = schoolGrades.get(Long.valueOf(dataLine[2]));
				}	
				if(schoolClasses !=null && !schoolClasses.isEmpty()){
					currentClass = schoolClasses.get(dataLine[8].toString());
				}		
				if(student != null && currentGrade != null && currentClass!=null){
					CAASPPScores elaScore = new CAASPPScores();
					elaScore.setTeacher(teacher);
					elaScore.setStudent(student);
					elaScore.setGrade(currentGrade);
					elaScore.setCaasppType(elaType);
					elaScore.setCaasppScore(Float.parseFloat(dataLine[5]));	
					elaScore.setStudentClass(currentClass);
					caasppScores.add(elaScore);						
					CAASPPScores mathScore = new CAASPPScores();
					mathScore.setTeacher(teacher);
					mathScore.setStudent(student);
					mathScore.setGrade(currentGrade);
					mathScore.setCaasppType(mathType);
					mathScore.setCaasppScore(Float.parseFloat(dataLine[7]));		
					mathScore.setStudentClass(currentClass);
					caasppScores.add(mathScore);
				}	
				
			}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return caasppScores;
	}

	private HashMap<Long, Student> getActiveStudents() {
		HashMap<Long, Student> studentHashMap = new HashMap<Long, Student>();
		List<Student> students = null;
		students = caasppdao.getActiveStudents();
		for (Student student : students) {
			studentHashMap.put(student.getStudentScId(), student);
		}
		return studentHashMap;
	}

	private HashMap<Long, Teacher> getActiveTeachers() {
		HashMap<Long, Teacher> teacherHashMap = new HashMap<Long, Teacher>();
		List<Teacher> teachers = null;
		teachers = caasppdao.getActiveTeachers();
		for (Teacher teacher : teachers) {
			teacherHashMap.put(teacher.getTeacherScId(), teacher);
		}
		return teacherHashMap;
	}

	private HashMap<Long,  HashMap<Long, Grade>> getActiveGrades() {
		HashMap<Long, HashMap<Long, Grade>> gradesHashMap = new HashMap<Long, HashMap<Long, Grade>>();		
		List<Grade> grades = null;
		grades = caasppdao.getActiveGrades();
		Long previousSchoolId = null;
		if(!grades.isEmpty()){
			previousSchoolId = grades.get(0).getSchoolId(); 
		}
 		HashMap<Long, Grade> schoolGrades = new HashMap<Long, Grade> ();
		for(Grade grade : grades){			
			if(previousSchoolId != grade.getSchoolId()){
				HashMap<Long, Grade> schoolGradesVar = new HashMap<> ();
				schoolGradesVar.putAll(schoolGrades);
				gradesHashMap.put(previousSchoolId, schoolGradesVar);
				previousSchoolId = grade.getSchoolId();
				schoolGrades.clear();
			}
			schoolGrades.put(grade.getMasterGrades().getMasterGradesId(), grade);
		}
		return gradesHashMap;
	}
	

	private HashMap<String, School> getSchools() {
		HashMap<String, School> schoolHashMap = new HashMap<String, School>();
		List<School> schools = null;
		schools = caasppdao.getSchools();
		for (School school : schools) {
			schoolHashMap.put(school.getSchoolName(), school);
		}
		return schoolHashMap;
	}
	
	private HashMap<Long,  HashMap<String, StudentClass>> getActiveClasses() {
		HashMap<Long, HashMap<String, StudentClass>> classesHashMap = new HashMap<Long, HashMap<String, StudentClass>>();		
		List<StudentClass> classes = null;
		classes = caasppdao.getActiveClasses();
		Long previousSchoolId = null;
		if(!classes.isEmpty()){
			previousSchoolId = classes.get(0).getSchool().getSchoolId(); 
		}
		HashMap<String, StudentClass> schoolClasses = new HashMap<String, StudentClass> ();
		for(StudentClass clas : classes){			
			if(previousSchoolId != clas.getSchool().getSchoolId()){
				HashMap<String, StudentClass> schoolClassesVar = new HashMap<String, StudentClass> ();
				schoolClassesVar.putAll(schoolClasses);
				classesHashMap.put(previousSchoolId, schoolClassesVar);
				previousSchoolId = clas.getSchool().getSchoolId();
				schoolClasses.clear();
			}
			schoolClasses.put(clas.getClassName(), clas);
		}
		return classesHashMap;
	}
}
