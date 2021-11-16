package edu.sjtu.party.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguagePack {

    // language = "en", country = "US";
    // currentLocale = new Locale(language, country);
    // static Locale DEFAULT_LOCALE = new Locale("en", "US");

    static String LANGUAGE_PACKS = "lang";

    static Locale getCurrentLocale() {
        HttpServletRequest request = null;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        } catch (Exception ignored) { }
        return null != request ? request.getLocale() : null;
    }

    static Locale getCurrentLocaleWithDefault() {
        Locale locale = LanguagePack.getCurrentLocale();
        return null == locale ? Locale.getDefault() : locale;
    }

    public static String get(String key) {
        return LanguagePack.get(getCurrentLocaleWithDefault(), key);
    }

    public static String get(APIErrors err) {
        return LanguagePack.get(getCurrentLocaleWithDefault(), err);
    }

    public static String get(Locale locale, APIErrors err) {
        if(null == err)
            return null;
        return LanguagePack.get(locale, err.name());
    }

    public static String get(Locale locale, String key) {
        // if(null == locale) return key;
        if(null == locale) return key;
        ResourceBundle resource = ResourceBundle.getBundle(LANGUAGE_PACKS, locale);
        if(resource.containsKey(key)) {
            String message = resource.getString(key);
            if (null != message) {
                return  message;
            }
        }
        return key;
    }

}
