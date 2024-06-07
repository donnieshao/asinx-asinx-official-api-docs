package com.asinx.models;

import lombok.Data;

@Data
public class UserInfoAddressVo {
    private  String addressLine1;
    /**
     * Example: Nashville TN 37011
     */
    private  String addressLine2;
    /**
     * Example:Paris
     */
    private  String city;
    /**
     *  2-digit country code complies with ISO3166-2 specification,Example:FR
     */
    private  String countryCode;
    /**
     *  Example:C2A2EX
     */
    private  String postCode;
}
