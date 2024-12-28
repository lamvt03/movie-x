package com.moviex.config;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ConfigProperties(prefix = "vnp")
@ApplicationScoped
@Getter
public class VNPayConfigurationProperties {
  String payUrl;
  String returnUrl;
  String tmnCode;
  String secretKey;
  String version;
  String command;
  String orderType;
  String bankCode;
}
