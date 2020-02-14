package com.qiniu.autoconfigure.response.av.video;

import com.qiniu.autoconfigure.response.av.AVPutRet;

/**
 * @author xzmeasy
 * @date 2020/2/14
 */
public class VideoPutRet extends AVPutRet {

    private Video video;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
