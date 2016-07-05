package com.epam.inet.provider.resource;

import java.util.ResourceBundle;

/**
 * Created by Hedgehog on 13.06.2016.
 */
public class MessageManager {

    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("message");

    private MessageManager() {}

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
