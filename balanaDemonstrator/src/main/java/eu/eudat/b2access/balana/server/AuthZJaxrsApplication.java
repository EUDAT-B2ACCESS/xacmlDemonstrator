package eu.eudat.b2access.balana.server;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import eu.eudat.b2access.balana.DataService;

public class AuthZJaxrsApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(DataService.class);
		return s;
	}
}
