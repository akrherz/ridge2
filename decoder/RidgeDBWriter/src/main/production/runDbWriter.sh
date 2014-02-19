#!/bin/csh
setenv JAVA /usr/local/java/bin/java
cd /usr/local/RIDGEVer2Program/RidgeDBWriter-0.0.1
#${JAVA} -Xmx256m -DconnectorName=amqp -jar RidgeDbWriter-0.0.1.jar &
${JAVA} -Xmx256m -DconnectorName=activemq -jar RidgeDbWriter-0.0.1.jar &