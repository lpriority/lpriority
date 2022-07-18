<script type="text/javascript" src="resources/javascript/AppAdminJs.js"></script>
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
          <div id="title" style="min-height:15px">School Administrator</div>
          <div style="text-align: right; margin-top:-28px;"> <a href="addUserRegistration.htm" type="button" class="btn btn-sm btn-primary"> Add </a> <a href="appManagerHomePage.htm" type="button" class="btn btn-sm btn-danger"> Cancel </a> </div>
        </div>
        <div class="col-md-12 text-center" id= "message" style="color: red">
          <c:out value="${returnMessage}">
          </c:out>
        </div>
        <div class="col-md-12">
          <table id="schoolInfo" border="0" cellpadding="0" cellspacing="0" class="pretty" >
            <thead>
              <tr>
                <th>School Name</th>
                <th>Email Id</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>&nbsp;</th>
				<th>&nbsp;</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${urList}" var="ul">
                <tr>
                  <td>${ul.school.schoolName}</td>
                  <td>${ul.emailId}</td>
                  <td>${ul.firstName}</td>
                  <td>${ul.lastName}</td>
                  <td><a data-toggle="tooltip" title="EDIT" style="color:green; font-size: 1.4em;" href="editUserRegistration.htm?id=${ul.regId}"><i class="fa fa-pencil-square-o fa-3 green" aria-hidden="true"></i></a></td>
                  <td><a data-toggle="tooltip" title="DELETE" style="color:#d60404; font-size: 1.4em;" href="#" onclick="deleteSchoolAdmin(${ul.regId})"><i class="fa fa-trash fa-3 red" aria-hidden="true"></i></a></td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>
