pipeline {
    agent {label 'master'}
        environment {
        AWS_ACCESS_KEY_PROD_ACCOUNT = credentials('FYSELF_AWS_ACCESS_KEY_PROD_ACCOUNT')
        AWS_SECRET_KEY_PROD_ACCOUNT = credentials('FYSELF_AWS_SECRET_KEY_PROD_ACCOUNT')
    }

	stages {
	    stage('Deploy Dev') {
	        when {expression { env.BRANCH_NAME ==~ /^(dev|hotfix|bugfix|feature|stagging|release|deploy)(.*)?/ }}
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
	        steps {
                sh '''
                          cat <<EOF >>env.aws
AWS_ACCESS_KEY_PROD_ACCOUNT=$AWS_ACCESS_KEY_PROD_ACCOUNT
AWS_SECRET_KEY_PROD_ACCOUNT=$AWS_SECRET_KEY_PROD_ACCOUNT
AWS_REGION=us-east-1
EOF
                        '''
	            sh "bash -v deploy/master/deploy-ms-master.sh"

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
