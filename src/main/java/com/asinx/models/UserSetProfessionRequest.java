package com.asinx.models;

import lombok.Data;

@Data
public class UserSetProfessionRequest extends APApiBaseRequest {

    private String first_name;

    private String last_name;

    private String first_name_en;

    private String last_name_en;

    private String birthday;

    private String annual_income;

    private String occupation;

    private String position;

    private String id_type;

    private String frontImg;

    private String backImg;

    private String number;

    private String country;

    private String expiry_date;
}
