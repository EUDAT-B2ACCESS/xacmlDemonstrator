package eu.eudat.b2access.balana;

import java.io.IOException;

import org.wso2.balana.Balana;
import org.wso2.balana.ConfigurationStore;
import org.wso2.balana.PDP;
import org.wso2.balana.PDPConfig;
import org.wso2.balana.finder.impl.FileBasedPolicyFinderModule;

public class DataServicePDP {
	private static Balana balana;
	private static PDP pdp;
	/**
	 * Returns a new PDP instance with new XACML policies
	 *
	 * @return a PDP instance
	 */
	private void initPDP() {
		if (pdp == null) {
			PDPConfig pdpConfig = balana.getPdpConfig();
			pdp = new PDP(pdpConfig);	
		}
	}

	/**
	 * Initialise Balana object
	 * */	
	private void initBalana() throws IOException {
		if (balana == null) {
			System.setProperty(ConfigurationStore.PDP_CONFIG_PROPERTY, "src/main/conf/xacmlPdpConfig.xml");
			System.setProperty(FileBasedPolicyFinderModule.POLICY_DIR_PROPERTY, "src/main/conf/policies");
			balana = Balana.getInstance();			
		}
	}
	public DataServicePDP() {
		try {
			initBalana();
			initPDP();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String evaluate(String request) {
		return pdp.evaluate(request);
	}
}
