
package com.lp.appadmin.excelexport;


import java.text.DecimalFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.itextpdf.text.pdf.CFFFont;
import com.lp.model.BenchmarkResults;
import com.lp.model.Grade;
import com.lp.model.ReadRegActivityScore;
import com.lp.model.School;
import com.lp.model.Student;
import com.lp.student.dao.StudentReadRegDAO;
import com.lp.admin.service.AdminService;
import com.lp.admin.service.ReadRegReviewResultsService;
import com.lp.common.service.*;

public class DistrictRRReportsExcelView extends AbstractXlsView {
	
	@Autowired
	private ReadRegReviewResultsService resultsService;
	@Autowired
	private StudentReadRegDAO studentReadRegDAO;
	@Autowired 
	private AdminService adminService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void buildExcelDocument(Map model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Sheet sheet = workbook.createSheet("District Reading Register Reports");
		String reportType=model.get("reportType").toString();
		/*Map<String,Map<String,List<ReadRegActivityScore>>> gradeRRReports1 = new HashMap<String,Map<String,List<ReadRegActivityScore>>>() ; 
		List<School> schoolLst1=(List<School>) model.get("schoolLst");*/
		if(reportType.equalsIgnoreCase("districtWise")){
				Map<String,Map<String,Map<String,List<ReadRegActivityScore>>>> districtRRReports=(HashMap<String,Map<String,Map<String,List<ReadRegActivityScore>>>>) model.get("listDistrictRRReports");
				List<School> schoolLst=(List<School>) model.get("schoolLst");
				setExcelHeader(sheet,reportType);
				setExcelRows2(sheet,districtRRReports,schoolLst);
				setExcelRows4(districtRRReports,schoolLst,workbook);
		}
			
	}

