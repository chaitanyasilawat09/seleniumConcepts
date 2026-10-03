# Docker & Kubernetes - Complete Interview Preparation Guide

---

## TABLE OF CONTENTS
1. Containerization Fundamentals
2. Docker Fundamentals
3. Docker Installation and Setup
4. Docker Images
5. Docker Containers
6. Dockerfile
7. Docker Compose
8. Docker Volumes
9. Docker Networks
10. Docker Registry
11. Kubernetes Fundamentals
12. Kubernetes Architecture
13. Kubernetes Installation
14. Pods
15. Deployments
16. Services
17. ConfigMaps and Secrets
18. Namespaces
19. Ingress
20. Storage in Kubernetes
21. Kubernetes Scaling
22. Kubernetes Updates and Rollbacks
23. Kubernetes Monitoring
24. Docker vs Kubernetes
25. Common Interview Questions

---

## 1. CONTAINERIZATION FUNDAMENTALS

### What is Containerization?
Containerization is a lightweight form of virtualization that packages applications and their dependencies into isolated units called containers. Containers share the host OS kernel but run in isolated user space.

### Containers vs Virtual Machines

| Feature | Containers | Virtual Machines |
|---------|-----------|------------------|
| OS | Shared host kernel | Separate guest OS |
| Size | Small (MB) | Large (GB) |
| Startup Time | Seconds | Minutes |
| Performance | Near native | Overhead |
| Isolation | Process-level | Hardware-level |
| Portability | High | Medium |

### Benefits of Containerization

- **Consistency**: Same environment everywhere
- **Portability**: Run anywhere
- **Efficiency**: Lightweight, fast startup
- **Scalability**: Easy to scale
- **Isolation**: Process isolation
- **Resource Efficiency**: Shared kernel

### Container Runtime

**Popular Runtimes:**
- Docker
- containerd
- CRI-O
- runc

### Interview Questions

**Q1: What is containerization?**
- Lightweight virtualization
- Packages app and dependencies
- Shares host kernel
- Isolated user space

**Q2: What is the difference between containers and VMs?**
- Containers: Shared kernel, lightweight
- VMs: Separate OS, heavy
- Containers: Fast startup
- VMs: Slow startup

**Q3: What are the benefits of containerization?**
- Consistency across environments
- Portability
- Efficiency
- Scalability
- Isolation

---

## 2. DOCKER FUNDAMENTALS

### What is Docker?
Docker is a platform for developing, shipping, and running applications in containers. It provides tools to build, deploy, and manage containers.

### Docker Architecture

```
Docker Client
    ↓
Docker Daemon
    ↓
Container Images
    ↓
Container Runtime
    ↓
Containers
```

### Docker Components

**Docker Engine:**
- Docker Daemon
- Docker CLI
- Docker API

**Docker Hub:**
- Public registry
- Pre-built images
- Image storage

**Docker Compose:**
- Multi-container apps
- YAML configuration
- Easy orchestration

### Docker vs Other Container Tools

| Feature | Docker | Podman | containerd |
|---------|--------|--------|------------|
| Daemon | Yes | No | Yes |
| Rootless | Limited | Yes | Yes |
| Compose | Yes | Limited | No |
| Learning Curve | Easy | Medium | Hard |

### Interview Questions

**Q1: What is Docker?**
- Container platform
- Build, ship, run containers
- Provides tools for container management
- Industry standard

**Q2: What are the components of Docker?**
- Docker Engine: Daemon, CLI, API
- Docker Hub: Registry
- Docker Compose: Multi-container
- Docker Swarm: Orchestration

**Q3: What is the difference between Docker and Podman?**
- Docker: Uses daemon
- Podman: Daemonless
- Docker: Root by default
- Podman: Rootless

---

## 3. DOCKER INSTALLATION AND SETUP

### Installation

**Linux (Ubuntu/Debian):**
```bash
# Update package index
sudo apt update

# Install Docker
sudo apt install docker.io

# Start Docker
sudo systemctl start docker
sudo systemctl enable docker

# Add user to docker group
sudo usermod -aG docker $USER
```

**macOS:**
```bash
# Download Docker Desktop
# Or use Homebrew
brew install --cask docker
```

**Windows:**
- Download Docker Desktop
- Run installer
- Enable WSL 2

### Verify Installation

```bash
# Check Docker version
docker --version

# Check Docker info
docker info

# Run test container
docker run hello-world
```

### Docker Configuration

```bash
# Configure daemon
sudo nano /etc/docker/daemon.json

{
  "registry-mirrors": ["https://mirror.gcr.io"],
  "log-driver": "json-file",
  "log-opts": {
    "max-size": "10m",
    "max-file": "3"
  }
}

# Restart Docker
sudo systemctl restart docker
```

### Interview Questions

