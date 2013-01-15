# Auto Test plugin for Grails

## Authors

- Björn Wilmsmann, MetaSieve
- Mike Hugo

## Modifications Made By

- Chris Sterling

## Grails support

Version 1.0 of this plugin works with Grails 2.0 or higher. If you have a Grails application using a version of grails prior to 2.0, please use version 0.1 of this plugin

## Usage

```
$ grails
| Enter a script name to run. Use TAB for completion: 
grails> auto-test
```

The plugin will monitor the project directory and will attempt to run only the subset of tests affected by what changed.  This is very rudimentary - for example, if you modify BookController or BookControllerTests or BookControllerSpec, it will run `test-app BookController`

Example usage screencast: http://youtu.be/7qLuDlJqFwY

The `auto-test` command will also honor any extra test arguments passed to it. For example:

    grails> auto-test unit:
    grails> auto-test --rerun unit:spock

In order to run functional tests continually upon changes, the application must be started separately and the "-baseUrl" command line arg must be used to run against already started instance. Here is an example:

```
$ grails
| Enter a script name to run. Use TAB for completion: 
grails> run-app
| Server running. Browse to http://localhost:8080/app
grails> auto-test -baseUrl=http://localhost:8080/app/
```

## Support

Please log issues in the github repository at https://github.com/mjhugo/grails-auto-test/issues  Pull requests with enhancements and bug fixes are greatly appreciated.