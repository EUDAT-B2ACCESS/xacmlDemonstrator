# B2ACCESS XACML 3.0 Demonstrator
The Project is an XACML 3.0 Application using Balana library to demonstrate a flow of a client accessing a dummy (data management) service. Each request from the client intercepted by Policy Enforcement Point (PEP) at the service side and then sent to Policy Decision Point (PDP), which eventually decides (after evaluating the request against the xacml policies) to grant or deny the access.

## Prerequisites
* Java 1.8
* Maven 3

## Build and run instructions for local client
	
* checkout and change to project directory
* Build '$> mvn clean install -DskipTests'
* Run local sample '$> mvn exec:java -Dexec.mainClass="eu.eudat.b2access.authz.samples.DataManagement"'

## Build and run instructions for XACML 3.0 server and sample client
* change to sub-project directory balanaDemonstrator and execute '$> mvn clean assembly:assembly -DskipTests'
* go to target sub-directory and untar/unzip balanaDemonstrator-x.y.z.(tar.gz|zip) and change to the newly created directory
* edit paths of the 'xacml configuration' inside 'conf/xacmlServer.config' and then execute '$> bin/start.sh'
