<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <gui:resources components="['tabView','dialog','dataTable','richEditor','autoComplete']"/>
    <title>TabView Mashup</title>
</head>

<body>

    <h1>TabView Mashup</h1>

    <gui:tabView id="tabView">

        <!-- totally static -->
        <gui:tab id="t2" label="with dialog">
            <h3>This is the inside of tab 2</h3>
            <p/>This tab also has static content, and a picture
            <br/>
            <img id="popit" src="${createLinkTo(dir:'/images', file:'thumbsup.jpg')}" alt="Thumbs up for Grails!!"/>
            <p/>Click on this picture to pop up a dialog box.
        </gui:tab>

        <!-- contains a complex javascript item that is rendered after the tabs are complete -->
        <gui:tab id="t5" active="true" label="with dataTable">
            <gui:dataTable
                    id="dataTable"
                    columnDefs="[
                            [key:'id', sortable:true, resizeable: true],
                            [key:'name', sortable:true, resizeable: true],
                            [key:'birthDate', type:'date', sortable:true, resizeable: true],
                            [key:'age', type:'number', sortable:true, resizeable: true],
                            [key:'netWorth', type:'currency', sortable:true, resizeable: true]
                        ]"
                    controller="demo" action="dataTableDataAsJSON"
                    rowExpansion="true"
                    caption="click on a row, and it will expand"
                    paginate="true"/>
        </gui:tab>

        <!-- tab with richEditor -->
        <gui:tab id="t6" label="with richEditor">
            <gui:richEditor
                    id='testEditor'
                    value="Edit me!"/>
        </gui:tab>

        <!-- tab with autoComplete -->
        <gui:tab id="t7" label="with autoComplete">
            <div style="width: 300px">
                <gui:autoComplete
                        name="testControl"
                        id="testId"
                        resultName="TestData"
                        labelField="description"
                        idField="id"
                        width="300px"
                        controller="demo"
                        action="testFruitDataAsJSON"
                        forceSelection="true"
                        useShadow="true"
                        /><br/><br/>
            </div>
        </gui:tab>

    </gui:tabView>

    <!-- this dialog is triggered from within a tab -->
    <gui:dialog
            id="dialog1"
            title="popped up from tab"
            triggers="[show:[id:'popit', on:'click']]">
        This is within a simple dialog, and was popped up from within a tab.
    </gui:dialog>

</body>

</html>