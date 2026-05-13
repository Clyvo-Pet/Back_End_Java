#!/bin/bash
# ============================================================
# Clyvo Pet - Script Azure CLI
# Provisionamento completo da infraestrutura na Azure
# ============================================================

# Variaveis
RESOURCE_GROUP="clyvo-pet-rg"
LOCATION="brazilsouth"
VM_NAME="clyvo-pet-vm"
VM_SIZE="Standard_B2s"
ADMIN_USER="clyvoAdmin"
IMAGE="Ubuntu2204"
NSG_NAME="clyvo-pet-nsg"

echo "===================================================="
echo " Clyvo Pet - Iniciando provisionamento na Azure"
echo "===================================================="

# 1. Criar Resource Group
echo "[1/5] Criando Resource Group..."
az group create \
  --name $RESOURCE_GROUP \
  --location $LOCATION

# 2. Criar a VM Linux
echo "[2/5] Criando Maquina Virtual Linux..."
az vm create \
  --resource-group $RESOURCE_GROUP \
  --name $VM_NAME \
  --image $IMAGE \
  --size $VM_SIZE \
  --admin-username $ADMIN_USER \
  --generate-ssh-keys \
  --output json \
  --verbose

# 3. Abrir as portas necessarias
echo "[3/5] Abrindo portas na VM..."

# Porta 8080 - aplicacao Java
az vm open-port \
  --resource-group $RESOURCE_GROUP \
  --name $VM_NAME \
  --port 8080 \
  --priority 1001

# Porta 22 - SSH
az vm open-port \
  --resource-group $RESOURCE_GROUP \
  --name $VM_NAME \
  --port 22 \
  --priority 1002

# Porta 1521 - Oracle
az vm open-port \
  --resource-group $RESOURCE_GROUP \
  --name $VM_NAME \
  --port 1521 \
  --priority 1003

# 4. Instalar Docker, Git e ferramentas na VM
echo "[4/5] Instalando Docker e ferramentas na VM..."
az vm run-command invoke \
  --resource-group $RESOURCE_GROUP \
  --name $VM_NAME \
  --command-id RunShellScript \
  --scripts "
    # Atualizar pacotes
    sudo apt-get update -y

    # Instalar dependencias
    sudo apt-get install -y \
      ca-certificates \
      curl \
      gnupg \
      git \
      nano \
      unzip \
      wget

    # Adicionar repositorio Docker
    sudo install -m 0755 -d /etc/apt/keyrings
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
    sudo chmod a+r /etc/apt/keyrings/docker.gpg
    echo \
      \"deb [arch=\$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
      \$(. /etc/os-release && echo \"\$VERSION_CODENAME\") stable\" | \
      sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

    # Instalar Docker
    sudo apt-get update -y
    sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

    # Iniciar Docker e habilitar no boot
    sudo systemctl start docker
    sudo systemctl enable docker

    # Adicionar usuario ao grupo docker (sem precisar de sudo)
    sudo usermod -aG docker $ADMIN_USER

    # Verificar instalacao
    docker --version
    docker compose version

    echo 'Instalacao concluida!'
  "

# 5. Obter IP publico da VM
echo "[5/5] Obtendo IP publico da VM..."
PUBLIC_IP=$(az vm show \
  --resource-group $RESOURCE_GROUP \
  --name $VM_NAME \
  --show-details \
  --query publicIps \
  --output tsv)

echo "===================================================="
echo " Provisionamento concluido com sucesso!"
echo " IP Publico da VM: $PUBLIC_IP"
echo " Acesse via SSH: ssh $ADMIN_USER@$PUBLIC_IP"
echo " Aplicacao (apos deploy): http://$PUBLIC_IP:8080"
echo " Swagger: http://$PUBLIC_IP:8080/swagger-ui.html"
echo "===================================================="

# ============================================================
# COMANDOS PARA DEPLOY (executar apos conectar na VM via SSH)
# ============================================================
# ssh clyvoAdmin@<IP_DA_VM>
# git clone https://github.com/Clyvo-Pet/Back_End_Java.git
# cd Back_End_Java
# docker compose up -d
# docker compose logs -f

# ============================================================
# COMANDO PARA DELETAR A VM AO FINAL (OBRIGATORIO)
# ============================================================
# az group delete --name clyvo-pet-rg --yes --no-wait
