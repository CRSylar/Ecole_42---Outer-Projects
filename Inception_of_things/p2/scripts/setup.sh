kubectl create configmap static-1 --from-file=confs/app1/index.html
kubectl create configmap static-2 --from-file=confs/app2/index.html
kubectl create configmap static-3 --from-file=confs/app3/index.html

kubectl create -f confs/app-1.yaml
kubectl create -f confs/app-2.yaml
kubectl create -f confs/app-3.yaml

kubectl create -f confs/ingress.yaml

sudo apt update
sudo apt install -y curl