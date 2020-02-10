package com.qiniu.autoconfigure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Qiniu configuration properties.
 *
 * @author xzmeasy
 */
@ConfigurationProperties(prefix = "spring.qiniu")
public class QiNiuProperties {

    private String domain;

    private String domainPrefix = "http://";

    private String bucket;

    private String accessKey;

    private String secretKey;

    /**
     * Enable callback.
     */
    private boolean callBack = false;

    private String callBackUrl;

    private String callBackBody;

    private String callBackBodyType = "application/json";

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomainPrefix() {
        return domainPrefix;
    }

    public void setDomainPrefix(String domainPrefix) {
        this.domainPrefix = domainPrefix;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public boolean isCallBack() {
        return callBack;
    }

    public void setCallBack(boolean callBack) {
        this.callBack = callBack;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

    public String getCallBackBody() {
        return callBackBody;
    }

    public void setCallBackBody(String callBackBody) {
        this.callBackBody = callBackBody;
    }

    public String getCallBackBodyType() {
        return callBackBodyType;
    }

    public void setCallBackBodyType(String callBackBodyType) {
        this.callBackBodyType = callBackBodyType;
    }
}
