docker stop $(docker ps -a -q)
docker rm -f $(docker ps -a -q)
docker rmi -f $(docker images -q)
docker volume rm -f $(docker volume ls -q)
docker system prune -a -f --volumes