#! /bin/bash

helm repo add gitlab https://charts.gitlab.io
helm repo update

# bare minimum configuration for GitLab
helm upgrade --install gitlab gitlab/gitlab \
  -n gitlab \
  -f https://gitlab.com/gitlab-org/charts/gitlab/raw/master/examples/values-minikube-minimum.yaml \
  --set global.hosts.domain=k3d.gitlab.com \
  --set global.hosts.externalIP=0.0.0.0 \
  --set global.hosts.https=false \
  --set global.ingress.class=traefik \
  --timeout 600s

echo -e "\n\e[33mWaiting for gitlab pod to be READY...\e[0m"
sudo kubectl wait --for=condition=ready --timeout=1200s pod -l app=webservice -n gitlab

echo "GitLab installed successfully, please use the browser to create the repo."
echo "!ATTENTION PLEASE!: add this to the hosts file of the local machine ( where vagrant was launched):\n# iot-p3-bonus\n192.168.56.111 gitlab.k3d.gitlab.com "
echo "Than open https://gitlab.k3d.gitlab.com and create the repo."
echo "!WARNING!: must be a PUBLIC REPO"
echo "Initial login credentials:"
echo "Username: root"
echo -n "Password: "
# Get the initial root password from the secret
kubectl -n gitlab get secret gitlab-gitlab-initial-root-password -ojsonpath='{.data.password}' | base64 -d; echo

kubectl -n gitlab port-forward svc/gitlab-webservice-default 8080:8181 &