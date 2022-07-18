<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Welcome to The Learning Priority Inc | Teacher Guide</title>
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_special_effects.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="resources/css/registration-css.css">
</head>
<body>
<c:if test="${userReg == null}">
  <div align="center" valign="middle" style=''>
    <table width="100%">
      <tr>
        <td width="37%"  valign="middle" align="right"><span class="logoStyle1">Learning Priority</span></td>
        <td  width="30%"  valign="middle" align="left"><img src="images/common_images/LpLOGO.png" width="90" height="60" style=""></td>
      </tr>
      <tr>
        <td width="100%" valign="middle" align="right" colspan="2" style="padding-right:12em;"><button class="stylish-button button--pipaluk button--inverted  button--round-s button--text-thick" style="float:right;min-width: 100px;max-width: 100px;background: #00e3ff;text-shadow:0 1px 1px rgb(128, 128, 128);font-size: 11px;" onclick="loadPage('index.htm')"><i class="fa fa-home" aria-hidden="true" style="font-size: 18px;"></i>&nbsp; Go Home</button></td>
      </tr>
    </table>
  </div>
</c:if>
<div class="form teacherguide teacherimgs">
  <div class="row">
    <div class="col-sm-12 text-right" style="margin-top:-30px;"> <a class="urllink" href="downloadFile.htm?fileName=LP Online Teacher Guide.pdf"><i class="fa fa-download" aria-hidden="true" style="font-size: 17px;color:#8e1400;"><span style="font-size: 13px;color:#8e1400;padding:5px;font-weight:bold;">Download</span></i></a> </div>
  </div>
  <div class="row">
    <div class="col-sm-12 text-center"><span class="mainhead">Rio Online Literacy</span></div>
    <div class="col-sm-12 text-center">
      <p class="subhead">Learning Priority is providing great opportunities during these school closure times.</p>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12">
      <p><b>1. Reading Fluency Suite -</b> Students can <span class="focustxt">read and record</span> passages. LST will score them and students will automatically have <span class="focustxt">word work</span> assigned, as well as <span class="focustxt">comprehension</span> quizzes and <span class="focustxt">sight words</span>. Teachers can listen to the kids read, and also view the scores.<br>
        Follow this link for a student tutorial: <a class="urllink" href="https://drive.google.com/file/d/1knA-yLqohr_Lz0e-p1gNAcizRbTwyq-8/view?usp=drive_open">LP Fluency Student Video Guide.</a></p>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12">
      <p><b>2. Reading Register -</b> Kids read books or passages and create their own assignments with a choice of 5 activities. Students can also see what books other kids are reading and reflect
        ‘meaning making’ through their creative activities.<br>
        Follow this link for a student tutorial: <a class="urllink" href="https://drive.google.com/file/d/1IGWaRCtVElHHcMOdg3CYqi85adijb3nx/view">Reading Register Student Video Guide</a> </p>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12">
      <p><b>1. Reading Fluency Suite</b><br>
        <span class="subhead focustxt">Select a passage from any source of your choice.</span>
      <p>(Reading AZ, Reading for comprehension, Houghton Mifflin Harcourt, Newsela, Epic, etc) Using this passage you can also select one or all of the following activities for your students.LST will upload and assign them to your students’ portal.</p>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">A. Fluency Reading</span>
      <p>Your student will be able to read it out loud, and retell the information retained. (1 minute)</p>
      <div class="text-center"><img src="images/Teacher/guide/fluency-reading.PNG"></div>
    </div>
    <div class="col-sm-12"> <span class="subhead focustxt">B. Comprehension Questions</span>      
      <div class="text-center"><img src="images/Teacher/guide/comprehension-questions.PNG"></div>
    </div>
    <div class="col-sm-12"> <span class="subhead focustxt">C. Sight Words</span>
      <p> target specific vocabulary standards. Students will record each word out loud, all error words are assigned again. Students may view their results after. check</p>
      <div class="text-center"><img src="images/Teacher/guide/sight-words.PNG"></div>
    </div>
    <div class="col-sm-12"> <span class="subhead focustxt">D. Word Work</span>
      <p> automatically generates a vocabulary list from each student’s words read incorrectly in their fluency passage. Students will search the definition of the misread word, pronunciation, create a sentence, then record it using the word correctly.</p>
      <div class="text-center"><img src="images/Teacher/guide/word-work.PNG"></div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12">
      <p><b>2. Reading Register</b><br>
      <p> Using a passage, article, book of teacher’s or student’s choice. Kids can enter independent “meaning making” activities, that reflect their connection and creativity towards what they read. They can write a <span class="subhead focustxt">Review</span> , say a <span class="subhead focustxt">Retell</span> (these two are the most encouraged), create a <span class="subhead focustxt">Quiz</span>, or make a descriptive <span class="subhead focustxt">Drawing</span> or diagram. Student worksheet instructions for each activity can be provided to teachers. </p>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">A. Written Review</span>
      <p>Students will type a review with details like the genre, main characters or subject, a sequential plot, and their expressive opinion . Hence, creating connection and interest towards what they are reading.</p>
      <div class="text-center"><img src="images/Teacher/guide/written-review.PNG"></div>
    </div>
    <div class="col-sm-12"> <span class="subhead focustxt">B. Audio Retell</span>
      <p>Children can comfortably speak in length about what they read. Again, they will give concrete information like: genres, main characters or subjects, a sequential plot, and their expressive opinion.</p>
      <div class="text-center"><img src="images/Teacher/guide/audio_retell.PNG"></div>
    </div>
    <div class="col-sm-12"> <span class="subhead focustxt">C. Quiz Creation</span>
      <p> Kids can self create a 10 question quiz of what they read. Using a sequential order they can generate 6 basic  questions and 4 more challenging ones.</p>
      <div class="text-center"><img src="images/Teacher/guide/quiz-creation.PNG"></div>
    </div>
    <div class="col-sm-12"> <span class="subhead focustxt">D. Drawing or Diagram</span>
      <p> Students will create a descriptive drawing or diagram of the plot of the story, their favorite part, or sequence of the beginning, middle, end; as instructed by the teacher.</p>
      <div class="text-center"><img src="images/Teacher/guide/drawing-diagram.PNG"></div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12">
      <p>Teachers are welcome to email LST staff with a passage, article, or book of your choice, indicating the activities you’re selecting for your class. LST will give you weekly reports of your students' scores. We will provide individualized explanation and teacher training if you’d like to explore more possibilities. We will reach out one by one as needed through google hangout, zoom, phone calls, or other online means.</p>
      <p class="urllink"><b>LST staff contact information:</b></p>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-4"> <b>Liliania Rabelo</b> </div>
    <div class="col-sm-8"> <span>lrabelo@rioschools.org</span> </div>
  </div>
  <div class="row">
    <div class="col-sm-4"> <b>Nelly Jauregui</b> </div>
    <div class="col-sm-8"> <span>njauregui@rioschools.org</span> </div>
  </div>
  <div class="row">
    <div class="col-sm-4"> <b>Teresa Jauregui</b> </div>
    <div class="col-sm-8"> <span>tjauregui@rioschools.org</span> </div>
  </div>
  
  <div class="row">   
    <div class="col-sm-12">
      <div class="subhead text-center"><br/ >LST is providing great opportunities during these school closure times.</div>
    </div>
  </div>
   <div class="row">    
    <div class="col-sm-3"><div class="text-center"><img src="images/Teacher/guide/smallgroups.PNG"></div></div>
    <div class="col-sm-9">
	<p class="subhead focustxt" style="padding-top:14px;"><u>Small Groups</u></p>
	<p style="padding-top:6px;">We are now providing support for teachers by forming reading groups to read and complete reading comprehension activities together. </p>
	<p>These groups can be directed to include students who need help improving their reading, who are having trouble transitioning to online learning, or 
