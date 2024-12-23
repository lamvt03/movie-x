package com.moviex.api.req;

import java.util.UUID;

public record CheckUserBalanceReq(
    UUID videoId
) {

}
