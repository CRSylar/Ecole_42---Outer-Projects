kubectl delete cm static-{1,2,3} 

kubectl delete -f confs/app-1.yaml
kubectl delete -f confs/app-2.yaml
kubectl delete -f confs/app-3.yaml

kubectl delete -f confs/ingress.yaml
