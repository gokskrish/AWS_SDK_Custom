#!groovy
node() {
    
    env.JAVA_HOME = "/opt/java/jdk1.8.0_162"

    stage('Download AWSApp Sources') { 
        sh "rm -rf *"
        echo "1. Download Sources for Server"
        sh "git clone https://github.com/gokskrish/AWS_SDK_Custom.git"
    }

    stage('Run Code Analysis') { 
        echo "2. Run Code Analysis"
        dir("AWS_SDK_Custom") {
            sh "mvn clean"
            sh "mvn checkstyle:checkstyle"
        }
        step([$class: 'hudson.plugins.checkstyle.CheckStylePublisher', pattern: '**/target/checkstyle-result.xml', healthy:'20', unHealthy:'100'])
    }
    
    stage('Build Jar') { 
        echo "3. Build Jar"
        dir("AWS_SDK_Custom") {
            sh "mvn install"
        }
        step([$class: 'JacocoPublisher', execPattern: '**/target/jacoco.exec', healthy:'20', unHealthy:'100'])
    }
    
    stage('Build Docker') { 
        echo "4. Build Docker - TODO"
    }
    
    stage('Upload to Docker Hub') { 
        echo "5. Upload to Docker Hub --TODO"
    }
}