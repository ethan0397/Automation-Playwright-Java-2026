package com.company.automation.config;


public class Config {

    public static String baseUrl() {
        return Environment.getBaseUrl();
    }

    public static boolean headless() {
           return Environment.isHeadless();
    }

}
