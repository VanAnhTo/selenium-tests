package com.wikia.webdriver.common.core.configuration;

import com.wikia.webdriver.common.core.exceptions.TestEnvInitFailedException;
import com.wikia.webdriver.common.core.geoedge.GeoEdgeProxy;
import com.wikia.webdriver.common.properties.Credentials;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriverException;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configuration handler. This Class should handle run configuration and global properties.
 * Configuration handling: <ol> <li>Look for the property key in testConfig map, if key is present,
 * return the value</li> <li>Look for the property key in system properties - return value of this
 * property if key present </li> <li>If no System Property is found - value is provided from
 * configuration files (config_default.yml and config.yml). Values provided in config.yml, are
 * overriding values from config_default.yml</li> </ol>
 */
public class Configuration {

  private static final String DEFAULT_CONFIG_FILE_NAME = "config_default.yml";
  private static final String LOCAL_CONFIG_FILE_NAME = "config.yml";
  private static final Logger LOGGER = Logger.getLogger(Configuration.class.getName());
  private static String wikiaDomain;
  private static Map<String, String> defaultConfig;
  private static Map<String, String> testConfig = new HashMap<>();

  private Configuration() {
  }

  private static Map<String, String> readConfiguration() {
    if (defaultConfig == null) {
      Yaml yaml = new Yaml();

      try {
        defaultConfig =
            (Map<String, String>) yaml
                .load(new FileInputStream(new File(DEFAULT_CONFIG_FILE_NAME)));
      } catch (FileNotFoundException e) {
        throw new TestEnvInitFailedException(String.format("CANNOT FIND DEFAULT CONFIG FILE : %s",
                                                           DEFAULT_CONFIG_FILE_NAME), e);
      }

      File localConfigFile = new File(LOCAL_CONFIG_FILE_NAME);
      if (localConfigFile.exists()) {
        try {
          defaultConfig.putAll((Map<String, String>) yaml
              .load(new FileInputStream(localConfigFile)));
        } catch (FileNotFoundException e) {
          LOGGER.log(Level.INFO, "local config file not found", e);
        }
      } else {
        LOGGER.log(Level.INFO, "local config file does not exist");
      }
    }

    return defaultConfig;
  }

  private static String getPropertyFromFile(String propertyName) {
    return "null".equals(String.valueOf(readConfiguration().get(propertyName))) ? null : String
        .valueOf(readConfiguration().get(propertyName));
  }

  private static String getProp(String propertyName) {
    if (testConfig.get(propertyName) == null) {
      return System.getProperty(propertyName) != null ? System.getProperty(propertyName)
                                                      : getPropertyFromFile(propertyName);
    } else {
      return testConfig.get(propertyName);
    }
  }

  public static String getBrowser() {
    return getProp("browser");
  }

  public static String getEnv() {
    return getProp("env");
  }

  public static String getWikiName() {
    return getProp("wikiName");
  }

  public static String getPlatform() {
    return getProp("platform");
  }

  public static String getCredentialsFilePath() {
    return getProp("credentialsPath");
  }

  public static String getQS() {
    return getProp("qs");
  }

  public static String getAppiumIp() {
    return getProp("appiumIp");
  }

  public static String getDeviceName() {
    return getProp("deviceName");
  }

  public static String getDisableFlash() {
    return getProp("disableFlash");
  }

  public static String getJSErrorsEnabled() {
    return getProp("jsErrorsEnabled");
  }

  public static String getLogEnabled() {
    return getProp("logEnabled");
  }

  public static String getMockAds() {
    return getProp("mockAds");
  }

  public static String getEmulator() {
    return getProp("emulator");
  }

  public static String getPageLoadStrategy(){
    return getProp("unstablePageLoadStrategy");
  }

  public static Credentials getCredentials() {
    return new Credentials();
  }

  public static String getEnvType() {
    if (getEnv().contains("prod")) {
      return "prod";
    } else if (getEnv().contains("verify") || getEnv().contains("preview")
               || getEnv().contains("sandbox") || getEnv().contains("staging")) {
      return "staging";
    } else if (getEnv().contains("dev")) {
      return "dev";
    }
    return "";
  }

  public static String getWikiaDomain() {
    if (StringUtils.isBlank(wikiaDomain)) {
      wikiaDomain = "dev".equals(getEnvType()) ? ".wikia-dev.com" : ".wikia.com";
    }

    return wikiaDomain;
  }

  public static void setTestValue(String key, String value) {
    testConfig.put(key, value);
  }

  public static void clearCustomTestProperties() {
    testConfig.clear();
  }

  public static String getCountryCode() {
    return getProp("countryCode");
  }

  public static String getProxyAddress() {
    return getProp("proxyAddress");
  }

  public static String getEffectiveProxyAddress() {
    String countryCode = Configuration.getCountryCode();
    String proxyAddress = Configuration.getProxyAddress();
    if (StringUtils.isNotBlank(proxyAddress)) {
      return proxyAddress;
    } else if (StringUtils.isNotBlank(countryCode)) {
      return GeoEdgeProxy.getProxyAddress(countryCode);
    }
    return null;
  }

  public static Proxy getEffectiveProxy() {
    String proxyAddress = getEffectiveProxyAddress();
    if ((proxyAddress != null) && StringUtils.isNotBlank(proxyAddress)) {
      int colonPosition = proxyAddress.indexOf(':');
      return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
        proxyAddress.substring(0,colonPosition),
        Integer.valueOf(proxyAddress.substring(colonPosition+1))
      ));
    }
    return null;
  }

  public static boolean useProxy(){
    return Boolean.valueOf(getProp("useProxy")) || StringUtils.isNotBlank(getCountryCode()) || StringUtils.isNotBlank(getProxyAddress());
  }

  /**
   * @return null if window is supposed to be maximised, Dimension if any other size is demanded
   */
  public static Dimension getBrowserSize() {
    String size = getProp("browserSize");

    if (StringUtils.isNotBlank(size) || "maximised".equals(size) || size.split("x").length == 2) {
      if ("maximised".equals(size)) {
        return null;
      } else {
        return new Dimension(Integer.valueOf(size.split("x")[0]),
                             Integer.valueOf(size.split("x")[1]));
      }
    } else {
      throw new WebDriverException("browser size: " + size + " is not a proper value");
    }
  }

  public static String[] getExtensions() {
    String exts = getProp("extensions");

    if (StringUtils.isEmpty(exts)) {
      return new String[]{};
    }

    ArrayList<String> res = new ArrayList<>();
    for (String ext : exts.replace("[", "").replace("]", "").split(",")) {
      res.add(ext.trim());
    }

    return res.toArray(new String[res.size()]);
  }
}
