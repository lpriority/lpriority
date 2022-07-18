package com.lp.appadmin.excelexport;


import java.text.DecimalFormat;
import java.text.ParseException;
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
import com.lp.model.FluencyMarks;
import com.lp.model.FluencyMarksDetails;
import com.lp.model.Grade;
import com.lp.model.ReadRegActivityScore;
import com.lp.model.Student;
import com.lp.student.dao.StudentReadRegDAO;
import com.lp.utils.WebKeys;
import com.lp.admin.service.ReadRegReviewResultsService;
import com.lp.common.service.*;

public class StudSelfAndPeerReportsExcel extends AbstractXlsView {
	
	@Autowired
	private ReadRegReviewResultsService resultsService;
	@Autowired
	private StudentReadRegDAO studentReadRegDAO;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void buildExcelDocument(Map model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Sheet sheet = workbook.createSheet("Self And Peer Reports");
		//String reportType=model.get("reportType").toString();
		long userTypeId=Long.parseLong(model.get("userTypeId").toString());
		setExcelHeader(sheet,userTypeId);
		List<FluencyMarks> studSelfPeerReports = (List<FluencyMarks>) model.get("listStudSelfAndPeerReports");
		/*List<Long> studTotPoints= (List<Long>) model.get("allStudsTotPointsEarned");*/
		//List<Student> studentLst=(List<Student>) model.get("studentLst");
		setExcelRows(sheet,studSelfPeerReports,userTypeId);
		
	}
	public void setExcelHeader(Sheet sheet,long userTypeId) {
		sheet.setDefaultColumnWidth(25);
			Row excelHeader = sheet.createRow(0);
			excelHeader.createCell(0).setCellValue("Student Name");
			excelHeader.createCell(1).setCellValue("Type");
			excelHeader.createCell(2).setCellValue("Words Read");
			excelHeader.createCell(3).setCellValue("Errors");
			excelHeader.createCell(4).setCellValue("WordCount");
			excelHeader.createCell(5).setCellValue("Accuracy Score");
			excelHeader.createCell(6).setCellValue("WCPM");
			excelHeader.createCell(7).setCellValue("Error Word");
			
	}
					
	public void setExcelRows(Sheet sheet, List<FluencyMarks> studSelfPeerReports ,long userTypeId){
		int record = 1,i=0;
		SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
	    final DecimalFormat df2 = new DecimalFormat("#.##");
		for (FluencyMarks studReport : studSelfPeerReports) {
			if((studReport.getGradingTypes().getGradingTypesId()==2 && studReport.getAssignmentQuestions().getStudentAssignmentStatus().getSelfGradedStatus().equals(WebKeys.GRADED_STATUS_NOTGRADED)) ||
				(studReport.getGradingTypes().getGradingTypesId()==3 && studReport.getAssignmentQuestions().getStudentAssignmentStatus().getPeerGradedStatus().equals(WebKeys.GRADED_STATUS_NOTGRADED)) ||
				(studReport.getGradingTypes().getGradingTypesId()==1 && studReport.getAssignmentQuestions().getStudentAssignmentStatus().getGradedStatus().equals(WebKeys.GRADED_STATUS_NOTGRADED))) {
				continue;
			}
			Row excelRow = sheet.createRow(record++);
			int size=studReport.getFluencyMarksDetails().size();
			excelRow.setHeight((short)((excelRow.getHeight() * size)+250));
			
			String studentName=studReport.getAssignmentQuestions().getStudentAssignmentStatus().getStudent().getUserRegistration().getFirstName()+" "+studReport.getAssignmentQuestions().getStudentAssignmentStatus().getStudent().getUserRegistration().getLastName();
			excelRow.createCell(0).setCellValue(studentName);													
			if(studReport.getGradingTypes().getGradingTypesId()==2) {
				excelRow.createCell(1).setCellValue("Self");							
			} else if (studReport.getGradingTypes().getGradingTypesId()== 3) {
				excelRow.createCell(1).setCellValue("Peer");
		    } else if (studReport.getGradingTypes().getGradingTypesId()== 1) {
				excelRow.createCell(1).setCellValue("Teacher");
            }
									
			if(studReport.getWordsRead()!=null)
			excelRow.createCell(2).setCellValue(studReport.getWordsRead());
			if(studReport.getCountOfErrors()!=null)
			excelRow.createCell(3).setCellValue(studReport.getCountOfErrors());
			if(studReport.getMarks()!=null)
			excelRow.createCell(4).setCellValue(studReport.getMarks());
			if(studReport.getAccuracyScore()!=null)
			excelRow.createCell(5).setCellValue(df2.format(studReport.getAccuracyScore()));
			if(studReport.getWcpm()!=null)
				excelRow.createCell(6).setCellValue(studReport.getWcpm());
			else 
				excelRow.createCell(6).setCellValue("N/A");
			String ErrWords="";
			for(FluencyMarksDetails fluMarksDet : studReport.getFluencyMarksDetails()){
					String ErrorWord=fluMarksDet.getErrorWord()+", ";
					ErrWords=ErrWords+ErrorWord;
			}
			excelRow.createCell(7).setCellValue(ErrWords);
		}
	
	}
}