/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
