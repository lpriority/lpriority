package com.lp.appadmin.excelexport;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.lp.admin.service.ReadRegReviewResultsService;
import com.lp.model.Grade;
import com.lp.model.ReadRegActivityScore;
import com.lp.model.Student;
import com.lp.student.dao.StudentReadRegDAO;

public class StudentRRReportsExcelView extends AbstractXlsView {
	
	@Autowired
	private ReadRegReviewResultsService resultsService;
	@Autowired
	private StudentReadRegDAO studentReadRegDAO;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void buildExcelDocument(Map model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Sheet sheet = workbook.createSheet("Reading Register Reports");
		String reportType=model.get("reportType").toString();
		long userTypeId=Long.parseLong(model.get("userTypeId").toString());
		setExcelHeader(sheet,reportType,userTypeId);
		if(reportType.equalsIgnoreCase("studentWise")){
			List<ReadRegActivityScore> readRegActScoreLst = (List<ReadRegActivityScore>) model.get("readRegActivityScores");
			String allBookTotPE=model.get("allBookTotPE").toString();
			setExcelRows1(sheet, readRegActScoreLst,allBookTotPE);
		}else if(reportType.equalsIgnoreCase("classWise")){
			Map<String,List<ReadRegActivityScore>> classRRReports = (HashMap<String,List<ReadRegActivityScore>>) model.get("listClassRRReports");
			List<Long> studTotPoints= (List<Long>) model.get("allStudsTotPointsEarned");
			List<Student> studentLst=(List<Student>) model.get("studentLst");
			setExcelRows(sheet,classRRReports,studTotPoints,studentLst,userTypeId);
		}else if(reportType.equalsIgnoreCase("gradeWise")){
			Map<String,Map<String,List<ReadRegActivityScore>>> gradeRRReports=(HashMap<String,Map<String,List<ReadRegActivityScore>>>) model.get("listGradeRRReports");
			List<Grade> gradeList=(List<Grade>) model.get("gradeList");
			//Map<String,List<ReadRegActivityScore>> classRRReports = (HashMap<String,List<ReadRegActivityScore>>) model.get("listClassRRReports");
			setExcelRows2(sheet,gradeRRReports,gradeList);
		}
			
	}

