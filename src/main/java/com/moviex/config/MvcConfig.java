package com.moviex.config;

import jakarta.mvc.engine.ViewEngine;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashMap;
import java.util.Map;

@ApplicationPath("/movie-x")
public class MvcConfig extends Application {

    //override default view location: /WEB-INF/views/
    @Override
    public Map<String, Object> getProperties() {
        final Map<String, Object> map = new HashMap<>();
        map.put(ViewEngine.VIEW_FOLDER, "/views/");
        return map;
    }
}
