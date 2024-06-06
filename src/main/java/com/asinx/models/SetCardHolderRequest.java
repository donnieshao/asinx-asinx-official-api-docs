package com.asinx.models;

import lombok.Data;

@Data
public class SetCardHolderRequest extends APApiBaseRequest {
    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String countryCode;

    private String residentialAddressCity;

    private String residentialAddressCountry;

    private String residentialAddressLine1;

    private String residentialAddressState;

    private String residentialAddressLine2;

    private String residentialAddressPostalCode;
}
