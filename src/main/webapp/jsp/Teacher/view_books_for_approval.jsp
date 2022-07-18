<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<script type="text/javascript"
	src="resources/javascript/Student/reading_register_student.js"></script>
<link href="resources/css/common_buttons.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/common_design.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="resources/javascript/common/img_viewer.js"></script>
<link rel="stylesheet" href="resources/css/tooltip.css" />
<style>
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

.pagination a:hover:not (.active ) {
	background-color: #ddd;
	border-radius: 5px;
}

a.disabled {
	pointer-events: none;
	cursor: default;
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

</style>
<script>

function sortByColumn(columnName, oldColumn, sortingOrder, pageNumber){
	if(oldColumn == columnName){		
		if(sortingOrder == 'ASC')
			sortingOrder = "DESC";
		else 
			sortingOrder = 'ASC';
	}
	else
		sortingOrder = 'ASC';
	gotoPages(pageNumber, columnName,sortingOrder);
	
}

function searchBook(bookName){
	$("#loading-div-background").show();
	var gradeId=$("#gradeId").val();
	var academicYear = $("#academicYear").val();
	var sortBy = $("#sortBy").val();
	var sortingOrder = $("#sortingOrder").val();
	var searchBy = "";
	if(bookName != 'undefined' && bookName !== '')
		searchBy = bookName.value;	
	if(gradeId!="select"){
	$.ajax({
		type : "GET",
		url : "gotoPages.htm",
		data : "pageId=1&sortBy="+sortBy+"&sortingOrder="+sortingOrder+"&searchBy="+encodeURIComponent(searchBy)+"&gradeId="+gradeId+ "&academicYearId=" + academicYear,
		success : function(response) {
			$("#contentDiv").empty();
			$("#contentDiv").append(response);	
			$("#loading-div-background").hide();
			$("#bookName").focus();
		}
	});	
  }else{
	  alert("Please Select Grade");
	  return false;
  }
	
}
$(function(){
	  $("#bookName").keyup(function (e) {
	    if (e.which == 13) {
	    	searchBook(bookName);	
	    	
	    }
	  });
	});
	
function selectAllLists(obj,tot){
	if($("#"+obj.id).is(':checked')){
		for(i=1;i < tot+1; i++){
		 $('#select'+i).prop('checked', true);
		}
	}else{
		for(i=1;i < tot+1; i++){
		 $('#select'+i).prop('checked', false);
		}
	}
}

function mergeBooks() {
	var readRedTitleIdArray = [];
	$.each($("input[name='checkboxSelect']:checked"), function(){            
		readRedTitleIdArray.push($(this).val());
    });
	if(readRedTitleIdArray.length > 1) {
		var len=readRedTitleIdArray.length;
		var authorArray = [];
		var pagesArray = [];
		
		for(var i=0; i<len; i++) {
			authorArray.push(document.getElementById("authors"+readRedTitleIdArray[i]).value.toLowerCase());
			pagesArray.push(document.getElementById("pages"+readRedTitleIdArray[i]).value);
		}
		
		function getUnique(array){
			var uniqueArray = []; 
		 	for(var value of array){
				if(uniqueArray.indexOf(value) === -1){
		        	uniqueArray.push(value);
				}
			}
			return uniqueArray;
		}
		
		var uniqueAuthors = getUnique(authorArray);
		var uniquepages = getUnique(pagesArray);
		
		if(uniqueAuthors.length > 1) {
			alert("Author names are different. The Books cannot be merged");
		} else if (uniquepages.length > 1) {
			alert("No of Pages are different. The Books cannot be merged");
		}else (confirm("Do you want to merge these books?",function(status) {
			if(status){
				$.ajax({
					type : "GET",
					url : "mergeBooks.htm",
					data : "readRedTitleIdArray="+readRedTitleIdArray,
					success : function(response) {
						//window.location.reload();
						alert(response.returnMessage);
						searchBook('');
						return false;
					}
				});	
			}
			else {
				return false;
			}
		}));
	} else {
		alert("Please select atleast two books to Merge");
	}
	
}



</script>
</head>
<body>
<input type="hidden" name="sortBy" id="sortBy" value="${sortBy}" />
<input type="hidden" name="sortingOrder" id="sortingOrder" value="${sortingOrder}"/>
	<table>	
		<tr>
			<td width="60%" align="center"> 
				<input type="text" name="bookName" id="bookName" value="${searchBy}" onchange="searchBook(bookName)" placeholder="Search Book" >&nbsp;&nbsp;&nbsp;&nbsp;
				<div class="fa fa-search fa-rotate-90"
					style="color: #008FCF; text-decoration: none; font-size: 25px; vertical-align: top"
					height="52" onclick="searchBook(bookName)">
				</div>
			</td>
			<td align="right" width="10%">
				<div class="button_green" id="btAdd" name="btAdd" height="52"
					style="vertical-align: bottom" width="50"
					onclick="openNewBookDialog(0)"
					title="Look in the Reading Register Library and see if the book you read is in there. Proceed to add the book if its not found.">Add a book
				</div>
			</td>
		</tr>
	</table>
	<c:set var="i" value="0" />
	<c:choose>
		<c:when test="${fn:length(readRegMasterLt) gt 0}">
			<input type='hidden' id='user' value='${user}' />
			<table id="booksToApprove" class="jAccordionTable des" width="90%" data-page-size="6" data-page-navigation=".pagination" data-limit-navigation="5">
				<tr>
					<td>
						<table class='Divheads' width="100%">
							<tr align='center' height='20'>
								<td width="10%">&nbsp;&nbsp; <input type="checkbox" class="checkbox-design" id="select_all" name="select_all" onClick='selectAllLists(this,${fn:length(readRegMasterLt)})'><label for="select_all" class="checkbox-label-design">Select All</label></td>
								<td width="9%" align='center' onClick="sortByColumn('createDate', '${sortBy}', '${sortingOrder}', ${pageId})">Date Created <i class="sort-indicator" aria-hidden="true"></i><span class="sr-only">Sort</span></td>
								<td width="20%" align='center' onClick="sortByColumn('bookTitle', '${sortBy}', '${sortingOrder}', ${pageId})">Book Name <i class="sort-indicator" aria-hidden="true"></i><span class="sr-only">Sort</span></td>
								<td width="13.5%" align='center' onclick="sortByColumn('author', '${sortBy}', '${sortingOrder}', ${pageId})">Author <i class="sort-indicator" aria-hidden="true"></i><span class="sr-only">Sort</span></td>
								<td width="8%" align='center' onclick="sortByColumn('numberOfPages', '${sortBy}', '${sortingOrder}', ${pageId})">No.Of Pages <i class="sort-indicator" aria-hidden="true"></i><span class="sr-only">Sort</span></td>
								<td width="13.5%" align='center' onclick="sortByColumn('userRegistration.firstName', '${sortBy}', '${sortingOrder}', ${pageId})">Created By<i class="sort-indicator" aria-hidden="true"></i><span class="sr-only">Sort</span></td>
								<td width="20%" align='center' onclick="sortByColumn('approved', '${sortBy}', '${sortingOrder}', ${pageId})">Approve<i class="sort-indicator" aria-hidden="true"></i><span class="sr-only">Sort</span></td>
								<td width="26%">Action</td>
							</tr>
						</table>
						<table class='DivContents' width="100%">
							<c:forEach items="${readRegMasterLt}" var="readRegMaster" varStatus="status">
								<c:set var="i" value="${i+1}" />
								<tr class='txtStyle' id="tr${readRegMaster.titleId}" align='center' height='30'>								
									<c:choose>
										<c:when test="${readRegMaster.approved != 'returned' }">
											<td width="10%" class='txtStyle'><input type="checkbox" class="checkbox-design" id="select${i}" name="checkboxSelect" value="${readRegMaster.titleId}">
												<label for="select${i}"  class="checkbox-label-design"></label>
											</td>
											<input type="hidden" width="20%" id="status${readRegMaster.titleId}" value ="${readRegMaster.approved}"/>
										</c:when> 
										<c:otherwise>
										  <td width="10%" class='txtStyle'>
												N/A
											</td>
										</c:otherwise>
									</c:choose>
									<td width="9%"><fmt:formatDate pattern="MM/dd/yyyy" var="createdDate" value="${readRegMaster.createDate}" />${createdDate}<input type="hidden" name="titleId${status.index}" id="titleId${status.index}" value="${readRegMaster.titleId}" /></td>
									<td width="20%" id="bookTitle${readRegMaster.titleId}">${readRegMaster.bookTitle}</td>
									<input type="hidden" width="20%" id="bookTitles${readRegMaster.titleId}" value ="${readRegMaster.bookTitle}"/>
									<td width="15%" id="author${readRegMaster.titleId}">${readRegMaster.author}</td>
									<input type="hidden" width="20%" id="authors${readRegMaster.titleId}" value ="${readRegMaster.author}"/>
									<td width="5%" id="numberOfPages${readRegMaster.titleId}">${readRegMaster.numberOfPages}</td>
									<input type="hidden" width="20%" id="pages${readRegMaster.titleId}" value ="${readRegMaster.numberOfPages}"/>
									<td width="15%">${readRegMaster.userRegistration.firstName}&nbsp;${readRegMaster.userRegistration.lastName}</td>
									<td width="20%"><input type=radio
										name="approve${readRegMaster.titleId}" value="approved"
										${readRegMaster.approved == 'approved' ? 'checked' : ''}
										${readRegMaster.approved != 'waiting' ? 'disabled': ''}
										onClick='bookApprove(${readRegMaster.titleId})'>Approve
										&nbsp; <input type=radio
										name="approve${readRegMaster.titleId}" value='returned'
										${readRegMaster.approved != 'waiting' ? 'disabled': ''}
										${readRegMaster.approved == 'returned' ? 'checked' : ''}
										onClick='bookApprove(${readRegMaster.titleId})'>Reject
									</td>
									<td width="26%"><i class='fa fa-pencil-square-o' aria-hidden='true'
										style='margin-left: -0.5em; cursor: hand; cursor: pointer; font-size: 22px; font-weight: bold; color: #185C64;'
										onclick="openNewBookDialog(${readRegMaster.titleId})"></i>
										<i class='fa fa-times' aria-hidden=true
										style='margin-left: 1em; cursor: hand; cursor: pointer; font-size: 18px; color: #c70000;'
										onclick="deleteBook(${readRegMaster.titleId},'${sortBy}', '${sortingOrder}', ${pageId})"></i>
									</td>
								</tr>
							</c:forEach>
							<tr>
								<td width="10%">
									<button class="button_green" align='center' class='txtStyle' onclick="mergeBooks();">Merge &nbsp;&nbsp;<i class="fa fa-code-fork" aria-hidden="true"></i></button>	 ${readRegMaster.bookTitle}							
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<table>
				<tbody class='DivContents'>
					<span class='status-message'> No data found </span>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
	<c:if test="${fn:length(readRegMasterLt) gt 0}">
	<table align="center">
			<tr>
			<td align="center">
				<div class="pagination" align="center" id="pagination">
					<a href="#" onclick="gotoPages(${firstPage - 10 },'${sortBy}','${sortingOrder}')" 
					<c:if test = "${firstPage - 10 < 0}">  class="disabled"</c:if>>&laquo;</a>
						
					<c:forEach begin="${firstPage}" end="${lastPage}" var="page">
						<a href="#" <c:if test='${page== pageId}' >class="active"</c:if>
							onclick="gotoPages(${page},'${sortBy}', '${sortingOrder}')">${page}</a>
					</c:forEach>
					<a
						<c:if test = "${lastPage+1 > maxPages}">  class="disabled"</c:if>
						href="#" onclick="gotoPages(${lastPage+1}, '${sortBy}', '${sortingOrder}')">&raquo;
					</a>
				</div>
			</td>
		</tr>

	</table>
	</c:if>
</body>
</html>