import org.grails.jaxrs.web.JaxrsFilter
    // the plugin version
    def version = "0.2"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.1.1 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def author = "Martin Krasser"
    def authorEmail = "krasserm@googlemail.com"
    def title = "JSR 311 plugin for Grails"
    def description = """

    // URL to the plugin's documentation
    def documentation = 'http://code.google.com/p/grails-jaxrs/'

    def doWithWebDescriptor = { xml ->
        lastListener + {
            'listener' {
                'listener-class'(JaxrsListener.class.name)
            }
        }
    
        def firstFilter = xml.'filter'[0]
        firstFilter + {
            'filter' {
                'filter-name'('jaxrsFilter')
                'filter-class'(JaxrsFilter.class.name)
            }
        }
    
        def firstFilterMapping = xml.'filter-mapping'[0]
        firstFilterMapping + {
            'filter-mapping' {
                'filter-name'('jaxrsFilter')
                'url-pattern'('/*')
                'dispatcher'('FORWARD')
                'dispatcher'('REQUEST')
            }
        }
        
    }

    def doWithSpring = {
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }
}