need extra support and motivation to join their class activities. </p>
	<p>Also, they can be made up of students who enjoy reading, want to be engaged and form part of a book club to make reading fun! </p>
	</div>
	 <div class="col-sm-12">
      <div class="subhead text-center"><br/ >*LST will order books for the students and mail them out to their homes. We will set a 
weekly time to meet with the group of students and report back to the teacher on the student progress. </div>
    </div>
  </div>
     <div class="row">
    <div class="col-sm-12 text-center">
     
        <span class="subhead focustxt">We will also have online reading sources and activities from Learning Priority.com </span>
      <div class="subhead text-center">Please reach out to us, we are happy to help!</div>
    </div>
  </div>   
    <div class="row">
    <div class="col-sm-12">
    
      <p class="urllink"><b>LST staff contact information:</b></p>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-4"> <b>Liliania Rabelo</b> </div>
    <div class="col-sm-8"> <span>lrabelo@rioschools.org</span> </div>
  </div>
  <div class="row">
    <div class="col-sm-4"> <b>Nelly Jauregui</b> </div>
    <div class="col-sm-8"> <span>njauregui@rioschools.org</span> </div>
  </div>
  <div class="row">
    <div class="col-sm-4"> <b>Teresa Jauregui</b> </div>
    <div class="col-sm-8"> <span>tjauregui@rioschools.org</span> </div>
  </div>
    <div class="row">
    <div class="col-sm-4"> <b>Laura Camarillo-Torres</b> </div>
    <div class="col-sm-8"> <span>ltorres@rioschools.org </span> </div>
  </div>
</div>
<!-- <script src="resources/javascript/index.js"></script> -->
</body>
</html>