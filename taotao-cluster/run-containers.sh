#!/bin/bash

if [ "$(/usr/bin/whoami)" != "root" ] ; then
    echo "Error: This script should be run as root! (current user = $(/usr/bin/whoami))"
    exit 1
fi

PROJ_ROOT=$(cd `dirname $0`;pwd)

NETWORK_NAME="ebd-network"

HOST_LAPTOP="laptop"
HOST_FASTDFS_TRACKER="fastdfs-tracker"
HOST_FASTDFS_STORAGE1="fastdfs-storage1"
HOST_FASTDFS_STORAGE2="fastdfs-storage2"
HOST_MYSQL="mysql"
HOST_NGINX="nginx"
HOST_REDIS1="redis1"
HOST_REDIS2="redis2"
HOST_REDIS3="redis3"
HOST_REDIS4="redis4"
HOST_REDIS5="redis5"
HOST_REDIS6="redis6"
HOST_SOLR="solr"
HOST_TOMCAT1="tomcat1"
HOST_TOMCAT2="tomcat2"

IP_LAPTOP="192.168.25.1"
IP_FASTDFS_TRACKER="192.168.25.133"
IP_FASTDFS_STORAGE1="192.168.25.134"
IP_FASTDFS_STORAGE2="192.168.25.135"
IP_MYSQL="192.168.25.11"
IP_NGINX="192.168.25.2"
IP_REDIS1="192.168.25.151"
IP_REDIS2="192.168.25.152"
IP_REDIS3="192.168.25.153"
IP_REDIS4="192.168.25.154"
IP_REDIS5="192.168.25.155"
IP_REDIS6="192.168.25.156"
IP_SOLR="192.168.25.161"
IP_TOMCAT1="192.168.25.21"
IP_TOMCAT2="192.168.25.22"

ADD_HOST_OPT=" --add-host $HOST_LAPTOP:$IP_LAPTOP"
ADD_HOST_OPT=" --add-host $HOST_FASTDFS_TRACKER:$IP_FASTDFS_TRACKER $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_FASTDFS_STORAGE1:$IP_FASTDFS_STORAGE1 $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_FASTDFS_STORAGE2:$IP_FASTDFS_STORAGE2 $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_MYSQL:$IP_MYSQL $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_NGINX:$IP_NGINX $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_REDIS1:$IP_REDIS1 $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_REDIS2:$IP_REDIS2 $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_REDIS3:$IP_REDIS3 $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_REDIS4:$IP_REDIS4 $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_REDIS5:$IP_REDIS5 $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_REDIS6:$IP_REDIS6 $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_SOLR:$IP_SOLR $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_TOMCAT1:$IP_TOMCAT1 $ADD_HOST_OPT"
ADD_HOST_OPT=" --add-host $HOST_TOMCAT2:$IP_TOMCAT2 $ADD_HOST_OPT"

# also add the domains to /etc/hosts in every container
ADD_HOST_OPT=$ADD_HOST_OPT" --add-host taotao.com:$IP_NGINX"
ADD_HOST_OPT=$ADD_HOST_OPT" --add-host www.taotao.com:$IP_NGINX"
ADD_HOST_OPT=$ADD_HOST_OPT" --add-host rest.taotao.com:$IP_NGINX"
ADD_HOST_OPT=$ADD_HOST_OPT" --add-host search.taotao.com:$IP_NGINX"
ADD_HOST_OPT=$ADD_HOST_OPT" --add-host sso.taotao.com:$IP_NGINX"
ADD_HOST_OPT=$ADD_HOST_OPT" --add-host order.taotao.com:$IP_NGINX"
ADD_HOST_OPT=$ADD_HOST_OPT" --add-host manager.taotao.com:$IP_NGINX"
ADD_HOST_OPT=$ADD_HOST_OPT" --add-host image.taotao.com:$IP_NGINX"

if [ "$(echo $SKIP_EBD_HOSTS | grep -F $HOST_FASTDFS_TRACKER)" = "" ] ; then
    docker run --net $NETWORK_NAME --ip $IP_FASTDFS_TRACKER --hostname $HOST_FASTDFS_TRACKER $ADD_HOST_OPT --name $HOST_FASTDFS_TRACKER -dit ebd-img-fastdfs /bin/bash
    docker exec $HOST_FASTDFS_TRACKER service sshd start
    docker exec $HOST_FASTDFS_TRACKER /usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf
    echo "### fastdfs tracker started ###"
else
    echo "### fastdfs tracker was skipped ###"
fi

