 <div id="div${inc}">
    <table><tr>
    <td><input type="hidden" id="activityId${inc}" name="activityList[${count}].activityId" form="createLesson" /><span id='span${inc}' style="padding-top:0.2em;" class="tabtxt"></span></td>
	<td><textarea id="${inc}" rows="3" cols="60" name="activityList[${count}].activityDesc" onblur="${(mode eq 'edit')?'updateActivity(this)':''}" form="createLesson" /></td>
	<td><i class="fa fa-times" aria-hidden="true" style="cursor: hand; cursor: pointer;font-size: 25px;color:#CD0000;" onclick="removeActivity(${inc})" ></i></td>
	<td id="result${inc}"></td></tr>
	</table>
</div>