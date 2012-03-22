class AutoTestGrailsPlugin {
    def version = "1.0.1"
    def grailsVersion = "2.0.0 > *"
    def dependsOn = [:]

    def pluginExcludes = [
		"grails-app/views/error.gsp"
    ]

    def author = "Bjoern Wilmsmann, Mike Hugo"
    def authorEmail = "bjoern@metasieve.com, mike@piragua.com"
    def title = "Auto test plugin"
    def description = '''\\
Auto test plugin.
'''

    def documentation = "http://grails.org/plugin/auto-test"

}
