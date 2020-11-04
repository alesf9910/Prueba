pipeline {
    agent {label 'master'}
	stages {
	    stage('Clean Docker') {
            agent {label 'master'}
            steps {
                sh '''
                    sudo docker image prune -a -f
                '''
            }
        }
	    stage('Deploy Dev') {
	        when {expression { env.BRANCH_NAME ==~ /^(dev|hotfix|bugfix|feature|stagging|release|deploy)(.*)?/ }}
            agent {label 'master'}
			steps {	            
	            sh '''
	            rm Jenkinsfile README.md
	            sudo docker build -f Dockerfile -t ms-post .
	            sudo $(aws ecr get-login --no-include-email --region us-east-1)
	            sudo docker tag ms-post:latest 045641265786.dkr.ecr.us-east-1.amazonaws.com/fyself-ms-post-staging:dev
	            sudo docker push 045641265786.dkr.ecr.us-east-1.amazonaws.com/fyself-ms-post-staging:dev
                aws lambda invoke --function-name Restart_Fyself_Services --invocation-type Event --log-type Tail --payload '{"cluster":"Fyself-DEV","service":"ServiceMSPost"}' logsfile.txt --region us-east-1
	            '''
	            }
        }
        stage('Deploy Prod') {
	        when {expression { env.BRANCH_NAME == 'master' }}
            agent {label 'master'}
	        steps {
	            sh '''
	            rm Jenkinsfile README.md
	            sudo docker build -f Dockerfile -t ms-post .
	            sudo $(aws ecr get-login --no-include-email --region us-east-1 --profile fyself)
	            sudo docker tag ms-post:latest 045641265786.dkr.ecr.us-east-1.amazonaws.com/fyself-ms-post:master
	            sudo docker push 045641265786.dkr.ecr.us-east-1.amazonaws.com/fyself-ms-post:master
                aws lambda invoke --function-name Restart_Fyself_Services --invocation-type Event --log-type Tail --payload '{"cluster":"Fyself-PROD","service":"ServiceMSPost"}' logsfile.txt --region us-east-1
	            '''
	            }
           }
	    }	    
	    
	    
		post
	    {
            success {
                sh '''
                    curl -X POST -H "Content-Type: application/json"  https://api.telegram.org/bot1285826100:AAHHfIvTg2GKf1pvTds_j5Bd6IsEcHg-Q3Y/sendMessage  -d '{"chat_id": "-1001404537016", "text": "The ms-post (master) job execution was successfully", "disable_notification": true}'
                '''
                }
            failure {
                sh '''
                    curl -X POST -H "Content-Type: application/json"  https://api.telegram.org/bot1285826100:AAHHfIvTg2GKf1pvTds_j5Bd6IsEcHg-Q3Y/sendMessage  -d '{"chat_id": "-1001404537016", "text": "The ms-post (master) job execution was failed", "disable_notification": true}'
                '''
                }
            aborted{
                sh '''
                    curl -X POST -H "Content-Type: application/json"  https://api.telegram.org/bot1285826100:AAHHfIvTg2GKf1pvTds_j5Bd6IsEcHg-Q3Y/sendMessage  -d '{"chat_id": "-1001404537016", "text": "The ms-post (master) job execution was aborted", "disable_notification": true}'
                '''
                }
        }
    
}
