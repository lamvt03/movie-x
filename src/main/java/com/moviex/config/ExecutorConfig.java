package com.moviex.config;

import static java.util.concurrent.TimeUnit.SECONDS;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

@ApplicationScoped
public class ExecutorConfig {
  
  @Produces
  @Named("sendEmailExecutor")
  public Executor sendEmailExecutor() {
    return new ThreadPoolExecutor(
        1, 4, 30, SECONDS, new LinkedBlockingQueue<>()
    );
  }
}
