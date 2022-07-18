<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<%@ include file="../CommonJsp/include_resources.jsp" %>
<script>
	function goToDiv(i) {
		document.getElementById("sho" + i).scrollIntoView(true);
	}
</script>
<script src="resources/javascript/TeacherJs/grade_assessments.js"></script>
</head>
<body>
<form:form id="testQuestionsForm" modelAttribute="studentAssignmentStatus">
  <input type="hidden" id="lessonId" name="lessonId" value="${lessonId}" />
  <input type="hidden" id="studentId" name="studentId" value="${studentId}" />
  <input type="hidden" id="csId" name="csId" value="${csId}" />
  <form:hidden path="studentAssignmentId" />
  <form:hidden path="assignment.assignmentType.assignmentTypeId" value="${assignmentTypeId}"></form:hidden>
  <c:set var="queCount" value="0">
  </c:set>
  <c:set var="co" value="1">
  </c:set>
  <c:set var="quedisplayCount" value="1">
  </c:set>
  <c:choose>
    <c:when test="${fn:length(benchQuestions[0].fluencyMarks) gt 0 && assignmentTypeId==8}">
      <div class="container-fluid dboard">
        <div class="row">
          <div class="col-md-4"> </div>
          <div class="col-md-4 des">
            <div class="row Divheads">
              <div class="col-md-4"> Test</div>
              <div class="col-md-4"> WCPM</div>
              <div class="col-md-4"> Accuracy</div>
            </div>
            <div class="DivContents">
              <c:set var="marks" value="0">
              </c:set>
              <c:set var="coun" value="0">
              </c:set>
              <c:set var="Type" value="">
              </c:set>
              <c:set var="co" value="0" />
              <c:forEach items="${benchQuestions}" var="questionList" varStatus="count">
                <c:forEach items="${questionList.fluencyMarks}" var="typesList">
                  <input type="hidden" id="marks" name="marks" value="${typesList.marks}" />
                  <c:if test="${queCount%2==0}">
                    <c:set var="co" value="${co+1}">
                    </c:set>
                  </c:if>
                  <c:set var="yes" value="0">
                  </c:set>
                  <c:if test="${empty typesList.marks}">
                    <c:set var="marks" value="0">
                    </c:set>
                  </c:if>
                  <c:if test="${not empty typesList.marks}">
                    <c:set var="marks" value="${typesList.marks}">
                    </c:set>
                  </c:if>
                  <div class="row" style="padding-top:6px;">
                    <div class="col-md-4 text-left"> <a class="label"  href='javascript:openFluencyWindow(${questionList.assignmentQuestionsId},${questionList.studentAssignmentStatus.studentAssignmentId},${questionList.studentAssignmentStatus.assignment.benchmarkCategories.benchmarkCategoryId},${typesList.readingTypes.readingTypesId},${gradeTypesId})'>
                      <c:if test="${typesList.readingTypes.readingTypesId==3}">
                        <c:set var="Type" value="Score">
                        </c:set>
                      </c:if>
                      <c:out value="${typesList.readingTypes.readingTypes}" />
                      <%-- <c:if test="${passageCount>1}"> --%>
                      <c:out value="${co}" />
                      <%-- </c:if> --%>
                      </a></div>
                    <div class="col-md-4">
                      <c:choose>
                        <c:when test="${typesList.readingTypes.readingTypesId ne 3}">
                          <input type='text' id='marks:${questionList.assignmentQuestionsId}:${typesList.readingTypes.readingTypesId}' name='marks' value='${typesList.marks}' maxlength='3' size='8' disabled />
                        </c:when>
                        <c:otherwise>
                          <input type='text' id='marks:${questionList.assignmentQuestionsId}:${typesList.readingTypes.readingTypesId}' name='marks' value='${typesList.qualityOfResponse.qorId}' maxlength='3' size='8' disabled />
                        </c:otherwise>
                      </c:choose>
                    </div>
                    <div class="col-md-4">
                      <c:if test="${typesList.readingTypes.readingTypesId==2}">
                        <input type='text' id='accuracyMarks:${questionList.assignmentQuestionsId}:${typesList.readingTypes.readingTypesId}' name='accuracyMarks' value='${typesList.accuracyScore}' maxlength='4' size='8' disabled />
                        <input type="hidden" name="assignmentQuesList" id="assignQuesList:${co}" value="${questionList.assignmentQuestionsId}" />
                      </c:if>
                    </div>
                  </div>
                  <c:set var="queCount" value="${queCount+1}">
                  </c:set>
                </c:forEach>
              </c:forEach>
              <div class="row" style="padding-top:5px;">
                <div class="col-md-4 text-left">
                  <label class="label"> Notes</label>
                </div>
                <div class="col-md-8">
                  <c:if test="${gradeTypesId==1}">
                    <textarea id='teacherNotes' name='teacherNotes' style="margin: 0px; width: 200px; height: 50px;"><c:out value="${teacherComment}"></c:out>
</textarea>
                  </c:if>
                </div>
              </div>
              <div id="gradeBenchmark" style="background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center; align: center"></div>
              <div class="row tabtxt text-center" style="padding-top:8px;">
                <div class="col-md-12">
                  <div border="0" class="button_green" id="btSubmitChanges" name="btSubmitChanges" height="52" onclick="gradeTest(${assignmentId},${assignmentTypeId}); return false;" style="width:150px;font-size:12px;">Submit Changes</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </c:when>
    <c:otherwise>Not assigned for this grading</c:otherwise>
  </c:choose>
</form:form>
</body>
</html>