@echo off
echo Cleaning and building the project...
mvn clean package

if %ERRORLEVEL% NEQ 0 (
    echo Build failed! Check the errors above.
    exit /b %ERRORLEVEL%
)

echo Checking if the JAR file exists...
if not exist target\spring_jpa_cache-0.0.1-SNAPSHOT.jar (
    echo JAR file not found! Check the Maven build.
    exit /b 1
)

echo Removing old Docker images and volumes...
docker-compose down -v
docker system prune -af
docker volume prune -f

echo Building the Docker image...
docker build -t spring_jpa_cache .

if %ERRORLEVEL% NEQ 0 (
    echo Docker build failed!
    exit /b %ERRORLEVEL%
)

echo Running the container...
docker-compose up -d --build

echo Done!
