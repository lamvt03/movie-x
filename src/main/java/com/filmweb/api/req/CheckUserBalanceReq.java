package com.filmweb.api.req;

import java.util.UUID;

public record CheckUserBalanceReq(
    UUID videoId
) {

}
