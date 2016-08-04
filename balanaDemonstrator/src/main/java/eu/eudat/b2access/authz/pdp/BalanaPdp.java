package eu.eudat.b2access.authz.pdp;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.wso2.balana.Balana;
import org.wso2.balana.ConfigurationStore;
import org.wso2.balana.PDP;
import org.wso2.balana.PDPConfig;
import org.wso2.balana.finder.impl.FileBasedPolicyFinderModule;

public class BalanaPdp {
	private static Logger log = LogManager.getLogger(BalanaPdp.class);
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
	 * @param policyDirPath 
	 * @param xacmlConfigPath 
	 * */	
	private void initBalana(String xacmlConfigPath, String policyDirPath) throws IOException {
		if (balana == null) {
			log.info("initialising balana");
			System.setProperty(ConfigurationStore.PDP_CONFIG_PROPERTY, xacmlConfigPath);
			System.setProperty(FileBasedPolicyFinderModule.POLICY_DIR_PROPERTY, policyDirPath);
			balana = Balana.getInstance();			
		}
	}
	
	public BalanaPdp(String xacmlConfigPath, String policyDirPath) {
		try {
			initBalana(xacmlConfigPath,policyDirPath);
			initPDP();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String evaluate(String request) {
		return pdp.evaluate(request);
	}
	
	public static void reloadConfiguration(String xacmlConfigPath, String policyDirPath){
		pdp = null;
		log.info("reloading Balana PDP configuration");
		BalanaPdp p = new BalanaPdp(xacmlConfigPath, policyDirPath);
	}
}
