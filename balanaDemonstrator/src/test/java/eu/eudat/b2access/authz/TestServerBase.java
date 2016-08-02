package eu.eudat.b2access.authz;

import org.junit.After;
import org.junit.Before;

import eu.eudat.b2access.authz.server.XACMLServer;

public class TestServerBase {
	XACMLServer server;
	@Before
	public void setup() throws Exception{
		server = new XACMLServer("src/main/conf/xacmlServer.config");
		server.start();
	}
	@After
	public void tearDown(){
		server.stop();
	}
	
}
