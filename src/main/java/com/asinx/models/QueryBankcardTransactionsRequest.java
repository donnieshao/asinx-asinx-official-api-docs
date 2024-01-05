package com.asinx.models;

import lombok.Data;

@Data
public class QueryBankcardTransactionsRequest extends APApiBaseRequest {
    private Integer userBankcardId;
    private Integer pageSize;
    private Integer pageNum;
    private Long fromTimestamp;
    private Long endTimestamp;
}
