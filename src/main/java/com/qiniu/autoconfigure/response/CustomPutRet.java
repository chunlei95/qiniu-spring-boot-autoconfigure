package com.qiniu.autoconfigure.response;

/**
 * @author xzmeasy
 */
public class CustomPutRet {

    private String key;

    private String fname;

    private Long fsize;

    private String miniType;

    private String ext;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Long getFsize() {
        return fsize;
    }

    public void setFsize(Long fsize) {
        this.fsize = fsize;
    }

    public String getMiniType() {
        return miniType;
    }

    public void setMiniType(String miniType) {
        this.miniType = miniType;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

}
