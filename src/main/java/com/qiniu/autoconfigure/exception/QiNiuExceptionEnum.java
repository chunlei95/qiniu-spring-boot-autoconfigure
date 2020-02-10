package com.qiniu.autoconfigure.exception;

/**
 * @author xzmeasy
 */
public enum QiNiuExceptionEnum {

    /**
     * Can't be null.
     */
    CAN_NOT_BE_NULL(2000, "can not be null", "不能为空"),

    /**
     * File not found.
     */
    FILE_NOT_FOUND(2001, "file not found", "找不到该文件"),

    /**
     * Failed to read file.
     */
    READ_FILE_FAILED(2002, "failed to read file", "读取文件失败"),

    /**
     * Failed to upload file.
     */
    UPLOAD_FILE_FAILED(2003, "failed to upload file", "文件上传失败"),

    /**
     * Empty file.
     */
    EMPTY_FILE(2004, "empty file", "文件为空");

    /**
     * Exception code.
     */
    private Integer code;

    /**
     * Exception message.
     */
    private String message;

    /**
     * Exception message of Chinese.
     */
    private String zhMessage;

    QiNiuExceptionEnum(int i, String s, String s1) {
        this.code = i;
        this.message = s;
        this.zhMessage = s1;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getZhMessage() {
        return zhMessage;
    }

    public void setZhMessage(String zhMessage) {
        this.zhMessage = zhMessage;
    }
}
