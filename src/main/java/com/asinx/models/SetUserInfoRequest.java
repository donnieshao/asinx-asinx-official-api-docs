package com.asinx.models;

import lombok.Data;

@Data
public class SetUserInfoRequest extends APApiBaseRequest {
    private  String firstName;
    /**
     *
     */
    private  String firstNameEnglish;
    /**
     *
     */
    private  String lastName;
    /**
     *
     */
    private  String lastNameEnglish;
    /**
     *  date Of Birth: yyyy-MM-dd,Example:1992-02-01
     */
    private String dateOfBirth;
    /**
     *  ISO3166-2 2-digit country code, Example:CN,FR
     */
    private  String nationality;

    /**
     *  Customer Address
     */
    private UserInfoAddressVo address;

    private IdentificationVo identification;
}