**Q1: How do you install Docker on Linux?**
- apt install docker.io
- Start and enable service
- Add user to docker group
- Verify with docker run hello-world

**Q2: How do you verify Docker installation?**
- docker --version
- docker info
- docker run hello-world
- Check if service is running

**Q3: How do you configure Docker daemon?**
- Edit /etc/docker/daemon.json
- Add configuration
- Restart Docker service
- Apply changes

---

## 4. DOCKER IMAGES

### Pull Image

```bash
# Pull image from registry
docker pull ubuntu:latest

# Pull specific version
docker pull ubuntu:20.04

# Pull without version (latest)
docker pull ubuntu
```

### List Images

```bash
# List all images
docker images

# List with details
docker image ls

# List dangling images
docker images -f dangling=true
```

### Build Image

```bash
# Build from Dockerfile
docker build -t myapp:1.0 .

# Build with context
docker build -t myapp:1.0 -f Dockerfile .

# Build with build args
docker build --build-arg VERSION=1.0 -t myapp .
```

### Remove Image

```bash
# Remove specific image
docker rmi myapp:1.0

# Force remove
docker rmi -f myapp:1.0

# Remove all dangling images
docker image prune

# Remove all unused images
docker image prune -a
```

### Image Layers

```bash
# View image history
docker history myapp:1.0

# Inspect image
docker inspect myapp:1.0
```

### Interview Questions

**Q1: What is a Docker image?**
- Read-only template
- Contains application and dependencies
- Built from Dockerfile
- Stored in registry

**Q2: How do you build a Docker image?**
- docker build -t name:tag .
- Requires Dockerfile
- Can use build args
- Creates layered image

**Q3: What are Docker image layers?**
- Read-only layers
- Each instruction creates layer
- Caching for faster builds
- Shared between images

---

## 5. DOCKER CONTAINERS

### Run Container

```bash
# Run container
docker run ubuntu

# Run with name
docker run --name mycontainer ubuntu

# Run in background
docker run -d ubuntu

# Run with command
docker run ubuntu echo "Hello World"

# Run interactive
docker run -it ubuntu bash
```

### List Containers

```bash
# List running containers
docker ps

# List all containers
docker ps -a

# List with size
docker ps -s
```

### Stop Container

```bash
# Stop container
docker stop mycontainer

# Force stop
docker kill mycontainer

# Stop all containers
docker stop $(docker ps -q)
```

### Remove Container

```bash
# Remove container
docker rm mycontainer

# Remove running container
docker rm -f mycontainer

# Remove all stopped containers
docker container prune
```

### Container Logs

```bash
# View logs
docker logs mycontainer

# Follow logs
docker logs -f mycontainer

# View last N lines
docker logs --tail 100 mycontainer
```

### Execute Command in Container

```bash
# Execute command
docker exec mycontainer ls

# Execute interactive
docker exec -it mycontainer bash

# Execute as user
docker exec -u root mycontainer command
```

### Interview Questions

**Q1: What is a Docker container?**
- Running instance of image
- Isolated process
- Has writable layer
- Can be started/stopped

**Q2: How do you run a Docker container?**
- docker run image
- Can add flags: -d, -it, --name
- Can specify command
- Example: docker run -d --name web nginx

**Q3: How do you execute commands in running container?**
- docker exec container command
- docker exec -it container bash
- Can specify user
- Example: docker exec -it web bash

---

## 6. DOCKERFILE

### Basic Dockerfile

```dockerfile
FROM ubuntu:20.04

RUN apt update && apt install -y nginx

COPY index.html /var/www/html/

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
```

### Dockerfile Instructions

**FROM:**
```dockerfile
FROM ubuntu:20.04
FROM node:18
FROM maven:3.8.1-openjdk-11
```

**RUN:**
```dockerfile
RUN apt update
RUN apt install -y nginx
RUN apt update && apt install -y nginx
```

**COPY:**
```dockerfile
COPY file.txt /app/
COPY *.txt /app/
COPY src/ /app/src/
```

**ADD:**
```dockerfile
ADD file.txt /app/
ADD archive.tar.gz /app/
ADD url/file.txt /app/
```

**ENV:**
```dockerfile
ENV APP_ENV=production
ENV PATH="/app/bin:${PATH}"
```

**EXPOSE:**
```dockerfile
EXPOSE 80
EXPOSE 8080 8443
```

**CMD:**
```dockerfile
CMD ["nginx", "-g", "daemon off;"]
CMD nginx -g daemon off;
```

**ENTRYPOINT:**
```dockerfile
ENTRYPOINT ["nginx"]
ENTRYPOINT ["nginx", "-g", "daemon off;"]
```

**WORKDIR:**
```dockerfile
WORKDIR /app
WORKDIR /app/src
```

### Multi-Stage Build

