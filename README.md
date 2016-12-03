# 本地开发/演示环境的搭建 #

## 1. 集群环境概述 ##
taotao-cluster中的脚本可用于快速在本机上搭建本项目所需要的服务器Docker容器集群, 集群中的"主机"包括:
- fastdfs (3台)
- mysql
- nginx
- redis (6台)
- solr
- tomcat (2台, 占内存多, 仅用作最后发布, 开发调试时不开)
上述集群中的"主机"均处于同一个私有网络`192.168.25.0/24`之中. 除去2*6=12个tomcat实例不开, 集群整体内存消耗不到800M.


## 2. 搭建本地集群的步骤 ##

如果你已有Docker 1.10+, 可考虑自行构建并运行整套环境, 详细步骤及规格说明见taotao-cluster/README.md

如果你使用Windows, 或者想把环境部署到vsphere中, 建议直接导入预先装好Docker的ova镜像(CentOS7), 下载地址: (待更新)
此虚拟机镜像可导入到Virtualbox或vmware中, 下文以Virtualbox为例, 简述镜像的使用步骤:
- 下载ova文件并导入为一台新的虚拟机
- 注意此虚拟机为双网卡, 网卡1是NAT(方便访问外网), 网卡2为HostOnly网络(启用DHCP), 如果虚拟机不是部署在本机电脑(例如,要部署到vsphere中), 则网卡2应该是Bridge模式
- 启动虚拟机, 控制台用户名/密码登录: root/password
- 虚拟机中运行ifconfig看一下网卡2的IP
- 从你的本机新建一条静态路由, 以便访问到虚拟机内部的`192.168.25.0/24`网络: `route add 192.168.25.0 mask 255.255.255.0 网卡2的IP`, 例如我的Virtualbox分配虚拟机网卡2的IP为192.168.56.101, 则`route add 192.168.25.0 mask 255.255.255.0 192.168.56.101`.
- 虚拟机中启动集群:

```
cd /opt/taotao-cluster
export SKIP_EBD_HOSTS="tomcat1 tomcat2" #跳过两台tomcat服务器不开
./run-containers.sh
```
- 另: 销毁集群的命令: `./destroy-containers.sh`
- 修改本机hosts文件, 加入:

```
192.168.25.1 taotao-cluster
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

192.168.25.2 taotao.com
192.168.25.2 www.taotao.com
192.168.25.2 rest.taotao.com
192.168.25.2 search.taotao.com
192.168.25.2 sso.taotao.com
192.168.25.2 order.taotao.com
192.168.25.2 manager.taotao.com
192.168.25.2 image.taotao.com
```
- 这时已经可以从本机访问到集群中的机器名了, 比如访问solr的话可以浏览器里打开`http://solr:8080/solr`
- 集群中各主机的登录密码也是`root/password`, 例如`ssh root@redis1 cat /opt/redis/cluster.log`可查看redis cluster是否启动成功.

## 3. 本机启动/调试taotao-*的webapp ##
```
mvn tomcat7:run
...
```
注: taotao-*中各种服务的连接配置都是使用机器名的, 这样做的好处是, 只要修改你的hosts文件中对应的ip就能切换到其他环境运行.
