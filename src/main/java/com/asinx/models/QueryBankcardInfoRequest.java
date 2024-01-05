package com.asinx.models;

import lombok.Data;

@Data
public class QueryBankcardInfoRequest extends APApiBaseRequest {
    private Integer userBankcardId;
}
