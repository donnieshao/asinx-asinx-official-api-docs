package com.asinx.models;

import lombok.Data;

@Data
public class UserRegisterRequest extends APApiBaseRequest {
    private String mobilePrefix;
    private String mobileNumber;
    private String email;
}
