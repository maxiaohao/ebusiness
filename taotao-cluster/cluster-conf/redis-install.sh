#!/bin/bash

# this file should only be called in 'redis-dockerfile', to setup basic redis.master.conf and redis.slave.conf

mkdir -p $INST_TMP
mkdir -p /opt/redis

# install redis
tar xzf $INST_STUFF/redis-3.2.2.tar.gz -C $INST_TMP
cd $INST_TMP/redis-3.2.2
make

cp ./src/redis-server /opt/redis 
cp ./redis.conf /opt/redis 
cp ./src/redis-trib.rb /opt/redis 

sed -i 's/# cluster-enabled yes/cluster-enabled yes/g' /opt/redis/redis.conf
sed -i 's/daemonize no/daemonize yes/g' /opt/redis/redis.conf
sed -i 's/bind 127\.0\.0\.1/bind 0\.0\.0\.0/g' /opt/redis/redis.conf

gem install $INST_STUFF/redis-3.2.2.gem

