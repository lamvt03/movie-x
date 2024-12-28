package com.moviex.utils;

import jakarta.enterprise.context.ApplicationScoped;

import java.security.SecureRandom;

@ApplicationScoped
public class RandomUtils {
    
    private final SecureRandom RANDOM = new SecureRandom();

    public Integer randomAvtId(int avtTotal){
        return 1 + RANDOM.nextInt(avtTotal);
    }

    public long randomInRangeExcept(long range, long except){
        long randomValue = Math.abs(RANDOM.nextLong() % range) + 1;
        if(randomValue == except){
            return randomInRangeExcept(range, except);
        }
        return randomValue;
    }
}
