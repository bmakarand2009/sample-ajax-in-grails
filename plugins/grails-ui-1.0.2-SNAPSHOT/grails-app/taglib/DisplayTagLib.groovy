class DisplayTagLib {

    static namespace = "gui"

    def grailsUITagLibService

    def expandablePanel = {attrs, body ->
        attrs = grailsUITagLibService.establishDefaultValues(
                [
                        id: grailsUITagLibService.getUniqueId(),
                        expanded: false
                ],
                attrs,
        )
        def expanded = ''
        if (grailsUITagLibService.makeJavascriptFriendly(attrs.expanded)) expanded = ' selected'
        def title = 'Expandable Panel Dummy title'
        if (attrs.title) title = attrs.title
        out << """
            <div id="${attrs.id}" class="yui-skin-sam yui-cms-accordion multiple fade fixIE" rel="bounceOut">
                <div class="yui-cms-item yui-panel${expanded}">
                    <div class="hd">${title}</div>

                    <div class="bd">
                      <div class="fixed">
                        <p>
                          ${body()}
                        </p>
                      </div>
                    </div>

                    <div class="actions">
                      <a href="#" class="accordionToggleItem">&nbsp;</a>
                      <a href="#" class="accordionRemoveItem">&nbsp;</a>
                    </div>
                </div>
            </div>
        """
    }

    def accordion = {attrs, body  ->
        attrs = grailsUITagLibService.establishDefaultValues(
                [
                        id: grailsUITagLibService.getUniqueId(),
                        multiple: false,
                        persistent: false,
                        fade: false,
                        bounce: false,
                        slow: false
                ],
                attrs,
        )
        def config = ['class="yui-cms-accordion fixIE ',
            (attrs.multiple ? 'multiple ' : ''),
            (attrs.persistent ? 'persistent ' : ''),
            (attrs.fade ? 'fade' : ''),
            (attrs.slow ? 'slow' : ''),
            '"',
            (attrs.bounce ? ' rel="bounceOut"' : '')].join('')
        out << """
    		<div id="${attrs.id}" ${config}>
    			${body()}
    		</div>
        """
    }

    def accordionElement = {attrs, body ->
        attrs = grailsUITagLibService.establishDefaultValues(
                [
                        id: grailsUITagLibService.getUniqueId(),
                        selected: false
                ],
                attrs,
                ['title']
        )
        out << """
            <div class="yui-cms-item yui-panel ${attrs.selected ? 'selected' : ''}">
                <div class="hd">${attrs.title}</div>
                <div class="bd">
                    <div id="${attrs.id}" class="fixed">
                        ${body()}
                    </div>
                </div>
                <div class="actions">
                    <a href="#" class="accordionToggleItem">&nbsp;</a>
                </div>
            </div>
        """
    }

    /**
     * DataTable creates a YUI DataTable component markup with JavaScript to transform it.
     *
     * @id id
     * @resultsList expected JSON root of the data to populate the table
     * @sortedBy initial column to sort by.  This should be in defined in the columnDefs mapping, unless allowExclusiveSort=true,
     * in which case the server will have to handle the initial sorting.
     * @sortOrder asc or desc
     * @rowExpansion true/false, will attempt to find a url to load additional markup in an expansion element on row click
     * @allowExclusiveSort default is false, when true will allow unrecognized column names to be used as initial sort value 
     */
    def dataTable = {attrs ->

        attrs = grailsUITagLibService.establishDefaultValues(
                [
                        id: grailsUITagLibService.getUniqueId(),
                        selectionMode: 'single',
                        resultsList: 'results',
                        recordOffset: 0,
                        rowsPerPage: 10,
                        sortedBy: 'id',
                        sortOrder: 'asc',
                        rowExpansion: false,
                        rowClickNavigate: false,
                        rowClickMode: 'none',
                        params: [:],
                        connMethodPost:true,
                        allowExclusiveSort:false,
                        paginatorConfig:[:]
                ],
                attrs,
                ['columnDefs', 'controller', 'action']
        )

        def sortedBy = attrs.remove('sortedBy')
        def allowExclusiveSort = attrs.remove('allowExclusiveSort') ?: false
        // if sortedBy refers to a field that doesn't exist in the column defs, throw an exception to warn that the table can't render
        def colDefsContainsSortedBy = attrs.columnDefs.find {it.key == sortedBy}
        if (!colDefsContainsSortedBy && !allowExclusiveSort) {
            throw new GrailsUIException("The GrailsUI dataTable cannot be defined with a 'sortedBy' value of '${sortedBy}' because it "
                + "does not exist in the column definition.  To fix, either update the columnDefs attribute to contain the sortedBy "
                + "value, change sortedBy to refer to a column defined in 'columnDefs', or add \"allowExclusiveSort='true'\" to the tag "
                + "attributes (assuming the server will sort on a field not defined within the datatable column definitions).")
        }
        
        // can't have rowExpansion and rowClickNavigation both true
        def rowExpansion = attrs.remove('rowExpansion')
        def rowClickNavigation = attrs.remove('rowClickNavigation')
        if (rowExpansion && rowClickNavigation) {
            throw new GrailsUIException('\'rowExpansion\' and \'rowClickNavigation\' cannot both be '
                    + 'true.  Only one row click handler is allowed.  To fix, remove one, or set one to false.')
        }
        
        // the GRAILSUI.DataTable.rowClickMode is either 'none', 'expand', or 'navigate'
        if (rowExpansion) attrs.rowClickMode = 'expand'
        if (rowClickNavigation) attrs.rowClickMode = 'navigate'
        
        def initialSortedByConfigString = !colDefsContainsSortedBy && allowExclusiveSort ? '' : "sortedBy               : {key: \"${sortedBy}\", dir: YAHOO.widget.DataTable.CLASS_ASC},"
        def id = attrs.remove('id')
        def jsid = grailsUITagLibService.toJS(id)
        def params = attrs.remove('params')
        def columnDefs = attrs.remove('columnDefs')
        def connMethodPost = attrs.remove('connMethodPost')
        def sortOrder = attrs.remove('sortOrder')
        def recordOffset = attrs.remove('recordOffset')
        def rowsPerPage = attrs.remove('rowsPerPage')
        def paginatorConfig = attrs.remove('paginatorConfig')
        if (paginatorConfig.rowsPerPage) rowsPerPage = paginatorConfig.rowsPerPage
        def paramQueryString = params.collect {key,val -> "$key=$val" }.join('&')
        
        def dataUrl = createLink(attrs)
        
        // build query for initial request
        def query = "max=${rowsPerPage}&offset=${recordOffset}&sort=${sortedBy}&order=${sortOrder}&${paramQueryString}"

        // if row expansion is enabled, add the dataUrl to the datasource
        if (rowExpansion || rowClickNavigation) {
            columnDefs << [key: 'dataUrl', type: 'dataDrillDown', hidden: true]
        }
        
        if (rowsPerPage) { paginatorConfig.rowsPerPage = rowsPerPage }
        
        out << """
            <div id="dt_div_${id}"></div>
        """
        out << """
            <script>
            YAHOO.util.Event.onDOMReady(function () {
                var DataSource = YAHOO.util.DataSource,
                    DataTable  = YAHOO.widget.DataTable,
                    Paginator  = YAHOO.widget.Paginator;

                var ${jsid}_ds = new DataSource('${dataUrl}?');
                ${jsid}_ds.responseType   = DataSource.TYPE_JSON;
                ${jsid}_ds.connMethodPost=${connMethodPost};
                ${jsid}_ds.responseSchema = {
                    resultsList : '${attrs.remove('resultsList')}',
                    fields      : [${columnDefs.collect { "\"$it.key\""}.join(',')}],
                    metaFields  : {
                        totalRecords: 'totalRecords'
                    }
                };

                var ${jsid}_paginator = new Paginator(
                    {${grailsUITagLibService.mapToConfig paginatorConfig}}
                    );

                var myColumnDefs = [${grailsUITagLibService.listToConfig columnDefs}];

                GRAILSUI.${jsid} = new GRAILSUI.DataTable('dt_div_${id}', myColumnDefs, ${jsid}_ds, '${paramQueryString}', {
                    initialRequest         : '${query}',
                    paginator              : ${jsid}_paginator,
                    dynamicData            : true,
                    ${initialSortedByConfigString}
                    ${grailsUITagLibService.mapToConfig attrs}
                });
                // Update totalRecords on the fly with value from server
                GRAILSUI.${jsid}.handleDataReturnPayload = function(oRequest, oResponse, oPayload) {
                    oPayload.totalRecords = oResponse.meta.totalRecords;
                    return oPayload;
                }
            });
            </script>
        """
    }

    def toolTip = {attrs, body ->

        attrs = grailsUITagLibService.establishDefaultValues(
                [
                        id: grailsUITagLibService.getUniqueId(),
                ],
                attrs
        )

        def id = attrs.remove('id')        
        def text = attrs.remove('text')
    
        // if  the tooltip text is set, we can assume that we want to use it, not a server call
        if (text) {
            // surround body with span that includes the tooltip trigger
            out << "<span id='$id' class='yui-tip' title='${text}'>${body()}</span>"
        } else {
            def dataUrl
            try {
                dataUrl = createLink(attrs)
            } catch (Exception e) {
                throw new GrailsUIException("There was not enough information in the gui:toolTip tag to create the link." +
                  "  Either a 'text' attribute, 'controller'/'action' atributes, or a 'url' attribute is required.")
            }
            // otherwise create the span with an URL to call to populate the toolTip
            out << "<span id='$id' url='$dataUrl' class='yui-tip'>${body()}</span>"
        }
    }
}
