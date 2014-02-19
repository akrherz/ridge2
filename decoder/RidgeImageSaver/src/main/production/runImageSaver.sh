#!/bin/csh
setenv JAVA /usr/local/java/bin/java
cd /usr/local/RIDGEVer2Program/RidgeImageSaver-0.0.1
#${JAVA} -Xmx256m -DconnectorName=amqp -jar RidgeImageSaver-0.0.1.jar &
${JAVA} -Xmx256m -DconnectorName=activemq -jar RidgeImageSaver-0.0.1.jar &