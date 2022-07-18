<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Welcome to The Learning Priority Inc | Fluency Suite</title>
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
    <div class="col-sm-12 text-right" style="margin-top:-30px;"> <a class="urllink" href="download.htm?fileName=Learning_Priority_Fluency_Suite_English_Learners.pptx" download=""><i class="fa fa-download" aria-hidden="true" style="font-size: 17px;color:#8e1400;"><span style="font-size: 13px;color:#8e1400;padding:5px;font-weight:bold;">Download</span></i></a> </div>
  </div>
  <div class="row">
    <div class="col-sm-12 text-center"><span class="mainhead">The Fluency Suite & English Learners<br>
      <br>
      </span></div>
  </div>
  <div class="row"> <i class="col-sm-1 text-right fa fa-hand-o-right" style="font-size:20px"></i>
    <p class="col-sm-11"><b>Fluency</b> is the ability to <b>read</b> a text accurately, quickly, and with expression. <b>Fluency</b> is important because it provides a bridge between word recognition and comprehension.</p>
  </div>
  <div class="row"> <i class="col-sm-1 text-right fa fa-hand-o-right" style="font-size:20px"></i>
    <p class="col-sm-11">When <b>fluent readers read</b> silently, they recognize words automatically. They group words quickly to help them gain meaning from what they <b>read</b>.</p>
  </div>
  <div class="row"> <i class="col-sm-1 text-right fa fa-hand-o-right" style="font-size:20px"></i>
    <p class="col-sm-11">Instruction in fluency can be particularly beneficial for <b>English language learners</b> because activities designed to enhance fluency in reading can also contribute to oral language development in English. </p>
  </div>
  <div class="row"> <i class="col-sm-1 text-right fa fa-hand-o-right" style="font-size:20px"></i>
    <p class="col-sm-11">As students practice reading English text accurately, automatically, and prosodically, they are gaining valuable information about the sounds and cadences of spoken English, and they are also developing vocabulary skills that can contribute to oral language fluency, as well as reading and listening comprehension.</p>
  </div>
  <div class="row"> <i class="col-sm-1 text-right fa fa-hand-o-right" style="font-size:20px"></i>
    <p class="col-sm-11">Even though fluency instruction is important, teachers must remember that many ELLs can be deceptively fast and accurate while reading in English without fully comprehending the meaning of the text they are reading. That is because <b>reading comprehension</b> depends upon a variety of complex skills that are not as important to word reading. </p>
  </div>
  <div class="row"> <i class="col-sm-1 text-right fa fa-hand-o-right" style="font-size:20px"></i>
    <p class="col-sm-11">These include deep vocabulary knowledge, syntactical knowledge, and background knowledge of the subject discussed in the text. For this reason, it is always important to pair fluency instruction with good instruction in comprehension.</p>
  </div>
  <div class="row"> <i class="col-sm-1 text-right fa fa-hand-o-right" style="font-size:20px"></i>
    <p class="col-sm-11">The Learning Priority Fluency suites provides the reader and teacher with simple authentic tasks to measure fluency, accuracy, prosody, and basic comprehension. </p>
  </div>
  <div class="row"> <i class="col-sm-1 text-right fa fa-hand-o-right" style="font-size:20px"></i>
    <p class="col-sm-11">It also allows students tools to self-assess, or peer-assess as well as do immediate feedback word work that is so critical to developing higher level understanding of the content in texts. </p>
  </div>
  <div class="row"> <i class="col-sm-1 text-right fa fa-hand-o-right" style="font-size:20px"></i>
    <p class="col-sm-11">Students enjoy the privacy of their fluency suite work as well as the immediate auditory and visual feedback they get when they listen to themselves reading. </p>
  </div>
  <div class="row"> <i class="col-sm-1 text-right fa fa-hand-o-right" style="font-size:20px"></i>
    <p class="col-sm-11">The design of the process makes sense to them universally and offers them simple steps to better fluency which leads to opportunities for greater meaning making. </p>
  </div>
  <hr>
  <div class="row">
    <div class="col-sm-5 text-center"><img src="images/Aboutus/Suite/Picture1.JPG"></div>
    <div class="col-sm-7">
      <div class="row">
        <div class="col-sm-10">
          <div class="text-center"><img src="images/Aboutus/Suite/Picture2.JPG"></div>
        </div>
        <div class="col-sm-10">
          <p>Learning priority designs leverage software to save teacher time so they can spend their time on teaching. </p>
        </div>
      </div>
    </div>
  </div>
  <hr>
  <div class="row">
    <div class="col-sm-5 text-center"><img src="images/Aboutus/Suite/Picture3.JPG"></div>
    <div class="col-sm-7">
      <div class="row">
        <div class="col-sm-10">
          <div class="text-center"><img src="images/Aboutus/Suite/Picture4.JPG"></div>
        </div>
        <div class="col-sm-10">
          <p> Students of every age and at every level engage with visual text and their own auditory production to make meaning of texts at multiple levels.</p>
        </div>
      </div>
    </div>
  </div>
  <hr>
  <div class="row">
    <div class="col-sm-7 text-center"><img src="images/Aboutus/Suite/Picture5.JPG"></div>
    <div class="col-sm-5">
      <p><b><br>
        The Reading Register is LP’s newest entry into the Reading Realm. </b></p>
      <br>
      <p>It brings 21st century thinking to independent reading programs by offering readers choice, meaning making and connections to others through their own creative productions following reading. </p>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-1"></div>
    <div class="col-sm-3 text-center"><img src="images/Aboutus/Suite/Picture6.PNG"></div>
    <div class="col-sm-7">
      <p><br>
        Learning Priority makes learning the priority.</p>
      <p>LP designs software that works at the intersections of learner, teacher, parent, and peers. </p>
      <p>Learning Priority never forgets that learning is socially constructed. </p>
    </div>
  </div>
</div>
</body>
</html>