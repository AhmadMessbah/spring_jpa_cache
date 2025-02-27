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
          mvn clean package

    Run Docker:
          docker-compose up --build

    Access Database :
          docker exec -it <postgres-container-name> psql -U mft -d springdb

        - Find postgres-container-name :
          postgres-container-name

    Stop Docker:
        - docker-compose down