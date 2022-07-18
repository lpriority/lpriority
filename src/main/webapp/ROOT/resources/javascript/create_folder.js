function selectRadio()
{
    var createFolder = window.document.createFolder;
    var radio = createFolder.authenticated;
    var folderName = createFolder.folderName.value;

    res=emptyVal(folderName);
    if(res!="")
    {
    	systemMessage("Please select folder type");
        createFolder.folderName.focus();
        return false;
    }

    var temp2=getCheckboxStatus(radio);
    if(temp2=="")
    {
    	systemMessage("Please choose one of the radio button public or private");
        createFolder.authenticated.focus();
        return false;
    }
    createFolder.action="../Folder?formname=addfolder";
    createFolder.method="post";
    createFolder.submit();
    return true;

}

         

