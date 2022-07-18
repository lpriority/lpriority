<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="resources/javascript/canvasjs/canvasjs.min.js"></script>
<script type="text/javascript" src="resources/javascript/TeacherJs/whole_class_iol_grade.js"></script>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
	
<style type="text/css">
/* .ui-dialog>.ui-widget-header {
	background: #94B8FF;
} */

.ui-dialog>.ui-widget-content {
	background: white;
}
.golLink{
 color:blue;
}

.listStrateg{
   color: #041b2d;
   width: 100%;
   height: 40px;
   padding: 1px 5px;
   font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
   font-size: 15px;
   font-weight:500;
   vertical-align:middle;
   vertical-align: middle;
   overflow: hidden;
  background: linear-gradient(30deg,#08d6ef 30%, #08d6ef 30%);
   /*background: rgb(220,220,220);
   color: #fff;
  /* text-shadow: 0 1px 0 rgba(0, 0, 0, 0.4);
   /* background: linear-gradient(to bottom,#efefef 0%,#f4f4f4 2%,#f6f6f6 4%,whitesmoke 10%,#f8f8f8 22%,#fcfcfc 80%,#fcfcfc 88%,#fafafa 90%,#fcfcfc 92%,#f9f9f9 94%,#fcfcfc 96%,#fcfcfc 98%,#f6f6f6 100%);
   border: 0.5px solid rgb(191, 191, 191);
   border-radius: 2px;
   position: relative;
  /*  box-shadow: 0px 0px 5px 1px #e8fcff; */
  /* border-top-left-radius: 3px;
   border-top-right-radius: 3px;
   border-bottom-left-radius: 3px;
   border-bottom-right-radius: 3px; */
   outline:none;
   white-space: nowrap;
}
select {
  text-align: center;
  text-align-last: center;
    
 }
option {
  text-align: left;
  font-size:14px; 
  font-weight:500;
 }
</style>
</head>
<body>
<table width="100%" align="center">
<tr><td width="100%">
<select name="legendCriteriaId" class="listStrateg" id="legendCriteriaId" onChange="getSubCriteriasForWholeClass()" required="required">  
 											<option value="select" selected>Select Legend Criteria</option> 
  												<c:forEach items="${legendCriterias}" var="lc">  
  													<%-- <c:set var="stat" value="" />   
    											    <c:set var="stat" value="selected" />  --%>    
     												<option value="${lc.legendCriteriaId}" ${stat}>${lc.legendCriteriaName}</option>  
												</c:forEach>   
  								</select> 

</table>
<div id="subcriterias" />
</body>
</html>