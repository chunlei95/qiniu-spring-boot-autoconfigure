package com.qiniu.autoconfigure.response.av;

import com.qiniu.autoconfigure.response.CustomPutRet;

/**
 * @author xzmeasy
 * @date 2020/2/14
 */
public class AVPutRet extends CustomPutRet {

    private Format format;

    private String path;

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
