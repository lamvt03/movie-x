package com.filmweb.util;

import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

@ApplicationScoped
public class TimeFormatter {

    private Duration getDuration(Timestamp timestamp){
        LocalDateTime createdTime = timestamp.toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();
        return Duration.between(createdTime, now);
    }

    public String getTimeAgoString(Timestamp timestamp){
        Duration duration = this.getDuration(timestamp);
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;

        if (days > 0) {
            return days + " ngày trước";
        } else if (hours > 0) {
            return hours + " giờ trước";
        } else if(minutes > 0){
            return minutes + " phút trước";
        } else {
            return "Vừa xong";
        }

    }
}
