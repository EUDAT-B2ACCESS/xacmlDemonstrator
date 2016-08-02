package eu.eudat.b2access.authz.server;

import java.io.File;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigSyntax;

public class XACMLServerConfig {
	public static final String HTTP_SERVER_HOSTNAME = "b2access.authz.server.hostname";
	public static final String HTTP_SERVER_PORT = "b2access.authz.server.port";
	public static final String XACML_PDP_CONFIG = "b2access.authz.xacml.pdpConfig";
	public static final String XACML_POLICY_DIR = "b2access.authz.xacml.policyDir";
	private Config conf;
	public XACMLServerConfig(String propsPath) {
		conf = ConfigFactory.parseFile(new File(propsPath),
				ConfigParseOptions.defaults().setSyntax(ConfigSyntax.PROPERTIES));
	}
	
	public String getStringValue(String fieldName){
		return conf.getString(fieldName);
	}
	
	public Integer getIntegerValue(String fieldName){
		return conf.getInt(fieldName);
	}
	

}
