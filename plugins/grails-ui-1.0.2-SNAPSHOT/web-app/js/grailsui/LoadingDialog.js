GRAILSUI.LoadingDialog = function(oId, oText, oRenderTo) {
    var id = 'loading';
    if (arguments.length > 0) {
        id = oId;
    }
    var width = "50px";
    var height = "50px";
    var useHeader = oText != null && oText != undefined && oText != '';
    if (useHeader) {
        width = "auto";
        height = "80px";
    }
    var oConfig = {
        width:width,
        height:height,
        fixedcenter:true,
        close:false,
        draggable:false,
        zindex:4,
        modal:true,
        visible:false
    };
    GRAILSUI.LoadingDialog.superclass.constructor.call(this, oId, oConfig);
    if (useHeader) {
        this.setHeader(oText);
    }
    this.setBody("<div id='grailsuiLoading'></div>");
    if (oRenderTo != null) {
        this.render(oRenderTo);
    } else {
        this.render(document.body);
    }
};

YAHOO.lang.extend(GRAILSUI.LoadingDialog, YAHOO.widget.Panel);
