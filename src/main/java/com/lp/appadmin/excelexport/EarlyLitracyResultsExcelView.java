package com.lp.appadmin.excelexport;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.lp.model.AssignK1Tests;

public class EarlyLitracyResultsExcelView extends AbstractXlsView {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void buildExcelDocument(Map model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Sheet sheet = workbook.createSheet("EarlyLitracy Results");
		setExcelHeader(sheet);
		
		List<AssignK1Tests> earlyLitracyResults = (List<AssignK1Tests>) model.get("earlyLitracyResults");
		setExcelRows(sheet, earlyLitracyResults);
	}

	public void setExcelHeader(Sheet sheet) {
		Row excelHeader = sheet.createRow(0);
		excelHeader.createCell(0).setCellValue("First Name");
		excelHeader.createCell(1).setCellValue("Last Name");
		excelHeader.createCell(2).setCellValue("Test Title");
		excelHeader.createCell(3).setCellValue("Set Name");
		excelHeader.createCell(4).setCellValue("Percentage");
		excelHeader.createCell(5).setCellValue("Num of Words");
		excelHeader.createCell(6).setCellValue("Num of correct Words ");
		excelHeader.createCell(7).setCellValue("Teacher Name");
		excelHeader.createCell(8).setCellValue("Student Grade Level");
		excelHeader.createCell(9).setCellValue("Test Submission Date");
		excelHeader.createCell(10).setCellValue("School");
	}
	
	public void setExcelRows(Sheet sheet, List<AssignK1Tests> earlyLitracyResults){
		int record = 1,correctWords=0;
		SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
		String[] marks=null;
		for (AssignK1Tests earlyLitResult : earlyLitracyResults) {
			correctWords=0;
			Row excelRow = sheet.createRow(record++);
			excelRow.createCell(0).setCellValue(earlyLitResult.getStudentAssignmentStatus().getStudent().getUserRegistration().getFirstName());
			excelRow.createCell(1).setCellValue(earlyLitResult.getStudentAssignmentStatus().getStudent().getUserRegistration().getLastName());
			excelRow.createCell(2).setCellValue(earlyLitResult.getStudentAssignmentStatus().getAssignment().getTitle());
			excelRow.createCell(3).setCellValue(earlyLitResult.getk1Set().getSetName());
			excelRow.createCell(4).setCellValue(earlyLitResult.getStudentAssignmentStatus().getPercentage()); 
			String gradedMarks = earlyLitResult.getMarksGraded();
			if(gradedMarks != null && !gradedMarks.equals("")){
				marks =  gradedMarks.split(" ");
				for(int i=0;i<marks.length;i++){
					if(marks[i].equalsIgnoreCase("1"))
						correctWords++;
				}
					
			}
			excelRow.createCell(5).setCellValue(marks.length); 	
			excelRow.createCell(6).setCellValue(correctWords);
			excelRow.createCell(7).setCellValue((earlyLitResult.getStudentAssignmentStatus().getAssignment().getClassStatus().getTeacher().getUserRegistration().getTitle().isEmpty()?"":earlyLitResult.getStudentAssignmentStatus().getAssignment().getClassStatus().getTeacher().getUserRegistration().getTitle()+". ")
					+ earlyLitResult.getStudentAssignmentStatus().getAssignment().getClassStatus().getTeacher().getUserRegistration().getFirstName()+" "
					+earlyLitResult.getStudentAssignmentStatus().getAssignment().getClassStatus().getTeacher().getUserRegistration().getLastName());
			excelRow.createCell(8).setCellValue(earlyLitResult.getStudentAssignmentStatus().getStudent().getGrade().getMasterGrades().getGradeName());
			excelRow.createCell(9).setCellValue(sm.format(earlyLitResult.getStudentAssignmentStatus().getSubmitdate()));
			excelRow.createCell(10).setCellValue(earlyLitResult.getStudentAssignmentStatus().getStudent().getUserRegistration().getSchool().getSchoolAbbr());
		}
	}
}