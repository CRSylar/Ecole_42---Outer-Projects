#!/bin/bash

####
## this script installs ArgoCD CLI and sets up the wil application
###

# check if ARGOCD_REPO_URL is set
if [ -z "$ARGOCD_REPO_URL" ]; then
    echo "ARGOCD_REPO_URL is not set. Please set it to the repository URL where the wil application is located."
    exit 1
fi

# auto port-forward argocd commands to the instance running in 'argocd' namespace
export ARGOCD_OPTS='--port-forward-namespace argocd --port-forward'

# log in with default credentials
ARGOCDPASS=$(kubectl get secret -n argocd argocd-initial-admin-secret -o jsonpath='{.data.password}' | base64 -d)
argocd login --username admin --password $ARGOCDPASS --insecure
[ $? -ne 0 ] && { echo "argocd login failed"; exit 1; }

kubectl create ns dev
argocd app create wil-app --repo $ARGOCD_REPO_URL --path . --dest-server https://kubernetes.default.svc --dest-namespace dev
argocd app set wil-app --sync-policy automated
argocd app wait wil-app

