package eu.eudat.b2access.authz.server;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import eu.eudat.b2access.authz.pdp.PdpResource;
import eu.eudat.b2access.authz.samples.DataService;

public class AuthZJaxrsApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		System.out.println("Deploying Data Service");
        s.add(DataService.class);
        System.out.println("Deploying PDP Service");
        s.add(PdpResource.class);
		return s;
	}
}
