package eu.eudat.b2access.authz.client;

import java.io.File;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigSyntax;

/**
 * Client configuration for testing
 *
 * @author Willem Elbers
 * @author a.memon
 */

public class ClientConfig {
    public static final String HTTP_SERVER_HOSTNAME = "b2access.authz.server.hostname";
	public static final String HTTP_SERVER_PORT = "b2access.authz.server.port";
	private Config conf;
	
	public ClientConfig(String propsPath) {
		conf = ConfigFactory.parseFile(new File(propsPath),
				ConfigParseOptions.defaults().setSyntax(ConfigSyntax.PROPERTIES));
	}
	
	public String getHostname(){
		return conf.getString(HTTP_SERVER_HOSTNAME);
	}
	
	public Integer getPort(){
		return conf.getInt(HTTP_SERVER_PORT);
	}
	
	public String getStringValue(String fieldName){
		return conf.getString(fieldName);
	}
	
	public Integer getIntegerValue(String fieldName){
		return conf.getInt(fieldName);
	}
}
