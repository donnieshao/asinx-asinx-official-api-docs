package com.asinx.models;

import lombok.Data;

@Data
public class KycGatewayRequest extends APApiBaseRequest {
    private String doneViewURL;
    private String timeoutViewURL;
}
