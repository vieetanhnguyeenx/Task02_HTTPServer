package config;

public class ConfigurationManager {
    public static ConfigurationManager myConfigManager;
    private static Configuration myCurrentConfig;

    private ConfigurationManager() {

    }

    public static ConfigurationManager getMyConfigManager() {
        if(myConfigManager == null) {
            myConfigManager = new ConfigurationManager();
        }
        return myConfigManager;
    }

    public void loadConfigFile(String filePath) {

    }

    public void getCurrentConfig() {

    }
}
