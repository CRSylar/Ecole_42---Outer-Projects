# Inception-of-Things
## PART 1
#### In this part, you must install VirtualBox, Vagrant, create a [Vagrantfile](https://github.com/CRSylar/Ecole_42---Outer-Projects/blob/main/Inception_of_things/p1/Vagrantfile) which, using the “vagrant up” command, will launch two virtual machines and install k3s as master (cromaldeS) and worker (cromaldeSW). 
1. [Install VirtualBox](https://www.virtualbox.org/wiki/Linux_Downloads)
2. [Install Vagrant](https://developer.hashicorp.com/vagrant/downloads)<br>
&emsp; 2.1 vagrant init - initialize Vagrantfile<br>
&emsp; 2.2 vim Vagrantfile - open Vagrantfile -> [describe the configuration](https://developer.hashicorp.com/vagrant/docs/vagrantfile) -> save -> vagrant up
1. [Installing K3s mater and K3s agent](https://docs.k3s.io/quick-start)<br>

## PART 2

#### To complete this part, you must install VirtualBox, Vagrant, create a [Vagrantfile](https://github.com/CRSylar/Ecole_42---Outer-Projects/blob/main/Inception_of_things/p2/Vagrantfile) which, using the “vagrant up” command, will launch virtual machine and use your [script](https://github.com/CRSylar/Ecole_42---Outer-Projects/blob/main/Inception_of_things/p1/scripts) to install k3s on the machine.

#### The next step is to write some K8S manifests, which will be launched by Vagrant inside the virtual machine.
## this is been automated during the 'vagrant up' step by launching the `setup.sh` script located in /p2/scripts
1. [Deployment](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/) - in Kubernetes manages Pods and ReplicaSets, ensuring the desired number of pod replicas, specified in its configuration, are running and up-to-date. [Volume](https://kubernetes.io/docs/concepts/storage/volumes/) inside Deployment manifest plays a key role in managing data storage and availability within pods. Our pods which contains nginx are linked with our [applications](https://github.com/SavchenkoDV/Inception-of-Things/tree/main/p2/rsc).
2. [Services](https://kubernetes.io/docs/concepts/services-networking/service/) - in Kubernetes acts as a stable network interface to a dynamic set of Pods, facilitating internal or external network communication to these Pods.<br>
3. [Ingress](https://kubernetes.io/docs/concepts/services-networking/ingress/) - in Kubernetes is a way to route external HTTP and HTTPS traffic to internal Services, providing features like URL routing, load balancing, and SSL termination.

The services are exposed as `ClusterIP` to use the traefik ingress (installed by default in k3s)
as the subject requested the services are reachable via an http curl and the Host header to select which service to target, by default (no header or unknown one) the service 3 will be selected

## PART 3

#### For this part we need to install `docker` on the VM and `k3d` (k3s in docker). Than we need to install and setup ArgoCD to deploy an application. The app image is given by the school and is a simple webserver that echo it's image version. Run the ArgoCD [script](https://github.com/CRSylar/Ecole_42---Outer-Projects/blob/main/Inception_of_things/p3/scripts/argocd.sh) which start [manifest](https://github.com/CRSylar/IoT-p3-app/blob/main/manifest.yaml) to deploy the application, using the manifest (see what consist K8S manifest in PART 2) which is located in a separate repository.

## Bonus

#### In the bonus part we used [helm](https://helm.sh/), you need to [install git, helm and deploy gitlab](https://github.com/CRSylar/Ecole_42---Outer-Projects/blob/main/Inception_of_things/bonus/scripts/gitlab.sh) to the k3s cluster. There are other scripts to make easy set up the repo and the manifest file so please follow this steps:
- `vagrant up`
- `vagrant ssh`
- `./scripts/gitlab.sh` ( this can take up to 15 minutes to have al the gitlab pods ready)
- follow the instruction printed by the script
- once the repo is created in gitlab run `./repo-setup.sh` ( this will spawn a pod that clones the repo and fill it with the manifest used in part 3, and push it)
- follow again the instruction printed by the script
- once the app is being deployed by argo, to test the actual CI/CD behavior please run `./scripts/repo-update.sh`  (this will push a small update to the gitlab repo to use the image:v2 of the app instead of the v1)
- as always follow the instruction printed by the script
- when the deployment is up you can simply `curl localhost` to see the log from the app with the image version in use (should be v2)


---
#### Additional Information:
##### Vagrant: 
  ```
- vagrant - vagrant manual
- vagrant init - initialize the Vagrantfile
- vim Vagrantfile - configure Vagrantfile
- vagrant up - raise virtual machines
- vagrant destroy - destroy virtual machines
- vagrant status - shows the current status of virtual machines
- vagrant global status - shows the status of active virtual machines
- vagrant validate - checking the validity of Vagrantfile
- vagrant ssh <machine name> - to connect to the machine via ssh
  ```

##### Kubernetes:
```
- kubectl get all -n [namespace-name] - view all resources in a specific namespace
- kubectl get all --all-namespaces - view all resources in all namespaces
- kubectl get [pod, ingress, or another Kubernetes resource] -n [namespace-name] -o yaml - show YAML manifest information about a specific Kubernetes resource in some namespace
- kubectl describe [pod, ingress, or another essence of k8s] -n [namespace-name] - show detailed information about a specific Kubernetes resource in some namespace
- kubectl exec -it [pod-name] -- /bin/sh - access the Pod
```
---