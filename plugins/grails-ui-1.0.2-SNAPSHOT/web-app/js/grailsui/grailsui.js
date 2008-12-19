var GRAILSUI = YAHOO.namespace('grailsui');
GRAILSUI.util = YAHOO.namespace('grailsui.util');

//+ Jonas Raoni Soares Silva
//@ http://jsfromhell.com/string/pad [rev. #1]
String.prototype.pad = function(l, s, t){
    return s || (s = " "), (l -= this.length) > 0 ? (s = new Array(Math.ceil(l / s.length)
        + 1).join(s)).substr(0, t = !t ? l : t == 1 ? 0 : Math.ceil(l / 2))
        + this + s.substr(0, l - t) : this;
};

GRAILSUI.util.replaceWithServerResponse = function(div, response) {
    div.innerHTML = response.responseText;
    // need to manually execute the javascripts within the server response, because it doesn't happen
    // automatically when you set innerHTML
    var scripts = div.getElementsByTagName("script");
    for(var i=0;i<scripts.length;i++) {
        eval(scripts[i].text);
    }
};