	public void setExcelHeader(Sheet sheet,String reportType) {
		
		sheet.setDefaultColumnWidth(25);
		 	Row excelHeader = sheet.createRow(0);
			excelHeader.createCell(0).setCellValue("School Name");
			excelHeader.createCell(1).setCellValue("Grade Level");
			excelHeader.createCell(2).setCellValue("Student Name");
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
					
	
public void setExcelHeader1(Sheet sheet1) {
	sheet1.setDefaultColumnWidth(25);
		    Row excelHeader1 = sheet1.createRow(0);
			excelHeader1.createCell(0).setCellValue("Grade Level");
			excelHeader1.createCell(1).setCellValue("Student Name");
			excelHeader1.createCell(2).setCellValue("Book Title");
			excelHeader1.createCell(3).setCellValue("Author");
			excelHeader1.createCell(4).setCellValue("Created Date");
			excelHeader1.createCell(5).setCellValue("Teacher Name");
			excelHeader1.createCell(6).setCellValue("Student Rating");
			excelHeader1.createCell(7).setCellValue("Activity type");
			excelHeader1.createCell(8).setCellValue("Rubric score");
			excelHeader1.createCell(9).setCellValue("Page Range");
			excelHeader1.createCell(10).setCellValue("Total Points Earned for Activity");
			excelHeader1.createCell(11).setCellValue("Total Points earned all books");
	
	}
	
	public void setExcelRows2(Sheet sheet, Map<String,Map<String,Map<String,List<ReadRegActivityScore>>>> districtRRReports ,List<School> schoolLst){
		int record = 1,i=0;
		long totalPointsEarned=0;
		SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
		Map<String,Map<String,List<ReadRegActivityScore>>> gradeRRReports = new HashMap<String,Map<String,List<ReadRegActivityScore>>>() ;
		Map<String,List<ReadRegActivityScore>> classRRReports = new HashMap<String,List<ReadRegActivityScore>>() ;
		List<ReadRegActivityScore> readRegActScoreLst=new ArrayList<ReadRegActivityScore>();
		for(School school : schoolLst){
			String schol="school"+school.getSchoolId();
			List<Grade> gradeLst = adminService.getSchoolGrades(school.getSchoolId());
			if(districtRRReports.containsKey(schol)){
				gradeRRReports=districtRRReports.get(schol);
				int sc=1;
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
								//if(sc==1)
									excelRow.createCell(0).setCellValue(school.getSchoolName());
								//if(ge==1){
								excelRow.createCell(1).setCellValue(grade.getMasterGrades().getGradeName());
								//}
								//if(count==1){
									String studentName=student.getUserRegistration().getFirstName()+" "+student.getUserRegistration().getLastName();
									excelRow.createCell(2).setCellValue(studentName);
								//}
								excelRow.createCell(3).setCellValue(studReadRegActScore.getReadRegMaster().getBookTitle());
								excelRow.createCell(4).setCellValue(studReadRegActScore.getReadRegMaster().getAuthor());
								excelRow.createCell(5).setCellValue(sm.format(studReadRegActScore.getReadRegMaster().getCreateDate()));
								excelRow.createCell(6).setCellValue(studReadRegActScore.getTeacher().getUserRegistration().getFirstName()+" "+studReadRegActScore.getTeacher().getUserRegistration().getLastName());
								excelRow.createCell(7).setCellValue(studReadRegActScore.getReadRegReview().getRating());
								excelRow.createCell(8).setCellValue(studReadRegActScore.getReadRegActivity().getActitvity());
								if(studReadRegActScore.getReadRegRubric()!=null)
								excelRow.createCell(9).setCellValue(studReadRegActScore.getReadRegRubric().getScore());
								else
									excelRow.createCell(9).setCellValue(0);	
								excelRow.createCell(10).setCellValue(studReadRegActScore.getReadRegMaster().getReadRegPageRange().getRange());
								if(studReadRegActScore.getPointsEarned()!=null)
								 excelRow.createCell(11).setCellValue(studReadRegActScore.getPointsEarned());
								if(count==1)
									totalPointsEarned=studentReadRegDAO.getStudentTotalPointsEarned(student.getStudentId(), grade.getGradeId());
								excelRow.createCell(12).setCellValue(totalPointsEarned);
								count++;
								ge++;
								sc++;
								
					
							}
						}
						
				}
	        }
          }
		}
		}
	}
	public void setExcelRows4( Map<String,Map<String,Map<String,List<ReadRegActivityScore>>>> districtRRReports2 ,List<School> schoolLst2,Workbook workbook2){
		int record2 = 0,i2=0;
		long totalPointsEarned=0;
		SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
		Map<String,Map<String,List<ReadRegActivityScore>>> gradeRRReports2 = new HashMap<String,Map<String,List<ReadRegActivityScore>>>() ;
		Map<String,List<ReadRegActivityScore>> classRRReports2 = new HashMap<String,List<ReadRegActivityScore>>() ;
		List<ReadRegActivityScore> readRegActScoreLst=new ArrayList<ReadRegActivityScore>();
		for(School school2 : schoolLst2){
			record2 = 1;
			String schol2="school"+school2.getSchoolId();
			String schoolName1=school2.getSchoolName();
			List<Grade> gradeLst2 = adminService.getSchoolGrades(school2.getSchoolId());
			Sheet sheet2=workbook2.createSheet(""+schoolName1+" Reports");
			setExcelHeader1(sheet2);
			if(districtRRReports2.containsKey(schol2)){
				gradeRRReports2=districtRRReports2.get(schol2);
				int sc2=1;
				for (Grade grade2 : gradeLst2) {
					String grad2="grade"+grade2.getGradeId();
				if(gradeRRReports2.containsKey(grad2)){
					classRRReports2=gradeRRReports2.get(grad2);
					List<Student> studentLst2=resultsService.getRRStudents(grade2.getGradeId());
					int ge2=1;
					for (Student student2 : studentLst2) {
					    long studentId=student2.getStudentId();
						String stud2="stud"+student2.getStudentId();
						if(classRRReports2.containsKey(stud2)){
							readRegActScoreLst = classRRReports2.get(stud2); //val is the value corresponding to key temp
							int count2=1;
							for (ReadRegActivityScore studReadRegActScore : readRegActScoreLst) {
								Row excelRow = sheet2.createRow(record2++);
								//if(sc==1)
									//excelRow.createCell(0).setCellValue(school2.getSchoolName());
								//if(ge==1){
								excelRow.createCell(0).setCellValue(grade2.getMasterGrades().getGradeName());
								//}
								//if(count==1){
									String studentName=student2.getUserRegistration().getFirstName()+" "+student2.getUserRegistration().getLastName();
									excelRow.createCell(1).setCellValue(studentName);
								//}
								excelRow.createCell(2).setCellValue(studReadRegActScore.getReadRegMaster().getBookTitle());
								excelRow.createCell(3).setCellValue(studReadRegActScore.getReadRegMaster().getAuthor());
								excelRow.createCell(4).setCellValue(sm.format(studReadRegActScore.getReadRegMaster().getCreateDate()));
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
								if(count2==1)
									totalPointsEarned=studentReadRegDAO.getStudentTotalPointsEarned(student2.getStudentId(),grade2.getGradeId());
								excelRow.createCell(11).setCellValue(totalPointsEarned);
								count2++;
								ge2++;
								sc2++;
								
					
							}
						}
						
				}
	        }
          }
		}
		}
	}
		
}