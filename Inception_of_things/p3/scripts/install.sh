#!/bin/bash

####
## install dependencies, curl, git, docker, kubectl, k3d, argocd
###

sudo apt-get-update

command -v curl || { 
    echo "curl could not be found, installing..."
    sudo apt-get install -y curl
}

command -v git || { 
	echo "git could not be found, installing..."
	sudo apt-get install -y git
}

command -v docker || { 
	# install docker
    echo "docker could not be found, installing..."
    sudo apt-get install -y ca-certificates
    sudo install -m 0755 -d /etc/apt/keyrings
    sudo curl -fsSL https://download.docker.com/linux/debian/gpg -o /etc/apt/keyrings/docker.asc
    sudo chmod a+r /etc/apt/keyrings/docker.asc

    # Add the repository to Apt sources:
    echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/debian $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
      sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

	sudo apt-get update
    sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
}

sudo systemctl enable docker
sudo systemctl start docker

command -v kubectl || {
	# install kubectl
    echo "kubectl could not be found, installing..."
    curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
    sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
    rm kubectl
}


curl -s https://raw.githubusercontent.com/k3d-io/k3d/main/install.sh | bash
[ $? -ne 0 ] && { echo "k3d installation failed"; exit 1; }

curl -sSL -o argocd-linux-amd64 https://github.com/argoproj/argo-cd/releases/latest/download/argocd-linux-amd64
sudo install -m 555 argocd-linux-amd64 /usr/local/bin/argocd
rm argocd-linux-amd64

####
## Create cluster and install argocd
###

sudo k3d cluster create iot-cluster -p "80:80@loadbalancer"
[ $? -ne 0 ] && { echo "k3d cluster creation failed"; exit 1; }

mkdir -p .kube
sudo k3d kubeconfig get iot-cluster > .kube/config

kubectl create namespace argocd
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
kubectl rollout status deployment -n argocd argocd-server --timeout=90s
