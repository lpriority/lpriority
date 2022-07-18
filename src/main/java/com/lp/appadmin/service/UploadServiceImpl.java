package com.lp.appadmin.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.lp.admin.dao.GradesDAO;
import com.lp.appadmin.dao.UploadDAO;
import com.lp.model.CAASPPScores;
import com.lp.model.CAASPPTypes;
import com.lp.model.ColumnHeaders;
import com.lp.model.District;
import com.lp.model.FormativeAssessmentCategory;
import com.lp.model.FormativeAssessmentKeywords;
import com.lp.model.FormativeAssessmentRubric;
import com.lp.model.FormativeAssessments;
import com.lp.model.FormativeAssessmentsColumnHeaders;
import com.lp.model.FormativeAssessmentsGradeWise;
import com.lp.model.Grade;
import com.lp.model.Legend;
import com.lp.model.LegendSubCriteria;
import com.lp.model.MasterGrades;
import com.lp.model.School;
import com.lp.model.StarScores;
import com.lp.model.States;
import com.lp.model.StemAreas;
import com.lp.model.StemConceptSubCategory;
import com.lp.model.StemConceptSubCategoryItems;
import com.lp.model.StemGradeStrands;
import com.lp.model.StemStrandConceptDetails;
import com.lp.model.StemStrandConcepts;
import com.lp.model.Student;
import com.lp.model.Teacher;
import com.lp.model.Trimester;
import com.lp.model.UserRegistration;
import com.lp.student.dao.StudentDAO;
import com.lp.teacher.dao.CAASPPDAO;
import com.lp.teacher.dao.IOLReportCardDAO;
import com.lp.utils.WebKeys;

public class UploadServiceImpl implements UploadService {

	static final Logger logger = Logger.getLogger(UploadServiceImpl.class);
	
	@Autowired
	private IOLReportCardDAO iOLReportCardDAO;
	@Autowired
	private UploadDAO uploadDAO;
	@Autowired
	private HttpSession session;
	@Autowired
	private StudentDAO studentDAO; 
	@Autowired
	private GradesDAO gradesDAO;
	@Autowired
	private CAASPPDAO cAASPPDAO;

