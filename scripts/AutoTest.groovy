import grails.build.logging.GrailsConsole
import groovy.transform.Synchronized
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor
import org.apache.commons.io.monitor.FileAlterationMonitor
import org.apache.commons.io.monitor.FileAlterationObserver
import org.codehaus.groovy.grails.cli.GrailsScriptRunner
import org.codehaus.groovy.grails.cli.interactive.InteractiveMode

includeTargets << grailsScript("_GrailsSettings")
includeTargets << grailsScript("_GrailsTest")
includeTargets << grailsScript("_GrailsCompile")

target(default: "autotest") {

    File baseProjectDirectory = new File(basedir)
    long interval = 2000
    FileListener listener = new FileListener()

    FileAlterationObserver observer = new FileAlterationObserver(baseProjectDirectory);
    observer.addListener(listener)
    observer.initialize()

    GrailsConsole.getInstance().updateStatus "listening for changes to directory ${baseProjectDirectory}"

    FileAlterationMonitor monitor = new FileAlterationMonitor(interval);
    monitor.addObserver(observer);
    monitor.start();

}
class FileListener extends FileAlterationListenerAdaptor {
    Set testsToRun = []

    @Synchronized
    void onFileChange(File file) {
        addTestName(file)
    }

    @Override
    void onFileCreate(File file) {
        addTestName(file)
    }

    private addTestName(File file) {
        if (file.name.endsWith('.groovy') || file.name.endsWith('.java')) {
            String test = file.name.replaceAll(/.groovy|.java/, '').replaceAll(/Tests\z|Test\z|Spec\z/, '')
            testsToRun << test
        }
    }

    @Override
    @Synchronized
    void onStop(FileAlterationObserver observer) {
        if (testsToRun) {
            String command = "test-app ${testsToRun.join(' ')}"
            GrailsConsole.getInstance().updateStatus("${testsToRun.size()} files modified, running test-app command with: ${command}")

            def parser = GrailsScriptRunner.getCommandLineParser()
            def commandLine = parser.parseString(command)
            InteractiveMode.current.scriptRunner.executeScriptWithCaching(commandLine)

            testsToRun = []
        }
    }

    @Override
    void onStart(FileAlterationObserver observer) {
        testsToRun = []
    }


}