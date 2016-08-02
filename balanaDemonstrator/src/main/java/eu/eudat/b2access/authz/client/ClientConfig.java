package eu.eudat.b2access.authz.client;

import java.io.File;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigSyntax;

import eu.eudat.b2access.authz.server.XACMLServerConfig;

/**
 * Client configuration for testing
 *
 * @author Willem Elbers
 * @author a.memon
 */

public class ClientConfig extends XACMLServerConfig{

	public ClientConfig(String propsPath) {
		super(propsPath);
	}
    
}