```dockerfile
# Build stage
FROM maven:3.8.1-openjdk-11 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package

# Runtime stage
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
```

### Interview Questions

**Q1: What is a Dockerfile?**
- Text file with instructions
- Used to build images
- Contains build steps
- Automated image creation

**Q2: What are common Dockerfile instructions?**
- FROM: Base image
- RUN: Execute commands
- COPY: Copy files
- CMD: Default command
- EXPOSE: Expose ports

**Q3: What is multi-stage build?**
- Multiple FROM instructions
- Separate build and runtime
- Smaller final image
- Better security

---

## 7. DOCKER COMPOSE

### docker-compose.yml

```yaml
version: '3.8'

services:
  web:
    image: nginx:latest
    ports:
      - "80:80"
    volumes:
      - ./html:/usr/share/nginx/html
    networks:
      - app-network
  
  db:
    image: postgres:15
    environment:
      POSTGRES_PASSWORD: secret
    volumes:
      - db-data:/var/lib/postgresql/data
    networks:
      - app-network

volumes:
  db-data:

networks:
  app-network:
    driver: bridge
```

### Compose Commands

```bash
# Start services
docker-compose up

# Start in background
docker-compose up -d

# Stop services
docker-compose down

# Stop and remove volumes
docker-compose down -v

# View logs
docker-compose logs

# Follow logs
docker-compose logs -f

# View specific service logs
docker-compose logs web
```

### Scale Services

```bash
# Scale service
docker-compose up -d --scale web=3

# Scale with compose file
docker-compose -f docker-compose.yml up -d --scale web=3
```

### Interview Questions

**Q1: What is Docker Compose?**
- Multi-container tool
- YAML configuration
- Define services
- Easy orchestration

**Q2: How do you start services with Docker Compose?**
- docker-compose up
- docker-compose up -d (background)
- docker-compose down (stop)
- docker-compose logs (view logs)

**Q3: What is the difference between Docker and Docker Compose?**
- Docker: Single container
- Compose: Multiple containers
- Docker: CLI commands
- Compose: YAML file

---

## 8. DOCKER VOLUMES

### Create Volume

```bash
# Create volume
docker volume create myvolume

# List volumes
docker volume ls

# Inspect volume
docker volume inspect myvolume
```

### Use Volume

```bash
# Run with volume
docker run -v myvolume:/data ubuntu

# Run with bind mount
docker run -v /host/path:/container/path ubuntu

# Run with read-only volume
docker run -v myvolume:/data:ro ubuntu
```

### Remove Volume

```bash
# Remove volume
docker volume rm myvolume

# Remove unused volumes
docker volume prune
```

### Docker Compose Volumes

```yaml
version: '3.8'

services:
  db:
    image: postgres:15
    volumes:
      - db-data:/var/lib/postgresql/data
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql

volumes:
  db-data:
    driver: local
```

### Interview Questions

**Q1: What is a Docker volume?**
- Persistent storage
- Survives container deletion
- Managed by Docker
- Can be shared

**Q2: What is the difference between volume and bind mount?**
- Volume: Managed by Docker
- Bind mount: Host directory
- Volume: More portable
- Bind mount: Faster

**Q3: How do you use volumes in Docker Compose?**
- Define in volumes section
- Reference in service
- Can use named volumes
- Can use bind mounts

---

## 9. DOCKER NETWORKS

### Network Types

**Bridge:**
```bash
# Default network
docker network create mynetwork

# Connect container
docker network connect mynetwork mycontainer

# Disconnect container
docker network disconnect mynetwork mycontainer
```

**Host:**
```bash
# Use host network
docker run --network host ubuntu
```

**None:**
```bash
# No network
docker run --network none ubuntu
```

### List Networks

```bash
# List networks
docker network ls

# Inspect network
docker network inspect bridge
```

### Docker Compose Networks

```yaml
version: '3.8'

services:
  web:
    image: nginx
    networks:
      - frontend
  
  db:
    image: postgres
    networks:
      - backend

networks:
  frontend:
    driver: bridge
  backend:
    driver: bridge
```

### Interview Questions

**Q1: What are Docker network types?**
- Bridge: Default, isolated
- Host: Share host network
- None: No network
- Overlay: Multi-host

**Q2: What is the default Docker network?**
- Bridge network
- Created automatically
- Containers can communicate
- Isolated from host

**Q3: How do containers communicate?**
- Same network: By name
- Different networks: Need connection
- Host network: Direct access
- Port mapping: External access

---

## 10. DOCKER REGISTRY

### Docker Hub

```bash
# Login to Docker Hub
docker login

# Logout
docker logout

# Push image
docker push username/myapp:1.0

# Pull image
docker pull username/myapp:1.0
```

### Private Registry

