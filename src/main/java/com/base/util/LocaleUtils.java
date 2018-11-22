package com.base.util;

import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * @Description:
 */
public class LocaleUtils {
    public static String getLocale(){
        String LOCALE_SESSION_ATTRIBUTE_NAME = SessionLocaleResolver.class.getName() + ".LOCALE";
        String locale="zh";
        if(RequestContextHolderUtil.getRequest().getSession().getAttribute(LOCALE_SESSION_ATTRIBUTE_NAME)!=null){
            locale = ((Locale)RequestContextHolderUtil.getRequest().getSession().getAttribute(LOCALE_SESSION_ATTRIBUTE_NAME)).getLanguage() ;
        }
        return locale;
    }
}