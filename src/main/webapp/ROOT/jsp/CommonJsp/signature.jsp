<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Signature Pad demo</title>
  <script src="resources/javascript/jquery/jquery-2.1.4.js"></script>
  <link href="resources/css/common_buttons.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="resources/css/signature_pad/signature-pad.css">
</head>
 <input type="hidden" name="studId" id="studId" value="${studentId}" />
  <input type="hidden" name="gradeId" id="gradeId" value="${gradeId}" />
  <input type="hidden" name="trimesterId" id="trimesterId" value="${trimesterId}" />
  <input type="text" name="page" id="page" value="${page}" />
   <input type="text" name="page1" id="page1" value="xxxxx" />
  <div id="signature-pad" class="m-signature-pad">
    <div class="m-signature-pad--body">
      <canvas></canvas>
    </div>
    <div class="m-signature-pad--footer">
      <div class="description">Sign above</div>
      <div class="left">
        <button type="button" class="button_green clear" data-action="clear" style="font-size: 1.3em;">Clear</button>
      </div>
      <div class="right">
        <button type="button" class="button_green save" data-action="save-png" style="font-size: 1.3em;">That's OK</button>
      </div>
    </div>
  </div>

<script src='resources/javascript/signature_pad/signature_pad.js'></script>
<script src='resources/javascript/signature_pad/signature.js'></script>
</html>