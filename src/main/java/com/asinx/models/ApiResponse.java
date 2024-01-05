package com.asinx.models;


import lombok.Data;

import java.io.Serializable;

@Data
public class ApiResponse<T> implements Serializable {


    private boolean success;
    private T result;
    private int code;
    private String message;

    public ApiResponse() {
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ApiResponse response = (ApiResponse) o;
            return this.success == response.success && this.result.equals(response.result) && this.code==response.code;
        } else {
            return false;
        }
    }

}
