package com.moviex.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class TimeFormatUtils {

    private final static Map<String, Long> intervals = new LinkedHashMap<>();
    
    static {
        intervals.put("năm", 31536000L);
        intervals.put("tháng", 2592000L);
        intervals.put("tuần", 604800L);
        intervals.put("ngày", 86400L);
        intervals.put("giờ", 3600L);
        intervals.put("phút", 60L);
        intervals.put("giây", 1L);
    }

    private static Duration getDuration(LocalDateTime timestamp) {
        return Duration.between(timestamp, LocalDateTime.now());
    }

    public static String getTimeAgoString(LocalDateTime timestamp){
        long seconds = getDuration(timestamp).toSeconds();

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
