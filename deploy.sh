git pull
mvn package
mv target/uss-1.0.war ~/apache-tomcat-8.0.20/webapps/ROOT.war
sudo ~/apache-tomcat-8.0.20/bin/startup.sh