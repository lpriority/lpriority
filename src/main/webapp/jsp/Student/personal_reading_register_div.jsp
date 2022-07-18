<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet" href="resources/css/icons/font-awesome/css/font-awesome.min.css">
<script type="text/javascript">
	$(document).ready(function() {
		$('#returnMessage').fadeIn();
		$('#returnMessage').fadeOut(4000);
	
    $('#list').click(function(event){event.preventDefault();$('#products .item').addClass('list-group-item');});
    $('#grid').click(function(event){event.preventDefault();$('#products .item').removeClass('list-group-item');$('#products .item').addClass('grid-group-item');});
});
</script>

<style>
.glyphicon { margin-right: 5px; }
.thumbnail { margin-bottom: 20px; padding: 0px;	-webkit-border-radius: 0px; -moz-border-radius: 0px; border-radius: 0px; }
.pointer { cursor: pointer; }
.item.list-group-item { float: none; width: 100%; background-color: #fff; margin-bottom: 10px; }
.item.list-group-item:nth-of-type(odd):hover, .item.list-group-item:hover { background: #428bca; }
.list-group-image { margin-bottom: 0px; background-color: #08d6ef; background-image: linear-gradient(to right, #eff2f2, #08d6ef); }
.caption { min-height: 165px; background-color: #ffffff; background-image: linear-gradient(315deg, #ffffff 0%, #d7e1ec 74%); }
.item.list-group-item .list-group-image { margin-right: 10px; }
.item.list-group-item .thumbnail { margin-bottom: 0px; }
.item.list-group-item .caption { padding: 9px 9px 0px 9px; }
.item.list-group-item:nth-of-type(odd) { background: #eeeeee; }
.item.list-group-item:before, .item.list-group-item:after {	display: table; content: " "; }
.item.list-group-item img { float: left; }
.item.list-group-item:after { clear: both; }
.list-group-item-text { margin: 0 0 11px; }
.ion-chatbox-working { padding: 0px 6px;font-size: 35px !important; color: #5d9c13 !important;}
.faupload { padding: 6px !important;font-size: 38px !important; color: #273f4a !important; }
.ion-ios-mic { padding: 6px !important;font-size: 35px !important; color: #3f51b5 !important; }
</style>

<div>
  <c:if test="${fn:length(studentActivities) gt 0}">
    <div class="well well-sm text-left dboard">
      <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8 text-center">
          <c:if test="${classView == 'yes'}">
            <h4 style="color: #10646D;">Class View</h4>
          </c:if>
        </div>
        <div class="col-md-2 text-right">
          <div class="btn-group"> <a href="#" id="list" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-th-list"></span>List</a> <a href="#" id="grid" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-th"></span>Grid</a> </div>
        </div>
      </div>
    </div>
    <div id="products" class="row list-group pointer">
      <c:forEach items="${studentActivities}" var="studentActivity"
			varStatus="index">        
        <c:set var="iconStyle" value="" />
        <c:if test="${classView == 'yes'}">
          <c:set var="studentName" value="Student Name : ${studentActivity.student.userRegistration.firstName} ${studentActivity.student.userRegistration.lastName}">
          </c:set>
        </c:if>
        <fmt:formatDate pattern="MM/dd/yyyy" var="submitDate" value="${studentActivity.readRegMaster.createDate}" />
        <c:set var="titleContent" value='							
      <div class="row">
        <div class="col-xs-3 shead">Book Title</div>
        <div class="col-xs-9"><b>: </b>${studentActivity.readRegMaster.bookTitle} </div>
      </div>
      <div class="row">
        <div class="col-xs-3 shead">Author</div>
        <div class="col-xs-9"><b>: </b>${studentActivity.readRegMaster.author} </div>
      </div>
      <div class="row">
        <div class="col-xs-3 shead">Submit Date</div>
        <div class="col-xs-9"><b>: </b>${submitDate} </div>
      </div>
      <div class="row">
        <div class="col-xs-3 shead">No of Pages</div>
        <div class="col-xs-9"><b>: </b>${studentActivity.readRegMaster.numberOfPages} </div>
      </div>
      <div class="row">
        <div class="col-xs-3 shead">PointsEarned</div>
        <div class="col-xs-9"><b>: </b>${studentActivity.pointsEarned} (pages of the book = ${studentActivity.readRegMaster.readRegPageRange.range} x Activity Value = ${studentActivity.readRegActivity.activityValue } x Rubric value = ${studentActivity.readRegRubric.score} ) </div>
      </div>
	  '>
        </c:set>
        <c:choose>
          <c:when test="${studentActivity.readRegActivity.actitvity == 'review' }">
            <div class="item col-xs-4 col-lg-4" onclick="getActivityScore('${studentActivity.readRegActivityScoreId}', true)">
              <div class="thumbnail">
                <div class="group list-group-image">
                  <div class="row">
                    <div class="col-md-2 imgicon text-right"><i class="ion-chatbox-working"></i></div>
                    <div class="col-md-10">
                      <div class="row">
                        <div class="col-md-12 group inner list-group-item-heading pnlhead">${studentName } &nbsp;</div>
                        <div class="col-md-12 group inner list-group-item-heading pnlsubhead">Activity ${studentActivity.readRegActivity.actitvity}</div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="caption"> ${titleContent } </div>
              </div>
            </div>
          </c:when>
		  <c:when test="${studentActivity.readRegActivity.actitvity == 'create a quiz' }">
            <div class="item col-xs-4 col-lg-4" onclick="getActivityScore('${studentActivity.readRegActivityScoreId}', true)">
              <div class="thumbnail">
                <div class="group list-group-image">
                  <div class="row">
                    <div class="col-md-2 imgicon text-right"><i class="fa fa-list-ol"></i></div>
                    <div class="col-md-10">
                      <div class="row">
                        <div class="col-md-12 group inner list-group-item-heading pnlhead">${studentName } &nbsp;</div>
                        <div class="col-md-12 group inner list-group-item-heading pnlsubhead">Activity ${studentActivity.readRegActivity.actitvity}</div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="caption"> ${titleContent } </div>
              </div>
            </div>
          </c:when>
		  <c:when test="${studentActivity.readRegActivity.actitvity == 'retell' }">
            <div class="item col-xs-4 col-lg-4" onclick="getActivityScore('${studentActivity.readRegActivityScoreId}', true)">
              <div class="thumbnail">
                <div class="group list-group-image">
                  <div class="row">
                    <div class="col-md-2 imgicon text-right"><i class="ion-ios-mic"></i></div>
                    <div class="col-md-10">
                      <div class="row">
                        <div class="col-md-12 group inner list-group-item-heading pnlhead">${studentName } &nbsp;</div>
                        <div class="col-md-12 group inner list-group-item-heading pnlsubhead">Activity ${studentActivity.readRegActivity.actitvity}</div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="caption"> ${titleContent } </div>
              </div>
            </div>
          </c:when>
		  <c:when test="${studentActivity.readRegActivity.actitvity == 'take a quiz' }">
            <div class="item col-xs-4 col-lg-4" onclick="getActivityScore('${studentActivity.readRegActivityScoreId}', true)">
              <div class="thumbnail">
                <div class="group list-group-image">
                  <div class="row">
                    <div class="col-md-2 imgicon text-right"><i class="ion-clipboard"></i></div>
                    <div class="col-md-10">
                      <div class="row">
                        <div class="col-md-12 group inner list-group-item-heading pnlhead">${studentName } &nbsp;</div>
                        <div class="col-md-12 group inner list-group-item-heading pnlsubhead">Activity ${studentActivity.readRegActivity.actitvity}</div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="caption"> ${titleContent } </div>
              </div>
            </div>
          </c:when>
		  <c:when test="${studentActivity.readRegActivity.actitvity == 'upload a picture' }">
            <div class="item col-xs-4 col-lg-4" onclick="getActivityScore('${studentActivity.readRegActivityScoreId}', true)">
              <div class="thumbnail">
                <div class="group list-group-image">
                  <div class="row">
                    <div class="col-md-2 imgicon text-right"><i class="fa fa-upload faupload"></i></div>
                    <div class="col-md-10">
                      <div class="row">
                        <div class="col-md-12 group inner list-group-item-heading pnlhead">${studentName } &nbsp;</div>
                        <div class="col-md-12 group inner list-group-item-heading pnlsubhead">Activity ${studentActivity.readRegActivity.actitvity}</div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="caption"> ${titleContent } </div>
              </div>
            </div>
          </c:when>
		</c:choose>
		<c:set var="titleContent" value="">
        </c:set>
      </c:forEach>
      </div>
  </c:if>
  <c:if test="${fn:length(studentActivities) == 0}"> <span id="returnMessage" class="status-message">Activities are
    not available</span> </c:if>
  <div id="activityScoreDailog" style="display:none;background: #f7f5f5 url('images/Common/bg.jpg') repeat fixed top center;">
    <iframe id='activityScoreIframe' width="100%" height="95%" style="border-radius:1em;"></iframe>
  </div>
</div>
