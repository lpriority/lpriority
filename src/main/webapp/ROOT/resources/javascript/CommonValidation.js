var msg="";

function emptyVal(field)
{
	if(field=="")
        msg="Fields with * are mandatory";
    else if(field=="select")
        msg="Fields with * are mandatory And Choose One from Dropdown";
    else if(field=="Select One")
        msg="Fields with * are mandatory And Choose One from Dropdown";
    else if(field == "SelectEvent")
        msg="Fields with * are mandatory And Choose One from Dropdown";
    else
        msg="";
    return msg;
}

function fieldLength(field,min,max)
{
    var fldlen=field.length;
    alert('fieldlength'+fldlen);
     if(fldlen<min || fldlen>max)
    	msg=" length must be within " + min + "-" + max + " ";

    return msg;
}

function passwordlength(field)
{
	msg = "";
    var len=field.length;
    if(len<6)
        msg=" must be at least 6 characters long";

    return msg;
}

function charVal(field)
{
    var condition=/^([a-zA-Z]+(\s|\.|\')?)+$/.test(field);
    if(condition==false)
        msg=" requires alphabetic characters";
    else
        msg="";

    return msg;
}

function schoolVal(field)
{
    var condition=/^([a-zA-Z0-9]+(\s|\.|\'|\,|\/|\-)?)+$/.test(field);
    if(condition==false)
        msg=" should be alphanumeric characters along with some special characters ";
    else
        msg="";

    return msg;
}

function userVal(field)
{
    var condition=/^([a-zA-Z\d]+(\_|\.|\-)?)+$/.test(field);
    if(condition==false)
        msg=" can only contain the following special characters: hyphen, underscore, period";
    else
        msg="";

    return msg;
}

function zeroVal(field)
{
    var condition=/^[0]+$/.test(field);
    if(condition==true)
        msg="Enter valid ";
    else
        msg="";

    return msg;
}

function numVal(field)
{
    var condition=/^[0-9]+$/.test(field);
    if(condition==false)
        msg=" requires numeric characters";
    else
        msg="";

    return msg;
}


function alphanumVal(field)
{
    var condition=/^([a-zA-Z0-9]+(\_|\-|\s)?)+$/.test(field);
    if(condition==false)
        msg=" requires alphanumeric characters";
    else
        msg="";

    return msg;
}


function floatVal(field)
{
    var condition=/^[0-9]+(\.[0-9]+)?$/.test(field);
    if(condition==false)
        msg=" requires numeric characters and decimals";
    else
        msg="";

    return msg;
}

function emailVal(field)
{
	var condition = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/.test(field);
    if(condition==false)
        msg="Please enter your valid ";
    else
        msg="";

    return msg;
}

function urlVal(field)
{
    var condition=/^(http:\/\/|https:\/\/)?(www)\.[a-z0-9]+\.([a-z]+\.)?[a-z]+$/.test(field);
    if(condition==false)
        msg = " Enter valid ";
    else
        msg="";

    return msg
}

function zeroVal(field)
{
    var condition=/^[0]+$/.test(field);
    if(condition==true)
        msg="Enter valid ";
    else
        msg="";

    return msg;
}

function addressVal(field)
{

    var condition=/^[a-zA-Z0-9#]*[^(\!|\@|\$|\^|\*|\_|\~|\<|\>|\?|\{|\}|\[|\]|\(|\)|\%)]+$/.test(field);
    if(condition==false)
        msg=" cannot contain the following symbols: !,@,%,$,^,*,_,~,(,),{,},[,],<,>,? ";
    else
        msg="";

    return msg;
}
function spaceVal(field)
{
    var condition=/^[\s]+$/.test(field);
    if(condition==true)
        msg=" requires alphanumeric characters";
    else
        msg="";

    return msg;
}


function passwordsCompareVal(field1,field2)
{
    if(field1!=field2)
        msg = " fields are not same"

    return msg;
}

function fieldsCompareVal(field1,field2)
{
    if(field1==field2)
        msg = " fields should not be same"

    return msg;
}
function nextField(key,e)
{
    if(e.keyCode==9)
    {
        key.focus();
        return false;
    }
}

function getCheckboxStatus(field) {
    var checkboxvalue = "";
    var error = "";
    var checkb = field;
    var leng = checkb.length;
    for (var i = 0; i < leng; i++) {
        //alert("for loop---"+checkb[i].value);
        if (checkb[i].checked) {
            checkboxvalue = checkb[i].value;
        }
    }
    return checkboxvalue;
}
function dateValidation(field){
    var inputDate = new Date(field);
    var today = new Date();
    if (inputDate < today) {
        return false;
    }
    else
        return true;
}
function checkDates(field1,field2){
    var startDate = new Date(field1);
    var endDate = new Date(field2);

    if (startDate>= endDate) {
        return false;
    }
    else
        return true;
}