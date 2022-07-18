<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Welcome to The Learning Priority Inc | Lighthouse Schools</title>
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
    <div class="col-sm-12 text-right" style="margin-top:-30px;"> <a class="urllink" href="download.htm?fileName=LP Presentation.pptx" download><i class="fa fa-download" aria-hidden="true" style="font-size: 17px;color:#8e1400;"><span style="font-size: 13px;color:#8e1400;padding:5px;font-weight:bold;">Download</span></i></a> </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">Learning Priority Lighthouse Schools</span>
      <div class="row">
        <div class="col-md-12">
          <div class="text-center"><img src="images/Aboutus/Presentation/Picture1.PNG"></div>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">LEARNING PRIORITY MAKES LEARNING #1</span>
      <div class="row">
        <div class="col-sm-7">
          <p class="subhead">LP helps schools & districts..</p>
          <div class="row"> <i class="col-sm-2 text-right fa fa-hand-o-right" style="font-size:20px"></i>
            <p class="col-sm-10">Make learning the # 1 priority.</p>
          </div>
          <div class="row"> <i class="col-sm-2 text-right fa fa-hand-o-right" style="font-size:20px"></i>
            <p class="col-sm-10">Helps to achieve their learning goals & meet their GREATEST CHALLENGES.</p>
          </div>
        </div>
        <div class="col-sm-5">
          <div class="text-center"><img src="images/Aboutus/Presentation/Picture2.PNG"></div>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">LP technology tools are……</span>
      <div class="row">
        <div class="col-sm-7">
          <p class="subhead">..designed by and for educators</p>
        </div>
        <div class="col-sm-5">
          <div class="text-center"><img src="images/Aboutus/Presentation/Picture3.PNG"></div>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">LP designers ……</span>
      <div class="row">
        <div class="col-sm-7">
          <p class="subhead">Honor & Respect </p>
          <p>The work of Teachers and so we work with them to make the tool better and more effective. </p>
        </div>
        <div class="col-sm-5">
          <div class="text-center"><img src="images/Aboutus/Presentation/Picture4.PNG"></div>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">LEARNING PRIORITY OFFERS & DEVELOPS</span>
      <div class="row">
        <p class="col-sm-6 col-md-offset-1"><br />
          Learning Management Systems</p>
        <p class="col-sm-2"><br />
          (LMS)</p>
      </div>
      <div class="row">
        <p class="col-sm-6 col-md-offset-1">Student Information Systems</p>
        <p class="col-sm-2">(SIS)</p>
      </div>
      <div class="row">
        <p class="col-sm-6 col-md-offset-1">Learning Programs</p>
        <p class="col-sm-2">(Apps)</p>
      </div>
      <div class="row">
        <p class="col-sm-6 col-md-offset-1">Custom System Designs</p>
        <p class="col-sm-2">(CSD)</p>
      </div>
      <div class="row">
        <p class="col-sm-6 col-md-offset-1">Learning Task Force </p>
        <p class="col-sm-2">(LTF)</p>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">LP designers are expert navigators of ..... </span>
      <div class="row">
        <div class="col-sm-7">
          <p class="subhead">School Culture</p>
        </div>
        <div class="col-sm-5">
          <div class="text-center"><img src="images/Aboutus/Presentation/Picture5.PNG"></div>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">LEARNING PRIORITY</span>
      <div class="row">
        <div class="col-sm-7">
          <p><br>
            LP has achieved recent developments in partnership with a Lighthouse District, The Rio School District. </p>
        </div>
        <div class="col-sm-5">
          <div class="text-center"><img src="images/Aboutus/Presentation/Picture6.PNG"></div>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">LP/Rio Partnership has developed</span>
      <div class="row">
        <div class="col-sm-7">
          <p class="subhead">Teacher Designed tools for.. </p>
          <div class="row">
            <div class="col-sm-6"><b>Literacies</b></div>
            <div class="col-sm-6">
              <p>Reading, Writing</p>
              <p>Math & Technology</p>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6"><b>21st Century Learning</b></div>
            <div class="col-sm-6">
              <p>Communication</p>
              <p>Collaboration</p>
              <p>Critical Thinking</p>
              <p>Creativity & Caring</p>
            </div>
          </div>
        </div>
        <div class="col-sm-5">
          <div class="text-center"><img src="images/Aboutus/Presentation/Picture7.PNG"></div>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">LP has improved school’s performance in the following areas: </span>
      <div class="row">
        <div class="col-sm-7">
          <div class="row"><br>
            <i class="col-sm-2 text-right fa fa-hand-o-right" style="font-size:20px"></i>
            <p class="col-sm-10">Student Learning</p>
          </div>
          <div class="row"> <i class="col-sm-2 text-right fa fa-hand-o-right" style="font-size:20px"></i>
            <p class="col-sm-10">Teacher Effectiveness</p>
          </div>
          <div class="row"> <i class="col-sm-2 text-right fa fa-hand-o-right" style="font-size:20px"></i>
            <p class="col-sm-10">Systemwide Efficiency</p>
          </div>
          <div class="row"> <i class="col-sm-2 text-right fa fa-hand-o-right" style="font-size:20px"></i>
            <p class="col-sm-10">Time Management</p>
          </div>
        </div>
        <div class="col-sm-5">
          <div class="text-center"><img src="images/Aboutus/Presentation/Picture8.PNG"></div>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">LP’s Fundamental Aim is…. </span>
      <div class="row">
        <div class="col-sm-7">
          <p><br>
            to create efficient and effective computer-human interactions that facilitate  desired learning outcomes. </p>
        </div>
        <div class="col-sm-5">
          <div class="text-center"><img src="images/Aboutus/Presentation/Picture9.PNG"></div>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">LP creates…..</span>
      <div class="row">
        <div class="col-sm-7">
          <p><br>
            Transparent & Protected feedback systems for learners, parents, and educators.</p>
        </div>
        <div class="col-sm-5">
          <div class="text-center"><img src="images/Aboutus/Presentation/Picture10.PNG"></div>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">LP places…..</span>
      <div class="row">
        <div class="col-sm-7">
          <div class="row"><br>
            <i class="col-sm-2 text-right fa fa-hand-o-right" style="font-size:20px"></i>
            <p class="col-sm-10">humans at the core of learning by creating technology tools…...</p>
          </div>
          <div class="row"> <i class="col-sm-2 text-right fa fa-hand-o-right" style="font-size:20px"></i>
            <p class="col-sm-10">with humans for humans...</p>
          </div>
        </div>
        <div class="col-sm-5">
          <div class="text-center"><img src="images/Aboutus/Presentation/Picture11.PNG"></div>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">LP is redefining…….</span>
      <div class="row">
        <div class="col-sm-7">
          <p class="subhead">“BLENDED LEARNING”</p>
        </div>
        <div class="col-sm-5">
          <div class="text-center"><img src="images/Aboutus/Presentation/Picture12.PNG"></div>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">LP is redefining…….</span>
      <div class="row">
        <div class="col-sm-7">
          <p class="subhead">“21st Century Learning”</p>
        </div>
        <div class="col-sm-5">
          <div class="text-center"><img src="images/Aboutus/Presentation/Picture13.PNG"></div>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">LP creates</span>
      <div class="row">
        <div class="col-sm-7">
          <p class="subhead">“Simple to use” tools<br>
            for complex organizational problems.</p>
        </div>
        <div class="col-sm-5">
          <div class="text-center"><img src="images/Aboutus/Presentation/Picture14.PNG"></div>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">LP saves schools and Districts…..</span>
      <div class="row">
        <div class="col-sm-7">
          <p class="subhead">Time & Money ……</p>
          <p>So people can spend more time doing their most important work…...</p>
        </div>
        <div class="col-sm-5">
          <div class="text-center"><img src="images/Aboutus/Presentation/Picture15.PNG"></div>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">LP tools…….</span>
      <div class="row">
        <div class="col-sm-7">
          <p class="subhead">enhance connections between learners, parents, peers, teachers, and school.</p>
        </div>
        <div class="col-sm-5">
          <div class="text-center"><img src="images/Aboutus/Presentation/Picture16.PNG"></div>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-12"> <span class="subhead focustxt">LP is seeking Lighthouse Districts…..</span>
      <div class="row">
        <div class="col-sm-7">
          <div class="row"><br>
            <i class="col-sm-2 text-right fa fa-hand-o-right" style="font-size:20px"></i>
            <p class="col-sm-10">Generous Pilot Period</p>
          </div>
          <div class="row"> <i class="col-sm-2 text-right fa fa-hand-o-right" style="font-size:20px"></i>
            <p class="col-sm-10">Discounted Rates</p>
          </div>
          <div class="row"> <i class="col-sm-2 text-right fa fa-hand-o-right" style="font-size:20px"></i>
            <p class="col-sm-10">Collaborative Design Opportunities</p>
          </div>
        </div>
        <div class="col-sm-5">
          <div class="text-center"><img src="images/Aboutus/Presentation/Picture17.PNG"></div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>