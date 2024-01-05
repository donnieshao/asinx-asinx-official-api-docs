package com.asinx.models;

import lombok.Data;

@Data
public class ApplyBankcardRequest extends APApiBaseRequest {
    private Integer bankcardId;
    private String residenceAddress;
    private Integer userBankcardId;
    private String tag;
}
