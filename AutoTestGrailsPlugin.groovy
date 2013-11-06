class AutoTestGrailsPlugin {
    def version = "1.0.1"
    def grailsVersion = "2.0.0 > *"

    def title = "Auto test plugin"
    def description = '''\
Provides continuous testing for your Grails projects. Run a single command and then every time you save a file,\
the relevant tests will run. Great for instant feedback while you're coding.\
'''

    def documentation = "http://grails.org/plugin/auto-test"
    def license = "APACHE"
    def developers = [
        [name: "Bjoern Wilmsmann", email: "bjoern@metasieve.com"],
        [name: "Mike Hugo", email: "mike@piragua.com"]
    ]
    def scm = [url: "https://github.com/mjhugo/grails-auto-test"]
    def issueManagement = [system: "JIRA", url: "http://jira.grails.org/browse/GPAUTOTEST"]
}
