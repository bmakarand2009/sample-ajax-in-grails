Ant.property(environment:"env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"

includeTargets << new File ( "${grailsHome}/scripts/Init.groovy" )  
checkVersion()
configureProxy()

packagedVersion = "1.50"
bubblingVersion = "1.5.0"

Ant.sequential {
    mkdir(dir:"${grailsHome}/downloads")

    event("StatusUpdate", ["Downloading Bubbling Library ${packagedVersion}"])

    get(dest:"${grailsHome}/downloads/bubbling.library.v${packagedVersion}.zip",
        src:"http://downloads.sourceforge.net/bubbling/bubbling.library.v${packagedVersion}.zip",
        verbose:true,
        usetimestamp:true)
    unzip(dest:"${grailsHome}/downloads/bubbling.library.v${packagedVersion}",
        src:"${grailsHome}/downloads/bubbling.library.v${packagedVersion}.zip")	
	
    mkdir(dir:"${basedir}/web-app/js/yui-cms/${bubblingVersion}")
    copy(todir:"${basedir}/web-app/js/yui-cms/${bubblingVersion}") {
        fileset(dir:"${grailsHome}/downloads/bubbling.library.v${packagedVersion}/bubbling-library/${bubblingVersion}/build", includes:"**/**")
    }		 
}            
event("StatusFinal", ["Bubbling Library ${packagedVersion} installed successfully"])