if [ "$(echo $SKIP_EBD_HOSTS | grep -F $HOST_FASTDFS_STORAGE1)" = "" ] ; then
    docker run --net $NETWORK_NAME --ip $IP_FASTDFS_STORAGE1 --hostname $HOST_FASTDFS_STORAGE1 $ADD_HOST_OPT --name $HOST_FASTDFS_STORAGE1 -dit ebd-img-fastdfs /bin/bash
    docker exec $HOST_FASTDFS_STORAGE1 service sshd start
    docker exec $HOST_FASTDFS_STORAGE1 /usr/local/nginx/sbin/nginx
    docker exec $HOST_FASTDFS_STORAGE1 /usr/bin/fdfs_storaged /etc/fdfs/storage.conf start
    echo "### fastdfs storage 1 started ###"
else
    echo "### fastdfs storage 1 was skipped ###"
fi

if [ "$(echo $SKIP_EBD_HOSTS | grep -F $HOST_FASTDFS_STORAGE2)" = "" ] ; then
    docker run --net $NETWORK_NAME --ip $IP_FASTDFS_STORAGE2 --hostname $HOST_FASTDFS_STORAGE2 $ADD_HOST_OPT --name $HOST_FASTDFS_STORAGE2 -dit ebd-img-fastdfs /bin/bash
    docker exec $HOST_FASTDFS_STORAGE2 service sshd start
    docker exec $HOST_FASTDFS_STORAGE2 /usr/local/nginx/sbin/nginx
    docker exec $HOST_FASTDFS_STORAGE2 /usr/bin/fdfs_storaged /etc/fdfs/storage.conf start
    echo "### fastdfs storage 2 started ###"
else
    echo "### fastdfs storage 2 was skipped ###"
fi

if [ "$(echo $SKIP_EBD_HOSTS | grep -F $HOST_MYSQL)" = "" ] ; then
    docker run --net $NETWORK_NAME --ip $IP_MYSQL --hostname $HOST_MYSQL $ADD_HOST_OPT --name $HOST_MYSQL -dit ebd-img-mysql /bin/bash
    docker exec $HOST_MYSQL service sshd start
    docker exec $HOST_MYSQL service mysqld start
    echo "### mysql server started ###"
else
    echo "### mysql server was skipped ###"
fi

if [ "$(echo $SKIP_EBD_HOSTS | grep -F $HOST_NGINX)" = "" ] ; then
    docker run --net $NETWORK_NAME --ip $IP_NGINX --hostname $HOST_NGINX $ADD_HOST_OPT --name $HOST_NGINX -dit ebd-img-nginx /bin/bash
    docker exec $HOST_NGINX service sshd start
    docker exec $HOST_NGINX service nginx start
    echo "### nginx server started ###"
else
    echo "### nginx server was skipped ###"
fi

if [ "$(echo $SKIP_EBD_HOSTS | grep -F $HOST_REDIS1)" = "" ] ; then
    docker run --net $NETWORK_NAME --ip $IP_REDIS1 --hostname $HOST_REDIS1 $ADD_HOST_OPT --name $HOST_REDIS1 -dit ebd-img-redis /bin/bash
    docker exec $HOST_REDIS1 service sshd start
    docker exec $HOST_REDIS1 /opt/redis/redis-server /opt/redis/redis.conf
    echo "### $HOST_REDIS1 started ###"
else
    echo "### $HOST_REDIS1 was skipped ###"
fi

if [ "$(echo $SKIP_EBD_HOSTS | grep -F $HOST_REDIS2)" = "" ] ; then
    docker run --net $NETWORK_NAME --ip $IP_REDIS2 --hostname $HOST_REDIS2 $ADD_HOST_OPT --name $HOST_REDIS2 -dit ebd-img-redis /bin/bash
    docker exec $HOST_REDIS2 service sshd start
    docker exec $HOST_REDIS2 /opt/redis/redis-server /opt/redis/redis.conf
    echo "### $HOST_REDIS2 started ###"
else
    echo "### $HOST_REDIS2 was skipped ###"
fi

if [ "$(echo $SKIP_EBD_HOSTS | grep -F $HOST_REDIS3)" = "" ] ; then
    docker run --net $NETWORK_NAME --ip $IP_REDIS3 --hostname $HOST_REDIS3 $ADD_HOST_OPT --name $HOST_REDIS3 -dit ebd-img-redis /bin/bash
    docker exec $HOST_REDIS3 service sshd start
    docker exec $HOST_REDIS3 /opt/redis/redis-server /opt/redis/redis.conf
    echo "### $HOST_REDIS3 started ###"
else
    echo "### $HOST_REDIS3 was skipped ###"
fi

if [ "$(echo $SKIP_EBD_HOSTS | grep -F $HOST_REDIS4)" = "" ] ; then
    docker run --net $NETWORK_NAME --ip $IP_REDIS4 --hostname $HOST_REDIS4 $ADD_HOST_OPT --name $HOST_REDIS4 -dit ebd-img-redis /bin/bash
    docker exec $HOST_REDIS4 service sshd start
    docker exec $HOST_REDIS4 /opt/redis/redis-server /opt/redis/redis.conf
    echo "### $HOST_REDIS4 started ###"
