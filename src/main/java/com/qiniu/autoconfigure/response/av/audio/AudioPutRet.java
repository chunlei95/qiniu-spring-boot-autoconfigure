package com.qiniu.autoconfigure.response.av.audio;

import com.qiniu.autoconfigure.response.av.AVPutRet;

/**
 * @author xzmeasy
 * @date 2020/2/14
 */
public class AudioPutRet extends AVPutRet {

    private Audio audio;

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

}