```bash
# Login to private registry
docker login registry.example.com

# Push to private registry
docker tag myapp:1.0 registry.example.com/myapp:1.0
docker push registry.example.com/myapp:1.0

# Pull from private registry
docker pull registry.example.com/myapp:1.0
```

### Docker Compose with Registry

```yaml
version: '3.8'

services:
  app:
    image: registry.example.com/myapp:1.0
    imagePullSecrets:
      - registry-credentials
```

### Interview Questions

**Q1: What is a Docker registry?**
- Image storage
- Public or private
- Docker Hub is default
- Can host own registry

**Q2: How do you push an image to Docker Hub?**
- docker login
- docker tag image username/name:tag
- docker push username/name:tag
- Requires authentication

**Q3: What is the difference between public and private registry?**
- Public: Anyone can pull
- Private: Authentication required
- Public: Docker Hub
- Private: Self-hosted or cloud

---

## 11. KUBERNETES FUNDAMENTALS

### What is Kubernetes?
Kubernetes (K8s) is an open-source container orchestration platform for automating deployment, scaling, and management of containerized applications.

### Kubernetes Features

- **Automated rollouts and rollbacks**
- **Service discovery and load balancing**
- **Self-healing**
- **Horizontal scaling**
- **Secret and configuration management**
- **Storage orchestration**
- **Batch execution**

### Kubernetes Architecture

```
Master Node (Control Plane)
├── API Server
├── etcd (Key-Value Store)
├── Scheduler
├── Controller Manager
└── Cloud Controller Manager

Worker Nodes
├── Kubelet
├── Kube-proxy
└── Container Runtime (Docker, containerd)
```

### Kubernetes vs Docker Swarm

| Feature | Kubernetes | Docker Swarm |
|---------|-----------|--------------|
| Complexity | High | Low |
| Scalability | High | Medium |
| Auto-scaling | Yes | No |
| Load Balancing | Built-in | External |
| Learning Curve | Steep | Easy |

### Interview Questions

**Q1: What is Kubernetes?**
- Container orchestration platform
- Automates deployment, scaling
- Manages containerized apps
- Open-source by Google

**Q2: What are the key features of Kubernetes?**
- Automated rollouts/rollbacks
- Service discovery
- Self-healing
- Horizontal scaling
- Secret management

**Q3: What is the difference between Kubernetes and Docker Swarm?**
- Kubernetes: Complex, powerful
- Swarm: Simple, limited
- Kubernetes: Auto-scaling
- Swarm: Manual scaling

---

## 12. KUBERNETES ARCHITECTURE

### Control Plane Components

**API Server:**
- Frontend for control plane
- Exposes REST API
- Authenticates and authorizes

**etcd:**
- Key-value store
- Stores cluster state
- Consistent and highly available

**Scheduler:**
- Assigns pods to nodes
- Considers resources
- Constraints and affinities

**Controller Manager:**
- Runs controller processes
- Maintains desired state
- Node controller, replication controller

### Worker Node Components

**Kubelet:**
- Agent on each node
- Communicates with API server
- Manages pods and containers

**Kube-proxy:**
- Network proxy
- Implements service abstraction
- Manages network rules

**Container Runtime:**
- Runs containers
- Docker, containerd, CRI-O
- Implements CRI

### Interview Questions

**Q1: What are the components of Kubernetes control plane?**
- API Server
- etcd
- Scheduler
- Controller Manager
- Cloud Controller Manager

**Q2: What is the role of Kubelet?**
- Agent on worker nodes
- Communicates with API server
- Manages pods and containers
- Reports node status

**Q3: What is etcd in Kubernetes?**
- Key-value store
- Stores cluster state
- Consistent and available
- Used by all components

---

## 13. KUBERNETES INSTALLATION

### Minikube (Local Development)

```bash
# Install Minikube
# macOS
brew install minikube

# Linux
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube

# Start Minikube
minikube start

# Check status
minikube status

# Stop Minikube
minikube stop
```

### kubectl (CLI Tool)

```bash
# Install kubectl
# macOS
brew install kubectl

# Linux
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install kubectl /usr/local/bin/

# Verify installation
kubectl version --client

# Get cluster info
kubectl cluster-info
```

### Interview Questions

**Q1: How do you install Kubernetes locally?**
- Use Minikube
- Use Kind (Kubernetes in Docker)
- Use Docker Desktop
- Use k3s

**Q2: What is Minikube?**
- Local Kubernetes
- Single-node cluster
- For development
- Easy to set up

**Q3: What is kubectl?**
- Kubernetes CLI tool
- Manage clusters
- Deploy applications
- Inspect resources

---

## 14. PODS

### Pod Definition

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: mypod
  labels:
    app: myapp
spec:
  containers:
  - name: mycontainer
    image: nginx:latest
    ports:
    - containerPort: 80
```

### Pod Commands

```bash
# Create pod
kubectl apply -f pod.yaml

