package com.epam.inet.provider.resource;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * Manages locales
 */
public enum LocaleManager {
    INSTANCE;

    public static final String REQUEST_PARAMETER = "lang";

    private Locale defaultLocale = Locale.forLanguageTag("ru");

    private HashMap<String, Locale> locales = new HashMap<String, Locale>();
    {
        locales.put("English", Locale.forLanguageTag("en"));
        locales.put("Русский", Locale.forLanguageTag("ru"));
    }

    public Locale getDefaultLocale(){
        return defaultLocale;
    }

    public HashMap<String, Locale> getLocales(){
        return locales;
    }

    /**
     * Get locale from code
     * @return
     */
    public Locale resolveLocale(ServletRequest request){
        String code = request.getParameter(REQUEST_PARAMETER);

        if (code != null){
            Iterator<Map.Entry<String, Locale>> iterator = locales.entrySet().iterator();

            while(iterator.hasNext()){
                Locale locale = iterator.next().getValue();
                if (locale.toLanguageTag().equalsIgnoreCase(code)){
                    return locale;
                }
            }
        }
        return defaultLocale;
    }

}
