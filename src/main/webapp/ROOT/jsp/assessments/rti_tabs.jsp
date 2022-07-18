<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page session="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function changeYear(thisvar){
		$("#loading-div-background").show();
		$.ajax({  			
			url : "changeAcademicYear.htm",
			type : "GET",
      		data: "selectedYearId=" + thisvar.value,
			success : function(data) {
				$("#loading-div-background").hide();
        	}  
    	}); 
	}
	
	function getTeacherGrades(thisvar) {
		var academicYear = $('#academicYear').val();
		if(academicYear != 'select'){	
			$.ajax({
				type : "GET",
				url : "getTeacherGrades.htm",
				data : "academicYear=" + academicYear,
				success : function(response) {
					window.location.reload();
					$("#gradeId").empty();
					$("#gradeId").append($("<option></option>").val('select').html('Select Grade'));
					var teacherGrades = response.grList;
					$.each(teacherGrades, function(index, value) {
						$("#gradeId").append($("<option></option>").val(value.gradeId).html(value.masterGrades.gradeName));
					});
					if(callback)
						callback();
				}
			});
		}
	}
</script>
</head>
<body>
<div class="container-fluid ldboard">
  <div class="row">
    <div class="col-md-12 text-right">
      <nav id="primary_nav_wrap">
        <ul class="button-group">
          <li class="dline"><a href="rti.htm" class="${(tab == 'prepareRti')?'buttonSelect leftPill  tooLongTitle':'button leftPill tooLongTitle'}"> Literacy Development</a></li>
          <li class="dline"><a href="assignRti.htm" class="${(tab == 'assignRti')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Assign Literacy Development</a></li>
          <c:if test="${teacherObj == null}">
            <li><a href="assignRtf.htm" class="${(tab == 'assignRtf')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Assign LST</a></li>
          </c:if>
          <li><a href="RTIResultsPage.htm" class="${(tab == 'rtiResults')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Literacy Development Results</a></li>
          <c:if test="${teacherObj != null }">
            <li><a href="showAssignedRtis.htm" class="${(tab == 'showAssignedRTI')?'buttonSelect longTitle tooLongTitle':'button longTitle tooLongTitle'}">Show Assigned Literacy Development</a></li>
          </c:if>
          <li class="dline"><a href="gotoGradeRti.htm" class="${(tab == 'gradeRti')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Grade Literacy Development </a></li>
          <c:choose>
            <c:when test="${teacherObj != null}">
              <li class="dline"><a href="viewProgressMonitor.htm" class="${(tab == 'progressMonitoring')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Progress Monitoring </a></li>
            </c:when>
            <c:otherwise>
              <li class="dline"><a href="assignGearGameByAdmin.htm" class="${(tab == 'assignGearGame')?'buttonSelect button tooLongTitle':'button button tooLongTitle'}">Assign Gear Game</a></li>
              <li class="dline"><a href="reviewMathGameByAdmin.htm" class="${(tab == 'reviewMathGame')?'buttonSelect button tooLongTitle':'button button tooLongTitle'}" >Review Gear Game</a></li>
              <li><a href="errorWordItem.htm" class="${(tab == 'errorWordItem')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Error Word Item Analysis</a></li>
              <li><a href="taskForceResults.htm" class="${(tab == 'FluencyReading Results')?'buttonSelect tooLongTitle':'button tooLongTitle'}" >Literacy Support Team Results</a></li>
              <li class="dline"><a href="viewProgressMonitor.htm" class="${(tab == 'progressMonitoring')?'buttonSelect rightPill tooLongTitle':'button rightPill tooLongTitle'}">Progress Monitoring </a></li>
            </c:otherwise>
          </c:choose>
          <c:if test="${teacherObj != null }">
            <li class="dline"> <a href="benchmarkResults.htm" class="${(tab == 'FluencyReading Results')?'buttonSelect tooLongTitle':'button tooLongTitle'}">FluencyReading Results</a>
              <ul>
                <li><a href="benchmarkResults.htm" class="${(page == 'FluencyReading Results')?'selectOnSubButton':'selectOffSubButton'}" >FluencyReading Results</a></li>
                <li><a href="taskForceResults.htm" class="${(page == 'taskForceResults')?'selectOnSubButton':'selectOffSubButton'}" >Literacy Support Team Results</a></li>
                <li><a href="selfAndPeerReviewResults.htm" class="${(page == 'selfAndPeerReviewResults')?'selectOnSubButton':'selectOffSubButton'}" >Accuracy Self And Peer Results</a></li>
              </ul>
            </li>
            <li> <a href="assignLetterSet.htm" class="${(tab == 'earlyIdentification')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Early Literacy: Letter Identification</a>
              <ul>
                <li><a href="assignLetterSet.htm" class="${(divId == 'assignLetterSet')?'selectOnSubButton':'selectOffSubButton'}" >Assign Letter Sets</a></li>
                <li><a href="gradeLetterSet.htm" class="${(divId == 'gradeLetterSet')?'selectOnSubButton':'selectOffSubButton'}" >Grade Letter Sets</a></li>
              </ul>
            </li>
            <li class="dline"> <a href="earlyLiteracyWord.htm" class="${(tab == 'earlySight')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Early Literacy: Test Sight Word</a>
              <ul>
                <li><a href="earlyLiteracyWord.htm" class="${(divId == 'CreateWord')?'selectOnSubButton':'selectOffSubButton'}" >Create word Lists</a></li>
                <li><a href="assignWordSet.htm" class="${(divId == 'assignWordSet')?'selectOnSubButton':'selectOffSubButton'}" >Assign word Lists</a></li>
                <li><a href="gradeWordSet.htm" class="${(divId == 'gradeWordSet')?'selectOnSubButton':'selectOffSubButton'}" >Grade word Lists</a></li>
              </ul>
            </li>
            <li class="sline"> <a href="phonicSkillTest.htm" class="${(tab =='phonicSkillTest')?'buttonSelect tooLongTitle':'button tooLongTitle'}">Phonic Test</a>
              <ul>
                <li><a href="phonicSkillTest.htm" class="${(page == 'assignPhonic')?'selectOnSubButton':'selectOffSubButton'}" style="float:right;" >Assign Phonic Skill Test</a></li>
                <li><a href="gradePhonicSkill.htm" class="${(page == 'gradePhonic')?'selectOnSubButton':'selectOffSubButton'}" style="float:right;" >Grade Phonic Skill Test</a></li>
                <li><a href="phonicTestSignleReports.htm" class="${(page == 'phonicSingleReports')?'selectOnSubButton':'selectOffSubButton'}" style="float:right;" >Single Test Report</a></li>
                <li><a href="phonicTestMultipleReports.htm" class="${(page == 'phonicMultipleReports')?'selectOnSubButton':'selectOffSubButton'}" style="float:right;">Multiple Tests Report</a></li>
              </ul>
            </li>
            <li class="sline"> <a href="createMathAssessment.htm" class="${(tab == 'mathAssessment')?'buttonSelect rightPill tooLongTitle':'button rightPill tooLongTitle'}">Math Assessment</a>
              <ul style="left:-6.50em;">
                <li><a href="createMathAssessment.htm" class="${(page == 'createMathAssessment')?'selectOnSubButton':'selectOffSubButton'}" >Create Math Assessment</a></li>
                <li><a href="assignMathAssessment.htm" class="${(page == 'assignMathAssessment')?'selectOnSubButton':'selectOffSubButton'}" >Assign Math Assessment</a></li>
                <li><a href="gradeMathAssessment.htm" class="${(page == 'gradeMathAssessment')?'selectOnSubButton':'selectOffSubButton'}" >Grade Math Assessment</a></li>
                <li><a href="analysisMathAssessment.htm" class="${(page == 'analysisMathAssessment')?'selectOnSubButton':'selectOffSubButton'}" >Math Assessment Analysis</a></li>
                <li><a href="assignMathGame.htm" class="${(page == 'assignGearGame')?'selectOnSubButton':'selectOffSubButton'}" >Assign Gear Game</a></li>
                <li><a href="reviewMathGame.htm" class="${(page == 'Gear Game')?'selectOnSubButton':'selectOffSubButton'}" >Review Gear Game</a></li>
              </ul>
            </li>
          </c:if>
        </ul>
      </nav>
    </div>
  </div>
  <div class="row"> 
  
    <div class="col-md-12 sub-title title-border">
        <c:if test="${tab == 'prepareRti' }">
          <div id="title">Literacy Development</div>
        </c:if>
        <c:if test="${tab == 'edit' }">
          <div id="title">Edit Literacy Development</div>
        </c:if>
        <c:if test="${tab == 'assignRti' }">
          <div id="title">Assign Literacy Development</div>
        </c:if>
        <c:if test="${tab == 'assignRtf' }">
          <div id="title">Assign LST</div>
        </c:if>
        <c:if test="${tab == 'rtiResults' }">
          <div id="title">Literacy Development Results</div>
        </c:if>
        <c:if test="${tab == 'gradeRti' }">
          <div id="title">Grade Literacy Development</div>
        </c:if>
        <c:if test="${tab == 'showAssignedRTI' && teacherObj != null }">
          <div id="title">Show Assigned Literacy Development</div>
        </c:if>
        <c:if test="${tab == 'assignGearGame' }">
          <div id="title">Assign Gear Game</div>
        </c:if>
        <c:if test="${tab == 'reviewMathGame' }">
          <div id="title">Review Gear Game</div>
        </c:if>
        <c:if test="${tab == 'errorWordItem' }">
          <div id="title">Error Word Item Analysis</div>
        </c:if>
        <c:if test="${tab == 'progressMonitoring' }">
          <div id="title">Progress Monitoring</div>
        </c:if>
        <c:if test="${tab == 'FluencyReading Results'}">
          <div id="title">
            <c:choose>
              <c:when test="${page == 'FluencyReading Results'}"> Fluency Reading </c:when>
              <c:when test="${page == 'taskForceResults'}"> Literacy Support Team Results </c:when>
              <c:when test="${page == 'selfAndPeerReviewResults'}"> Self And Peer Review Results </c:when>
            </c:choose>
          </div>
        </c:if>
        <c:if test="${tab == 'earlyIdentification' }">
          <div id="title">
            <c:choose>
              <c:when test="${divId == 'CreateLetter'}"> Create Letter Sets </c:when>
              <c:when test="${divId == 'assignLetterSet'}"> Assign Letter Sets </c:when>
              <c:when test="${divId == 'gradeLetterSet'}"> Grade Letter Sets </c:when>
            </c:choose>
          </div>
        </c:if>
        <c:if test="${tab == 'earlySight' }">
          <div id="title">
            <c:choose>
              <c:when test="${divId == 'CreateWord'}"> Create Words Sets </c:when>
              <c:when test="${divId == 'assignWordSet'}"> Assign Words Sets </c:when>
              <c:when test="${divId == 'gradeWordSet'}"> Grade Words Sets </c:when>
            </c:choose>
          </div>
        </c:if>
        <c:if test="${page == 'assignPhonic' }">
          <div id="title"> Assign Phonic Skill Test </div>
        </c:if>
        <c:if test="${page == 'gradePhonic' }">
          <div id="title"> Grade Phonic Skill Test </div>
        </c:if>
        <c:if test="${page == 'phonicSingleReports' }">
          <div id="title"> Phonic Single Test Report </div>
        </c:if>
        <c:if test="${page == 'phonicMultipleReports' }">
          <div id="title"> Phonic Multiple Tests Report </div>
        </c:if>
        <c:if test="${page == 'createMathAssessment' }">
          <div id="title"> Create Math Assessment </div>
        </c:if>
        <c:if test="${page == 'assignMathAssessment' }">
          <div id="title"> Assign Math Assessment </div>
        </c:if>
        <c:if test="${page == 'gradeMathAssessment' }">
          <div id="title"> Grade Math Assessment </div>
        </c:if>
        <c:if test="${page == 'analysisMathAssessment' }">
          <div id="title"> Math Assessment Analysis </div>
        </c:if>
        <c:if test="${page == 'assignGearGame' }">
          <div id="title"> Assign Gear Game </div>
        </c:if>
        <c:if test="${page == 'Gear Game' }">
          <div id="title"> Review Gear Game </div>
        </c:if>
    </div>
  </div>
  <c:if test="${tab == 'rtiResults' || tab =='progressMonitoring' || tab == 'FluencyReading Results' || (tab == 'earlyIdentification' && divId == 'gradeLetterSet') || (tab == 'earlySight' && divId == 'gradeWordSet') || (tab =='phonicSkillTest' && (page == 'phonicMultipleReports' || page == 'phonicSingleReports')) }">
  <div class="row">
    <div class="col-md-3">
      <div class="form-group">
        <label class="col-sm-3 col-form-label label">Academic Year</label>
        <div class="col-sm-9">
          <select onchange="getTeacherGrades(this)" id="academicYear" style="color: rgb(128, 128, 128);">
                <c:forEach items="${academicYears}" var="academicYear">
                  <option value="${academicYear.academicYearId }" <c:if test="${academicYear.academicYearId  == selectedYearId}"> selected="Selected" </c:if>>${academicYear.academicYear}</option>
                </c:forEach>
           </select>
        </div>
      </div>
    </div>   
  </div>
  </c:if>
</div>
</body>
</html>