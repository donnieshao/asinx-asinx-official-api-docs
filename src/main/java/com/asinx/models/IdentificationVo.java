package com.asinx.models;

import lombok.Data;

@Data
public class IdentificationVo {
    private String identificationType;
    /**
     * identification Number ,Example: G221256P
     */
    private String identificationNumber;
    /**
     * identification Expiry Date:yyyy-MM-dd ,Example: 2022-10-01
     */
    private String identificationExpiryDate;
}
