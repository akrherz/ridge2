#!/bin/csh

setenv JAVA /usr/local/java/bin/java
cd /usr/local/RIDGEVer2Program/RidgeServer-0.0.1

#${JAVA} -Xmx1024m -DconnectorName=amqp -jar RidgeServer-0.0.1.jar &
${JAVA} -Xmx1024m -DconnectorName=activemq -jar RidgeServer-0.0.1.jar &