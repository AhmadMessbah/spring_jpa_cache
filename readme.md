Requirements :
    
    Docker :
        - In PowerSehll run as Adminstrator : 
          wsl --install
        - Restart
        - Enable Virtualization in Bios
        - Download and install : Docker Desktop Installer.exe from : https://www.docker.com/products/docker-desktop
          Chech Install required Windows components for WSL 2
        - Run Desktop Docker and select WSL2

        - Check in PowerShell :
          docker --version
          docker-compose --version


    Build Jar :
        - In Terminal : mvn clean package