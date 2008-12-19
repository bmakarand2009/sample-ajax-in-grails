import org.springframework.context.ApplicationContextAware
import org.springframework.context.ApplicationContext

class ResourcesTagLibService implements ApplicationContextAware {

    static transactional = false

    def grailsUITagLibService
    def applicationContext
    def taglib

    def stylesheetTag = {cssKey, pluginContextPath, config ->
        def uri = conjureURI(cssKey, 'css', null, pluginContextPath, config)
        "<link rel='stylesheet' type='text/css' href='$uri'/>\n"
    }

    def javascriptTag(jsKey, mode, pluginContextPath, config) {
        def uri = conjureURI(jsKey, 'js', mode, pluginContextPath, config)

        // if the file is custom grailsui stuff with only raw javascript
        if (hasOnlyRawJavascript(uri)) {
            // if the mode is already set to raw then we have a missing dependency
            if (mode == 'raw') {
                throw new GrailsUIException("Missing file for $jsKey: $uri")
            }
            // otherwise, try to reprocess as raw mode to get a good uri
            return javascriptTag(jsKey, 'raw', pluginContextPath, config)
        }
        "<script type=\"text/javascript\" src=\"$uri\" ></script>  \n"
    }
    
    private def conjureURI = {key, fileType, mode, pluginContextPath, config ->
        def trueKey = key.replaceAll('-','_')
        def resourceType = resourceType(trueKey, fileType, config)
        def safeRT = resourceType.replaceAll('-','_')
        def file = config.resources."$safeRT"."$fileType"."$trueKey"
        def version = config.resources."$safeRT".version ? '/' + config.resources."$safeRT".version : ''
        def dirPath = dirPath(resourceType, version, pluginContextPath)
        // find the correct file extention (-min.js, -debug.js, etc.) for javascript includes
        if (fileType == 'js') file += getFileExtension(mode, trueKey, config)
        taglib.createLinkTo(dir: dirPath, file: file)
    }
    
    private def resourceType = { key, type, config -> 
        if (key in config.resources.yui."$type".keySet()) return 'yui'
        if (key in config.resources.yui_cms."$type".keySet()) return 'yui-cms'
        if (key in config.resources.grailsui."$type".keySet()) return 'grailsui'
        throw new GrailsUIException("Resource $key was not available within GrailsUI config.")
    }
    
    private def dirPath = { resourceType, version, pluginContextPath ->
        def path = "/js/${resourceType.replaceAll('_','-')}${version}"
        // only grails ui source files will use the plugin context path for URI
        if (resourceType == 'grailsui') {
            path = pluginContextPath + path
        }
        path
    }
    
    private def getFileExtension(mode, key, config) {
        if (key in config.rollUps.keySet()) {
            mode = 'raw'
        }
        if (!mode) {
            mode = 'minimal'
        }
        config.mode."$mode"
    }

    // At this point, we are only checking to see if the javascript file is a part of GrailsUI's YUI extensions.  If it is, then
    // there will be no -min.js or -debug.js files for it (yet).  All these URIs should contain 'grailsui' because they are stored
    // within the /js/grailsui folder
    private def hasOnlyRawJavascript = { uri ->
        uri.contains('grailsui') && ( uri.contains('-debug.js') || uri.contains('-min.js') )
    }

    // only getting the app context so we can grab the ApplicationTagLib, which will be used to createLinkTo() in the
    // tag helper functions
    public void setApplicationContext(ApplicationContext applicationContext) {
        taglib = applicationContext.getBean('org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib')
    }
}