	public void setExcelHeader(Sheet sheet,String reportType,long userTypeId) {
		sheet.setDefaultColumnWidth(25);
		if(reportType.equalsIgnoreCase("studentWise")){
		Row excelHeader = sheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Book Title");
		excelHeader.createCell(1).setCellValue("Author");
		excelHeader.createCell(2).setCellValue("Created Date");
		excelHeader.createCell(3).setCellValue("Student Rating");
		excelHeader.createCell(4).setCellValue("Activity type");
		excelHeader.createCell(5).setCellValue("Rubric score");
		excelHeader.createCell(6).setCellValue("Page Range");
		excelHeader.createCell(7).setCellValue("Total Points Earned for Activity");
		excelHeader.createCell(8).setCellValue("Total Points earned all books");
		}else if(reportType.equalsIgnoreCase("classWise")){
			Row excelHeader = sheet.createRow(0);
			excelHeader.createCell(0).setCellValue("Student First Name");
			excelHeader.createCell(1).setCellValue("Student Last Name");
			excelHeader.createCell(2).setCellValue("Book Title");
			excelHeader.createCell(3).setCellValue("Author");
			excelHeader.createCell(4).setCellValue("Created Date");
			if(userTypeId==3){
			excelHeader.createCell(5).setCellValue("Student Rating");
			excelHeader.createCell(6).setCellValue("Activity type");
			excelHeader.createCell(7).setCellValue("Rubric score");
			excelHeader.createCell(8).setCellValue("Page Range");
			excelHeader.createCell(9).setCellValue("Total Points Earned for Activity");
			excelHeader.createCell(10).setCellValue("Total Points earned all books");
			}else{
				excelHeader.createCell(5).setCellValue("Teacher Name");
				excelHeader.createCell(6).setCellValue("Student Rating");
				excelHeader.createCell(7).setCellValue("Activity type");
				excelHeader.createCell(8).setCellValue("Rubric score");
				excelHeader.createCell(9).setCellValue("Page Range");
				excelHeader.createCell(10).setCellValue("Total Points Earned for Activity");
				excelHeader.createCell(11).setCellValue("Total Points earned all books");
			}
		}else{
			Row excelHeader = sheet.createRow(0);
			excelHeader.createCell(0).setCellValue("Grade Level");
			excelHeader.createCell(1).setCellValue("Student First Name");
			excelHeader.createCell(2).setCellValue("Student Last Name");
			excelHeader.createCell(3).setCellValue("Book Title");
			excelHeader.createCell(4).setCellValue("Author");
			excelHeader.createCell(5).setCellValue("Created Date");
			excelHeader.createCell(6).setCellValue("Teacher Name");
			excelHeader.createCell(7).setCellValue("Student Rating");
			excelHeader.createCell(8).setCellValue("Activity type");
			excelHeader.createCell(9).setCellValue("Rubric score");
			excelHeader.createCell(10).setCellValue("Page Range");
			excelHeader.createCell(11).setCellValue("Total Points Earned for Activity");
			excelHeader.createCell(12).setCellValue("Total Points earned all books");
		}
					
	}
	
	
	public void setExcelRows(Sheet sheet, Map<String,List<ReadRegActivityScore>> classRRReports ,List<Long> allStudBookTotPE,List<Student> studentLst,long userTypeId){
		int record = 1,i=0;
		SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
		List<ReadRegActivityScore> readRegActScoreLst=new ArrayList<ReadRegActivityScore>();
		for (Student student : studentLst) {
			    long studentId=student.getStudentId();
				String stud="stud"+student.getStudentId();
				if(classRRReports.containsKey(stud)){
					readRegActScoreLst = classRRReports.get(stud); //val is the value corresponding to key temp
					int count=1;
					for (ReadRegActivityScore studReadRegActScore : readRegActScoreLst) {
						Row excelRow = sheet.createRow(record++);
						String studentFirstName=student.getUserRegistration().getFirstName();
						String studentLastName=student.getUserRegistration().getLastName();
						excelRow.createCell(0).setCellValue(studentFirstName);
						excelRow.createCell(1).setCellValue(studentLastName);
						excelRow.createCell(2).setCellValue(studReadRegActScore.getReadRegMaster().getBookTitle());
						excelRow.createCell(3).setCellValue(studReadRegActScore.getReadRegMaster().getAuthor());
						excelRow.createCell(4).setCellValue(sm.format(studReadRegActScore.getReadRegMaster().getCreateDate()));
						if(userTypeId==3){
							excelRow.createCell(5).setCellValue(studReadRegActScore.getReadRegReview().getRating());
							excelRow.createCell(6).setCellValue(studReadRegActScore.getReadRegActivity().getActitvity());
							if(studReadRegActScore.getReadRegRubric()!=null)
							excelRow.createCell(7).setCellValue(studReadRegActScore.getReadRegRubric().getScore());
							else
							excelRow.createCell(7).setCellValue(0);
							excelRow.createCell(8).setCellValue(studReadRegActScore.getReadRegMaster().getReadRegPageRange().getRange());
							if(studReadRegActScore.getPointsEarned()!=null)
							excelRow.createCell(9).setCellValue(studReadRegActScore.getPointsEarned());
							//if(count==1)
								excelRow.createCell(10).setCellValue((allStudBookTotPE.get(i)));
						}else{
						    excelRow.createCell(5).setCellValue(studReadRegActScore.getTeacher().getUserRegistration().getFirstName()+" "+studReadRegActScore.getTeacher().getUserRegistration().getLastName());
						    excelRow.createCell(6).setCellValue(studReadRegActScore.getReadRegReview().getRating());
							excelRow.createCell(7).setCellValue(studReadRegActScore.getReadRegActivity().getActitvity());
							if(studReadRegActScore.getReadRegRubric()!=null)
							excelRow.createCell(8).setCellValue(studReadRegActScore.getReadRegRubric().getScore());
							else
							excelRow.createCell(8).setCellValue(0);
							excelRow.createCell(9).setCellValue(studReadRegActScore.getReadRegMaster().getReadRegPageRange().getRange());
							if(studReadRegActScore.getPointsEarned()!=null)
							excelRow.createCell(10).setCellValue(studReadRegActScore.getPointsEarned());
							//if(count==1)
								excelRow.createCell(11).setCellValue((allStudBookTotPE.get(i))); 
					  }
						count++;
			
					}
				}
				i++;
		}
	}
	public void setExcelRows1(Sheet sheet, List<ReadRegActivityScore> readRegActScoreLst,String allBookTotPE) throws ParseException{
		int record = 1;
		SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
		for (ReadRegActivityScore studReadRegActScore : readRegActScoreLst) {
			Row excelRow = sheet.createRow(record++);
			excelRow.createCell(0).setCellValue(studReadRegActScore.getReadRegMaster().getBookTitle());
			excelRow.createCell(1).setCellValue(studReadRegActScore.getReadRegMaster().getAuthor());
			excelRow.createCell(2).setCellValue(sm.format(studReadRegActScore.getReadRegMaster().getCreateDate()));
			excelRow.createCell(3).setCellValue(studReadRegActScore.getReadRegReview().getRating());
			excelRow.createCell(4).setCellValue(studReadRegActScore.getReadRegActivity().getActitvity());
			if(studReadRegActScore.getReadRegRubric()!=null)
			excelRow.createCell(5).setCellValue(studReadRegActScore.getReadRegRubric().getScore());
			else
			excelRow.createCell(5).setCellValue(0);
			excelRow.createCell(6).setCellValue(studReadRegActScore.getReadRegMaster().getReadRegPageRange().getRange());
			if(studReadRegActScore.getPointsEarned()!=null)
			excelRow.createCell(7).setCellValue(studReadRegActScore.getPointsEarned());
			if(record==2)
			excelRow.createCell(8).setCellValue((allBookTotPE));
				
		}
}
	public void setExcelRows2(Sheet sheet, Map<String,Map<String,List<ReadRegActivityScore>>> gradeRRReports ,List<Grade> gradeLst){
		int record = 1,i=0;
		SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
		long totalPointsEarned=0;
		Map<String,List<ReadRegActivityScore>> classRRReports = new HashMap<String,List<ReadRegActivityScore>>() ;
		List<ReadRegActivityScore> readRegActScoreLst=new ArrayList<ReadRegActivityScore>();
		for (Grade grade : gradeLst) {
			String grad="grade"+grade.getGradeId();
				if(gradeRRReports.containsKey(grad)){
					classRRReports=gradeRRReports.get(grad);
										
					List<Student> studentLst=resultsService.getRRStudents(grade.getGradeId());
					
					int ge=1;
					for (Student student : studentLst) {
						
					    long studentId=student.getStudentId();
						String stud="stud"+student.getStudentId();
						if(classRRReports.containsKey(stud)){
							readRegActScoreLst = classRRReports.get(stud); //val is the value corresponding to key temp
							int count=1;
							for (ReadRegActivityScore studReadRegActScore : readRegActScoreLst) {
								Row excelRow = sheet.createRow(record++);
								excelRow.createCell(0).setCellValue(grade.getMasterGrades().getGradeName());
								String studentName=student.getUserRegistration().getFirstName();
								String studentLastName = student.getUserRegistration().getLastName();
								excelRow.createCell(1).setCellValue(studentName);
								excelRow.createCell(2).setCellValue(studentLastName);
								excelRow.createCell(3).setCellValue(studReadRegActScore.getReadRegMaster().getBookTitle());
								excelRow.createCell(4).setCellValue(studReadRegActScore.getReadRegMaster().getAuthor());
								excelRow.createCell(5).setCellValue(sm.format(studReadRegActScore.getReadRegMaster().getCreateDate()));
								excelRow.createCell(6).setCellValue(studReadRegActScore.getTeacher().getUserRegistration().getFirstName()+" "+studReadRegActScore.getTeacher().getUserRegistration().getLastName());
								excelRow.createCell(7).setCellValue(studReadRegActScore.getReadRegReview().getRating());
								excelRow.createCell(8).setCellValue(studReadRegActScore.getReadRegActivity().getActitvity());
								if((studReadRegActScore.getReadRegRubric())!=null){
								excelRow.createCell(9).setCellValue(studReadRegActScore.getReadRegRubric().getScore());
								}else
									excelRow.createCell(9).setCellValue(0);
								excelRow.createCell(10).setCellValue(studReadRegActScore.getReadRegMaster().getReadRegPageRange().getRange());
								if(studReadRegActScore.getPointsEarned()!=null)
								 excelRow.createCell(11).setCellValue(studReadRegActScore.getPointsEarned());
								if(count==1)
									 totalPointsEarned=studentReadRegDAO.getStudentTotalPointsEarned(student.getStudentId(), grade.getGradeId());
								excelRow.createCell(12).setCellValue(totalPointsEarned);
								count++;
								ge++;
					
							}
						}
						
				}
	     }
}
	}
}