package com.example.codefest.springclouddepartmentclient.entity;

public enum ResponseCode {

    SUCCESS(200),
    NOT_FOUND(204),
    INTERNAL_ERROR(500),
    FAILURE(500),
    BAD_REQUEST(400),
    FORBIDDEN(403),
    INFORMATION(202);

    private final Integer code;

    ResponseCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