# List pods
kubectl get pods

# Describe pod
kubectl describe pod mypod

# Delete pod
kubectl delete pod mypod

# View pod logs
kubectl logs mypod

# Execute command in pod
kubectl exec -it mypod -- bash
```

### Multi-Container Pod

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: multi-container-pod
spec:
  containers:
  - name: web
    image: nginx
  - name: sidecar
    image: redis
```

### Interview Questions

**Q1: What is a Kubernetes pod?**
- Smallest deployable unit
- Contains one or more containers
- Shared network and storage
- Ephemeral by nature

**Q2: What is a multi-container pod?**
- Pod with multiple containers
- Share network namespace
- Share storage volumes
- Sidecar pattern

**Q3: Why would you use multiple containers in a pod?**
- Sidecar pattern
- Logging and monitoring
- Proxy containers
- Helper processes

---

## 15. DEPLOYMENTS

### Deployment Definition

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp-deployment
spec:
  replicas: 3
  selector:
    matchLabels:
      app: myapp
  template:
    metadata:
      labels:
        app: myapp
    spec:
      containers:
      - name: myapp
        image: myapp:1.0
        ports:
        - containerPort: 8080
```

### Deployment Commands

```bash
# Create deployment
kubectl apply -f deployment.yaml

# List deployments
kubectl get deployments

# Scale deployment
kubectl scale deployment myapp-deployment --replicas=5

# Update deployment
kubectl set image deployment/myapp-deployment myapp=myapp:2.0

# Rollout status
kubectl rollout status deployment/myapp-deployment

# Rollback deployment
kubectl rollout undo deployment/myapp-deployment
```

### Deployment Strategies

**Rolling Update:**
```yaml
spec:
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 1
      maxUnavailable: 0
```

**Recreate:**
```yaml
spec:
  strategy:
    type: Recreate
```

### Interview Questions

**Q1: What is a Kubernetes deployment?**
- Manages pods
- Provides updates and rollbacks
- Maintains desired state
- Self-healing

**Q2: How do you scale a deployment?**
- kubectl scale deployment --replicas=N
- Can use horizontal pod autoscaler
- Manual or automatic scaling
- Example: kubectl scale deployment myapp --replicas=5

**Q3: What are deployment strategies?**
- RollingUpdate: Gradual replacement
- Recreate: Stop all, start new
- RollingUpdate is default
- Recreate for quick updates

---

## 16. SERVICES

### Service Definition

```yaml
apiVersion: v1
kind: Service
metadata:
  name: myapp-service
spec:
  selector:
    app: myapp
  ports:
  - protocol: TCP
    port: 80
    targetPort: 8080
  type: LoadBalancer
```

### Service Types

**ClusterIP:**
```yaml
spec:
  type: ClusterIP
  # Default, internal only
```

**NodePort:**
```yaml
spec:
  type: NodePort
  ports:
  - port: 80
    targetPort: 8080
    nodePort: 30000
```

**LoadBalancer:**
```yaml
spec:
  type: LoadBalancer
  # External load balancer
```

### Service Commands

```bash
# Create service
kubectl apply -f service.yaml

# List services
kubectl get services

# Describe service
kubectl describe service myapp-service

# Delete service
kubectl delete service myapp-service
```

### Interview Questions

**Q1: What is a Kubernetes service?**
- Network abstraction for pods
- Stable network endpoint
- Load balancing
- Service discovery

**Q2: What are the different service types?**
- ClusterIP: Internal only
- NodePort: Expose on node port
- LoadBalancer: External LB
- ExternalName: DNS alias

**Q3: How does service discovery work in Kubernetes?**
- DNS for services
- Environment variables
- Service provides stable endpoint
- Pods can discover services

---

## 17. CONFIGMAPS AND SECRETS

### ConfigMap

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: myapp-config
data:
  database.url: "jdbc:postgresql://db:5432/mydb"
  cache.size: "100"
```

### Use ConfigMap

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: mypod
spec:
  containers:
  - name: myapp
    image: myapp:1.0
    envFrom:
    - configMapRef:
        name: myapp-config
```

### Secret

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: myapp-secret
type: Opaque
data:
  username: YWRtaW4=
  password: cGFzc3dvcmQ=
```

### Use Secret

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: mypod
spec:
  containers:
  - name: myapp
    image: myapp:1.0
    env:
    - name: DB_USERNAME
      valueFrom:
        secretKeyRef:
          name: myapp-secret
          key: username
```

### Interview Questions

**Q1: What is a ConfigMap?**
- Store configuration data
- Key-value pairs
- Decouple config from image
- Can be mounted as volume

**Q2: What is a Secret?**
- Store sensitive data
- Base64 encoded
- Not encrypted by default
- Can be encrypted at rest

**Q3: What is the difference between ConfigMap and Secret?**
- ConfigMap: Non-sensitive data
- Secret: Sensitive data
- ConfigMap: Plain text
- Secret: Base64 encoded

---

## 18. NAMESPACES

### Create Namespace

```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: development
```

### Use Namespace

```bash
# Create namespace
kubectl apply -f namespace.yaml

