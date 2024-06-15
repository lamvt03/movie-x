package com.filmweb.utils;

import com.filmweb.constant.SessionConstant;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.servlet.http.HttpSession;

@ApplicationScoped
public class AppUtils {
    public String getPrevPageUrl(HttpSession session){
        String prevUri = (String) session.getAttribute(SessionConstant.PREV_PAGE_URI);
        String prevQueryString = (String) session.getAttribute(SessionConstant.PREV_PAGE_QUERY_STRING);
        StringBuilder urlBd = new StringBuilder(prevUri);
        if(prevQueryString != null){
            urlBd
                    .append("?")
                    .append(prevQueryString);
        }
        return urlBd.toString();
    }
}
