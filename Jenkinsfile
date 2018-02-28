import groovy.json.JsonOutput
import java.util.Optional
import hudson.tasks.test.AbstractTestResultAction
import hudson.model.Actionable
import hudson.tasks.junit.CaseResult

def speedUp = '--configure-on-demand --daemon --parallel'
def nebulaReleaseScope = (env.GIT_BRANCH == 'origin/master') ? '' : "-Prelease.scope=patch"
def nebulaRelease = "-x prepare -x release snapshot ${nebulaReleaseScope}"
def gradleDefaultSwitches = "${speedUp} ${nebulaRelease}"
def gradleAdditionalTestTargets = "integrationTest"
def gradleAdditionalSwitches = "shadowJar"
def slackNotificationChannel = "development"
def author = ""
def message = ""
def testSummary = ""
def total = 0
def failed = 0
def skipped = 0

pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                //mvn '-Prelease clean install -DskipTests'
                mvn 'clean install -DskipTests'
            }
        }

        stage('Unit Test') {
            steps {
                //mvn '-Prelease test'
                mvn 'test'
            }
        }

        //stage('Integration Test') {
        //    steps {
        //        mvn 'verify -DskipUnitTests -Parq-wildfly-swarm '
        //    }
        //}
    }
    post {
        always {
            // Archive Unit and integration test results, if any
            junit allowEmptyResults: true,
                    testResults: '**/target/surefire-reports/TEST-*.xml, **/target/failsafe-reports/*.xml'
            mailIfStatusChanged env.EMAIL_RECIPIENTS
        }
        
        failure {
            
            populateGlobalVariables()

            def buildColor = "danger"
            def buildStatus = "Build failed"
            def jobName = "${env.JOB_NAME}"
            // Strip the branch name out of the job name (ex: "Job Name/branch1" -> "Job Name")
            jobName = jobName.getAt(0..(jobName.indexOf('/') - 1))
            def failedTestsString = getFailedTests()
            
            notifySlack("", slackNotificationChannel, [
                    [
                        title: "${jobName}, build #${env.BUILD_NUMBER}",
                        title_link: "${env.BUILD_URL}",
                        color: "${buildColor}",
                        text: "${buildStatus}\n${author}",
                        "mrkdwn_in": ["fields"],
                        fields: [
                            [
                                title: "Branch",
                                value: "${env.GIT_BRANCH}",
                                short: true
                            ],
                            [
                                title: "Test Results",
                                value: "${testSummary}",
                                short: true
                            ],
                            [
                                title: "Last Commit",
                                value: "${message}",
                                short: false
                            ]
                        ]
                    ],
                    [
                        title: "Failed Tests",
                        color: "${buildColor}",
                        text: "${failedTestsString}",
                        "mrkdwn_in": ["text"],
                    ]
                ])          
            // Send Slack-notification if build fails
            //slackSend (color: "danger", message: "BUILD FAILED \nBuild: ${env.JOB_NAME}, Number: ${env.BUILD_NUMBER} \nCommit: ${env.CHANGE_TITLE} by ${env.CHANGE_AUTHOR}")
        }
    }
}

def mailIfStatusChanged(String recipients) {
    // Also send "back to normal" emails. Mailer seems to check build result, but SUCCESS is not set at this point.
    if (currentBuild.currentResult == 'SUCCESS') {
        currentBuild.result = 'SUCCESS'
    }
    step([$class: 'Mailer', recipients: recipients])
}

def mvn(def args) {
    def mvnHome = tool 'M3'
    def javaHome = tool 'JDK8'

    // Apache Maven related side notes:
    // --batch-mode : recommended in CI to inform maven to not run in interactive mode (less logs)
    // -V : strongly recommended in CI, will display the JDK and Maven versions in use.
    //      Very useful to be quickly sure the selected versions were the ones you think.
    // -U : force maven to update snapshots each time (default : once an hour, makes no sense in CI).
    // -Dsurefire.useFile=false : useful in CI. Displays test errors in the logs directly (instead of
    //                            having to crawl the workspace files to see the cause).

    // Advice: don't define M2_HOME in general. Maven will autodetect its root fine.
    // See also
    // https://github.com/jenkinsci/pipeline-examples/blob/master/pipeline-examples/maven-and-jdk-specific-version/mavenAndJdkSpecificVersion.groovy
    withEnv(["JAVA_HOME=${javaHome}", "PATH+MAVEN=${mvnHome}/bin:${env.JAVA_HOME}/bin"]) {
        sh "${mvnHome}/bin/mvn ${args} --batch-mode -V -U -e -Dsurefire.useFile=false"
    }
}

// SLACK WEBHOOK INTEGRATION BELOW 

def isPublishingBranch = { ->
    return env.GIT_BRANCH == 'origin/master' || env.GIT_BRANCH =~ /release.+/
}

def isResultGoodForPublishing = { ->
    return currentBuild.result == null
}

def notifySlack(text, channel, attachments) {
    def slackURL = '[SLACK_WEBHOOK_URL]'
    def jenkinsIcon = 'https://wiki.jenkins-ci.org/download/attachments/2916393/logo.png'

    def payload = JsonOutput.toJson([text: text,
        channel: channel,
        username: "Jenkins",
        icon_url: jenkinsIcon,
        attachments: attachments
    ])

    sh "curl -X POST --data-urlencode \'payload=${payload}\' ${slackURL}"
}

def getGitAuthor = {
    def commit = sh(returnStdout: true, script: 'git rev-parse HEAD')
    author = sh(returnStdout: true, script: "git --no-pager show -s --format='%an' ${commit}").trim()
}

def getLastCommitMessage = {
    message = sh(returnStdout: true, script: 'git log -1 --pretty=%B').trim()
}

@NonCPS
def getTestSummary = { ->
    def testResultAction = currentBuild.rawBuild.getAction(AbstractTestResultAction.class)
    def summary = ""

    if (testResultAction != null) {
        total = testResultAction.getTotalCount()
        failed = testResultAction.getFailCount()
        skipped = testResultAction.getSkipCount()

        summary = "Passed: " + (total - failed - skipped)
        summary = summary + (", Failed: " + failed)
        summary = summary + (", Skipped: " + skipped)
    } else {
        summary = "No tests found"
    }
    return summary
}

@NonCPS
def getFailedTests = { ->
    def testResultAction = currentBuild.rawBuild.getAction(AbstractTestResultAction.class)
    def failedTestsString = "```"

    if (testResultAction != null) {
        def failedTests = testResultAction.getFailedTests()
        
        if (failedTests.size() > 9) {
            failedTests = failedTests.subList(0, 8)
        }

        for(CaseResult cr : failedTests) {
            failedTestsString = failedTestsString + "${cr.getFullDisplayName()}:\n${cr.getErrorDetails()}\n\n"
        }
        failedTestsString = failedTestsString + "```"
    }
    return failedTestsString
}

def populateGlobalVariables = {
    getLastCommitMessage()
    getGitAuthor()
    testSummary = getTestSummary()
}
