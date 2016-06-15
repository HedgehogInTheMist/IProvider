package com.epam.inet.provider.resource;

import java.util.ResourceBundle;

/**
 * Created by Hedgehog on 13.06.2016.
 */
public class MsgManager {

    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("message");

    private MsgManager() {}

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
