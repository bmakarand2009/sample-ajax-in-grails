import org.codehaus.groovy.grails.plugins.bubbling.Bubbling

class BubblingTagLib {

    static namespace = "bub"
    
    /**
     * Includes a Bubbing javascript file from the Bubbing distribution folder
     *  
     * <bub:javascript dir="accordion" file="accordion-min.js" /> 
     * 
     * Actually imports '/app/js/yui-cms/{version}/accordion/accordion-min.js'
     * 
     * @param dir The name of the directory within the bubbling folder to link to 
     * @param file The name of the file to link to
     **/
    def javascript = { attrs ->
        if (!attrs.dir)
            throwTagError("Tag [javascript] is missing required attribute [dir]")
        if (!attrs.file)
            throwTagError("Tag [javascript] is missing required attribute [file]")

        def version = attrs.version ? attrs.remove('version') : Bubbling.version
            
        def src = createLinkTo(dir: "js/yui-cms/${version}/${attrs.dir}", file: attrs.file)
        out << "<script type=\"text/javascript\" src=\"${src}\"></script>"
    }
    
    /**
     * Includes a Bubbling Library stylesheet file from the Bubbling distribution folder
     *
     *  
     * 
     * Actually imports '/app/js/yui-cms/{version}/accordion/assets/accordion.css'
     * 
     * @param dir The name of the directory within the bubbling folder to link to 
     * @param file The name of the file to link to 
     **/
    def stylesheet = { attrs ->
        if (!attrs.dir)
            throwTagError("Tag [stylesheet] is missing required attribute [dir]")
        if (!attrs.file)
            throwTagError("Tag [stylesheet] is missing required attribute [file]")

        def version = attrs.version ? attrs.remove('version') : Bubbling.version
            
        def href = createLinkTo(dir: "js/yui-cms/${version}/${attrs.dir}", file: attrs.file)
        out << "<link rel=\"stylesheet\" type=\"text/css\" href=\"${href}\" />"
    }
}
