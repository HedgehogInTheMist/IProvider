package com.epam.inet.provider.resource;

import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Message manager
 * Created by Hedgehog
 */
public enum MessageManager {
    INSTANCE;

    private static final String BUNDLE_NAME = "message";
    private ResourceBundle bundle = null;
    private Locale lastLocale = null;
    private LocaleManager localeManager = LocaleManager.INSTANCE;

    /**
     * get getMessage
     * @param key
     * @return
     */
    public synchronized String getMessage(String key, Locale locale) {

        try{
            if (lastLocale != null && lastLocale.equals(locale)){
                return bundle.getString(key);
            } else{
                Logger.getRootLogger().debug("Message manager reinitialized bundle");
//                bundle = ResourceBundle.getBundle("properties.database", locale);
                bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
                lastLocale = locale;
            }
        } catch (MissingResourceException e){
            return "???? " + key + " ????";
        }
        return bundle.getString(key);
    }

    public synchronized String getMessage(String key) {
        return bundle.getString(key);
    }


}
