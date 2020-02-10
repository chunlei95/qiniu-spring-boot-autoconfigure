package com.qiniu.autoconfigure.exception;

/**
 * @author xzmeasy
 */
public class QiNiuException extends RuntimeException {

    private Integer code;

    private String zhMessage;

    public QiNiuException(String message) {
        super(message);
    }

    public QiNiuException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public QiNiuException(Integer code, String message, String zhMessage) {
        super(message);
        this.code = code;
        this.zhMessage = zhMessage;
    }

    public QiNiuException(QiNiuExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
        this.zhMessage = exceptionEnum.getZhMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getZhMessage() {
        return zhMessage;
    }

    public void setZhMessage(String zhMessage) {
        this.zhMessage = zhMessage;
    }
}
