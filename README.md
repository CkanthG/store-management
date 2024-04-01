# Store Management Java 17 Application Deployment to Minikube using DockerHub.

## Minikube installation.
Follow this link to install the Minikube on your local computer/laptop.
https://minikube.sigs.k8s.io/docs/start/
After successful installation use "minikube start" command to start your minikube.

## MySQL Installation on minikube cluster.

Follow this link to install MySQL on your local computer/laptop.
https://bobcares.com/blog/mysql-statefulset-helm-chart/

## Build Docker image and push to DockerHub by below commands.

docker build -t store-management:version .

docker tag store-management:latest dockerhubuser/store-management:latest

docker push dockerhubuser/store-management:latest

## Application Deployment on Minikube by using below commands.

kubectl apply -f k8s/store-management-pv.yaml

kubectl apply -f k8s/store-management-deployment.yml





