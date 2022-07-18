<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>View Reading Register</title>
<link href="resources/css/style2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/Student/reading_register_student.js"></script>
<link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/javascript/common/img_viewer.js"></script>
<link rel="stylesheet" href="resources/css/tooltip.css" />
<script>

$(document).ready(function () {
	$('.jAccordionTable').each(accordionTable);
	function accordionTable(i,elem) {
		  var table = $(elem),
		      tbody = table.find('tbody'),
		      th_index = 0,
		      th_sortType = "string";

		  table.find('th').data("sort-direction","ASC");

		  //accordion on tbody > tr
		  tbody.find('tr:first').addClass('table-acc-header');
		  tbody.find('tr:last').addClass('table-acc-body');  

		  $('.table-acc-header').click(function(e) {
			  //console.log($(e.target).closest('tr').next('tr')[0].classList.contains("collapse"));
			 if($(e.target).closest('tr').next('tr')[0].classList.contains("collapse") == false){
				  console.log("inside");
				  $(this).next('.table-acc-body').addClass('collapse');
			  }else{
				  table.find('.table-acc-body').addClass('collapse');
				  $(this).next('.table-acc-body').removeClass('collapse');
				  setFooterHeight();
			  } 
 		  });		
		}
	
	//tooltipe
    $( document ).tooltip({
	    position: {
	      my: "center bottom-20",
	      at: "center top",
	      using: function( position, feedback ) {
	        $( this ).css( position );
	        $( "<div>" )
	          .addClass( "arrow" )
	          .addClass( feedback.vertical )
	          .addClass( feedback.horizontal )
	          .appendTo( this );
	      }
	    }
	  });
});

$(function(){
	  $("#bookName").keyup(function (e) {
	    if (e.which == 13) {
	    	var sortBy = $("#sortBy").val();
	    	var sortingOrder = $("#sortingOrder").val();
	    	searchBook(bookName, sortBy, sortingOrder);
	    	
	    }
	  });
	});

function setSelectedStarts(titleId,regId, val){
	$('#stars_'+titleId+'_'+regId+'  li').each(function (i) {
		  if(i < val)
	       $(this).addClass('selected');
	 });
}
function gotoPage(pageNumber, sortBy, sortingOrder){
	pageNum = pageNumber; 
	var searchBy = $("#bookName").val();
	$("#rr-loading-div-background").show();
	$.ajax({
		type : "GET",
		url : "gotoPage.htm",
		data : "pageId="+pageNumber +"&sortBy="+sortBy+"&sortingOrder="+sortingOrder+"&searchBy="+searchBy,
		success : function(response) {
			$("#contentDiv").empty();
			$("#contentDiv").append(response);	
			$("#rr-loading-div-background").hide();
		}
	});	
}
function sortByColumn(columnName, oldColumn, sortingOrder, pageNumber){
	if(oldColumn == columnName){		
		if(sortingOrder == 'ASC')
			sortingOrder = "DESC";
		else 
			sortingOrder = 'ASC';
	}
	else
		sortingOrder = 'ASC';
	gotoPage(pageNumber, columnName,sortingOrder);
	
}

