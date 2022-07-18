package com.lp.appadmin.excelexport;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.lp.model.StudentAssignmentStatus;

public class ComprehensionResultsExcelView extends AbstractXlsView {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void buildExcelDocument(Map model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Sheet sheet = workbook.createSheet("Comprehension Results");
		setExcelHeader(sheet);		
		List<StudentAssignmentStatus> comprehensionResults = (List<StudentAssignmentStatus>) model.get("comprehensionResults");
		setExcelRows(sheet, comprehensionResults);
	}
	
	public void setExcelHeader(Sheet sheet) {
		Row excelHeader = sheet.createRow(0);
		excelHeader.createCell(0).setCellValue("First Name");
		excelHeader.createCell(1).setCellValue("Last Name");
		excelHeader.createCell(2).setCellValue("Percentage");
		excelHeader.createCell(3).setCellValue("School");
		excelHeader.createCell(4).setCellValue("Teacher");
		excelHeader.createCell(5).setCellValue("Submission Date");
		excelHeader.createCell(6).setCellValue("Grade Level");
		excelHeader.createCell(7).setCellValue("Test Id");
		excelHeader.createCell(8).setCellValue("Student ID");
		excelHeader.createCell(9).setCellValue("Test Title");
		
	}
	
	public void setExcelRows(Sheet sheet, List<StudentAssignmentStatus> comprehensionResults){
		int record = 1;
		String studentScId = "";
		SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
		DecimalFormat df = new DecimalFormat("#.##"); 
		for (StudentAssignmentStatus comprehensionResult : comprehensionResults) {
			studentScId = "";
			Row excelRow = sheet.createRow(record++);
			excelRow.createCell(0).setCellValue(comprehensionResult.getStudent().getUserRegistration().getFirstName());
			excelRow.createCell(1).setCellValue(comprehensionResult.getStudent().getUserRegistration().getLastName());
			excelRow.createCell(2).setCellValue(df.format(comprehensionResult.getPercentage()));
			excelRow.createCell(3).setCellValue(comprehensionResult.getStudent().getUserRegistration().getSchool().getSchoolAbbr());
			excelRow.createCell(4).setCellValue((comprehensionResult.getAssignment().getClassStatus().getTeacher().getUserRegistration().getTitle().isEmpty()?"":comprehensionResult.getAssignment().getClassStatus().getTeacher().getUserRegistration().getTitle()+". ")
			+ comprehensionResult.getAssignment().getClassStatus().getTeacher().getUserRegistration().getFirstName()+" "
			+comprehensionResult.getAssignment().getClassStatus().getTeacher().getUserRegistration().getLastName());
			excelRow.createCell(5).setCellValue(sm.format(comprehensionResult.getSubmitdate()));
			excelRow.createCell(6).setCellValue(comprehensionResult.getStudent().getGrade().getMasterGrades().getMasterGradesId());
			excelRow.createCell(7).setCellValue(comprehensionResult.getStudentAssignmentId());
			if(comprehensionResult.getStudent().getStudentScId() != null){
				studentScId = comprehensionResult.getStudent().getStudentScId().toString(); 
			}
			excelRow.createCell(8).setCellValue(studentScId);
			excelRow.createCell(9).setCellValue(comprehensionResult.getAssignment().getTitle());
		}
	}
}