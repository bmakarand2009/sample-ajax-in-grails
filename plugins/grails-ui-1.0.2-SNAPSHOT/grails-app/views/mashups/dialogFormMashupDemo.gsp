<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <gui:resources components="['dialog','tabView','toolTip']"/>
    <title>Dialogs With Forms</title>
    <style type="text/css">
    .yui-tt {
        background-color:#FFEE69;
        border-color:#D4C237 #A6982B #A6982B;
        border-style:solid;
        border-width:1px;
        color:#000000;
        left:0;
        padding:2px 5px;
        position:relative;
        width: 100px;
        top:0;
        z-index:1;
    }
    .yui-cms-tt .yui-panel {
    	text-align: left;
    }
        .yui-panel-container.yui-cms-tt.shadow .underlay {
            top: 2px;
            bottom: -2px;
            right: -2px;
            left: -2px;
            padding-left: 4px;
        }
        .yui-cms-tt .yui-panel, .yui-cms-tt .yui-panel .bd {
        	color:#333;
        	background-color:#FDFFB4;
        	border-color:#FCC90D;
        }
        .yui-cms-tt .yui-panel .hd {
        	visible: hidden;
        	display: none;
        }
    
    </style>
</head>

<body>

    <h1>Dialogs With Forms</h1>

    <gui:dialog
            title="Form"
            form="true"
            controller="demo"
            action="acceptForm"
            update="replaceMe"
            triggers="[show:[type:'link', text:'click me for a form', on:'click']]">

                <input type="text" id="input1" name="input1"/>
        
    </gui:dialog>

    <gui:dialog
            title="Dialog with tabs"
            form="true"
            controller="demo"
            action="acceptForm"
            update="replaceMe"
            width="300px"
            triggers="[show:[type:'link', text:'click me for a tabbed dialog', on:'click']]">
                <gui:tabView>
                    <gui:tab label="tab 1" active="true">
                        <gui:toolTip text="This is tab 1 input 1">
                            <input type="text" id="tab1:input1" name="tab1:input1"/>
                        </gui:toolTip>
                        <gui:toolTip text="This is tab 1 input 2">
                            <input type="text" id="tab1:input2" name="tab1:input2"/>
                        </gui:toolTip>
                    </gui:tab>
                    <gui:tab label="tab 2">
                        <input type="text" id="tab2:input1" name="tab2:input1"/>
                        <input type="text" id="tab2:input2" name="tab2:input2"/>
                    </gui:tab>
                </gui:tabView>
    </gui:dialog>


    <div id="replaceMe" style="padding:20px;width:300px;border:solid 1px red; background-color:#EEE">
        This content was here when the page loaded... it will be replaced by an autoCompete selection made in the AutoComplete Dialog
    </div>

</body>

</html>