package com.asinx.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserRechargeInfoRequest extends APApiBaseRequest {
    private String currency;
    private BigDecimal amount;
}
