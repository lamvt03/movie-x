package com.filmweb.utils;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ApplicationScoped
public class TimeFormatter {

    private final Map<String, Long> intervals = new LinkedHashMap<>();

    public TimeFormatter(){
        intervals.put("năm", 31536000L);
        intervals.put("tháng", 2592000L);
        intervals.put("tuần", 604800L);
        intervals.put("ngày", 86400L);
        intervals.put("giờ", 3600L);
        intervals.put("phút", 60L);
        intervals.put("giây", 1L);
    }

    private Duration getDuration(LocalDateTime timestamp) {
        return Duration.between(timestamp, LocalDateTime.now());
    }

    public String getTimeAgoString(LocalDateTime timestamp){
        long seconds = this.getDuration(timestamp).toSeconds();

        for(Map.Entry<String, Long> entry : intervals.entrySet()){
            String key = entry.getKey();
            long interval = seconds / entry.getValue();
            if (interval >= 1) {
                return interval + " " + key + " trước";
            }
        }

        return "Vừa xong";
    }
}
