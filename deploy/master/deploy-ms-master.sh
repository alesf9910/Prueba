#!/bin/bash

set -x
set -e
set -o pipefail

set -a
. env.aws
set +a


rm Jenkinsfile README.md
sudo docker build -f Dockerfile -t ms-post .

#sudo $(aws ecr get-login --no-include-email --region us-east-1 --profile fyself)
sudo $(docker run --rm \
-e AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_PROD_ACCOUNT \
-e AWS_SECRET_ACCESS_KEY=$AWS_SECRET_KEY_PROD_ACCOUNT \
-e AWS_DEFAULT_REGION=$AWS_REGION \
banst/awscli ecr get-login --no-include-email --region us-east-1)

sudo docker tag ms-post:latest 200946669540.dkr.ecr.us-east-1.amazonaws.com/fyself-ms-post:master

sudo docker push 200946669540.dkr.ecr.us-east-1.amazonaws.com/fyself-ms-post:master

#aws lambda invoke --function-name Restart_Fyself_Services --invocation-type Event --log-type Tail --payload '{"cluster":"Fyself-PROD","service":"ServiceMSContacts"}' logsfile.txt --profile fyself

