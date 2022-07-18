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
<!-- <script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
<script src="resources/javascript/jquery/jquery-ui/jquery-ui.js"></script>
<link href="resources/javascript/jquery/jquery-ui/themes/start/jquery-ui.css" rel="stylesheet" type="text/css"> -->
<script type="text/javascript" src="resources/javascript/common/img_viewer.js"></script>
<link rel="stylesheet" href="resources/css/tooltip.css" />
<script>
/*  $(function() {
   $( "#rows" ).accordion({
      heightStyle: "content", // Set accordin height based on content
      collapsible: true,
      active: false,
      activate:function(event, ui ){
  		setFooterHeight();
  		
    }
    });   
}); */
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
			  } 
 		  });

		  function getSortList(i, elem){
		    var txt = $("td", elem).eq(th_index).text();
		    $(elem).attr("data-sortval", txt);
		  }
		  function sortAsc(a, b){
		    var aData = $(a).attr("data-sortval"),
		        bData = $(b).attr("data-sortval");
		    if(th_sortType==="int"){ 
		      return +bData < +aData ? 1 : -1; // Integer
		    }else{   
		      return  bData <  aData ? 1 : -1; // String or else
		    }
		  }
		  function sortDesc(a, b){
		    var aData = $(a).attr("data-sortval"),
		        bData = $(b).attr("data-sortval");
		    if(th_sortType==="int"){ 
		      return +bData > +aData ? 1 : -1; // Integer
		    }else{   
		      return  bData >  aData ? 1 : -1; // String or else
		    }
		  }
		  
		  //header sort
		  table.on("click", "th", function() {

		    //toggle the sorting direction
		    $(this).data('sort-direction', ( $(this).data('sort-direction') == 'ASC' ? 'DESC' : 'ASC'));
		    var dir = $(this).data('sort-direction');
		    
		    th_sortType = $(this).data('sort'); //get "int" or "string" from data-sort on th
		    th_index = $(this).index();

		    tbody = table.find('tbody').each(getSortList);

		    table.find('.sort-indicator').removeClass('sort-desc').removeClass('sort-asc');
		    
		    if ( dir == "ASC") {
		        $(this).find('.sort-indicator').addClass('sort-asc'); //css class for ASC arrow
		        tbody.sort(sortAsc).detach().appendTo(table); //sort table
		    } else {
		        $(this).find('.sort-indicator').addClass('sort-desc'); //css class for DESC arrow
		        tbody.sort(sortDesc).detach().appendTo(table);  //sort table
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

 /*$(document).ready(function(){	  
	  /* 1. Visualizing things on Hover - See next part for action on click */
/*	  $('#stars li').on('mouseover', function(){
	    var onStar = parseInt($(this).data('value'), 10); // The star currently mouse on
	   
	    // Now highlight all the stars that's not after the current hovered star
	    $(this).parent().children('li.star').each(function(e){
	      if (e < onStar) {
	        $(this).addClass('hover');
	      }
	      else {
	        $(this).removeClass('hover');
	      }
	    });
	    
	  }).on('mouseout', function(){
	    $(this).parent().children('li.star').each(function(e){
	      $(this).removeClass('hover');
	    });
	  }); 
	  
	  /* 2. Action to perform on click */
/*	  $('#stars li').on('click', function(){
	    var onStar = parseInt($(this).data('value'), 10); // The star currently selected
	    var stars = $(this).parent().children('li.star');
	   
	    for (i = 0; i < stars.length; i++) {
	      $(stars[i]).removeClass('selected');
	    }
	    
	    for (i = 0; i < onStar; i++) {
	      $(stars[i]).addClass('selected');
	    }
	    
	    // JUST RESPONSE (Not needed)
	    var ratingValue = parseInt($('#stars li.selected').last().data('value'), 10);
	    var msg = "";
	    if(ratingValue){
	    	if(ratingValue > 0){
	    		$('#rating').val(ratingValue);
	    	}
	    }    
	  });
	  var rating = parseInt($('#rating').val());	
		setSelectedStarts(rating)
	}); */

function setSelectedStarts(titleId,regId, val){
	$('#stars_'+titleId+'_'+regId+'  li').each(function (i) {
		  if(i < val)
	       $(this).addClass('selected');
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
</style>
</head>
<body>
<input type="hidden" name="usersFilePath" id="usersFilePath" value="${usersFilePath}" />
<input type="hidden" name="user" id="user" value="${user}" />
<input type="hidden" name="gradeId" id="gradeId" value="${gradeId}" />
<c:set var="studentId" value="${student.studentId}" />
<%-- <table border="0" cellspacing="0" cellpadding="0" align="right">
	<tbody>
		<tr>
			<td>
				<ul class="button-group">
					<li><a href="studentReadingRegister.htm" class="${(divId == 'Reading Register' )?'buttonSelect leftPill':'button leftPill'}">Reading Register</a></li>
					<li><a href="returnedBooks.htm" class="${(divId =='Returned Books')?'buttonSelect':'button'}">Returned Books</a></li>
					<li><a href="personalReadingRegister.htm" class="${(divId =='Personal Reading Register')?'buttonSelect rightPill':'button rightPill'}">PRR</a></li>
				</ul>
			</td>					
		</tr>
	</tbody>
</table> --%>
<table width="100%">
	<!-- <tr>
		<td colspan="" width="100%"> 
			 <table width="100%" class="title-pad" border="0">
				<tr>
					<td class="sub-title title-border" height="40" align="left" ><div id="title">Returned Books</div></td>
				</tr>
			</table>
		</td>
	</tr> -->
	<tr><td><br></td><tr>
	<c:choose>
<c:when test="${fn:length(readRegMasterLt) gt 0}">
   <tr><td align="center">
	    <div style="${isNew == 'true' ? 'display:none;':''}" id="bankDetails">
		 	<%@include file="returned_book_details.jsp"%>
		</div>
	</td></tr>
</c:when>
<c:otherwise>
<tr><td align="center">
	    <div>
		 	 <span class="status-message">No Returned books</span>
		</div>
	</td></tr>
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
