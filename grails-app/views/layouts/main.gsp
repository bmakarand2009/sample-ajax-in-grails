<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${createLinkTo(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:javascript library="scriptaculous" />
        <g:javascript library="application" />
        <g:javascript library="jquery-1.2.6.min" />
        <g:javascript>
           var $j = jQuery.noConflict();
        </g:javascript>
        <g:layoutHead />
        
    </head>
    <body>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${createLinkTo(dir:'images',file:'spinner.gif')}" alt="Spinner" />
        </div>	
        <div class="logo"><img src="${createLinkTo(dir:'images',file:'grails_logo.jpg')}" alt="Grails" /></div>	
        <g:layoutBody />		
    </body>	
</html>