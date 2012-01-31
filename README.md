# Auto Test plugin for Grails

## Authors

- Björn Wilmsmann, MetaSieve
- Mike Hugo

## Grails support

Version 1.0 of this plugin will only work with Grails 2.0 or higher.  If you have a Grails application using a version of grails prior to 2.0, please use version 0.1 of this plugin

## Usage

```
grails
auto-test
```

The plugin will monitor the project directory and will attempt to run only the subset of tests affected by what changed.  This is very rudimentary - for example, if you modify BookController or BookControllerTests or BookControllerSpec, it will run `test-app BookController`

Currently, there is no support for running the __entire__ test suite when something is changed (which is the way the plugin behaved in previous versions).  This will be added in a future release.

## Support

Please log issues in the github repository at https://github.com/mjhugo/grails-auto-test/issues  Pull requests with enhancements and bug fixes are greatly appreciated.