	@SuppressWarnings("resource")
	@Override
	public boolean saveLegendFromFile(MultipartFile excelFile,long lCriteriaId, List<MasterGrades> masterGrades, 
			String check, long districtId) throws Exception {
		boolean status = false;
		List<Legend> legendList = new ArrayList<Legend>();
		List<Long> legendValues = new ArrayList<>();
        List<List<String>> totalData = new ArrayList<>();
        UserRegistration userReg = (UserRegistration) session.getAttribute(WebKeys.USER_REGISTRATION_OBJ);
		try {			
			FileInputStream fis = (FileInputStream) excelFile.getInputStream();
			String fileName = excelFile.getOriginalFilename();
			Workbook workbook;
			if(fileName.substring(fileName.length()-3).equalsIgnoreCase(WebKeys.FORMAT_XLS)){
				workbook = new HSSFWorkbook(fis); 
			}else{
				workbook = new XSSFWorkbook (fis);
			}
			Sheet sheet = workbook.getSheetAt(0);
            long loops = 0;
            
            for(Row row:sheet) {
                List<String> data = new ArrayList<>();
                
                for(Cell cell : row){
                    if(loops == 0){
                    	if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                    		legendValues.add((new Double(cell.getNumericCellValue())).longValue());
                        }
                    }else{
                    	if(cell.getCellType() == Cell.CELL_TYPE_STRING){
                        	data.add(cell.getStringCellValue());
                        }
                    }  
                }
                loops++;
                if(!data.isEmpty()){
                	totalData.add(data);
                }                
            }
            List<LegendSubCriteria> lSubCriList = new ArrayList<LegendSubCriteria>();
            if(districtId == 0){
            	lSubCriList = iOLReportCardDAO.getLegendSubCriteriasByCriteriaId(lCriteriaId, masterGrades.get(0).getMasterGradesId());
            }
            else{
            	lSubCriList = iOLReportCardDAO.getLegendSubCriterias(lCriteriaId, masterGrades.get(0).getMasterGradesId(), districtId);
            }
            
            
            HashMap<String, Long> hmap = new HashMap<String, Long>();
            for(LegendSubCriteria lsc : lSubCriList){
            	hmap.put(lsc.getlegendSubCriteriaName().toLowerCase(), lsc.getLegendSubCriteriaId());
            }
            District district = new District();
            if(districtId>0){            	
                district.setDistrictId(districtId);
            }
            
            for(int i=0;i<totalData.size();i++){
            	List<String> data = new ArrayList<>();
            	data = totalData.get(i);
            	long subId = 0;
            	for(int j=0;j<data.size();j++){
            		Legend legend = new Legend();
            		LegendSubCriteria legendSubCri = new LegendSubCriteria();
    	        	if(j>0){
    	        		if(subId>0){
    	        			legendSubCri.setLegendSubCriteriaId(subId);
    	        			legend.setLegendValue(legendValues.get(j-1));
        	        		legend.setLegendName(data.get(j).toString());
        	        		legend.setLegendSubCriteria(legendSubCri);
        	        		if(check.equalsIgnoreCase(WebKeys.LP_TAB_UPLOAD_IOLRUBRIC)){
        	        			legend.setIsDefault(WebKeys.STEM_NO.toLowerCase());
        	        		}else{
        	        			legend.setIsDefault(WebKeys.LP_STEM);   
        	        		}
        	        		legend.setDistrict(district);
        	        		legend.setCreatedBy(userReg);
        	        		
        	        		legend.setStatus(WebKeys.LP_STATUS_ACTIVE);
        	        		legend.setFromGrade(masterGrades.get(0));
        	        		legend.setToGrade(masterGrades.get(masterGrades.size()-1));
        	        		legendList.add(legend);
    	        		}    	 
    	        	}else{
    	        		if(hmap.containsKey((data.get(j).toString().toLowerCase()))){
    	        			subId = hmap.get(data.get(j).toString().toLowerCase());
    	        		}    	        		
    	        	}
            	}            	
	        }
            if(!legendList.isEmpty()){
            	status = uploadDAO.saveLegendFromFile(legendList, masterGrades, check);
            }
		} catch (Exception e) {
			logger.error("Error in saveLegendFromFile() of  UploadServiceImpl" + e);
		}
		return status;
	}
	
	@Override
	public long saveFormativeAssessment(FormativeAssessments fa) throws Exception{
		return uploadDAO.saveFormativeAssessment(fa);
	}
	
	@Override
	public boolean saveFormativeAssessmentCategory(FormativeAssessmentCategory fac) throws Exception{
		return uploadDAO.saveFormativeAssessmentCategory(fac);
	}
	
	@Override
	public boolean saveFormativeAssessmentsGradeWise(FormativeAssessmentsGradeWise fag) throws Exception{
		return uploadDAO.saveFormativeAssessmentsGradeWise(fag);
	}
	
	@Override
	public boolean saveFormativeAssessmentKeywords(FormativeAssessmentKeywords fak) throws Exception{
		return uploadDAO.saveFormativeAssessmentKeywords(fak);
	}

	@Override
	public ColumnHeaders getColumnHeaderByColumn(String columnName) throws Exception{
		return uploadDAO.getColumnHeaderByColumn(columnName);
	}

	@Override
	public boolean saveFormativeAssessmentsColumnHeaders(FormativeAssessmentsColumnHeaders fach) throws Exception{
		return uploadDAO.saveFormativeAssessmentsColumnHeaders(fach);
	}
	
	@Override
	public FormativeAssessmentCategory getFormativeAssessmentCategoryByCategory(long formativeAssessmentsId, String category) throws Exception{
		return uploadDAO.getFormativeAssessmentCategoryByCategory(formativeAssessmentsId,category);
	}
	
	@Override
	public boolean saveFormativeAssessmentRubric(FormativeAssessmentRubric far) throws Exception{
		return uploadDAO.saveFormativeAssessmentRubric(far);
	}
	@Override
	public List<StemAreas> getAllStemAreas() {
		return uploadDAO.getAllStemAreas();
	}

	@SuppressWarnings("resource")
	@Override
	public boolean uploadStrandsFile(MultipartFile excelFile, long areaId,long mGradeId, long stateId) throws IOException {
		boolean status= false;
		List<StemGradeStrands> gradeStrandsLt = new ArrayList<StemGradeStrands>(); 
		try {			
			StemAreas stemAreas = new StemAreas();
			stemAreas.setStemAreaId(areaId);
			MasterGrades masterGrades = new MasterGrades();
			masterGrades.setMasterGradesId(mGradeId);
			States state = new States();
			state.setStateId(stateId);
			
			FileInputStream fis = (FileInputStream) excelFile.getInputStream();
			String fileName = excelFile.getOriginalFilename();
			Workbook workbook;
			if(fileName.substring(fileName.length()-3).equalsIgnoreCase(WebKeys.FORMAT_XLS)){
				workbook = new HSSFWorkbook(fis); 
			}else{
				workbook = new XSSFWorkbook (fis);
			}
			long noOfSheets = workbook.getNumberOfSheets();
			HashMap<String, List<StemStrandConceptDetails>> detailSheet = new HashMap<>();
			HashMap<String, List<StemConceptSubCategory>> categorySheet = new HashMap<>();
			HashMap<String, List<StemConceptSubCategoryItems>> catItemsSheet = new HashMap<>();
			if(noOfSheets > 3){					
				Sheet forthSheet = workbook.getSheetAt(3);
				for(Row row : forthSheet) {
					List<StemConceptSubCategoryItems> subCatItemLt = new ArrayList<StemConceptSubCategoryItems>(); 
					String key = "";
			        for(Cell inCell : row) {  
			        	if(inCell.getCellType() == Cell.CELL_TYPE_STRING){
			        		StemConceptSubCategoryItems cSubCatItems = new StemConceptSubCategoryItems();
			        		if(inCell.getColumnIndex() == 0){
			        			key = inCell.getStringCellValue();
			        		}else{
			        			cSubCatItems.setItemDesc(inCell.getStringCellValue());
			        			subCatItemLt.add(cSubCatItems);	
			        		}			        				        		
			        	}
			        }
			        if(!catItemsSheet.containsKey(key) && key!=""){
			        	catItemsSheet.put(key, subCatItemLt);
			        }
				}
			}
			if(noOfSheets > 2){					
				Sheet thirdSheet = workbook.getSheetAt(2);
				for(Row row : thirdSheet) {
					List<StemConceptSubCategory> subCategoryLt = new ArrayList<StemConceptSubCategory>(); 
					String key = "";
			        for(Cell inCell : row) {  
			        	if(inCell.getCellType() == Cell.CELL_TYPE_STRING){
			        		StemConceptSubCategory cSubCategory = new StemConceptSubCategory();
			        		if(inCell.getColumnIndex() == 0){
			        			key = inCell.getStringCellValue();
			        		}else{
			        			if(inCell.getStringCellValue().contains("&&")){
			        				String subSplits = inCell.getStringCellValue();
		                			String subData[] = subSplits.split("&&");
		                			if(subData.length > 1){
		                				if(catItemsSheet.containsKey(subData[0])){
		                					cSubCategory.setStemConceptSubCategoryItems(catItemsSheet.get(subData[0]));
		                					for(StemConceptSubCategoryItems subCatItem: catItemsSheet.get(subData[0])){
		                						subCatItem.setStemConceptSubCategory(cSubCategory);
		                					}
		                				}
		                				cSubCategory.setSubCategoryDesc(subData[1]);
		                			}
			        			}else{
			        				cSubCategory.setSubCategoryDesc(inCell.getStringCellValue());
			        			}			        			
			        			subCategoryLt.add(cSubCategory);	
			        		}			        				        		
			        	}
			        }
			        if(!categorySheet.containsKey(key) && key!=""){
			        	categorySheet.put(key, subCategoryLt);
			        }
				}
			}
			if(noOfSheets > 1){					
				Sheet secondSheet = workbook.getSheetAt(1);
				for(Row row : secondSheet) {
					List<StemStrandConceptDetails> conceptDetailsLt = new ArrayList<StemStrandConceptDetails>(); 
					String key = "";
			        for(Cell inCell : row) {  
			        	if(inCell.getCellType() == Cell.CELL_TYPE_STRING){
			        		StemStrandConceptDetails sConceptDetails = new StemStrandConceptDetails();
			        		if(inCell.getColumnIndex() == 0){
			        			key = inCell.getStringCellValue();
			        		}else{
			        			if(inCell.getStringCellValue().contains("&&")){
			        				String subSplits = inCell.getStringCellValue();
		                			String subData[] = subSplits.split("&&");
		                			if(subData.length > 1){
		                				if(categorySheet.containsKey(subData[0])){
		                					sConceptDetails.setStemConceptSubCategory(categorySheet.get(subData[0]));
		                					for(StemConceptSubCategory subCat: categorySheet.get(subData[0])){
		                						subCat.setStemStrandConceptDetails(sConceptDetails);
		                					}
		                				}
		                				sConceptDetails.setConceptDetDesc(subData[1]);
		                			}
			        			}else{
			        				sConceptDetails.setConceptDetDesc(inCell.getStringCellValue());
			        			}
			        			
			        			conceptDetailsLt.add(sConceptDetails);	
			        		}			        				        		
			        	}
			        }
			        if(!detailSheet.containsKey(key) && key!=""){
			        	detailSheet.put(key, conceptDetailsLt);
			        }			        
			    }  		                					
			}
			if(noOfSheets>0){
				Sheet firstSheet = workbook.getSheetAt(0);
				for(Row nextRow : firstSheet) {
					List<StemStrandConcepts> strandConceptsLt = new ArrayList<StemStrandConcepts>(); 
		            StemGradeStrands sGradeStrands = new StemGradeStrands();
		            long loop=0;
		            boolean isValid = false;
			        for(Cell cell : nextRow) { 
		                if(cell.getCellType() == Cell.CELL_TYPE_STRING){
		                	isValid = true;
		                	if(loop == 0){		                		
		                		sGradeStrands.setStemStrandTitle(cell.getStringCellValue());		                		
		                	}
		                	if(loop > 0){	
		                		if(cell.getStringCellValue().startsWith("added_desc")){
		                			String addDesc = cell.getStringCellValue().replace("added_desc&&", "");
		                			sGradeStrands.setAddedDesc(addDesc);
		                		}else{
		                			String splits = cell.getStringCellValue();
		                			String sConcepts[] = splits.split("&&");
		                			StemStrandConcepts sStrandConcept = new StemStrandConcepts();
		                			if(sConcepts.length > 1){
		                				if(!sConcepts[0].equalsIgnoreCase("")){
		                					sStrandConcept.setStemConcept(sConcepts[0]);
		                				}
		                				if(!sConcepts[1].equalsIgnoreCase("")){
		                					sStrandConcept.setStemConceptDesc(sConcepts[1]);
		                				}		                				
		                				sStrandConcept.setStemGradeStrands(sGradeStrands);
		                				if(detailSheet.containsKey(sConcepts[0])){
		                					sStrandConcept.setStemStrandConceptDetails(detailSheet.get(sConcepts[0]));
		                					for(StemStrandConceptDetails sscd:detailSheet.get(sConcepts[0])){
		                						sscd.setStemStrandConcepts(sStrandConcept);
		                					}
		                				}
		                			}	
		                			else if(sConcepts.length == 1){
		                				sStrandConcept.setStemGradeStrands(sGradeStrands);
		                				sStrandConcept.setStemConcept(sConcepts[0]);
		                			}
		                			strandConceptsLt.add(sStrandConcept);
		                		}	                		
		                	}
		                	sGradeStrands.setStemAreas(stemAreas);
		                	sGradeStrands.setMasterGrades(masterGrades);
		                	sGradeStrands.setStates(state);
                        }
		                loop++;
		                
		            }
		            if(isValid){
		            	sGradeStrands.setStemStrandConceptsLt(strandConceptsLt);
			            gradeStrandsLt.add(sGradeStrands);
		            }		            
				}
			}
			
			status = uploadDAO.uploadStrandsFile(gradeStrandsLt);
		}catch(IOException e){
			logger.error("Error in uploadStrandsFile() of UploadServiceImpl "+e);			
		}
		return status;
	}

	@Override
	public List<School> getAllSchoolStates() {
		return uploadDAO.getAllSchoolStates();
	}

	@Override
	public List<District> getDistricts() {
		return uploadDAO.getDistricts();
	}

	@Override
	public List<Trimester> getTrimesters() {
		return uploadDAO.getTrimesters();
	}

	@Override
	public List<CAASPPTypes> getCAASPPTypes() {
		return uploadDAO.getCAASPPTypes();
	}

	@SuppressWarnings("resource")
	@Override
	public boolean starUploadFile(MultipartFile file, long trimesterId,
			long caasppId) {		
		List<StarScores> starScores = new ArrayList<StarScores>();
		boolean status = false;
		try {			
			FileInputStream fis = (FileInputStream) file.getInputStream();
			String fileName = file.getOriginalFilename();
			Workbook workbook;
			if(fileName.substring(fileName.length()-3).equalsIgnoreCase(WebKeys.FORMAT_XLS)){
				workbook = new HSSFWorkbook(fis); 
			}else{
				workbook = new XSSFWorkbook (fis);
			}
			Sheet sheet = workbook.getSheetAt(0);
            long loops = 0;
            
            List<Student> studentList = Collections.emptyList();
            studentList = studentDAO.getAllStudents();
            HashMap<Long, Long> studentMap = new HashMap<>();
            
            for(Student st: studentList){
            	if(!studentMap.containsKey(st.getStudentScId())){
            		studentMap.put(st.getStudentScId(), st.getStudentId());
            	}
            }
            
            List<School> schoolList = Collections.emptyList();
            HashMap<Long, String> schoolMap = new HashMap<>();
            schoolList = uploadDAO.getAllSchools();
            for(School sc: schoolList){
            	if(!schoolMap.containsKey(sc.getSchoolId())){
            		schoolMap.put(sc.getSchoolId(), sc.getSchoolAbbr());
            	}
            }
            
            List<Grade> grades = Collections.emptyList();
            HashMap<String, Long> gradeMap = new HashMap<>();
            grades = gradesDAO.getGradesList();
            for(Grade gr: grades){
            	String scoolGrade = schoolMap.get(gr.getSchoolId())+""+gr.getMasterGrades().getMasterGradesId();
            	if(!gradeMap.containsKey(scoolGrade)){            		
            		gradeMap.put(scoolGrade, gr.getGradeId());
            	}
            }
            
            List<Teacher> tescherList = Collections.emptyList();
            tescherList = cAASPPDAO.getActiveTeachers();
            HashMap<Long, Long> teacherMap = new HashMap<>();
            
            for(Teacher tc: tescherList){
            	if(!teacherMap.containsKey(tc.getTeacherScId())){
            		teacherMap.put(tc.getTeacherScId(), tc.getTeacherId());
            	}
            }
            
            Trimester trimester =  new Trimester();
            trimester.setTrimesterId(trimesterId);
            
            CAASPPTypes cAASPPTypes = new CAASPPTypes();
            cAASPPTypes.setCaasppTypesId(caasppId);
            
            for(Row row:sheet) {
            	StarScores sScore = new StarScores();
                long recGrade = 0;
                for(Cell cell : row){
                    if(loops == 0){
                    	if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                    		
                        }
                    }else{
                    	if(cell.getColumnIndex() == 0){
                    		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                    			Student student = new Student();
                    			if(studentMap.containsKey((new Double(cell.getNumericCellValue())).longValue())){
                    				long studentId = studentMap.get((new Double(cell.getNumericCellValue())).longValue());
                    				if(studentId > 0){
    	                    			student.setStudentId(studentId);
    	                        		sScore.setStudent(student);
                        			}
                    			}else{
                    				logger.error("Student SC Id not present in Student table : "+(new Double(cell.getNumericCellValue())).longValue());
                    				break;
                    			}
                    			
                            }
                    	}
                    	if(cell.getColumnIndex() == 1){
                    		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                    			recGrade = (new Double(cell.getNumericCellValue())).longValue();
                            }
                    	}
                    	if(cell.getColumnIndex() == 2){
                    		if(recGrade > 0){
                    			if(cell.getCellType() == Cell.CELL_TYPE_STRING){
		                    		Grade grade = new Grade();
		                			String schoolAbbr = cell.getStringCellValue();
		                			if(schoolAbbr != ""){
		                				if(gradeMap.containsKey(schoolAbbr+""+recGrade)){
			                				long gradeId = gradeMap.get(schoolAbbr+""+recGrade);
			                				if(gradeId > 0){
				                				grade.setGradeId(gradeId);
				                        		sScore.setGrade(grade);
			                				}
		                				}else{
		                					logger.error("Master Grade Id not present in Grade table: "+cell.getStringCellValue());
		                    				break;
		                				}
		                			}
                    			}
                    		}
                    	}
                    	if(cell.getColumnIndex() == 3){
                    		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                        		sScore.setScore((new Float(cell.getNumericCellValue())));
                            }
                    	}
                    	if(cell.getColumnIndex() == 4){
                    		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                    			Teacher teacher = new Teacher();
                    			if(teacherMap.containsKey((new Double(cell.getNumericCellValue())).longValue())){
                    				long teacherId = teacherMap.get((new Double(cell.getNumericCellValue())).longValue());
                        			if(teacherId > 0){
                        				teacher.setTeacherId(teacherId);
    	                        		sScore.setTeacher(teacher);
                        			}
                    			}else{
                    				logger.error("Teacher SC Id not present in Teacher table : "+(new Double(cell.getNumericCellValue())).longValue());
                    				break;
                    			}
                    			
                            }
                    	}
                    	if(cell.getColumnIndex() == 5){
                    		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                    			 if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    				 sScore.setTestDate(cell.getDateCellValue());
                    			 }
                            }
                    	}
                    }                      
                }
                sScore.setTrimester(trimester);
                sScore.setCaasppType(cAASPPTypes);
                loops++;   
                if(sScore.getStudent() != null && sScore.getGrade() != null){
                	starScores.add(sScore);
                }
            }       
            status = uploadDAO.saveStarUploadFile(starScores);
		}catch(Exception e){
			logger.error("Error in starUploadFile() of UploadServiceImpl "+e);
		}
		return status;
	}

	@Override
	public boolean caasppFileUpload(MultipartFile file) {
		List<CAASPPScores> cAASPPScores = new ArrayList<CAASPPScores>();
		boolean status = false;
        CAASPPTypes elaType = new CAASPPTypes();
        CAASPPTypes mathType = new CAASPPTypes();
		try {			
			FileInputStream fis = (FileInputStream) file.getInputStream();
			String fileName = file.getOriginalFilename();
			Workbook workbook;
			if(fileName.substring(fileName.length()-3).equalsIgnoreCase(WebKeys.FORMAT_XLS)){
				workbook = new HSSFWorkbook(fis); 
			}else{
				workbook = new XSSFWorkbook (fis);
			}
			Sheet sheet = workbook.getSheetAt(0);
            long loops = 0;
            
            List<Student> studentList = Collections.emptyList();
            studentList = studentDAO.getAllStudents();
            HashMap<Long, Long> studentMap = new HashMap<>();
            
            for(Student st: studentList){
            	if(!studentMap.containsKey(st.getStudentScId())){
            		studentMap.put(st.getStudentScId(), st.getStudentId());
            	}
            }
            
            List<School> schoolList = Collections.emptyList();
            HashMap<Long, String> schoolMap = new HashMap<>();
            schoolList = uploadDAO.getAllSchools();
            for(School sc: schoolList){
            	if(!schoolMap.containsKey(sc.getSchoolId())){
            		schoolMap.put(sc.getSchoolId(), sc.getSchoolAbbr());
            	}
            }
            
            List<Grade> grades = Collections.emptyList();
            HashMap<String, Long> gradeMap = new HashMap<>();
            grades = gradesDAO.getGradesList();
            for(Grade gr: grades){
            	String scoolGrade = schoolMap.get(gr.getSchoolId())+""+gr.getMasterGrades().getMasterGradesId();
            	if(!gradeMap.containsKey(scoolGrade)){            		
            		gradeMap.put(scoolGrade, gr.getGradeId());
            	}
            }
            
            List<CAASPPTypes> types = new ArrayList<CAASPPTypes>();            
            types= uploadDAO.getCaasppElaMathTypes();
            for(CAASPPTypes ct : types){
            	if(ct.getCaasppType().equalsIgnoreCase(WebKeys.CAASPP_TYPE_ELA)){
            		elaType = ct;
            	}else if(ct.getCaasppType().equalsIgnoreCase(WebKeys.CAASPP_TYPE_MATH)){
            		mathType = ct;
            	}
            }
            
            List<CAASPPScores> testScores = new ArrayList<CAASPPScores>();
            testScores = uploadDAO.getAllCaasppScores();
            HashSet<String> cScoreSet = new HashSet<>();
            
            if(testScores.size() > 0){
            	for(CAASPPScores csp : testScores){
            		String set = csp.getStudent().getStudentId()+"&&"+csp.getGrade().getGradeId()+"&&"+csp.getCaasppType().getCaasppTypesId();
	            	if(!cScoreSet.contains(set)){
	            		cScoreSet.add(set);
	            	}
            	}
            }
            
            
            for(Row row:sheet) {
            	CAASPPScores caasppELAScore = new CAASPPScores();
            	CAASPPScores caasppMathScore = new CAASPPScores();
            	String caasppELASet = "";
            	String caasppMathSet = "";
                long recGrade = 0;
                for(Cell cell : row){
                	if(loops == 0){
                    	if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                    		
                        }
                    }else{
                    	if(cell.getColumnIndex() == 0){
                    		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                    			Student student = new Student();
                    			if(studentMap.containsKey((new Double(cell.getNumericCellValue())).longValue())){
                    				long studentId = studentMap.get((new Double(cell.getNumericCellValue())).longValue());
                        			if(studentId > 0){
    	                    			student.setStudentId(studentId);
    	                    			caasppELAScore.setStudent(student);
    	                    			caasppMathScore.setStudent(student);
    	                    			caasppELASet = ""+studentId;
    	                    			caasppMathSet = ""+studentId;
                        			}
                    			}else{
                    				logger.error("Student SC Id not present in Student table : "+(new Double(cell.getNumericCellValue())).longValue());
                    				break;
                    			}
                    			
                            }
                    	}
                    	if(cell.getColumnIndex() == 1){
                    		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                    			recGrade = (new Double(cell.getNumericCellValue())).longValue();
                            }
                    	}
                    	if(cell.getColumnIndex() == 2){
                    		if(recGrade > 0){
                    			if(cell.getCellType() == Cell.CELL_TYPE_STRING){
		                    		Grade grade = new Grade();
		                			String schoolAbbr = cell.getStringCellValue();
		                			if(schoolAbbr != ""){
		                				if(gradeMap.containsKey(schoolAbbr+""+recGrade)){
			                				long gradeId = gradeMap.get(schoolAbbr+""+recGrade);
			                				if(gradeId > 0){
				                				grade.setGradeId(gradeId);
				                				caasppELAScore.setGrade(grade);
				                				caasppMathScore.setGrade(grade);
				                				caasppELASet = caasppELASet+"&&"+gradeId;
		    	                    			caasppMathSet = caasppMathSet+"&&"+gradeId;
			                				}
		                				}else{
		                					logger.error("Master Grade Id not present in Grade table: "+cell.getStringCellValue());
		                    				break;
		                				}
		                			}
                    			}
                    		}
                    	}
                    	if(cell.getColumnIndex() == 3){
                    		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                    			caasppELAScore.setCaasppScore((new Float(cell.getNumericCellValue())));
                    			caasppELAScore.setCaasppType(elaType);
                    			caasppELASet = caasppELASet+"&&"+elaType.getCaasppTypesId();
                            }
                    	}
                    	
                    	if(cell.getColumnIndex() == 4){
                    		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                    			caasppMathScore.setCaasppScore((new Float(cell.getNumericCellValue())));
                    			caasppMathScore.setCaasppType(mathType);
                    			caasppMathSet = caasppMathSet+"&&"+mathType.getCaasppTypesId();
                            }
                    	}
                    }
                }
                loops++;   
                if(caasppELAScore.getStudent() != null && caasppELAScore.getGrade() != null && caasppELAScore.getCaasppType() != null ){
                	if(!cScoreSet.contains(caasppELASet) && caasppELAScore.getCaasppScore() != null){
                		cAASPPScores.add(caasppELAScore);
                	}                	
                }
                
                if(caasppMathScore.getStudent() != null && caasppMathScore.getGrade() != null && caasppMathScore.getCaasppType() != null){
                	if(!cScoreSet.contains(caasppMathSet) && caasppMathScore.getCaasppScore() != null){
                		cAASPPScores.add(caasppMathScore);
                	}
                }                
            }
            status = uploadDAO.saveCAASPPUploadFile(cAASPPScores);
            
		}catch(Exception e){
			logger.error("Error in caasppFileUpload() of UploadServiceImpl "+e);
		}
		return status;
	}
}
