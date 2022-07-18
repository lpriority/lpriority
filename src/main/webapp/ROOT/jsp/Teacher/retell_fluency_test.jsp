<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/TeacherJs/retell_fluency_test.js"></script>
<link href="resources/javascript/jplayer/css/jplayer.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="resources/javascript/CommonValidation.js"></script>
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>
<link rel="stylesheet"
	href="resources/javascript/jquery/jquery-ui/themes/redmond/jquery-ui.css">
<style type="text/css">
.ui-dialog>.ui-widget-content {
	background: white;
}
.ui-widget {
	font-family: Georgia, Times, 'Times New Roman', serif;
	font-size: 1em;
}
.AudioPlay {
	background-image: url('images/Teacher/audioOver.gif');
	background-color: white;
	background-repeat: no-repeat;
	border: none;
	cursor: pointer;
	width: 120px;
	height: 35px;
	vertical-align: middle;
}
.text1 {
	font-size: 1.3em;
	padding:5px;
}
.h2, h2 {
	font-size: 25px;
	text-align: center;
}
#retellComment {
	width: 495px;
	font-size: 14px;
}
</style>
<link href="resources/javascript/jplayer/css/jplayer.css" rel="stylesheet" type="text/css" />
<script src="resources/javascript/jplayer/jquery.jplayer.js"></script>

<div class="container-fluid des">
  <div class="row" style="padding-bottom:20px;">
    <div class="col-md-1"> </div>
    <div class="col-md-10">
      <form:form name="StudentRetellForm">
        <div class="row">
          <div class="col-md-12 tabtxt text-center">
            <input type="hidden" name="regId" value="${studentRegId}" id="regId">
            <input type="hidden" name="usersFilePath" value="${usersFilePath}" id="usersFilePath">
            <font size="3"><b>
            <h2>Retell Passage</h2>
            </b></font>
            <input type="hidden" name="questions" id="questions" value="${assignQuestions.assignmentQuestionsId}" />
            <input type="hidden" name="lessonId" id="lessonId" value="${lessonId}" />
            <input type="hidden" name="csId" id="csId" value="${csId}" />
            <input type="hidden" name="studentId" id="studentId" value="${studentId}" />
            <input type="hidden" name="langId" id="langId" value="${langId}"/>
            <input type="hidden" name="assignmentTitle" id="assignmentTitle" value="${assignmentTitle}" />
            <input type="hidden" name="hwAssignmentId" id="hwAssignmentId" value=0 />
          </div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <p onmouseup="selecttext()" align="justify">
              <c:set var="i" value="0">
              </c:set>
              <c:forEach items="${benchmarkqList}" var="ql">
                <c:set var="i" value="${i+1}">
                </c:set>
                <span id="ord${i}">
                <c:out value="${ql}">
                </c:out>
                </span> </c:forEach>
            </p>
          </div>
        </div>
        <div class="row" style="padding:8px 0px;">
          <div class="col-md-5 text1 text-right" style="padding-top: 18px;"><b>Answer :</b></div>
          <div class="col-md-7 text1">
            <input type="hidden" name="answerpath" id="answerpath" value="answer" />
            <audio id="accuracy" src="loadUserFile.htm?regId=${studentRegId}&usersFilePath=${usersFilePath}" controls></audio>
            <input type='hidden' id='errosString' name='errorsString' />
          </div>
        </div>
        <div class="row">
          <div class="col-md-2"></div>
          <div class="col-md-8">
            <div class="col-md-12 text1 des">
              <h2>Select Retell Score</h2>
            </div>
            <c:set var="select" value="">
            </c:set>
            <c:set var="n" value="0">
            </c:set>
            <c:forEach items="${qualityResponseList}" var="qr">
              <c:if test="${regrade eq 'no' || regrade eq 'view'}">
                <c:if test="${fluencyMarks.qualityOfResponse.qorId == qr.qorId}">
                  <c:set var="select" value="checked">
                  </c:set>
                </c:if>
              </c:if>
              <div class="col-md-12 text1 des"> <input type="radio" name="response" id="response${n}" onClick="getresponse(${n})" <c:out value="${select}"></c:out>>
                <c:out value="${qr.response}">
                </c:out>
              </div>
              <c:set var="select" value="">
              </c:set>
              <c:set var="n" value="${n+1}">
              </c:set>
            </c:forEach>
            <div class="col-md-12 text1 des">
              <c:choose>
                <c:when test="${regrade eq 'view' || regrade eq 'no'}">
                  <div class="col-md-3"><font size="4"> Retell score: </font></div>
                  <div class="col-md-9">
                    <input type="text" id="retellScore" name="retellScore" style="border: none; font-size: 20" value="${fluencyMarks.qualityOfResponse.qorId}" disabled />
                  </div>
                </c:when>
                <c:otherwise>
                  <div class="col-md-3"><font size="4"> Retell score: </font></div>
                  <div class="col-md-9">
                    <input type="text" id="retellScore" name="retellScore" style="border: none; font-size: 20">
                    </input>
                  </div>
                </c:otherwise>
              </c:choose>
            </div>
            <div class="col-md-12 text1 des">
              <div class="col-md-3">Comment:</div>
              <div class="col-md-9"><font size="4"> </font>
                <c:set var="retellComm" value="" />
                <c:set var="stat" value="" />
                <c:choose>
                  <c:when test="${regrade eq 'view' || regrade eq 'no'}">
                    <c:set var="retellComm" value="${fluencyMarks.comment}" />
                    <c:set var="stat" value="disabled" />
                  </c:when>
                </c:choose>
                <textarea id='retellComment' name='retellComment' ${stat}>${retellComm}</textarea>
              </div>
            </div>
            <div class="row">
              <div class="col-md-12 text1 text-center" id="benchmark">
                <c:if test="${butt == 'yes' }">
                  <div class="button_green" onClick='submitRetellTest(${readingTypesId},${gradeTypesId})'>Submit</div>
                </c:if>
                <div id="errorTable"></div>
              </div>
            </div>
          </div>
        </div>
      </form:form>
    </div>
  </div>
</div>