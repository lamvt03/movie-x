package com.filmweb.api.resp;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CheckUserBalanceResp {
  boolean isSuccess;
  boolean canPurchase;
}
