<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <gui:resources components="['datePicker','dialog']"/>
    <title>Dialog With datePickers</title>
</head>

<body>

    <h1>Dialog With datePickers</h1>

    <div style="height:800px;border:solid 1px black;margin:50px;padding:50px;">
        <gui:dialog
                title="Dialog with datePickers"
                form="true"
                controller="demo"
                action="acceptForm"
                update="replaceMe"
                width="300px"
                triggers="[show:[type:'link', text:'click me for a dialog with datePickers', on:'click']]">
                    <gui:datePicker name="dp_1" />
                    <gui:datePicker name="dp_2" includeTime     ="true" />
        </gui:dialog>


        <br/><br/>
        <div id="replaceMe" style="padding:20px;width:300px;border:solid 1px red; background-color:#EEE">
            This content was here when the page loaded... it will be replaced by an autoCompete selection made in the AutoComplete Dialog
        </div>
    </div>

</body>

</html>