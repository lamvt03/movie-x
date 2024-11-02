package com.filmweb.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.security.SecureRandom;

@ApplicationScoped
public class RandomUtils {

    @Inject
    private SecureRandom random;

    public Integer randomAvtId(int avtTotal){
        return 1 + random.nextInt(avtTotal);
    }

    public long randomInRangeExcept(long range, long except){
        long randomValue = Math.abs(random.nextLong() % range) + 1;
        if(randomValue == except){
            return randomInRangeExcept(range, except);
        }
        return randomValue;
    }
}
