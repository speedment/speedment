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
        success {
             // Send Slack-notification if build succeeds
            slackSend (color: "good", message: "Build success\nBuild: ${env.JOB_NAME} #${env.BUILD_NUMBER}\nURL: ${env.BUILD_URL}")
        }
        unstable {
             // Send Slack-notification if build succeeds
            slackSend (color: "warning", message: "Build unstable\nBuild: ${env.JOB_NAME} #${env.BUILD_NUMBER}\nURL: ${env.BUILD_URL}")
        }
        failure {
            // Send Slack-notification if build fails
            slackSend (color: "danger", message: "Build failed\nBuild: ${env.JOB_NAME} #${env.BUILD_NUMBER}\nURL: ${env.BUILD_URL}") 
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
