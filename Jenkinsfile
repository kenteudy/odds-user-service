echo "Build Number ${BUILD_NUMBER}"
def project
def project="odds-development"
def app_name="user-service"
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
          sh "cp target/*.jar ${app_name}.jar"
          stash name:"jar", includes:" ${app_name}.jar"
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
          sh "oc start-build ${app_name}-docker --from-file=${app_name}.jar -n ${project}"
          openshiftVerifyBuild bldCfg: '${app_name}-docker', waitTime: '20', waitUnit: 'min', namespace: '${project}'
        }
        stage("Deploy") {
          openshiftDeploy(deploymentConfig: '${app_name}', namespace: '${project}')
        }
      }
   }
} catch (err) {
   echo "in catch block"
   echo "Caught: ${err}"
   currentBuild.result = 'FAILURE'
   throw err
}