# List namespaces
kubectl get namespaces

# Set default namespace
kubectl config set-context --current --namespace=development

# Use namespace in command
kubectl get pods -n development
```

### Resource in Namespace

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: mypod
  namespace: development
spec:
  containers:
  - name: myapp
    image: myapp:1.0
```

### Interview Questions

**Q1: What is a Kubernetes namespace?**
- Virtual cluster
- Resource isolation
- Multiple environments
- Resource quotas

**Q2: Why use namespaces?**
- Separate environments
- Resource isolation
- Team separation
- Resource quotas

**Q3: How do you switch namespaces?**
- kubectl config set-context --current --namespace=name
- Use -n flag in commands
- Set in kubeconfig
- Default is default

---

## 19. INGRESS

### Ingress Definition

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: myapp-ingress
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
spec:
  rules:
  - host: myapp.example.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: myapp-service
            port:
              number: 80
```

### Ingress Controller

```bash
# Install NGINX Ingress Controller
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.8.1/deploy/static/provider/cloud/deploy.yaml
```

### Interview Questions

**Q1: What is Kubernetes Ingress?**
- HTTP/HTTPS routing
- External access to services
- URL-based routing
- SSL termination

**Q2: What is an Ingress Controller?**
- Implements Ingress rules
- Load balancer
- NGINX, Traefik, HAProxy
- Required for Ingress to work

**Q3: How does Ingress differ from Service?**
- Service: Layer 4 (TCP/UDP)
- Ingress: Layer 7 (HTTP)
- Service: Internal/external
- Ingress: HTTP routing only

---

## 20. STORAGE IN KUBERNETES

### Persistent Volume

```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: pv-volume
spec:
  capacity:
    storage: 10Gi
  accessModes:
    - ReadWriteOnce
  persistentVolumeReclaimPolicy: Retain
  storageClassName: manual
  hostPath:
    path: /mnt/data
```

### Persistent Volume Claim

```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: pvc-volume
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 5Gi
  storageClassName: manual
```

### Use PVC in Pod

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: mypod
spec:
  containers:
  - name: myapp
    image: myapp:1.0
    volumeMounts:
    - mountPath: /data
      name: my-volume
  volumes:
  - name: my-volume
    persistentVolumeClaim:
      claimName: pvc-volume
```

### Interview Questions

**Q1: What is a Persistent Volume?**
- Storage resource
- Cluster-wide
- Provisioned by admin
- Independent of pods

**Q2: What is a Persistent Volume Claim?**
- Request for storage
- Namespace-scoped
- Binds to PV
- Used by pods

**Q3: What are PV reclaim policies?**
- Retain: Keep PV after PVC deletion
- Delete: Delete PV after PVC deletion
- Recycle: Wipe data and reuse
- Default depends on storage class

---

## 21. KUBERNETES SCALING

### Manual Scaling

```bash
# Scale deployment
kubectl scale deployment myapp --replicas=5

# Scale statefulset
kubectl scale statefulset myapp --replicas=3
```

### Horizontal Pod Autoscaler

```yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: myapp-hpa
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: myapp
  minReplicas: 2
  maxReplicas: 10
  metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 80
```

### Vertical Pod Autoscaler

```yaml
apiVersion: autoscaling.k8s.io/v1
kind: VerticalPodAutoscaler
metadata:
  name: myapp-vpa
spec:
  targetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: myapp
  updatePolicy:
    updateMode: Auto
```

### Interview Questions

**Q1: How do you scale pods in Kubernetes?**
- Manual: kubectl scale
- HPA: Automatic based on metrics
- VPA: Automatic resource adjustment
- Cluster autoscaler: Node scaling

**Q2: What is Horizontal Pod Autoscaler?**
- Scales pods horizontally
- Based on CPU/memory metrics
- Automatic scaling
- Min/max replicas

**Q3: What is Vertical Pod Autoscaler?**
- Adjusts pod resources
- CPU and memory requests
- Automatic optimization
- Requires restart

---

## 22. KUBERNETES UPDATES AND ROLLBACKS

### Rolling Update

```bash
# Update image
kubectl set image deployment/myapp myapp=myapp:2.0

# Check rollout status
kubectl rollout status deployment/myapp

# View rollout history
kubectl rollout history deployment/myapp
```

### Rollback

```bash
# Rollback to previous
kubectl rollout undo deployment/myapp

# Rollback to specific revision
kubectl rollout undo deployment/myapp --to-revision=2

# Pause rollout
kubectl rollout pause deployment/myapp

# Resume rollout
kubectl rollout resume deployment/myapp
```

