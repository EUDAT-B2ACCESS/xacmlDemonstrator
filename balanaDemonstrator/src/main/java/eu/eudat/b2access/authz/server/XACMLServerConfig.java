package eu.eudat.b2access.authz.server;

import java.io.File;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigSyntax;

public class XACMLServerConfig {
	public static final String HTTP_SERVER_HOSTNAME = "b2access.authz.server.hostname";
	public static final String HTTP_SERVER_PORT = "b2access.authz.server.port";
	public static final String XACML_PDP_CONFIG = "b2access.authz.server.xacml.pdpConfig";
	public static final String XACML_POLICY_DIR = "b2access.authz.server.xacml.policyDir";
	public static final String XACML_REMOTE_PDP_HOSTNAME = "b2access.authz.server.xacml.remotePdp.hostname";
	public static final String XACML_REMOTE_PDP_PORT = "b2access.authz.server.xacml.remotePdp.port";
	
	private Config conf;
	private String propsPath;
	public XACMLServerConfig(String propsPath) {
		this.propsPath = propsPath; 
		conf = ConfigFactory.parseFile(new File(propsPath),
				ConfigParseOptions.defaults().setSyntax(ConfigSyntax.PROPERTIES));
	}
	
	public String getStringValue(String fieldName){
		return conf.getString(fieldName);
	}
	
	public Integer getIntegerValue(String fieldName){
		return conf.getInt(fieldName);
	}
	public String getHostname(){
		return conf.getString(HTTP_SERVER_HOSTNAME);
	}
	public Integer getPort(){
		return conf.getInt(HTTP_SERVER_PORT);
	}
	public String getXACMLPdpConfigPath(){
		return conf.getString(XACML_PDP_CONFIG);
	}
	public String getXACMLPolicyDirPath(){
		return conf.getString(XACML_POLICY_DIR);
	}
	public String getPropertiesPath(){
		return propsPath;
	}
	
}
