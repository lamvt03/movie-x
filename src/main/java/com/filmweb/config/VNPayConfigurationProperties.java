package com.filmweb.config;

import jakarta.enterprise.context.Dependent;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ConfigProperties(prefix = "vnp")
@Dependent
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
