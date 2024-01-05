package com.asinx.models;

import lombok.Data;

@Data
public class QueryBankcardBalanceRequest extends APApiBaseRequest {
    private Integer userBankcardId;
}
