package com.zkyne.jobmanager.common.enums;

/**
 * @ClassName: CodeEnum
 * @Description:
 * @Author: zkyne
 * @Date: 2018/7/17 18:20
 */
public enum CodeEnum {
    CODE_SUCCESS(1, "SUCCESS!"),
    CODE_FAILURE(0, "FAILURE!");
    private int code;
    private String message;

    CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CodeEnum getByCode(int code) {
        CodeEnum[] arr$ = values();
        for (CodeEnum codeEnum : arr$) {
            if (codeEnum.code == code) {
                return codeEnum;
            }
        }

        return null;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
