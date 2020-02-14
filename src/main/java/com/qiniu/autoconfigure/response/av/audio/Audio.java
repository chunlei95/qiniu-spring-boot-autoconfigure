package com.qiniu.autoconfigure.response.av.audio;

import java.util.Date;

/**
 * @author xzmeasy
 * @date 2020/2/14
 */
@SuppressWarnings("ALL")
public class Audio {

    private String bit_rate;

    private Integer channels;

    private String codec_name;

    private String codec_type;

    private String duration;

    private Integer index;

    private String nb_frames;

    private String r_frame_rate;

    private String sample_fmt;

    private String sample_rate;

    private String start_time;

    private Date creation_time;

    public String getBit_rate() {
        return bit_rate;
    }

    public void setBit_rate(String bit_rate) {
        this.bit_rate = bit_rate;
    }

    public Integer getChannels() {
        return channels;
    }

    public void setChannels(Integer channels) {
        this.channels = channels;
    }

    public String getCodec_name() {
        return codec_name;
    }

    public void setCodec_name(String codec_name) {
        this.codec_name = codec_name;
    }

    public String getCodec_type() {
        return codec_type;
    }

    public void setCodec_type(String codec_type) {
        this.codec_type = codec_type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getNb_frames() {
        return nb_frames;
    }

    public void setNb_frames(String nb_frames) {
        this.nb_frames = nb_frames;
    }

    public String getR_frame_rate() {
        return r_frame_rate;
    }

    public void setR_frame_rate(String r_frame_rate) {
        this.r_frame_rate = r_frame_rate;
    }

    public String getSample_fmt() {
        return sample_fmt;
    }

    public void setSample_fmt(String sample_fmt) {
        this.sample_fmt = sample_fmt;
    }

    public String getSample_rate() {
        return sample_rate;
    }

    public void setSample_rate(String sample_rate) {
        this.sample_rate = sample_rate;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public Date getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(Date creation_time) {
        this.creation_time = creation_time;
    }
}
