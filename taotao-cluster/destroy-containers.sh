#!/bin/bash

if [ "$(/usr/bin/whoami)" != "root" ] ; then
    echo "Error: This script should be run as root! (current user = $(/usr/bin/whoami))"
    exit 1
fi

CONTAINERS=`docker ps -a | grep "ebd-img-" | grep -v grep | awk '{printf "%s ",$1}'`

if [ "$CONTAINERS" != "" ] ; then
    docker rm -fv $CONTAINERS
fi

