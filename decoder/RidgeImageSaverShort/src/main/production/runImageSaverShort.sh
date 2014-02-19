#!/bin/csh
setenv JAVA /usr/local/java/bin/java
cd /usr/local/RIDGEVer2Program/RidgeImageSaverShort-0.0.1
#${JAVA} -Xmx256m -DconnectorName=amqp -jar RidgeImageSaverShort-0.0.1.jar &
${JAVA} -Xmx256m -DconnectorName=activemq -jar RidgeImageSaverShort-0.0.1.jar &