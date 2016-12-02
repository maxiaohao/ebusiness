#!/bin/bash

# this file should only be called in 'tomcat-dockerfile', to setup 6 tomcats which would run on different ports to avoid conflict

mkdir -p $INST_TMP

tar xzf $INST_STUFF/apache-tomcat-7.0.70.tar.gz -C /opt

mv /opt/apache-tomcat-7.0.70 /opt/apache-tomcat-solr

sed -i 's/<Connector port="8080" protocol="HTTP\/1\.1"/<Connector port="8080" protocol="org\.apache\.coyote\.http11\.Http11NioProtocol"/g' /opt/apache-tomcat-solr/conf/server.xml

TOMCAT_USER_CONTENT='<tomcat-users><user name="tomcat" password="tomcat" roles="manager-gui,manager-script" /></tomcat-users>'

echo $TOMCAT_USER_CONTENT > /opt/apache-tomcat-solr/conf/tomcat-users.xml

unzip $INST_STUFF/solr/solr-4.10.3.war -d /opt/apache-tomcat-solr/webapps/solr

cp $INST_STUFF/solr/lib/* /opt/apache-tomcat-solr/webapps/solr/WEB-INF/lib/

tar xzf $INST_STUFF/solr/solrhome.example.tar.gz -C /opt

sed -i 's/\/put\/your\/solr\/home\/here/\/opt\/solrhome/g' /opt/apache-tomcat-solr/webapps/solr/WEB-INF/web.xml
sed -i '46d' /opt/apache-tomcat-solr/webapps/solr/WEB-INF/web.xml
sed -i '40d' /opt/apache-tomcat-solr/webapps/solr/WEB-INF/web.xml

exit 0

