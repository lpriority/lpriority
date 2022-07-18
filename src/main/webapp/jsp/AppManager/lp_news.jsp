


<script type="text/javascript" src="resources/javascript/app_manager/delete_functions.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	$('#schoolInfo').DataTable({"iDisplayLength": 50, responsive: true});
	
	$('#message').fadeOut(4000);
});

</script>
<%@include file="Layout.jsp"%>

<div class="container-fluid">
  <div class="row">
    <div class="col-md-3">
      <%@include file="Menuinfo.jsp"%>
    </div>
    <div class="col-md-9">
      <div class="row">
        <div class="col-md-12 sub-title title-border">
          <div id="title" style="min-height:15px">LP News</div>
          <div style="text-align: right; margin-top:-28px;"> <a href="addLPNews.htm" type="button" class="btn btn-sm btn-primary"> Add </a> <a href="appManagerHomePage.htm" type="button" class="btn btn-sm btn-danger"> Cancel </a> </div>
        </div>
        <div class="col-md-12 text-center" id= "message" style="color: red">
          <c:out value="${returnMessage}">
          </c:out>
        </div>
        <div class="col-md-12">
          <table id="schoolInfo" border="0" cellpadding="0" cellspacing="0" class="pretty">
            <thead>
              <tr>
                <th>News Title</th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
				
				
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${lpNews}" var="news">
				<tr>
					<td>${news.title}</td>
					
					  <td width="7%"><a data-toggle="tooltip" title="EDIT" style="color:green; font-size: 1.4em;" href="editLPNews.htm?lpNewsId=${news.lpNewsId}"><i class="fa fa-pencil-square-o fa-3 green" aria-hidden="true"></i></a></td>
                  <td width="7%"><a data-toggle="tooltip" title="DELETE" style="color:#d60404; font-size: 1.4em;" href="#" onclick="deleteLPNews(${news.lpNewsId})"><i class="fa fa-trash fa-3 red" aria-hidden="true"></i></a></td>
              
			  
					
					
				</tr>
			  </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>





