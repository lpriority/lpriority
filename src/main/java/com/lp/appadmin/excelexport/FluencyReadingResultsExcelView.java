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

import com.lp.model.FluencyMarks;

public class FluencyReadingResultsExcelView extends AbstractXlsView {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void buildExcelDocument(Map model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Sheet sheet = workbook.createSheet("Fluency Results");
		setExcelHeader(sheet);		
		List<FluencyMarks> fluencyMarkLt = (List<FluencyMarks>) model.get("fluencyMarks");
		setExcelRows(sheet, fluencyMarkLt);
	}

	public void setExcelHeader(Sheet sheet) {
		Row excelHeader = sheet.createRow(0);
		excelHeader.createCell(0).setCellValue("First Name");
		excelHeader.createCell(1).setCellValue("Last Name");
		excelHeader.createCell(2).setCellValue("Grade");
		excelHeader.createCell(3).setCellValue("Fluency Score/WCPM");
		excelHeader.createCell(4).setCellValue("Accuracy");
		excelHeader.createCell(5).setCellValue("Teacher Notes");
		excelHeader.createCell(6).setCellValue("Submission Date");
		excelHeader.createCell(7).setCellValue("Teacher Name");
	}
	
	public void setExcelRows(Sheet sheet, List<FluencyMarks> fluencyMarks){
		int record = 1;
		SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
		for (FluencyMarks fluencyMark : fluencyMarks) {
			Row excelRow = sheet.createRow(record++);
			excelRow.createCell(0).setCellValue(fluencyMark.getAssignmentQuestions().getStudentAssignmentStatus().getStudent().getUserRegistration().getFirstName());
			excelRow.createCell(1).setCellValue(fluencyMark.getAssignmentQuestions().getStudentAssignmentStatus().getStudent().getUserRegistration().getLastName());
			excelRow.createCell(2).setCellValue(fluencyMark.getAssignmentQuestions().getStudentAssignmentStatus().getStudent().getGrade().getMasterGrades().getMasterGradesId());
			excelRow.createCell(3).setCellValue(fluencyMark.getMarks());
			excelRow.createCell(4).setCellValue(fluencyMark.getAccuracyScore());
			excelRow.createCell(5).setCellValue(fluencyMark.getComment());
			excelRow.createCell(6).setCellValue(sm.format(fluencyMark.getAssignmentQuestions().getStudentAssignmentStatus().getSubmitdate()));
			excelRow.createCell(7).setCellValue(fluencyMark.getAssignmentQuestions().getStudentAssignmentStatus().getAssignment().getClassStatus().getTeacher().getUserRegistration().getFirstName());
		}
	}
}