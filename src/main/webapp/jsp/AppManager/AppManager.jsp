<script type="text/javascript" src="resources/javascript/template/common_template.js"></script>

<%@include file="Layout.jsp"%>

<div class="container-fluid">
  <div class="row">
    <div class="col-sm-3">
      <%@include file="Menuinfo.jsp"%>
    </div>
    <div class="col-sm-9">
      <div class="column_middle_admin">
        <div class="column_middle_grid1">
          <div class="profile_picture">
            <div class="row">
              <div class="col-sm-4">
                <form name="uploadProfilePicForm" id="uploadProfilePicForm" novalidate>
                  <div class="show-image"> <a href="#" class="tooltip" data-tooltip="Click here to view your Profile Picture"><img id="imgDiv" class="imgCls" style="display:none;width: 85px;height: 85px;border-radius: 50%;" onclick="openPreviewImage()"></a>
                    <div id="iconDiv" class="fa fa-user-circle-o" aria-hidden="true" style="display:block;font-size: 80px;text-shadow: 0 1px 4px rgb(140, 156, 158);color:#00cae2;margin-top:5px;margin-bottom:5px;"></div>
                    <a href="#" class="tooltip-left" data-tooltip="Upload">
                    <button id="upload" class="stylish-button button--ujarak button--round-l button--border-thin button--text-thick image-buttons" style="margin-left:10%;"><i class="ion-edit" style="font-size:16px;font-weight:bold;"></i></button>
                    </a>
                    <input type="file" id="file" name="file" required style="display:none;" onchange="showimagepreview(this)">
                    <a href="#" class="tooltip-right" data-tooltip="Remove">
                    <button id="remove" class="stylish-button button--ujarak button--round-l button--border-thin button--text-thick image-buttons" onclick="deleteProfileImage()" style="margin-left: 70%; display: none;"><i class="fa fa-times" style="font-size:14px;font-weight:bold;"></i></button>
                    </a> </div>
                </form>
              </div>
              <div class="col-sm-8 text-left">
                <div class="col-sm-12 hcontent">Hello..! </div>
                <div class="col-sm-12"><b>${userName}</b></div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-12 text-left">
                <div class="middle_text" style="margin-top:1.2em;"> AppManager Administration </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
