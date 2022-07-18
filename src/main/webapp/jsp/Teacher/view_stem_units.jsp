<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript" src="resources/javascript/TeacherJs/stem_curriculum.js"></script>

<c:if test="${fn:length(stemUnitList) > 0}">
<table class="des" border=0 width='35%'>
	<tr>
	<td width='30%'><div class='Divheads'>
			<table align='center' width="100%">
				<tr>
					<th width='60' align='center' style='padding-right: 1.6em;'>S.No</th>
					<th width='200' align='center' style='padding-right: 1.6em;'>Unit Name</th>
					<th width='60' align='center' style='padding-right: 1.6em;'>Remove</th>
				</tr>
			</table>
		</div>
		<div class='DivContents'>
			<table style='font-size:13px;margin-top:.5em;font-family:"Lato", "PingFang SC", "Microsoft YaHei", sans-serif;' width="100%">
				<c:forEach items="${stemUnitList}" var="stUn" varStatus="status"> 
					<tr>
					   <td width='60' height=20 align='center' style='padding-right: 1.6em;'>${status.count}</td>
						<td width='200' align='center' style='padding-right: 1.6em;'>
							<a href="#" onClick="javascript:addStemUnit(${stUn.stemUnitId});">${stUn.stemUnitName}</a>
						</td>
						<td width='60' align='center' style='padding-right: 1.6em;'>
							<i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 22px;color:#CD0000;" onclick="deleteStemUnit(${stUn.stemUnitId},'${stUn.isShared}')"></i>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div></td>
	</tr>
</table>
</c:if>


