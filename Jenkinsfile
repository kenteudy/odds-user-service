echo "Build Number ${BUILD_NUMBER}"
def ocp_project
def image_project
def app_name="patient-service"
def image_tag="v1.${BUILD_NUMBER}"

def snapshot_release_number = "1.0-SNAPSHOT"
def release_number
def staging_build_job="odds-staging/odds-patient-service-staging"

//println "Branch Name:" + BRANCH_NAME


try {
   timeout(time: 20, unit: 'MINUTES') {
      node("maven") {
        stage("Initialize") {
        }
                  stage("Checkout") {
          //git url: "${GIT_SOURCE_URL}", branch: "${GIT_SOURCE_REF}", credentialsId: "odds-development-${GIT_SOURCE_SECRET}"
          //git url: "${GIT_SOURCE_URL}", branch: "${GIT_SOURCE_REF}"
          checkout scm

        }
        stage("Build Jar") {
          sh "mvn -s configuration/settings.xml clean install"
          sh "cp target/*.jar ${APP_NAME}.jar"
          stash name:"jar", includes:" ${APP_NAME}.jar"
          junit 'target/surefire-reports/*.xml'
        }
        stage('SonarQube analysis') {
          withSonarQubeEnv('Sonar') {
          // requires SonarQube Scanner for Maven 3.2+
          sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar'
          }
        }
      }
      node {
        stage("Build Image") {
          unstash name:"jar" 
          sh "oc start-build ${APP_NAME}-docker --from-file=${APP_NAME}.jar -n ${PROJECT}"
          openshiftVerifyBuild bldCfg: '${APP_NAME}-docker', waitTime: '20', waitUnit: 'min', namespace: '${PROJECT}'
        }
        stage("Deploy") {
          openshiftDeploy(deploymentConfig: '${APP_NAME}', namespace: '${PROJECT}')
        }
      }
   }
} catch (err) {
   echo "in catch block"
   echo "Caught: ${err}"
   currentBuild.result = 'FAILURE'
   throw err
}
