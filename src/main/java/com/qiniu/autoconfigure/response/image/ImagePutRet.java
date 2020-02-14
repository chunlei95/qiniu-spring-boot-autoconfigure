package com.qiniu.autoconfigure.response.image;

import com.qiniu.autoconfigure.response.CustomPutRet;

/**
 * @author xzmeasy
 * @date 2020/2/14
 */
public class ImagePutRet extends CustomPutRet {

    private ImageInfo imageInfo;

    private String imageUrl;

    public ImageInfo getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
