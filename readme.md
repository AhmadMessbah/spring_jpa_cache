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
          wsl --list --verbose
          docker-compose up -d
          docker ps

    PostgreSQL:
          docker exec -it postgres psql -U mft -d spring_db
          \dt
          \q

    MongoDB :
          docker exec -it mongodb mongosh
          show dbs
          use spring_db_photos
          show collections
          exit

          docker exec -it <mongo-container-name> mongosh
          show dbs
          use mydb
          show collections
          db.images.find()
          db.images.find().pretty()

    Redis:
          docker exec -it redis redis-cli
          GET myKey
          SET testKey "Hello Redis"
          GET testKey
          exit

    Log :
          docker logs -f spring_jpa_cache
          

    Test :
          curl -k https://localhost:8443/actuator/health

    Stop Services : 
          docker-compose down
          docker-compose down -v



    Build Jar :
          mvn clean package

    Run Docker:
          docker-compose up --build

    Access Database :
          docker exec -it <postgres-container-name> psql -U mft -d spring_db

        - Find postgres-container-name :
          postgres-container-name

    Stop Docker:
        - docker-compose down