<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
	/* .container {
	    width:100%;
	    border:1px solid #d3d3d3;
	}
	.container div {
	    width:100%;
	}
	.container .header {
	    background: -webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00c4dd), color-stop(1,#01cbe7) );
	    padding: 2px;
	    cursor: pointer;
	    font-weight: bold;
	    color:white;
	    text-shadow:0 1px 1px rgb(28, 28, 28);
	}
	.container .content {
	    display: none;
	    padding : 5px;
	} */
	.RSDDcontainer {
	transition: margin 200ms cubic-bezier(0.17, 0.04, 0.03, 0.94);
	padding: 0em 0em;
	background: #dcebee; /* -webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00D7F0), color-stop(1,#0AA6BB) ); */
	padding-top: 0.1em;
	box-shadow:1px 1px 6px 1px #60acba;
	/* border-bottom: 10px solid #007094; */
   }
   .RSDDcontainer .header {
	    background: -webkit-gradient( linear, left top, left bottom, color-stop(0.05,#00c4dd), color-stop(1,#01cbe7) );
	    padding: 3px;
	    cursor: pointer;
	    font-weight: 500;
	    color: #0d0d0d;
	    font-size:15px;
	    text-shadow:0 1px 2px rgb(140, 156, 158);
	}
	.RSDDcontainer .content {
	    display: none;
	    padding : 5px;
	} 
	.headerDiv{
		background-color: #004d71;
		padding: 15px 15px 15px 15px;
		align: center;
		color: #FFFFFF;
		font-weight:bold;
		border-top-left-radius: 5px;
		border-top-right-radius: 5px;
		font-family:"proxima-nova", "Open Sans","Gill Sans MT","Gill Sans",Corbel,Arial,sans-serif;
		font-size:14px;
		text-shadow: 0 1px 1px rgb(28, 28, 28);
	}
</style>

<script type="text/javascript">
	function expandCollaps(index, name) {	
	    $header = $("#header"+index);
	    //getting the next element
	    $content = $header.next();
	    //open up the content needed - toggle the slide- if visible, slide up, if not slidedown.
	    $content.slideToggle(500, function () {
	        //execute this after slideToggle is done
	        //change text of header based on visibility of content div
	        $header.text(function () {
	            //change text based on condition
	            return name;
	        });
	    });
	
	}
</script>
<table style="width: 50%"  class="title-pad">
	<tr> 
		<td align="center">
			<div align="center" style="padding-top: 1em">		 
				 <c:choose>
				 	<c:when test="${fn:length(studentRRDDFiles) eq 0}"><div class="status-message">No Data Available</div></c:when>
					<c:otherwise> 
								<div class="headerDiv">List of Student Files</div>	
								<c:forEach items="${studentRRDDFiles}" var="student" varStatus="index">
									<div class="RSDDcontainer"> 
										<div class="header" onclick="expandCollaps(${index.index}, '${student.userRegistration.firstName} ${student.userRegistration.lastName}')" id="header${index.index}" ><span> ${student.userRegistration.firstName} ${student.userRegistration.lastName}</span></div>
										<div class="content">
											<ul>												
											 <c:choose>
											 	<c:when test="${fn:length(student.rsFiles) eq 0}"><div class="status-message">No Files available</div></c:when>
												<c:otherwise>          
												<c:forEach items="${student.rsFiles}" var="file">
													<li>  
														<a href="viewPDForImage.htm?filePath=${file.filePath}" target="_blank" >${file.fileName}</a>
														<a href="downloadFile.htm?filePath=${file.filePath}" >Download</a>   
													</li>
												</c:forEach>
												</c:otherwise>
											</c:choose>
											</ul>
			    						</div>
			    					</div>
								</c:forEach>							
					</c:otherwise>
				</c:choose>
			</div>
		</td>
	</tr>
</table>