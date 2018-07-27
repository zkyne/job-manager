package com.zkyne.jobmanager.common.exception;

/**
 * @ClassName: JobManagerException
 * @Description: 自定义异常
 * @Author: zkyne
 * @Date: 2018/7/19 14:35
 */
public class JobManagerException extends RuntimeException {

    private static final long serialVersionUID = -1705001773282540010L;

    private int errorCode;

    public JobManagerException(int errorCode,String errorMsg){
        super(errorMsg);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}
