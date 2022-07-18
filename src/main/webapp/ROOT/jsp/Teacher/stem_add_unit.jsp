<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
 $(function() {
	  var stemUnitId = parseInt($('#stemUnitId').val());
	  var mode = $('#mode').val();
	  if(stemUnitId > 0){
		  var currentTab = $('.is-active').attr('id');
		   $("input[type=text], textarea").bind("focusin", function() {
			  var currentElement = $(this).attr('id').val();
			  var updatedVal = $(this).val();
			  synchronizeTab(stemUnitId, currentTab, currentElement, 'Yes', updatedVal, 'focusin');
			});
		    $("input[type=text], textarea").bind("focusout", function() {
		    	var currentElement = $(this).attr('id').val();
				var updatedVal = $(this).val();
			    synchronizeTab(stemUnitId, currentTab, currentElement, 'Yes', updatedVal, 'focusout');
			});
	    }
	    synchronizeTab(stemUnitId, currentTab, '', 'Yes', '', 'focusin');
 }); 
 $(window).keydown(function(event){
     if(event.keyCode == 13) {
       event.preventDefault();
       return false;
     }
   });
</script>
<form action="" id="createUnitForm">
  <table width="100%" style="color:black;margin-right: auto;margin-left: 25em;width: 1250px; overflow-y: scroll; overflow-x: hidden;">
      <tr>
		  <td width="15%" class="label" style="vertical-align:middle;text-align:left;height:60px;color:black;" > Unit Name &nbsp;&nbsp; :</td>
		  <td width="60%" style="vertical-align:middle;"><input type="text" id="stemUnitName" name="stemUnitName" class="textBox" value="${stemUnit.stemUnitName}" onblur="${mode == 'edit' ? 'autoSaveStemUnits(this)' : ''}" onkeyup="validateUnitName()"></input></td>
	  </tr>
	  <tr>
		 <td width="15%" class="label" style="vertical-align:middle;text-align:left;height:40px;color:black;" > Unit Description &nbsp;&nbsp; :</td>
		 <td width="60%" style="vertical-align:middle;"><textarea rows="3" cols="60" id="stemUnitDesc" name="stemUnitDesc" style="overflow: auto;" onblur="${mode == 'edit' ? 'autoSaveStemUnits(this)' : ''}">${stemUnit.stemUnitDesc}</textarea></td>
	  </tr>
	  <tr>
	  <td width="15%" class="label" style="vertical-align:middle;text-align:left;height:40px;color:black;" > Do you want to share the unit? &nbsp;&nbsp; </td>
	  <td width="60%" style="vertical-align:middle;color:black;" class="label">
	   <c:set var="chkStat" value="checked" />
	   <c:set var="chkStatus" value="" />
	   <c:if test="${stemUnit.isShared eq 'Yes'}">
	    <c:set var="chkStatus" value="checked" />
	    <c:set var="chkStat" value="" />
	   </c:if>
	   <input type="radio" name="shareId" value="Yes" ${chkStatus} onchange="${mode == 'edit' ? 'autoSaveStemUnits(this)' : ''}">Yes &nbsp;&nbsp;
	   <input type="radio" name="shareId" value="No" ${chkStat} onchange="${mode == 'edit' ? 'autoSaveStemUnits(this)' : ''}">No 
	  </td>
	  </tr>
	   <c:if test="${srcStemUnit != ''}">
       <tr>
		  <td width="15%" class="label" style="vertical-align:middle;text-align:left;height:40px;color:black;" > Share URL Links &nbsp;&nbsp; :</td>
		  <td width="60%" style="vertical-align:middle;"><textarea rows="3" cols="80" id="urlLinks" name="urlLinks" onblur="${mode == 'edit' ? 'autoSaveStemUnits(this)' : ''}">${stemUnit.urlLinks}</textarea></td>
	   </tr>
	   <tr>
	  		<td width="30%" height="40" align="left" valign="middle" style="padding-left: 11em;" colspan="2">
	    	   	<div class="button_green" align="right" onclick="window.open('http://www.google.com')">Google Search</div>
	      	</td>
	   </tr>
     </c:if>
	  <tr><td colspan="2" >
	   <table width="80%" align="center" id="tab1submitDiv" ${mode == 'edit' ? 'style="display:none;"' : 'style="display:block;"'} >
			<tr>
                <td width="35%" height="10" align="right" valign="middle"> 
               	<div class="button_green" align="right" onclick="saveStemUnits()">Submit Changes</div> 
                </td>
                <td width="30%" height="10" align="left" valign="middle" style="padding-left: 2em;">
                	 <div class="button_green" align="right" onclick="$('#createUnitForm')[0].reset(); return false;">Clear</div>
                </td>
            	</tr>
         		</table>
	  </td>
	 <td ${pathId == 1 ? 'style="display:block;"' : 'style="display:none;"'}>	  	
	  	<div class="button_green" align="right" style="margin:5px 15px 5px -180px;height:2em;width:10em;font-size: 1em;padding: 0.2px 25px;" onclick="getIpalResources('childWindow')">iPal Resources</div> 
	  </td>
	  </tr>
	  <tr width='100%'>
			<td colspan="2"><c:if test="${mode == 'edit'}">
				<table width='100%'>
					<tr>
						<td id="printBtn" class="text" align="center"><span
							class="button_white"
							style="font-size: 24px; color: black; box-shadow: -4px 4px 3px #43757b;"
							onclick="downloadContent('print')"> <i
								class="fa fa-print"></i></span>
						</td>
						<td id="pdfBtn" class="text" align="center"><span
							class="button_white"
							style="font-size:24px;color:red;box-shadow: -4px 4px 3px #43757b;"
							onclick="downloadContent('pdf')"> <i
								class="fa fa-file-pdf-o"></i></span>
						</td>
						<td colspan="2" width='100%'><div
								id="printContent" style="width: 70%;"></div></td>
					</tr>
				</table>
			</c:if></td>
		</tr>
  </table>
  <div class="active-users-div" id="tab0-active-users-div"></div>
  <div id="iPalDailog"  style="display:none;background : #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;" title="">
  		<div id ="iPalDiv"></div>
  	</div>
  </form>
