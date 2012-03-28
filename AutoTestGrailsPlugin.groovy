class AutoTestGrailsPlugin {
    def version = "1.0"
    def grailsVersion = "2.0.0 > *"
    def dependsOn = [:]

    def pluginExcludes = [
		"grails-app/views/error.gsp"
    ]

    def author = "Bjoern Wilmsmann, Mike Hugo"
    def authorEmail = "bjoern@metasieve.com, mike@piragua.com"
    def title = "Auto test plugin"
    def description = '''\
Provides continuous testing for your Grails projects. Run a single command and then every time you save a file,\
the relevant tests will run. Great for instant feedback while you're coding.\
'''

    def documentation = "http://grails.org/plugin/auto-test"
    def license = "APACHE"
    def scm = [url: "https://github.com/mjhugo/grails-auto-test"]
    def issueManagement = [system: "JIRA", url: "http://jira.grails.org/browse/GPAUTOTEST"]
}