else
    echo "### $HOST_REDIS4 was skipped ###"
fi

if [ "$(echo $SKIP_EBD_HOSTS | grep -F $HOST_REDIS5)" = "" ] ; then
    docker run --net $NETWORK_NAME --ip $IP_REDIS5 --hostname $HOST_REDIS5 $ADD_HOST_OPT --name $HOST_REDIS5 -dit ebd-img-redis /bin/bash
    docker exec $HOST_REDIS5 service sshd start
    docker exec $HOST_REDIS5 /opt/redis/redis-server /opt/redis/redis.conf
    echo "### $HOST_REDIS5 started ###"
else
    echo "### $HOST_REDIS5 was skipped ###"
fi

if [ "$(echo $SKIP_EBD_HOSTS | grep -F $HOST_REDIS6)" = "" ] ; then
    docker run --net $NETWORK_NAME --ip $IP_REDIS6 --hostname $HOST_REDIS6 $ADD_HOST_OPT --name $HOST_REDIS6 -dit ebd-img-redis /bin/bash
    docker exec $HOST_REDIS6 service sshd start
    docker exec $HOST_REDIS6 /opt/redis/redis-server /opt/redis/redis.conf
    echo "### $HOST_REDIS6 started ###"
else
    echo "### $HOST_REDIS6 was skipped ###"
fi


# start redis-cluster on redis1
cmd1="echo 'yes' | /opt/redis/redis-trib.rb create --replicas 1 $IP_REDIS1:6379 $IP_REDIS2:6379 $IP_REDIS3:6379 $IP_REDIS4:6379 $IP_REDIS5:6379 $IP_REDIS6:6379"
docker exec $HOST_REDIS1 sh -c "$cmd1 > /opt/redis/cluster.log 2>&1"


if [ "$(echo $SKIP_EBD_HOSTS | grep -F $HOST_SOLR)" = "" ] ; then
    docker run --net $NETWORK_NAME --ip $IP_SOLR --hostname $HOST_SOLR $ADD_HOST_OPT --name $HOST_SOLR -dit ebd-img-solr /bin/bash
    docker exec $HOST_SOLR service sshd start
    docker exec $HOST_SOLR /opt/apache-tomcat-solr/bin/startup.sh
    echo "### solr server started ###"
else
    echo "### solr server was skipped ###"
fi

if [ "$(echo $SKIP_EBD_HOSTS | grep -F $HOST_TOMCAT1)" = "" ] ; then
    docker run --net $NETWORK_NAME --ip $IP_TOMCAT1 --hostname $HOST_TOMCAT1 $ADD_HOST_OPT --name $HOST_TOMCAT1 -dit ebd-img-tomcat /bin/bash
    docker exec $HOST_TOMCAT1 service sshd start
    docker exec $HOST_TOMCAT1 /opt/tomcats/tomcat-portal/bin/startup.sh
    docker exec $HOST_TOMCAT1 /opt/tomcats/tomcat-manager/bin/startup.sh
    docker exec $HOST_TOMCAT1 /opt/tomcats/tomcat-rest/bin/startup.sh
    docker exec $HOST_TOMCAT1 /opt/tomcats/tomcat-search/bin/startup.sh
    docker exec $HOST_TOMCAT1 /opt/tomcats/tomcat-sso/bin/startup.sh
    docker exec $HOST_TOMCAT1 /opt/tomcats/tomcat-order/bin/startup.sh
    echo "### tomcat server 1 started ###"
else
    echo "### tomcat server 1 was skipped ###"
fi

if [ "$(echo $SKIP_EBD_HOSTS | grep -F $HOST_TOMCAT2)" = "" ] ; then
    docker run --net $NETWORK_NAME --ip $IP_TOMCAT2 --hostname $HOST_TOMCAT2 $ADD_HOST_OPT --name $HOST_TOMCAT2 -dit ebd-img-tomcat /bin/bash
    docker exec $HOST_TOMCAT2 service sshd start
    docker exec $HOST_TOMCAT2 /opt/tomcats/tomcat-portal/bin/startup.sh
    docker exec $HOST_TOMCAT2 /opt/tomcats/tomcat-manager/bin/startup.sh
    docker exec $HOST_TOMCAT2 /opt/tomcats/tomcat-rest/bin/startup.sh
    docker exec $HOST_TOMCAT2 /opt/tomcats/tomcat-search/bin/startup.sh
    docker exec $HOST_TOMCAT2 /opt/tomcats/tomcat-sso/bin/startup.sh
    docker exec $HOST_TOMCAT2 /opt/tomcats/tomcat-order/bin/startup.sh
    echo "### tomcat server 2 started ###"
else
    echo "### tomcat server 2 was skipped ###"
fi

echo "###################################################################################"
echo "####  container starting process finished, please check out the details above  ####"
echo "###################################################################################"

