
package com.lp.appadmin.excelexport;


import java.text.DecimalFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.web.servlet.view.AbstractView;

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

import com.lp.model.BenchmarkResults;
import com.lp.model.Grade;
import com.lp.model.ReadRegActivityScore;
import com.lp.model.School;
import com.lp.model.Student;
import com.lp.pdfview.AbstractITextPdfView;
import com.lp.student.dao.StudentReadRegDAO;
import com.lp.admin.service.AdminService;
import com.lp.admin.service.ReadRegReviewResultsService;
import com.lp.common.service.*;

import java.io.FileOutputStream;
import java.io.*;
import java.util.*;
import java.sql.*; 
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;


public class DistrictRRReportsPDFView extends AbstractITextPdfView {
	@Autowired
	private ReadRegReviewResultsService resultsService;
	@Autowired
	private StudentReadRegDAO studentReadRegDAO;
	@Autowired 
	private AdminService adminService;
    
   @SuppressWarnings("unchecked")
protected void buildPdfDocument(Map<String, Object> model, Document document,
      PdfWriter pdfWriter, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
	 
		String reportType=model.get("reportType").toString();
		String districtName=model.get("districtName").toString();
		if(reportType.equalsIgnoreCase("districtWise")){
				Map<String,Map<String,Map<String,List<ReadRegActivityScore>>>> districtRRReports=(HashMap<String,Map<String,Map<String,List<ReadRegActivityScore>>>>) model.get("listDistrictRRReports");
				 Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
				 boldFont.setColor(0,0,0); 
				if(districtRRReports.size()>0){
				 List<School> schoolLst=(List<School>) model.get("schoolLst");
				 Paragraph para =new Paragraph("READING REGISTER REPORT",boldFont);
				 Paragraph para1 =new Paragraph("District Name : "+districtName,boldFont);
				 para.setAlignment(Element.ALIGN_CENTER);
				 document.add(para);
				 document.add(Chunk.NEWLINE);
				 para1.setAlignment(Element.ALIGN_CENTER);
				 document.add(para1);
				 document.add(Chunk.NEWLINE);
				 PdfPTable pdfTable = new PdfPTable(13);
				 pdfTable.getDefaultCell().setFixedHeight(60);
				 pdfTable.getDefaultCell().setHorizontalAlignment(pdfTable.ALIGN_CENTER);
				 pdfTable.getDefaultCell().setVerticalAlignment(pdfTable.ALIGN_MIDDLE);
				 setPDFRows(pdfTable, districtRRReports,schoolLst,document,reportType);
				 }else{
					 Font boldFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
					 boldFont1.setColor(0,0,255);
					 Paragraph para1 =new Paragraph("No Reports are available for this District",boldFont1);
					 para1.setAlignment(Element.ALIGN_CENTER);
					 document.add(para1); 
				 }
		}
}
   public void setPDFHeader(PdfPTable pdfTable,String reportType) {
	   Font boldFont = new Font(Font.FontFamily.UNDEFINED, 16);
	   boldFont.setColor(0, 0, 0);
	   pdfTable.getDefaultCell().setBackgroundColor(new BaseColor(199,220,0));
	   pdfTable.addCell(new Phrase("School Name",boldFont));
	   pdfTable.addCell(new Phrase("Grade Level",boldFont));
	   pdfTable.addCell(new Phrase("Student Name",boldFont));
	   pdfTable.addCell(new Phrase("Book Title",boldFont));
	   pdfTable.addCell(new Phrase("Author",boldFont));
	   pdfTable.addCell(new Phrase("Created Date",boldFont));
		pdfTable.addCell(new Phrase("Teacher Name",boldFont));
	   pdfTable.addCell(new Phrase("Student Rating",boldFont));
	   pdfTable.addCell(new Phrase("Activity type",boldFont));
	   pdfTable.addCell(new Phrase("Rubric score",boldFont));
	   pdfTable.addCell(new Phrase("Page Range",boldFont));
	   pdfTable.addCell(new Phrase("Total Points Earned for Activity",boldFont));
	   pdfTable.addCell(new Phrase("Total Points earned for all books",boldFont));
		}
					
