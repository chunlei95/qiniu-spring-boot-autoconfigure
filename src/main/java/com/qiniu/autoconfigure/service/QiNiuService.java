package com.qiniu.autoconfigure.service;

import com.qiniu.autoconfigure.exception.QiNiuException;
import com.qiniu.autoconfigure.exception.QiNiuExceptionEnum;
import com.qiniu.autoconfigure.properties.QiNiuProperties;
import com.qiniu.autoconfigure.response.av.audio.AudioPutRet;
import com.qiniu.autoconfigure.response.av.video.VideoPutRet;
import com.qiniu.autoconfigure.response.image.ImagePutRet;
import com.qiniu.autoconfigure.util.FileType;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Used to operate qiniu.
 *
 * @author xzmeasy
 */
public class QiNiuService {

    private UploadManager uploadManager;

    private QiNiuProperties qiNiuProperties;

    @Autowired
    public QiNiuService(UploadManager uploadManager,
                        QiNiuProperties qiNiuProperties) {
        this.uploadManager = uploadManager;
        this.qiNiuProperties = qiNiuProperties;
    }

    /**
     * Upload image to Qiniu server.
     *
     * @param image File that need to upload.
     * @return File url after upload.
     */
    public ImagePutRet uploadImage(MultipartFile image) {
        String key = image.getOriginalFilename();
        String uploadToken = uploadToken(key, FileType.IMAGE);
        byte[] bytes = transferFileToByteArray(image);
        try {
            Response response = uploadManager.put(bytes, key, uploadToken);
            if (response.isOK()) {
                ImagePutRet imagePutRet = response.jsonToObject(ImagePutRet.class);
                String imageUrl = getDomain() + imagePutRet.getKey();
                imagePutRet.setImageUrl(imageUrl);
                return imagePutRet;
            } else {
                throw new QiNiuException(QiNiuExceptionEnum.UPLOAD_FILE_FAILED);
            }
        } catch (QiniuException e) {
            throw new QiNiuException(e.code(), e.error());
        }
    }

    /**
     * Upload audio to Qiniu server.
     *
     * @param audio Audio file
     * @return {@link AudioPutRet}
     */
    public AudioPutRet uploadAudio(MultipartFile audio) {
        String key = audio.getOriginalFilename();
        String uploadToken = uploadToken(key, FileType.AUDIO);
        byte[] bytes = transferFileToByteArray(audio);
        try {
            Response response = uploadManager.put(bytes, key, uploadToken);
            if (response.isOK()) {
                AudioPutRet audioPutRet = response.jsonToObject(AudioPutRet.class);
                String audioPath = getDomain() + audioPutRet.getKey();
                audioPutRet.setPath(audioPath);
                return audioPutRet;
            } else {
                throw new QiNiuException(QiNiuExceptionEnum.UPLOAD_FILE_FAILED);
            }
        } catch (QiniuException e) {
            throw new QiNiuException(e.code(), e.error());
        }
    }

    /**
     * Upload video to Qiniu server.
     *
     * @param video video file
     * @return {@link AudioPutRet}
     */
    public VideoPutRet videoUpload(MultipartFile video) {
        String key = video.getOriginalFilename();
        String uploadToken = uploadToken(key, FileType.AUDIO);
        byte[] bytes = transferFileToByteArray(video);
        try {
            Response response = uploadManager.put(bytes, key, uploadToken);
            if (response.isOK()) {
                VideoPutRet videoPutRet = response.jsonToObject(VideoPutRet.class);
                String videoPath = getDomain() + videoPutRet.getKey();
                videoPutRet.setPath(videoPath);
                return videoPutRet;
            } else {
                throw new QiNiuException(QiNiuExceptionEnum.UPLOAD_FILE_FAILED);
            }
        } catch (QiniuException e) {
            throw new QiNiuException(e.code(), e.error());
        }
    }

    /**
     * Upload token.
     *
     * @return Token value of String type.
     */
    private String uploadToken(String key, FileType fileType) {
        String bucket = qiNiuProperties.getBucket();
        validNull(bucket);
        String accessKey = qiNiuProperties.getAccessKey();
        validNull(accessKey);
        String secretKey = qiNiuProperties.getSecretKey();
        validNull(secretKey);
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy;
        if (Objects.equals(fileType, FileType.IMAGE)) {
            putPolicy = imageUploadPolicy();
        } else if (Objects.equals(fileType, FileType.AUDIO)) {
            putPolicy = audioUploadPolicy();
        } else {
            putPolicy = videoUploadPolicy();
        }
        long expires = 3600;
        return auth.uploadToken(bucket, key, expires, putPolicy);
    }

    /**
     * Validate if the data is null.
     *
     * @param data Data that need to validate.
     */
    private void validNull(String data) {
        if (StringUtils.isNullOrEmpty(data)) {
            QiNiuExceptionEnum canNotBeNull = QiNiuExceptionEnum.CAN_NOT_BE_NULL;
            throw new QiNiuException(canNotBeNull.getCode(), data + " " + canNotBeNull.getMessage(), data + " " + canNotBeNull.getZhMessage());
        }
    }

    /**
     * Transform file to byte array.
     *
     * @param file File that need to transform.
     * @return Byte array.
     */
    private byte[] transferFileToByteArray(MultipartFile file) {
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new QiNiuException(QiNiuExceptionEnum.EMPTY_FILE);
        }
        byte[] bytes = new byte[1024];
        int len;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            while (((len = inputStream.read(bytes)) != -1)) {
                byteArrayOutputStream.write(bytes, 0, len);
            }
        } catch (IOException e) {
            throw new QiNiuException(QiNiuExceptionEnum.READ_FILE_FAILED);
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Rewrite domain.
     *
     * @return Domain string after rewrite.
     */
    private String getDomain() {
        String domain = qiNiuProperties.getDomain();
        validNull(domain);
        String domainPrefix = qiNiuProperties.getDomainPrefix();
        validNull(domain);
        boolean endsWith = domain.endsWith("/");
        if (!endsWith) {
            domain = domain + "/";
        }
        return domainPrefix + domain;
    }

    @SuppressWarnings("JsonStandardCompliance")
    private StringMap imageUploadPolicy() {
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\"," +
                "\"fsize\":$(fsize),\"fname\":\"$(fname)\",\"ext\":\"$(ext)\",\"mimeType\":\"$(mimeType)\"," +
                "\"imageInfo\":$(imageInfo)}");
        return putPolicy;
    }

    @SuppressWarnings("JsonStandardCompliance")
    private StringMap audioUploadPolicy() {
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\"," +
                "\"fsize\":$(fsize),\"fname\":\"$(fname)\",\"ext\":\"$(ext)\",\"mimeType\":\"$(mimeType)\"," +
                "\"audio\":$(avinfo.audio),\"format\":$(avinfo.format)}");
        return putPolicy;
    }

    @SuppressWarnings("JsonStandardCompliance")
    private StringMap videoUploadPolicy() {
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\"," +
                "\"fsize\":$(fsize),\"fname\":\"$(fname)\",\"ext\":\"$(ext)\",\"mimeType\":\"$(mimeType)\"," +
                "\"video\":$(avinfo.video),\"format\":$(avinfo.format)}");
        return putPolicy;
    }

}
