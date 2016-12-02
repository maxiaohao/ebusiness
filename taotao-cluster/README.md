# Prerequisites #
- Docker 1.10.0+ is needed as there are some recently added network/host related features used by the scripts.

# Quick Start #
These scripts help to create a dozen of containers (as vms) along with your host in a private network utilizing the development of taotao
```
./create-network.sh
./create-img-common.sh      # this will take 5 or 10 mins (to install dependencies from yum repo)
./create-imgs-cluster.sh    # build the 6 images, taking no more than 5 mins
./run-containers.sh
```

TIPS: 
- Run `docker exec -it <container_id|container_name> /bin/bash` to attach your console into the container.
- `docker images` lists all images.
- `docker ps -a` lists all containers.
- sshd are also enabled in the containers so that you could ssh to them (`root/password`) in case you access from some other hosts in the private network.
- There are some `delete-*.sh` and `kill-*.sh` scripts you can make use of if you need to clear up things quickly.
- To skip to run some of the containers, an environmental variable `SKIP_EBD_HOSTS` can be used before you run the containers, e.g. `export SKIP_EBD_HOSTS="fastdfs-storage2 tomcat1 tomcat2" && sudo -E ./run-containers.sh`


# Specs #

### network (1) ###
- ebd-network (192.168.25.0/24)

### images built (1+6) ###
- ebd-img-common (only works as a dependency for the rest 6 images)
- ebd-img-fastdfs
- ebd-img-mysql
- ebd-img-nginx
- ebd-img-redis
- ebd-img-solr
- ebd-img-tomcat

### containers(vms) (14) ###
- fastdfs-tracker
- fastdfs-storage1
- fastdfs-storage2
- mysql
- nginx
- redis1
- redis2
- redis3
- redis4
- redis5
- redis6
- solr
- tomcat1
- tomcat2

| Hostname         | Ip Addr          |  Services (Ports)  | Remarks                                             |
| -------------    |------------------|--------------------|-----------------------------------------------------|
| fastdfs-tracker  | 192.168.25.133   | tracker (22122)    |                                                     |
| fastdfs-storage1 | 192.168.25.134   | storage 1 (22122) + nginx (80)      |                                    |
| fastdfs-storage2 | 192.168.25.135   | storage 2 (22122) + nginx (80)      |                                    |
| mysql            | 192.168.25.11    | empty mysql (3306)  | need to init db on container start                 |
| nginx            | 192.168.25.2     | 1) virtual-hosting all domains like search.taotao.com, sso.taotao.com, etc.(forwarding to specific tomcat instance per domain name); 2) Load balancing tomcats ranging on tomcat1 and tomcat2; 3) Listen on port 80   |      |
| redis1           | 192.168.25.151   | redis (6379)      | need syncing of data to mysql, including on delete? |
| redis2           | 192.168.25.152   | redis (6379)      |                                                     |
| redis3           | 192.168.25.153   | redis (6379)      |                                                     |
| redis4           | 192.168.25.154   | redis (6379)      |                                                     |
| redis5           | 192.168.25.155   | redis (6379)      |                                                     |
| redis6           | 192.168.25.156   | redis (6379)      |                                                     |
| solr             | 192.168.25.161   | solr (8080)       |  single node setup                                  |
| tomcat1          | 192.168.25.21    | 6 tomcats: portal(8081), rest(8082), manager(8083), search(8084), sso(8085), order(8086)    | tomcat admin/password: tomcat/tomcat     |
| tomcat2          | 192.168.25.22    | replica of tomcat1 |                                                     |
| (your host)      | 192.168.25.1     |                    |                                                     |


### hosts ###
- You may need to append the following into the hosts (e.g. /etc/hosts) file on your host(laptop) so that you could access the containers (vms) using hostname instead of ip address.
```
# ebd hosts
192.168.25.1 laptop #(an alias of your host)
192.168.25.133 fastdfs-tracker
192.168.25.134 fastdfs-storage1
192.168.25.135 fastdfs-storage1
192.168.25.11 mysql
192.168.25.2 nginx
192.168.25.151 redis1
192.168.25.152 redis2
192.168.25.153 redis3
192.168.25.154 redis4
192.168.25.155 redis5
192.168.25.156 redis6
192.168.25.161 solr
192.168.25.21 tomcat1
192.168.25.22 tomcat2

# ebd domains
192.168.25.2 taotao.com
192.168.25.2 www.taotao.com
192.168.25.2 rest.taotao.com
192.168.25.2 search.taotao.com
192.168.25.2 sso.taotao.com
192.168.25.2 order.taotao.com
192.168.25.2 manager.taotao.com
192.168.25.2 image.taotao.com
```

# Performance #
With all the 14 containers running idle (for now the 3 dummy solr ones don't do anything), a less than 1% cpu + 1.5 GB memory is consumed on the host. All images occupy a total of less than 1 GB of disk space taking advantage of Docker's layered fs. 

# TODO #
- 

# Licence #
MIT