### Canary Deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp-canary
spec:
  replicas: 1
  selector:
    matchLabels:
      app: myapp
      version: canary
  template:
    metadata:
      labels:
        app: myapp
        version: canary
    spec:
      containers:
      - name: myapp
        image: myapp:2.0
```

### Interview Questions

**Q1: How do you update a deployment in Kubernetes?**
- kubectl set image
- Rolling update by default
- Can pause/resume
- Can rollback

**Q2: How do you rollback a deployment?**
- kubectl rollout undo
- Can specify revision
- View history first
- Automatic rollback

**Q3: What is a canary deployment?**
- Gradual rollout
- Test with subset of users
- Reduce risk
- Monitor before full rollout

---

## 23. KUBERNETES MONITORING

### kubectl Commands

```bash
# Get pod metrics
kubectl top pods

# Get node metrics
kubectl top nodes

# View resource usage
kubectl describe pod mypod
```

### Prometheus and Grafana

```yaml
# Prometheus deployment
apiVersion: apps/v1
kind: Deployment
metadata:
  name: prometheus
spec:
  replicas: 1
  selector:
    matchLabels:
      app: prometheus
  template:
    metadata:
      labels:
        app: prometheus
    spec:
      containers:
      - name: prometheus
        image: prom/prometheus
        ports:
        - containerPort: 9090
```

### Logging

```bash
# View pod logs
kubectl logs mypod

# View previous logs
kubectl logs mypod --previous

# Follow logs
kubectl logs -f mypod

