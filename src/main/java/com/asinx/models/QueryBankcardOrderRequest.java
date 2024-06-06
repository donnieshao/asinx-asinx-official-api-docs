package com.asinx.models;

import lombok.Data;

@Data
public class QueryBankcardOrderRequest extends APApiBaseRequest {
    private Integer userBankcardId;
    private String requestOrderId;
}
