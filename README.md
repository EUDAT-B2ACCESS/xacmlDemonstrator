# B2ACCESS XACML 3.0 Demonstrator
The Project is an XACML 3.0 Application using Balana library to demonstrate a flow of a client accessing a dummy (data management) service. Each request from the client intercepted by Policy Enforcement Point (PEP) at the service side and then sent to Policy Decision Point (PDP), which eventually decides (after evaluating the request against the xacml policies) to grant or deny the access.

## Prerequisites
* Java 1.8
* Maven 3

## Build and run instructions
	
> checkout and change to project directory
> mvn clean install -DskipTests
> 
