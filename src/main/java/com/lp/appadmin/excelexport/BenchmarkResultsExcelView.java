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

import com.lp.model.BenchmarkResults;

public class BenchmarkResultsExcelView extends AbstractXlsView {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void buildExcelDocument(Map model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Sheet sheet = workbook.createSheet("Benchmark Results");
		setExcelHeader(sheet);
		
		List<BenchmarkResults> benchmarkResults = (List<BenchmarkResults>) model.get("benchmarkResults");
		setExcelRows(sheet, benchmarkResults);
	}

	public void setExcelHeader(Sheet sheet) {
		Row excelHeader = sheet.createRow(0);
		excelHeader.createCell(0).setCellValue("First Name");
		excelHeader.createCell(1).setCellValue("Last Name");
		excelHeader.createCell(2).setCellValue("Fluency Score");
		excelHeader.createCell(3).setCellValue("Retell Score");
		excelHeader.createCell(4).setCellValue("Accuracy");
		excelHeader.createCell(5).setCellValue("Teacher Notes");
		excelHeader.createCell(6).setCellValue("School");
		excelHeader.createCell(7).setCellValue("Teacher");
		excelHeader.createCell(8).setCellValue("Submission Date");
		excelHeader.createCell(9).setCellValue("Grade Level");
		excelHeader.createCell(10).setCellValue("Test Id");
		excelHeader.createCell(11).setCellValue("Language");
		excelHeader.createCell(12).setCellValue("Student ID");
		excelHeader.createCell(13).setCellValue("Fluency Test");
		
	}
	
	public void setExcelRows(Sheet sheet, List<BenchmarkResults> benchmarkResults){
		int record = 1;
		String studentScId = "";
		SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
		DecimalFormat df = new DecimalFormat("#.##"); 
		for (BenchmarkResults benchmarkResult : benchmarkResults) {
			studentScId = "";
			Row excelRow = sheet.createRow(record++);
			excelRow.createCell(0).setCellValue(benchmarkResult.getStudentAssignmentStatus().getStudent().getUserRegistration().getFirstName());
			excelRow.createCell(1).setCellValue(benchmarkResult.getStudentAssignmentStatus().getStudent().getUserRegistration().getLastName());
			excelRow.createCell(2).setCellValue(benchmarkResult.getMedianFluencyScore());
			excelRow.createCell(3).setCellValue(benchmarkResult.getQualityOfResponse()!=null?benchmarkResult.getQualityOfResponse().getQorId():0);
			excelRow.createCell(4).setCellValue(df.format(benchmarkResult.getAccuracy()));
			excelRow.createCell(5).setCellValue(benchmarkResult.getTeacherNotes());
			excelRow.createCell(6).setCellValue(benchmarkResult.getStudentAssignmentStatus().getStudent().getUserRegistration().getSchool().getSchoolAbbr());
			excelRow.createCell(7).setCellValue((benchmarkResult.getStudentAssignmentStatus().getAssignment().getClassStatus().getTeacher().getUserRegistration().getTitle().isEmpty()?"":benchmarkResult.getStudentAssignmentStatus().getAssignment().getClassStatus().getTeacher().getUserRegistration().getTitle()+". ")
			+ benchmarkResult.getStudentAssignmentStatus().getAssignment().getClassStatus().getTeacher().getUserRegistration().getFirstName()+" "
			+benchmarkResult.getStudentAssignmentStatus().getAssignment().getClassStatus().getTeacher().getUserRegistration().getLastName());
			excelRow.createCell(8).setCellValue(sm.format(benchmarkResult.getStudentAssignmentStatus().getSubmitdate()));
			excelRow.createCell(9).setCellValue(benchmarkResult.getStudentAssignmentStatus().getStudent().getGrade().getMasterGrades().getMasterGradesId());
			excelRow.createCell(10).setCellValue(benchmarkResult.getStudentAssignmentStatus().getStudentAssignmentId());
			excelRow.createCell(11).setCellValue(benchmarkResult.getStudentAssignmentStatus().getAssignment().getBenchmarkDirections().getLanguage().getLanguage());
			if(benchmarkResult.getStudentAssignmentStatus().getStudent().getStudentScId() != null){
				studentScId = benchmarkResult.getStudentAssignmentStatus().getStudent().getStudentScId().toString(); 
			}
			excelRow.createCell(12).setCellValue(studentScId);
			excelRow.createCell(13).setCellValue(benchmarkResult.getStudentAssignmentStatus().getAssignment().getBenchmarkCategories().getBenchmarkName());
		}
	}
}