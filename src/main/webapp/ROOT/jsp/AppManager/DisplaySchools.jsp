<script type="text/javascript" src="resources/javascript/app_manager/delete_functions.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	$('#schoolInfo').DataTable({"iDisplayLength": 50, responsive: true});
	$('#returnMessage').fadeOut(3000);
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
          <div id="title" style="min-height:15px">School</div>
          <div style="text-align: right; margin-top:-28px;"> <a href="addSchool.htm" type="button" class="btn btn-sm btn-primary"> Add </a> <a href="appManagerHomePage.htm" type="button" class="btn btn-sm btn-danger"> Cancel </a> </div>
        </div>
        <div class="col-md-12"> <font color="blue">
          <label id=returnMessage style="visibility: visible;">${status}</label>
          </font></div>
        <div class="col-md-12">
          <table id="schoolInfo" border="0" cellpadding="0" cellspacing="0" class="pretty" >
            <thead>
              <tr>
                <th>School Id</th>
                <th>School Name</th>
                <th>Type Of School</th>
                <th>Level Of School</th>
                <th>State</th>
                <th>City</th>
                <th>Phone Number</th>
                <th>Fax Number</th>
                <th>No Of Students</th>
                <th>&nbsp;</th>
                <th>&nbsp;</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${sList}" var="sl">
                <tr>
                  <td  style="text-align:center;">${sl.schoolId}</td>
                  <td>${sl.schoolName}</td>
                  <td>${sl.schoolType.schoolTypeName}</td>
                  <td>${sl.schoolLevel.schoolLevelName}</td>
                  <td>${sl.states.name}</td>
                  <td>${sl.city}</td>
                  <td>${sl.phoneNumber}</td>
                  <td>${sl.faxNumber}</td>
                  <td style="text-align:center;">${sl.noOfStudents}</td>
                  <td><a data-toggle="tooltip" title="EDIT" style="color:green; font-size: 1.4em;" href="editSchool.htm?id=${sl.schoolId}"><i class="fa fa-pencil-square-o fa-3 green" aria-hidden="true"></i></a></td>
                  <td><a data-toggle="tooltip" title="DELETE" style="color:#d60404; font-size: 1.4em;" href="#" onClick="deleteSchool()"><i class="fa fa-trash fa-3 red" aria-hidden="true"></i></a></td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>