# View all pod logs
kubectl logs -l app=myapp
```

### Interview Questions

**Q1: How do you monitor Kubernetes clusters?**
- kubectl top pods/nodes
- Prometheus and Grafana
- Metrics Server
- Custom metrics

**Q2: How do you view logs in Kubernetes?**
- kubectl logs pod-name
- kubectl logs -f for follow
- kubectl logs -l for selector
- Previous logs with --previous

**Q3: What is Prometheus in Kubernetes?**
- Monitoring system
- Collects metrics
- Time-series database
- Alerting

---

## 24. DOCKER VS KUBERNETES

### Comparison

| Feature | Docker | Kubernetes |
|---------|--------|-----------|
| Scope | Single host | Cluster |
| Orchestration | Docker Swarm | Native |
| Scaling | Manual | Automatic |
| Self-healing | No | Yes |
| Load Balancing | External | Built-in |
| Complexity | Low | High |
| Use Case | Development | Production |

### When to Use Docker

- Local development
- Single host deployment
- Simple applications
- Learning containers

### When to Use Kubernetes

- Production workloads
- Multi-host clusters
- Complex applications
- High availability
- Auto-scaling

### Interview Questions

**Q1: What is the difference between Docker and Kubernetes?**
- Docker: Container runtime
- Kubernetes: Orchestration platform
- Docker: Single host
- Kubernetes: Cluster management

**Q2: When would you use Docker instead of Kubernetes?**
- Local development
- Simple applications
- Single host
- Learning purposes

**Q3: Can you use Docker and Kubernetes together?**
- Yes, Kubernetes uses Docker as runtime
- Docker builds images
- Kubernetes runs containers
- Complementary technologies

---

## 25. COMMON INTERVIEW QUESTIONS

### Docker Fundamentals

**Q1: What is Docker?**
- Container platform
- Build, ship, run containers
- Industry standard
- Provides tools for container management

**Q2: What is a Docker image?**
- Read-only template
- Contains application and dependencies
- Built from Dockerfile
- Stored in registry

**Q3: What is a Docker container?**
- Running instance of image
- Isolated process
- Has writable layer
- Can be started/stopped

### Docker Commands

**Q4: How do you build a Docker image?**
- docker build -t name:tag .
- Requires Dockerfile
- Can use build args
- Creates layered image

**Q5: How do you run a Docker container?**
- docker run image
- Can add flags: -d, -it, --name
- Can specify command
- Example: docker run -d --name web nginx

**Q6: How do you execute commands in running container?**
- docker exec container command
- docker exec -it container bash
- Can specify user
- Example: docker exec -it web bash

### Docker Compose

**Q7: What is Docker Compose?**
- Multi-container tool
- YAML configuration
- Define services
- Easy orchestration

**Q8: How do you start services with Docker Compose?**
- docker-compose up
- docker-compose up -d (background)
- docker-compose down (stop)
- docker-compose logs (view logs)

**Q9: What is the difference between Docker and Docker Compose?**
- Docker: Single container
- Compose: Multiple containers
- Docker: CLI commands
- Compose: YAML file

### Kubernetes Fundamentals

**Q10: What is Kubernetes?**
- Container orchestration platform
- Automates deployment, scaling
- Manages containerized apps
- Open-source by Google

**Q11: What are the components of Kubernetes control plane?**
- API Server
- etcd
- Scheduler
- Controller Manager
- Cloud Controller Manager

**Q12: What is the role of Kubelet?**
- Agent on worker nodes
- Communicates with API server
- Manages pods and containers
- Reports node status

### Kubernetes Resources

**Q13: What is a Kubernetes pod?**
- Smallest deployable unit
- Contains one or more containers
- Shared network and storage
- Ephemeral by nature

**Q14: What is a Kubernetes deployment?**
- Manages pods
- Provides updates and rollbacks
- Maintains desired state
- Self-healing

**Q15: What is a Kubernetes service?**
- Network abstraction for pods
- Stable network endpoint
- Load balancing
- Service discovery

### Kubernetes Configuration

**Q16: What is a ConfigMap?**
- Store configuration data
- Key-value pairs
- Decouple config from image
- Can be mounted as volume

**Q17: What is a Secret?**
- Store sensitive data
- Base64 encoded
- Not encrypted by default
- Can be encrypted at rest

**Q18: What is the difference between ConfigMap and Secret?**
- ConfigMap: Non-sensitive data
- Secret: Sensitive data
- ConfigMap: Plain text
- Secret: Base64 encoded

### Kubernetes Scaling

**Q19: How do you scale pods in Kubernetes?**
- Manual: kubectl scale
- HPA: Automatic based on metrics
- VPA: Automatic resource adjustment
- Cluster autoscaler: Node scaling

**Q20: What is Horizontal Pod Autoscaler?**
- Scales pods horizontally
- Based on CPU/memory metrics
- Automatic scaling
- Min/max replicas

### Kubernetes Updates

**Q21: How do you update a deployment in Kubernetes?**
- kubectl set image
- Rolling update by default
- Can pause/resume
- Can rollback

**Q22: How do you rollback a deployment?**
- kubectl rollout undo
- Can specify revision
- View history first
- Automatic rollback

**Q23: What is a canary deployment?**
- Gradual rollout
- Test with subset of users
- Reduce risk
- Monitor before full rollout

### Storage

**Q24: What is a Persistent Volume?**
- Storage resource
- Cluster-wide
- Provisioned by admin
- Independent of pods

**Q25: What is a Persistent Volume Claim?**
- Request for storage
- Namespace-scoped
- Binds to PV
- Used by pods

### Scenario-Based

**Q26: How do you deploy an application to Kubernetes?**
- Create deployment YAML
- Apply with kubectl
- Create service for access
- Use ConfigMap/Secret for config

**Q27: How do you troubleshoot a failing pod?**
- kubectl describe pod
- kubectl logs pod
- Check events
- Check resource limits

**Q28: How do you expose an application externally?**
- Service of type LoadBalancer
- Service of type NodePort
- Ingress for HTTP
- Port forwarding for testing

**Q29: How do you handle configuration changes?**
- Update ConfigMap
- Rollout restart deployment
- Or use rolling update
- Pods pick up new config

**Q30: How do you implement blue-green deployment in Kubernetes?**
- Two deployments
- Switch service selector
- Zero downtime
- Rollback easy

---

## CONCLUSION

This comprehensive guide covers all essential Docker and Kubernetes topics for interview preparation. Key takeaways:

1. **Containerization**: Lightweight virtualization, shares kernel, isolated user space
2. **Docker**: Container platform, build/ship/run containers, industry standard
3. **Docker Images**: Read-only templates, layered, built from Dockerfile
4. **Docker Containers**: Running instances, isolated, can be managed
5. **Dockerfile**: Text file with instructions, automated image creation
6. **Docker Compose**: Multi-container tool, YAML configuration
7. **Docker Volumes**: Persistent storage, survive container deletion
8. **Docker Networks**: Bridge, host, none types, container communication
9. **Docker Registry**: Image storage, Docker Hub, private registries
10. **Kubernetes**: Container orchestration, automates deployment and scaling
11. **K8s Architecture**: Control plane and worker nodes, API server, etcd
12. **Pods**: Smallest unit, contains containers, shared network
13. **Deployments**: Manages pods, updates/rollbacks, self-healing
14. **Services**: Network abstraction, load balancing, service discovery
15. **ConfigMaps/Secrets**: Configuration and sensitive data management
16. **Namespaces**: Virtual clusters, resource isolation
17. **Ingress**: HTTP routing, external access
18. **Storage**: PV and PVC for persistent storage
19. **Scaling**: HPA and VPA for automatic scaling
20. **Updates**: Rolling updates, rollbacks, canary deployments
21. **Monitoring**: kubectl, Prometheus, Grafana
22. **Docker vs Kubernetes**: Runtime vs orchestration

Practice these concepts with real deployments and be prepared to explain the "why" behind each approach. Good luck with your interview!
