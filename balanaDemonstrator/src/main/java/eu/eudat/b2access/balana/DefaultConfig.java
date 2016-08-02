package eu.eudat.b2access.balana;

import eu.eudat.b2access.authz.server.XACMLServerConfig;

/**
 * Instead of default config {@link XACMLServerConfig} is recommended to be used
 *
 * @author Willem Elbers
 */
@Deprecated
public class DefaultConfig {
    public final static String HOSTNAME = "localhost";
    public final static int PORT = 8085;
}
