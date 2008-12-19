import java.rmi.server.UID
import org.apache.commons.codec.digest.DigestUtils

class GrailsUITagLibService {
    
    static transactional = false

    def badJSChars = [':','-']
    
    def getUniqueId = {
        'gui_' + DigestUtils.md5Hex(new UID().toString())
    }

    // sets up some default values given, and translates attribute values in javascript-compatible structures so they
    // can be used as config objects in YUI
    def establishDefaultValues(defaults, attrs) {
        def result = [:]
        // clone the attrs with boolean substitution
        attrs.each {key, val ->
            if (val instanceof Boolean || (val != null && val)) {
                result."$key" = makeJavascriptFriendly(val)
            }
        }
        defaults.each { key, val ->
            if (result."$key" == null) result."$key" = val
        }
        return result
    }

    // this will require some values to be present, or else throw an exception
    def establishDefaultValues(defaults, attrs, requirements) {
        requirements.each {
            if (!attrs."$it") {
                if (it.contains('|')) {
                    def valid = false
                    it.split('\\|').each { splitVal ->
                        if (attrs."$splitVal") valid = true
                    }
                    if (!valid) {
                        throw new GrailsUIException("Attribute ${it.split('\\|').join(' or ')} is required.")
                    }
                } else {
                    throw new GrailsUIException("Attribute $it is required.")
                }
            }
        }
        establishDefaultValues defaults, attrs
    }

    private def makeJavascriptFriendly(val) {
        if (!(val instanceof String)) return val
        if (val.isInteger()) return val.toInteger()        
        if (val.matches(/[Tt][Rr][Uu][Ee]/)) return true
        if (val.matches(/[Ff][Aa][Ll][Ss][Ee]/)) return false
        if (val instanceof Map) return '{' + mapToConfig(val) + '}'
        if (val instanceof List) return '[' + listToConfig(list) + ']'
        val
    }
    
    /**
     * Any string could be sent in as the id, so before using it as a javascript identifier, we need to ensure some
     * characters are replaced
     */
    def toJS(s) {
        badJSChars.each {
            s = s.replaceAll(it,'_')
        }
        s
    }
    
    /**
     * Turns map into a javascript config object string for YUI.  This is what allows configurations passed into the
     * grailsUI tag to be passed along to the YUI widget.
     */
    def mapToConfig(attrs) {
        attrs.collect { key, val ->
            if (val instanceof String || val instanceof GString) {
                // if this is a handler, the val will be a javascript function, so don't quote it
                if (key == 'handler') return "$key: $val"
                // if this string resolves to a number, also don't quote it
                if (val.isNumber()) return "$key: $val"
                return "'$key': '$val'"
            } else if (val instanceof Map) {
                return "'$key':{${mapToConfig(val)}}"
            } else if (val instanceof List) {
                return "'$key': [${listToConfig(val)}]"
            }
            "'$key': $val"
        }.join(",\n")
    }

    def listToConfig(list) {
        list.collect {
            if (it instanceof Map) {
                return "{" + mapToConfig(it) + "}"
            }
            "'$it'"
        }.join(", ")
    }
}