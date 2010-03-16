import org.codehaus.groovy.grails.plugins.PluginManagerHolder

scriptEnv = 'test'

includeTargets << grailsScript("_GrailsArgParsing")
includeTargets << grailsScript("_GrailsClean")
includeTargets << grailsScript("_GrailsCompile")
includeTargets << grailsScript("_GrailsInit")
includeTargets << grailsScript("_GrailsPackage")
includeTargets << grailsScript("_GrailsTest")

TEST_PHASE_AND_TYPE_SEPARATOR = ':'

recompileFrequency = System.getProperty("recompile.frequency")
recompileFrequency = recompileFrequency ? recompileFrequency.toInteger() : 3

target(main: "Auto test") {
	depends(checkVersion,
			configureProxy,
			parseArguments,
			cleanTestReports,
			packageApp)

    // The test targeting patterns 
    def testTargeters = []
    
    // The params that target a phase and/or type
    def phaseAndTypeTargeters = []
    
    // Separate the type/phase targeters from the test targeters
    argsMap["params"].each { 
        def destination = it.contains(TEST_PHASE_AND_TYPE_SEPARATOR) ? phaseAndTypeTargeters : testTargeters
        destination << it
    }

    // If we are targeting tests, set testNames (from _GrailsTest)
    if (testTargeters) testNames = testTargeters
    
    // treat pre 1.2 phase targeting args as '«phase»:' for backwards compatibility
    ["unit", "integration", "functional", "other"].each {
        if (argsMap[it]) {
            phaseAndTypeTargeters << "${it}${TEST_PHASE_AND_TYPE_SEPARATOR}"
            argsMap.remove(it) // these are not test "options"
        }
    }
    
    // process the phaseAndTypeTargeters, populating the targetPhasesAndTypes map from _GrailsTest
    phaseAndTypeTargeters.each {
        def parts = it.split(TEST_PHASE_AND_TYPE_SEPARATOR, 2)
        def targetPhase = parts[0] ?: TEST_PHASE_WILDCARD
        def targetType = parts[1] ?: TEST_TYPE_WILDCARD
        
        if (!targetPhasesAndTypes.containsKey(targetPhase)) targetPhasesAndTypes[targetPhase] = []
        targetPhasesAndTypes[targetPhase] << targetType
    }
    
    // Any switch style args are "test options" (from _GrailsTest)
    argsMap.each {
        if (it.key != 'params') {
            testOptions[it.key] = it.value
        }
    }

    if (argsMap["xml"]) {
        reportFormats = [ "xml" ]
        createTestReports = false
    }
    else {
        createTestReports = !argsMap["no-reports"]
    }

    reRunTests = argsMap["rerun"]

	// run tests
    allTests()

	println '---------------------'
	println 'Auto Test is running.'
	println '---------------------'

	Long lastModified = new Date().time
	Boolean autoRecompile = true
	Boolean keepRunning = true
	
    while (keepRunning) {
        if (autoRecompile) {
            lastModified = checkModificationTime(lastModified) {
                try {
					cleanTestReports()
					compile()

                    Thread currentThread = Thread.currentThread()
                    classLoader = new URLClassLoader([classesDir.toURI().toURL()] as URL[], rootLoader)
                    currentThread.setContextClassLoader classLoader
                    PluginManagerHolder.pluginManager = null

                    // reload plugins
                    loadPlugins()

					// run tests
                    allTests()

					println '--------------------------------'
					println 'Tests run. Auto Test is running.'
					println '--------------------------------'
                } catch (Throwable e) {
                    logError("Error running tests", e)
                    exit(1)
                }
            }
        }
        sleep(recompileFrequency * 1000)
	}
}

setDefaultTarget(main)

private checkModificationTime(def lastModified, def callback) {
	def foldersToCheck = [	"${basedir}/grails-app",
							"${basedir}/test",
							"${basedir}/src"]
	
	def tmp = lastModified
	foldersToCheck.each { folderName ->
		def folder = new File(folderName)
		folder.eachDir { file ->
			def fileModificationTime = checkFileModificationTime(file, tmp)

			if (fileModificationTime > tmp) {
				tmp = fileModificationTime
			}
		}
	}
	
    if(lastModified < tmp) {
        try {
            callback()
        }
        catch(Exception e) {
            logError("Error automatically running tests", e)
        }
    }
	lastModified = tmp

    return lastModified
}

private checkFileModificationTime(def file, def tmp) {
	def fileModificationTime
	
	if (file.isDirectory()) {
		for (nestedFile in file.listFiles()) {
			fileModificationTime = checkFileModificationTime(nestedFile, tmp)
			if (fileModificationTime > tmp) {
				return fileModificationTime
			}
		}
	}
	
	fileModificationTime = file.lastModified()
	if (fileModificationTime > tmp) {
		tmp = fileModificationTime
	}
	
	return tmp
}