	public void setPDFRows(PdfPTable pdfTable, Map<String,Map<String,Map<String,List<ReadRegActivityScore>>>> districtRRReports ,List<School> schoolLst,Document document,String reportType) throws DocumentException{
		setPDFHeader(pdfTable,reportType);
		SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy"); 
		Font boldFont = new Font(Font.FontFamily.UNDEFINED, 14);
		pdfTable.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
		int record = 1,i=0;
		long totalPointsEarned=0;
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
							readRegActScoreLst = classRRReports.get(stud); 
							int count=1;
							for (ReadRegActivityScore studReadRegActScore : readRegActScoreLst) {
								record++;
								if(sc==1)
								pdfTable.addCell(new Phrase(school.getSchoolName(),boldFont));
								else
								pdfTable.addCell("");	
								if(ge==1)
								pdfTable.addCell(new Phrase(grade.getMasterGrades().getGradeName(),boldFont));
								else
								pdfTable.addCell("");		
								if(count==1){
									String studentName=student.getUserRegistration().getFirstName()+" "+student.getUserRegistration().getLastName();
									pdfTable.addCell(new Phrase(studentName,boldFont));
								}else{
									pdfTable.addCell("");
								}
									pdfTable.addCell(new Phrase(studReadRegActScore.getReadRegMaster().getBookTitle(),boldFont));
									pdfTable.addCell(new Phrase(studReadRegActScore.getReadRegMaster().getAuthor(),boldFont));
									pdfTable.addCell(new Phrase(sm.format(studReadRegActScore.getReadRegMaster().getCreateDate()),boldFont));
									String teacherName=studReadRegActScore.getTeacher().getUserRegistration().getFirstName()+" "+studReadRegActScore.getTeacher().getUserRegistration().getLastName();
									pdfTable.addCell(new Phrase(teacherName,boldFont));
									pdfTable.addCell(new Phrase(String.valueOf(studReadRegActScore.getReadRegReview().getRating()),boldFont));
									pdfTable.addCell(new Phrase(studReadRegActScore.getReadRegActivity().getActitvity(),boldFont));
									if(studReadRegActScore.getReadRegRubric()!=null)
									pdfTable.addCell(new Phrase(String.valueOf(studReadRegActScore.getReadRegRubric().getScore()),boldFont));
									else
									pdfTable.addCell(new Phrase(String.valueOf(0)));	
									pdfTable.addCell(new Phrase(String.valueOf(studReadRegActScore.getReadRegMaster().getReadRegPageRange().getRange()),boldFont));
								if(studReadRegActScore.getPointsEarned()!=null)
									pdfTable.addCell(new Phrase(String.valueOf(studReadRegActScore.getPointsEarned()),boldFont));
								else
									pdfTable.addCell(new Phrase(String.valueOf(0)));
								if(count==1){
									totalPointsEarned=studentReadRegDAO.getStudentTotalPointsEarned(student.getStudentId(), grade.getGradeId());
								pdfTable.addCell(new Phrase(String.valueOf(totalPointsEarned),boldFont));
								}else{
									pdfTable.addCell("");	
								}
								count++;
								ge++;
								sc++;
								if(record==36 || record%36==0){
									document.add(pdfTable);
									document.newPage();
									pdfTable = new PdfPTable(13);
									pdfTable.getDefaultCell().setFixedHeight(60);
									pdfTable.getDefaultCell().setHorizontalAlignment(pdfTable.ALIGN_CENTER);
									pdfTable.getDefaultCell().setVerticalAlignment(pdfTable.ALIGN_MIDDLE);
									setPDFHeader(pdfTable,reportType);
									pdfTable.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
									
								}
								
					
							}
						}
						
				}
	        }
         }
		}
		}
		document.add(pdfTable);
	}
	
}


