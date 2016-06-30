package com.epam.inet.provider.resource;

import java.util.ResourceBundle;

/**
 * Performs path reading from property file
 * Created by Hedgehog
 */
public enum PathManager {
    INSTANCE;

    private static final String BUNDLE_NAME = "properties/path";
    private final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * Get path
     * @param key
     * @return
     */
    public synchronized String getString(String key) {
        return bundle.getString(key);
    }
}
