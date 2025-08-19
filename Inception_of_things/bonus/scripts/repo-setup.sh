#! /bin/bash

# ask for the repo name
read -p "Enter the repository name: " REPO_NAME
# ask for the repo group name
read -p "Enter the repository group name: " REPO_GROUP

GITLAB_PASS=$(kubectl -n gitlab get secret gitlab-gitlab-initial-root-password -ojsonpath='{.data.password}' | base64 -d)
# spawn a pod to clone the repo
kubectl run -i --rm debug --image=alpine --restart=Never -- sh <<EOF
apk add --no-cache git
git clone http://root:$GITLAB_PASS@gitlab-webservice-default.gitlab.svc.cluster.local:8181/$REPO_GROUP/$REPO_NAME.git
git config --global user.name "cromalde"
git config --global user.email "cromalde@42roma.it"

cd $REPO_NAME

#create a manifest.yaml file
cat <<FIN > manifest.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  creationTimestamp: null
  labels:
    app: dep
  name: dep
spec:
  replicas: 1
  selector:
    matchLabels:
      app: dep
  strategy: {}
  template:
    metadata:
      creationTimestamp: null
      labels:
        app: dep
    spec:
      containers:
      - image: wil42/playground:v1
        name: playground
        resources: {}
status: {}

---
# service
apiVersion: v1
kind: Service
metadata:
  creationTimestamp: null
  labels:
    app: dep
  name: dep
spec:
  ports:
  - port: 80
    protocol: TCP
    targetPort: 8888
  selector:
    app: dep

---
# ingress
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  creationTimestamp: null
  name: dep
spec:
  rules:
  - http:
      paths:
      - backend:
          service:
            name: dep
            port:
              number: 80
        path: /
        pathType: Prefix
FIN

git config --local credential.helper store
git add .
git commit -m "BATMAN"
git push -u origin main
echo "Manifest file created and pushed successfully."
echo "now please run 'export ARGOCD_REPO_URL=http://gitlab-webservice-default.gitlab.svc.cluster.local:8181/$REPO_GROUP/$REPO_NAME.git'"
echo "Then launch the argocd.sh script to deploy the application."
EOF