function searchBook(bookName, sortBy, sortingOrder){
	$("#rr-loading-div-background").show();
	var searchBy = "";
	if(bookName != 'undefined')
		searchBy = bookName.value;	
	$.ajax({
		type : "GET",
		url : "gotoPage.htm",
		data : "pageId=1&sortBy="+sortBy+"&sortingOrder="+sortingOrder+"&searchBy="+ searchBy,
		success : function(response) {
			$("#contentDiv").empty();
			$("#contentDiv").append(response);	
			$("#rr-loading-div-background").hide();
			$("#bookName").focus();
		}
	});	
	
}
</script>
<style>
.table {width:100%; font-family:sans-serif; border-collapse:collapse; border:1px solid #d2d2d2;}
.table th { text-align:left; padding:4px 8px; font-size:90%; }
.table td { border-top:1px solid #d2d2d2; padding:4px 8px;}

.table tr:hover th { cursor:pointer; 
-webkit-user-select: none; 
-moz-user-select: none; 
-ms-user-select: none; 
user-select: none; 
}
.table tr:hover td { cursor:pointer; background:#f8f8f8; 
}

.collapse {display:none;}

.sort-indicator {  
  display: inline-block; width: 0; height: 0; 
  position:relative; top:-3px;
  border-top: 4px solid #798182;
  border-right: 4px solid transparent;
  border-left: 4px solid transparent;
  border-bottom: 0;
}
.sort-desc {
  border-top: 0;  
  border-right: 4px solid transparent;
  border-left: 4px solid transparent;
  border-bottom: 4px solid black;
}

.sort-asc {
  border-top: 4px solid black;
  border-right: 4px solid transparent;
  border-left: 4px solid transparent;
  border-bottom: 0;
}

.sr-only { 
  position: absolute; width: 1px; height: 1px; margin: -1px; padding: 0; border: 0; overflow: hidden;
  clip: rect(0,0,0,0);
}

.table-acc-header{
	background:linear-gradient(to bottom,#f9f9f9 7%, #ffffff 33%, #ffffff 48%, rgb(239, 239, 239) 94%);
	height:40px;
	border-bottom: 1px solid #cecece;
	cursor:pointer;
}

layout-table {
    display : table;
    clear : both;
    table-layout : fixed;
    width : 100%;
    color:#000000;
    text-shadow: 0 1px 1px rgb(204, 204, 204);
    font-weight: bold;
    font-size:12px;
    font-family:-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
    border-collapse: collapse;
}

layout-table:unresolved {
    empty-cells : show;
}

layout-header, layout-footer, layout-row {
    display : table-row;
    clear : both;   
    empty-cells : show;
    width : 100%;
}

layout-column { 
    display : table-column;
    float : left;
    width : 25%;
    min-width : 25%;
    text-align: center;
    empty-cells : show;
    box-sizing: border-box;
    padding : 1px 1px 1px 1px;
}
layout-header layout-column {
 	width : 10%;
    min-width : 10%;
    float:right;
}
layout-row {
display:flex;
align-items:center;
   border-top: 1px solid #dadada;
}

*:focus {outline:none !important}

/* Rating Star Widgets Style */
.rating-stars ul {
  list-style-type:none;
  padding:0;
  text-shadow:0 1px 2px rgb(154, 154, 154);
  -moz-user-select:none;
  -webkit-user-select:none;
}
.rating-stars ul > li.star {
  display:inline-block;
  
}
/* Idle State of the stars */
.rating-stars ul > li.star > i.fa {
  font-size:1.2em; /* Change the size of the stars */
  color:#ccc; /* Color on idle state */
}
/* Hover state of the stars */
.rating-stars ul > li.star.hover > i.fa {
  color:#97e3ec;
}
/* Selected state of the stars */
.rating-stars ul > li.star.selected > i.fa {
  color:#00b1c7;
}
/*     .pagination {
	  display: inline-block;
	}
	
	.pagination a {
	  color: black;
	  float: left;
	  padding: 8px 16px;
	  text-decoration: none;
	}
	 */
.pagination {
  display: inline-block;
}

.pagination a {
  color: black;
  float: left;
  padding: 8px 16px;
  text-decoration: none;
}

.pagination a.active {
  background-color: #0ACBE3;
  color: white;
  border-radius: 5px;
}

.pagination a:hover:not(.active) {
  background-color: #ddd;
  border-radius: 5px;
}
a.disabled {
   pointer-events: none;
   cursor: default;
}

#bookName{
  width: 250px;
  height: 25px;
}
</style>
</head>
<body>
<input type="hidden" name="usersFilePath" id="usersFilePath" value="${usersFilePath}" />
<input type="hidden" name="user" id="user" value="${user}" />
<input type="hidden" name="gradeId" id="gradeId" value="${gradeId}" />
<input type="hidden" name="sortBy" id="sortBy" value="${sortBy}" />
<input type="hidden" name="sortingOrder" id="sortingOrder" value="${sortingOrder}"/>
<table border="0" cellspacing="0" cellpadding="0" align="right">
	<tbody>
		<tr>
			<td>
				<ul class="button-group">
				    <li><a href="getRRReview.htm" class="${(divId =='rr_review')?'buttonSelect leftPill':'button leftPill'}"> Review Activity</a></li>
					<li><a href="getStudentReadingRegister.htm" class="${(divId == 'prr' )?'buttonSelect':'button'}">Scored Activity</a></li>	
					<li><a href="getStudentRRReports.htm" class="${(divId == 'rr_reports' )?'buttonSelect':'button'}">RR Report</a></li>	
					<li><a href="getTeacherReadingRegister.htm" class="${(divId == 'Reading Register' )?'buttonSelect':'button'}">Reading Register</a></li>				
					<li><a href="bookApproval.htm" class="${(divId =='book_approval')?'buttonSelect rightPill':'button rightPill'}">Book Approval</a></li>
				</ul>
			</td>					
		</tr>
	</tbody>
</table>
<table width="100%">
	<tr>
		<td colspan="" width="100%"> 
			 <table width="100%" class="title-pad" border="0">
				<tr>
					<td class="sub-title title-border" height="40" align="left" ><div id="title">Reading Register</div></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="float:left;padding-left:3.2cm;" align="left" width="90%">
		   <td style="padding-left:1em;float:center" width="10%">
		   	<input type="text" name="bookName" id="bookName" value="${searchBy}" placeholder="Search Book">
		   </td>
		    <td style="padding-left:2em;float:center" width="10%">		   
		  		<div class="fa fa-search fa-rotate-90" style="color:#008FCF;text-decoration:none;font-size:26px;" onclick="searchBook(bookName,'${sortBy}', '${sortingOrder}')"></div>
		   </td>
		    <td style="padding-right:1em;float:center">
		</td>
		</td>
	</tr>
	<c:choose>
<c:when test="${fn:length(readRegMasterLt) gt 0}">
   <tr><td style="padding-top: 1em" align="center" colspan="4">
	    <div id="contentDiv"> 
		 	<%@include file="/jsp/Student/reading_register_view.jsp"%>
		</div>
	</td></tr>
	<tr>
	<td align="center" colspan="4">
	<div class="pagination" align="center" id="pagination" style="${ (isNew == false || bookBankChecked == true)  ? '':'display:none;'}" >
		  <a href="#" onclick="gotoPage(${firstPage - 10 }, '${sortBy}', '${sortingOrder}')" <c:if test = "${firstPage - 10 < 0}">  class="disabled"</c:if> >&laquo;</a>
		  <c:forEach begin="${firstPage}" end="${lastPage}" var="page">		  
		  	<a href="#" <c:if test='${page== pageId}' >class="active"</c:if>
		  		 onclick ="gotoPage(${page }, '${sortBy}', '${sortingOrder}')">${page }</a>
		  </c:forEach>		  
		  <a <c:if test = "${lastPage+1 > maxPages}">  class="disabled"</c:if>  href="#" onclick="gotoPage(${lastPage+1}, '${sortBy}', '${sortingOrder}' )">&raquo;</a>
		</div>
		</td>
		</tr>
</c:when>
<c:otherwise>
	<tr><td align="center">
	    No Results founds
	</td></tr>
	<tr>
</c:otherwise>
</c:choose>
</table>
 
<div id="addBookDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"><iframe id='bookIframe' width="98%" height="95%" style="border-radius:1em;"></iframe></div>
<div id="reviewDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"><iframe id='reviewIframe' width="98%" height="95%" style="border-radius:1em;"></iframe></div>
<div id="retellDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"><iframe id='retellIframe' width="98%" height="95%" style="border-radius:1em;"></iframe></div>
<div id="pictureDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"><iframe id='pictureIframe' width="98%" height="95%" style="border-radius:1em;"></iframe></div>
<div id="createQuizDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"><iframe id='createQuizIframe' width="99%" height="98%" style="border-radius:1em;"></iframe></div>
<div id="takeQuizDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;"><iframe id='takeQuizIframe' width="99%" height="98%" style="border-radius:1em;"></iframe></div>
<div id="activityScoreDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;">
	<iframe id='activityScoreIframe' width="100%" height="95%" style="border-radius:1em;"></iframe>
</div>
</body>
</html>
