package com.company.automation.config;
import java.io.InputStream;
import java.util.Properties;

public final class Environment {

    private static final Properties properties = new Properties();
    private static final String ENV;

    static {
        // Lấy env từ command line: -Denv=dev
        ENV = System.getProperty("env", "dev");

        String filePath = "env/" + ENV + ".properties";

        try (InputStream is = Environment.class
                .getClassLoader()
                .getResourceAsStream(filePath)) {

            if (is == null) {
                throw new RuntimeException("Cannot find config file: " + filePath);
            }

            properties.load(is);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load environment config", e);
        }
    }

    private Environment() {
        // prevent init
    }

    /* =====================
       Getter methods
       ===================== */

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }
    
    public static String getLoginPath() {
        return properties.getProperty("login.path");
    }

    public static String getContactPath() {
        return properties.getProperty("contact.path");
    }

    public static String getBrowser() {
        return properties.getProperty("browser", "chromium");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(
                properties.getProperty("headless", "true")
        );
    }

    public static int getTimeout() {
        return Integer.parseInt(
                properties.getProperty("timeout", "30000")
        );
    }
}
