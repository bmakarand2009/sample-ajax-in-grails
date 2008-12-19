class TabTagLib {

    static namespace = "gui"

    def grailsUITagLibService

    def tabs

    def tabView = {attrs, body ->
        attrs = grailsUITagLibService.establishDefaultValues(
                [
                        id: "tabView_${grailsUITagLibService.getUniqueId()}"
                ],
                attrs,
                []
        )
        tabs = []
        body()  // have to execute the body to ensure all tab tags contained within are executed
        def id = attrs.id
        def jsid = grailsUITagLibService.makeJavascriptFriendly(id)
        def tabNav = tabs.findAll {it.nav}.collect { it.nav }.join('\n')
        def tabContent = tabs.findAll {it.content}.collect { it.content }.join('\n')
        out << """
        <div id="${id}" class="yui-navset">
            <div class="tabOuter">
                <div class="tabInner">
                    <ul class="yui-nav">
                        ${tabNav}
                    </ul>
                </div><!-- end #tabInner -->
            </div><!-- end #tabOuter -->
            <div class="yui-content">
                ${tabContent}
            </div>
        </div>
        <script type="text/javascript">
            GRAILSUI.${id} = new YAHOO.widget.TabView('${id}');
        """
        tabs.eachWithIndex { tab, i ->
            if (tab.config) {
                def tabId = grailsUITagLibService.makeJavascriptFriendly(tab.config.remove('id'))
                out << "GRAILSUI.${tabId} = new YAHOO.widget.Tab({${grailsUITagLibService.mapToConfig tab.config}});"
                out << "GRAILSUI.${jsid}.addTab(GRAILSUI.${tabId} , $i );\n"
            }
        }
        out << '</script>'
    }

    def tab = {attrs, body ->
        attrs = grailsUITagLibService.establishDefaultValues(
                [
                        id: grailsUITagLibService.getUniqueId(),
                        active: false,
                        cached: false
                ],
                attrs,
                ['label']
        )

        if (attrs.controller && attrs.action) {
            def id = attrs.remove('id')
            attrs.dataSrc = createLink(attrs)   // set dynamic dataSrc if necessary
            attrs.id = id
        }
        def classes = "class='${attrs.active ? 'selected' : ''} ${attrs['class'] ? attrs['class'] : ''}'"
        if ((attrs.controller && attrs.action) || attrs.dataSrc) {
            // dynamically rendered config
            tabs << [config : attrs]
        } else {
            def nav = """<li ${classes}><a href="#${attrs.id}"><span class="tabLeft"></span><em>${attrs.label}</em><span class="tabRight"></span></a></li>\n"""
            def content = """<div id="${attrs.id}">${body()}</div>\n"""
            tabs << [nav:nav, content:content]
        }
    }

}