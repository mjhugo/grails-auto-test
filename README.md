# AutoTest Plugin

Author: Björn Wilmsmann, MetaSieve

## Usage

AutoTest provides an automatic (or continuous) testing feature for Grails. After having installed AutoTest you will be able to run the following new Grails script:

`grails auto-test`

This will start a continuously running Grails test process. Once you modify and save a file within your Grails project unit and integration tests (and as a matter of fact any kind of tests added by plugins) will be run against the new version of the changed file.

This allows you to get continuous feedback on code changes without having to switch between editor and terminal.

AutoTest supports Grails 1.2.x style test phases and types as well:

`grails auto-test unit:`

`grails auto-test :spock`

Please note that while it is possible to run integration tests continuously this can bring about PermGen space OutOfMemoryErrors rather quickly so for now

`grails auto-test unit:`

is the recommended way of using the AutoTest plugin.


## Plugin version history

See http://jira.codehaus.org for more information