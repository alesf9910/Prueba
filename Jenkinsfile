pipeline {
    agent {label 'master'}
	stages {
	    stage('Deploy Dev') {
	        when {expression { env.BRANCH_NAME ==~ /^(dev|hotfix|bugfix|feature|stagging|release|deploy)(.*)?/ }}
			    agent any
			steps {	            
	            sh '''
	            rm Jenkinsfile README.md
	            sudo docker build -f Dockerfile -t ms-contacts .
	            sudo $(aws ecr get-login --no-include-email --region us-east-1)
	            sudo docker tag ms-contacts:latest 045641265786.dkr.ecr.us-east-1.amazonaws.com/fyself-ms-contacts-staging:dev
	            sudo docker push 045641265786.dkr.ecr.us-east-1.amazonaws.com/fyself-ms-contacts-staging:dev
                aws lambda invoke --function-name Restart_Fyself_Services --invocation-type Event --log-type Tail --payload '{"cluster":"Fyself-DEV","service":"ServiceMSPost"}' logsfile.txt
	            '''
	            }
        }
        stage('Deploy Prod') {
	        when {expression { env.BRANCH_NAME == 'master' }}
	            agent any
	        steps {
	            sh '''
	            rm Jenkinsfile README.md
	            sudo docker build -f Dockerfile -t ms-contacts .
	            sudo $(aws ecr get-login --no-include-email --region us-east-1 --profile fyself)
	            sudo docker tag ms-contacts:latest 045641265786.dkr.ecr.us-east-1.amazonaws.com/fyself-ms-post:master
	            sudo docker push 045641265786.dkr.ecr.us-east-1.amazonaws.com/fyself-ms-post:master
                aws lambda invoke --function-name Restart_Fyself_Services --invocation-type Event --log-type Tail --payload '{"cluster":"Fyself-PROD","service":"ServiceMSPost"}' logsfile.txt --profile fyself
	            '''
	            }
           }
	    }	    
	    
	    
	post
	    {
            success {
                sh '''
                    curl -X POST -H "Content-Type: application/json"  https://api.telegram.org/bot905252129:AAFuPgw9SkDY355FzFP8tFbxaMNGRiu5Fgk/sendMessage  -d '{"chat_id": "-1001202201666", "text": "The ms-post job execution was successfully", "disable_notification": true}'
                '''
                }
            failure {
                sh '''
                    curl -X POST -H "Content-Type: application/json"  https://api.telegram.org/bot905252129:AAFuPgw9SkDY355FzFP8tFbxaMNGRiu5Fgk/sendMessage  -d '{"chat_id": "-1001202201666", "text": "The ms-post job execution was failed", "disable_notification": true}'
                '''
                }
            aborted{
                sh '''
                    curl -X POST -H "Content-Type: application/json"  https://api.telegram.org/bot905252129:AAFuPgw9SkDY355FzFP8tFbxaMNGRiu5Fgk/sendMessage  -d '{"chat_id": "-1001202201666", "text": "The ms-post job execution was aborted", "disable_notification": true}'
                '''
                }
        }
    
}


