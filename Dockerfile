FROM quay.io/wildfly/wildfly
COPY target/tp-monitoring.war /opt/jboss/wildfly/standalone/deployments/
COPY /config/standalone-full.xml /opt/jboss/wildfly/standalone/configuration/standalone-full.xml
COPY /config/standalone.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml

RUN /opt/jboss/wildfly/bin/add-user.sh admin AdzLkwgKY2zQyEN83Rtoh --silent
RUN /opt/jboss/wildfly/bin/add-user.sh david nJTLyjktv5nx --silent -g SuperUser

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "-c","standalone.xml"]
