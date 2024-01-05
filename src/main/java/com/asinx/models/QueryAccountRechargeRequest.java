package com.asinx.models;

import lombok.Data;

@Data
public class QueryAccountRechargeRequest extends APApiBaseRequest {
    private String uid;
    private String symbol;
    private Integer pageNum;
    private Integer pageSize;
}
