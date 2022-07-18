package com.lp.pdfview;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.pdf.PdfWriter;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;

import com.lp.model.Grade;
import com.lp.model.ReadRegActivityScore;
import com.lp.model.Student;
import com.lp.pdfview.AbstractITextPdfView;
import com.lp.student.dao.StudentReadRegDAO;
import com.lp.admin.service.AdminService;
import com.lp.admin.service.ReadRegReviewResultsService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;


public class StudentRRReportsPDFView extends AbstractITextPdfView {
	@Autowired
	private ReadRegReviewResultsService resultsService;
	@Autowired
	private StudentReadRegDAO studentReadRegDAO;
	@Autowired 
	private AdminService adminService;
	String reportType="";
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void buildPdfDocument(Map<String, Object> model, Document document,PdfWriter pdfWriter, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
 	    boldFont.setColor(0,0,0);
	    reportType=model.get("reportType").toString();
	    long userTypeId=Long.parseLong(model.get("userTypeId").toString());
	    if(reportType.equalsIgnoreCase("studentWise")){
	    	
			List<ReadRegActivityScore> readRegActScoreLst = (List<ReadRegActivityScore>) model.get("readRegActivityScores");
			if(readRegActScoreLst.size()>0){
			 String allBookTotPE=model.get("allBookTotPE").toString();
			 String studentName=model.get("studentName").toString();
			 Paragraph para =new Paragraph("READING REGISTER REPORT",boldFont);
			 Font boldFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
			 boldFont1.setColor(0,0,0);
			 Paragraph para1 =new Paragraph("Student Name : "+studentName,boldFont1);
			 para.setAlignment(Element.ALIGN_CENTER);
			 document.add(para);
			 document.add(Chunk.NEWLINE);
			 para1.setAlignment(Element.ALIGN_CENTER);
			 document.add(para1);
			 document.add(Chunk.NEWLINE);
			 PdfPTable pdfTable = new PdfPTable(9);
			 pdfTable.getDefaultCell().setFixedHeight(60);
			 pdfTable.getDefaultCell().setHorizontalAlignment(pdfTable.ALIGN_CENTER);
			 pdfTable.getDefaultCell().setVerticalAlignment(pdfTable.ALIGN_MIDDLE);
			 //setPDFHeader(pdfTable,reportType,userTypeId);
			 setPDFRows(pdfTable, readRegActScoreLst,allBookTotPE,document,userTypeId);
			}else{
				 Font boldFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
				 boldFont1.setColor(0,0,255);
				 Paragraph para1 =new Paragraph("No Reports are available for this student",boldFont1);
				 para1.setAlignment(Element.ALIGN_CENTER);
				 document.add(para1);
			}
		}else if(reportType.equalsIgnoreCase("classWise")){
			 Map<String,List<ReadRegActivityScore>> classRRReports = (HashMap<String,List<ReadRegActivityScore>>) model.get("listClassRRReports");
			 if(classRRReports.size()>0){
			 String gradeName=model.get("gradeName").toString();
			 Paragraph para =new Paragraph("READING REGISTER REPORT",boldFont);
			 Paragraph para1 =new Paragraph("Grade Name : "+gradeName,boldFont);
			 para.setAlignment(Element.ALIGN_CENTER);
			 document.add(para);
			 document.add(Chunk.NEWLINE);
			 para1.setAlignment(Element.ALIGN_CENTER);
			 document.add(para1);
			 document.add(Chunk.NEWLINE);
			 List<Long> studTotPoints= (List<Long>) model.get("allStudsTotPointsEarned");
			 List<Student> studentLst=(List<Student>) model.get("studentLst");
			 PdfPTable pdfTable1;
			 if(userTypeId==3)
			 pdfTable1 = new PdfPTable(11);
			 else
			 pdfTable1 = new PdfPTable(12); 
			 pdfTable1.getDefaultCell().setFixedHeight(60);
			 pdfTable1.getDefaultCell().setHorizontalAlignment(pdfTable1.ALIGN_CENTER);
			 pdfTable1.getDefaultCell().setVerticalAlignment(pdfTable1.ALIGN_MIDDLE);
			 setPDFRows1(pdfTable1, classRRReports,studTotPoints,studentLst,document,userTypeId);
			 }else{
				 Font boldFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
				 boldFont1.setColor(0,0,255);
				 Paragraph para1 =new Paragraph("No Reports are available for this Grade",boldFont1);
				 para1.setAlignment(Element.ALIGN_CENTER);
				 document.add(para1); 
			 }
		}else if(reportType.equalsIgnoreCase("gradeWise")){
			 Map<String,Map<String,List<ReadRegActivityScore>>> gradeRRReports=(HashMap<String,Map<String,List<ReadRegActivityScore>>>) model.get("listGradeRRReports");
			 if(gradeRRReports.size()>0){
			 String schoolName=model.get("schoolName").toString();
			 Paragraph para =new Paragraph("Reading Register Report",boldFont);
			 Paragraph para1 =new Paragraph("School Name : "+schoolName,boldFont);
			 para.setAlignment(Element.ALIGN_CENTER);
			 document.add(para);
			 document.add(Chunk.NEWLINE);
			 para1.setAlignment(Element.ALIGN_CENTER);
			 document.add(para1);
			 document.add(Chunk.NEWLINE);
			List<Grade> gradeList=(List<Grade>) model.get("gradeList");
			PdfPTable pdfTable2 = new PdfPTable(13);
			pdfTable2.getDefaultCell().setFixedHeight(60);
			 pdfTable2.getDefaultCell().setHorizontalAlignment(pdfTable2.ALIGN_CENTER);
			 pdfTable2.getDefaultCell().setVerticalAlignment(pdfTable2.ALIGN_MIDDLE);
			//setPDFHeader(pdfTable2,reportType,userTypeId);
			setPDFRows2(pdfTable2, gradeRRReports,gradeList,document,userTypeId);
			 }else{
				 Font boldFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
				 boldFont1.setColor(0,0,255);
				 Paragraph para1 =new Paragraph("No Reports are available for this School",boldFont1);
				 para1.setAlignment(Element.ALIGN_CENTER);
				 document.add(para1); 
			 }
			
		}
	      
   }
	 /*public void setStyleForPDF(PdfPTable pdfTable,String reportType,long userTypeId) {
		 
		 
	 }*/
   public void setPDFHeader(PdfPTable pdfTable,String reportType,long userTypeId) {
	   Font boldFont = new Font(Font.FontFamily.UNDEFINED, 16);
	   boldFont.setColor(0, 0, 0);
	   pdfTable.getDefaultCell().setBackgroundColor(new BaseColor(199,220,0));
		if(reportType.equalsIgnoreCase("studentWise")){
			
			pdfTable.addCell(new Phrase("Book Title",boldFont));
			pdfTable.addCell(new Phrase("Author",boldFont));
			pdfTable.addCell(new Phrase("Created Date",boldFont));
			pdfTable.addCell(new Phrase("Student Rating",boldFont));
			pdfTable.addCell(new Phrase("Activity type",boldFont));
			pdfTable.addCell(new Phrase("Rubric score",boldFont));
			pdfTable.addCell(new Phrase("Page Range",boldFont));
			pdfTable.addCell(new Phrase("Total Points Earned for Activity",boldFont));
			pdfTable.addCell(new Phrase("Total Points earned for all books",boldFont));
		
		}else if(reportType.equalsIgnoreCase("classWise")){
			pdfTable.addCell(new Phrase("Student First Name",boldFont));
			pdfTable.addCell(new Phrase("Student Last Name",boldFont));
		    pdfTable.addCell(new Phrase("Book Title",boldFont));
			pdfTable.addCell(new Phrase("Author",boldFont));
			pdfTable.addCell(new Phrase("Created Date",boldFont));
			if(userTypeId!=3)
				pdfTable.addCell(new Phrase("Teacher Name",boldFont));	
			pdfTable.addCell(new Phrase("Student Rating",boldFont));
			pdfTable.addCell(new Phrase("Activity type",boldFont));
			pdfTable.addCell(new Phrase("Rubric score",boldFont));
			pdfTable.addCell(new Phrase("Page Range",boldFont));
			pdfTable.addCell(new Phrase("Total Points Earned for Activity",boldFont));
			pdfTable.addCell(new Phrase("Total Points earned for all books",boldFont));
		}else{
					
			pdfTable.addCell(new Phrase("Grade Level",boldFont));
			pdfTable.addCell(new Phrase("Student First Name",boldFont));
			pdfTable.addCell(new Phrase("Student Last Name",boldFont));
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
					
	}
   public void setPDFRows(PdfPTable pdfTable,List<ReadRegActivityScore> readRegActScoreLst,String allBookTotPE,Document document,long userTypeId) throws DocumentException{
	   setPDFHeader(pdfTable,reportType,userTypeId);
	   SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
	   Font boldFont = new Font(Font.FontFamily.UNDEFINED, 14);
	   pdfTable.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
			int record = 1;
			for (ReadRegActivityScore studReadRegActScore : readRegActScoreLst) {
				record++;
				pdfTable.addCell(new Phrase(studReadRegActScore.getReadRegMaster().getBookTitle(),boldFont));
				pdfTable.addCell(new Phrase(studReadRegActScore.getReadRegMaster().getAuthor(),boldFont));
				pdfTable.addCell(new Phrase(sm.format(studReadRegActScore.getReadRegMaster().getCreateDate()),boldFont));
				pdfTable.addCell(new Phrase(String.valueOf(studReadRegActScore.getReadRegReview().getRating()),boldFont));
				pdfTable.addCell(new Phrase(studReadRegActScore.getReadRegActivity().getActitvity(),boldFont));
				pdfTable.addCell(new Phrase(String.valueOf(studReadRegActScore.getReadRegRubric().getScore()),boldFont));
				pdfTable.addCell(new Phrase(String.valueOf(studReadRegActScore.getReadRegMaster().getReadRegPageRange().getRange()),boldFont));
				if(studReadRegActScore.getPointsEarned()!=null)
					pdfTable.addCell(new Phrase(String.valueOf(studReadRegActScore.getPointsEarned()),boldFont));
				else
					pdfTable.addCell(new Phrase(String.valueOf(0),boldFont));
				if(record==2)
					pdfTable.addCell(new Phrase(allBookTotPE,boldFont));
				else
					pdfTable.addCell(new Phrase(""));
				
				if(record==36 || record%36==0){
					document.add(pdfTable);
					document.newPage();
					pdfTable = new PdfPTable(9);
					pdfTable.getDefaultCell().setFixedHeight(60);
					pdfTable.getDefaultCell().setHorizontalAlignment(pdfTable.ALIGN_CENTER);
					pdfTable.getDefaultCell().setVerticalAlignment(pdfTable.ALIGN_MIDDLE);
					setPDFHeader(pdfTable,reportType,userTypeId);
					pdfTable.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
					
				}
					
			}
			document.add(pdfTable);
	
   }
   public void setPDFRows1(PdfPTable pdfTable1,Map<String,List<ReadRegActivityScore>> classRRReports ,List<Long> allStudBookTotPE,List<Student> studentLst,Document document,long userTypeId)throws DocumentException{
	   int record = 1,i=0,co=0;
	   setPDFHeader(pdfTable1,reportType,userTypeId);
	   SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
	   Font boldFont = new Font(Font.FontFamily.UNDEFINED, 14);
	   pdfTable1.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
		List<ReadRegActivityScore> readRegActScoreLst=new ArrayList<ReadRegActivityScore>();
		for (Student student : studentLst) {
			    long studentId=student.getStudentId();
				String stud="stud"+student.getStudentId();
				if(classRRReports.containsKey(stud)){
					readRegActScoreLst = classRRReports.get(stud); //val is the value corresponding to key temp
					int count=1;
					for (ReadRegActivityScore studReadRegActScore : readRegActScoreLst) {
						record++;					
						if(count==1){
							String studentFirstName=student.getUserRegistration().getFirstName();
							String studentLastName=student.getUserRegistration().getLastName();
							pdfTable1.addCell(new Phrase(studentFirstName,boldFont));
							pdfTable1.addCell(new Phrase(studentLastName,boldFont));
						}else{
							pdfTable1.addCell(new Phrase(""));
							pdfTable1.addCell(new Phrase(""));
						}
							pdfTable1.addCell(new Phrase(studReadRegActScore.getReadRegMaster().getBookTitle(),boldFont));
							pdfTable1.addCell(new Phrase(studReadRegActScore.getReadRegMaster().getAuthor(),boldFont));
							pdfTable1.addCell(new Phrase(sm.format(studReadRegActScore.getReadRegMaster().getCreateDate()),boldFont));
							if(userTypeId!=3){
							String teacherName=studReadRegActScore.getTeacher().getUserRegistration().getFirstName()+" "+studReadRegActScore.getTeacher().getUserRegistration().getLastName();
							pdfTable1.addCell(new Phrase(teacherName,boldFont));
							}
							pdfTable1.addCell(new Phrase(String.valueOf(studReadRegActScore.getReadRegReview().getRating()),boldFont));
							pdfTable1.addCell(new Phrase(studReadRegActScore.getReadRegActivity().getActitvity(),boldFont));
							if(studReadRegActScore.getReadRegRubric()!=null)
							pdfTable1.addCell(new Phrase(String.valueOf(studReadRegActScore.getReadRegRubric().getScore()),boldFont));
							else
							pdfTable1.addCell(new Phrase(String.valueOf(0),boldFont));
							pdfTable1.addCell(new Phrase(String.valueOf(studReadRegActScore.getReadRegMaster().getReadRegPageRange().getRange()),boldFont));
						if(studReadRegActScore.getPointsEarned()!=null)
							pdfTable1.addCell(new Phrase(String.valueOf(studReadRegActScore.getPointsEarned()),boldFont));
						else
							pdfTable1.addCell(new Phrase(String.valueOf(0),boldFont));
						if(count==1)
						pdfTable1.addCell(new Phrase(String.valueOf(allStudBookTotPE.get(i)),boldFont));
						else
						pdfTable1.addCell(new Phrase(""));
						count++;
						
						if(record==36 || record%36==0){
							document.add(pdfTable1);
							document.newPage();
							if(userTypeId==3)
								 pdfTable1 = new PdfPTable(11);
							else
								 pdfTable1 = new PdfPTable(12); 
							
							pdfTable1.getDefaultCell().setFixedHeight(60);
							pdfTable1.getDefaultCell().setHorizontalAlignment(pdfTable1.ALIGN_CENTER);
							pdfTable1.getDefaultCell().setVerticalAlignment(pdfTable1.ALIGN_MIDDLE);
							setPDFHeader(pdfTable1,reportType,userTypeId);
							pdfTable1.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
							
						}
					 }
				}
				i++;
		}
		document.add(pdfTable1);
   }
   public void setPDFRows2(PdfPTable pdfTable2,Map<String,Map<String,List<ReadRegActivityScore>>> gradeRRReports ,List<Grade> gradeLst,Document document,long userTypeId)throws DocumentException{
	   setPDFHeader(pdfTable2,reportType,userTypeId);
	   SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
	   Font boldFont = new Font(Font.FontFamily.UNDEFINED, 14);
	   pdfTable2.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
	   int record = 1,i=0;
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
								record++;
								if(ge==1){
								pdfTable2.addCell(new Phrase(grade.getMasterGrades().getGradeName(),boldFont));
								}else{
									pdfTable2.addCell(new Phrase(""));	
								}
								if(count==1){
									/*String studentName=student.getUserRegistration().getFirstName()+" "+student.getUserRegistration().getLastName();
									pdfTable2.addCell(new Phrase(studentName,boldFont));*/
									String studentFirstName=student.getUserRegistration().getFirstName();
									String studentLastName=student.getUserRegistration().getLastName();
									pdfTable2.addCell(new Phrase(studentFirstName,boldFont));
									pdfTable2.addCell(new Phrase(studentLastName,boldFont));
								}
								else{
									pdfTable2.addCell(new Phrase(""));	
									pdfTable2.addCell(new Phrase(""));	
								}
									pdfTable2.addCell(new Phrase(studReadRegActScore.getReadRegMaster().getBookTitle(),boldFont));
									pdfTable2.addCell(new Phrase(studReadRegActScore.getReadRegMaster().getAuthor(),boldFont));
									pdfTable2.addCell(new Phrase(sm.format(studReadRegActScore.getReadRegMaster().getCreateDate()),boldFont));
									String teacherName=studReadRegActScore.getTeacher().getUserRegistration().getFirstName()+" "+studReadRegActScore.getTeacher().getUserRegistration().getLastName();
									pdfTable2.addCell(new Phrase(teacherName,boldFont));
									pdfTable2.addCell(new Phrase(String.valueOf(studReadRegActScore.getReadRegReview().getRating()),boldFont));
									pdfTable2.addCell(new Phrase(studReadRegActScore.getReadRegActivity().getActitvity(),boldFont));
									if(studReadRegActScore.getReadRegRubric()!=null)
									pdfTable2.addCell(new Phrase(String.valueOf(studReadRegActScore.getReadRegRubric().getScore()),boldFont));
									else
									pdfTable2.addCell(new Phrase(String.valueOf(0),boldFont));	
									pdfTable2.addCell(new Phrase(String.valueOf(studReadRegActScore.getReadRegMaster().getReadRegPageRange().getRange()),boldFont));
								if(studReadRegActScore.getPointsEarned()!=null)
									pdfTable2.addCell(new Phrase(String.valueOf(studReadRegActScore.getPointsEarned()),boldFont));
								else
									pdfTable2.addCell(new Phrase(String.valueOf(0),boldFont));
								if(count==1){
									 totalPointsEarned=studentReadRegDAO.getStudentTotalPointsEarned(student.getStudentId(), grade.getGradeId());
								pdfTable2.addCell(new Phrase(String.valueOf(totalPointsEarned),boldFont));
								}else{
									pdfTable2.addCell(new Phrase(""));
								}
								count++;
								ge++;
								if(record==36 || record%36==0){
									document.add(pdfTable2);
									document.newPage();
									pdfTable2 = new PdfPTable(13);
									pdfTable2.getDefaultCell().setFixedHeight(60);
									pdfTable2.getDefaultCell().setHorizontalAlignment(pdfTable2.ALIGN_CENTER);
									pdfTable2.getDefaultCell().setVerticalAlignment(pdfTable2.ALIGN_MIDDLE);
									setPDFHeader(pdfTable2,reportType,userTypeId);
									pdfTable2.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
									
								}
					
							}
						}
						
				}
	     }
}
		document.add(pdfTable2);
   }
}


