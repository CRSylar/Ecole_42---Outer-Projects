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

sed -i 's/wil42\/playground:v1/wil42\/playground:v2/g' manifest.yaml
git config --local credential.helper store
git add .
git commit -m "Upgraded image version"
git push -u origin main
echo "Manifest updated and pushed successfully"
echo "Now let's wait for ARGOCD to update the pod, you can use 'kubectl -n dev get po -w'"