<!DOCTYPE html>
<!-- xlsx.js (C) 2013-present  SheetJS http://sheetjs.com -->
<!-- vim: set ts=2: -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Upload Formative Assessment</title>
<style>
#drop {
	border:2px dashed #bbb;
	-moz-border-radius:5px;
	-webkit-border-radius:5px;
	border-radius:5px;
	padding:25px;
	text-align:center;
	font:20pt bold, "Vollkorn";
	color:#bbb
}
#b64data {
	width:100%;
}
</style>
<script>
function process_wb(wb) {
	var jsonContent = JSON.stringify(to_json(wb), 2, 2);
	$("#loading-div-background").show();
	  $.ajax({
            url: "uploadFormativeContent.htm",
            type: 'POST', 
            dataType: 'json', 
            data: jsonContent, 
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function(data) { 
            	systemMessage(data.status);
            	$("#loading-div-background").hide()
            	if(data.isEmpty == false){
            		$('#logDiv').show();
            		var parent = $('embed#showLog').parent();
            		var newEmbed = "<embed src='loadDirectUserFile.htm?usersFilePath="+data.path+"' id='showLog' width='100%' height='350' align='center'>";
            		$('embed#showLog').remove();
            		parent.append(newEmbed);
            		$("#logDownload").attr("href", "downloadFile.htm?filePath="+data.path);
            	}
        		document.getElementById("xlf").value = "";
            },
            error:function(data,status,er) { 
            }
        });
	if(typeof console !== 'undefined') {};
}
</script>
</head>
<body>
<%@include file="Layout.jsp"%>
<div class="container-fluid">
  <div class="row">
    <div class="col-md-3">
      <%@include file="Menuinfo.jsp"%>
    </div>
    <div class="col-md-9">
      <div class="row">
        <div class="col-md-12 sub-title title-border">
          <div id="title" style="min-height:15px"> Formative Assessment Upload :</div>
          <div style="text-align: right; margin-top:-28px;"> <a href="gotoDashboard.htm" type="button" class="btn btn-sm btn-primary"> Home </a> </div>
        </div>
      </div>
      <div class="col-md-12 text-center" id= "returnMessage" style="color: red"> <font color="blue">
        <label style="visibility:visible;">${status}</label>
        </font> </div>
      <div class="row tblbgcolor">
        <div class="col-md-2"></div>
        <div class="col-md-8"><br>
          <input type="checkbox" name="useworker" checked style="display:none;">
          <input type="checkbox" name="xferable" checked style="display:none;">
          <input type="checkbox" name="userabs" checked style="display:none;">
          <div class="col-md-12" style="padding-bottom:15px;">
            <div class="form-group">
              <div class="col-md-4">File to upload:</div>
              <div class="col-md-6">
                <input type="file" name="xlfile" id="xlf" />
              </div>
            </div>
          </div>
          <div class="col-md-12 text-center" id="logDiv" style="display:none;width:60%;height:auto;padding-left:25em;">
            <table style="width:100%" align="center">
              <tr>
                <td with="100%" align="center"><embed src="" id="showLog" width="100%" height="550" alt="log file" align="middle"></td>
              </tr>
              <tr>
                <td with="100%" align="left" style="padding-left:3em;" class="txtStyle"><a href="" id="logDownload">Download Log File</a></td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<!-- uncomment the next line here and in xlsxworker.js for encoding support --> 
<script type="text/javascript" src="resources/javascript/js-xlsx/cpexcel.js"></script> 
<script type="text/javascript" src="resources/javascript/js-xlsx/shim.js"></script> 
<script type="text/javascript" src="resources/javascript/js-xlsx/jszip.js"></script> 
<script type="text/javascript" src="resources/javascript/js-xlsx/xlsx.js"></script> 
<script type="text/javascript" src="resources/javascript/js-xlsx/excel_upload.js"></script>
</body>
</html>
