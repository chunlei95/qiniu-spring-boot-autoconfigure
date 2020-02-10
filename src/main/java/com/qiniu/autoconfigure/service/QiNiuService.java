package com.qiniu.autoconfigure.service;

import com.qiniu.autoconfigure.exception.QiNiuException;
import com.qiniu.autoconfigure.exception.QiNiuExceptionEnum;
import com.qiniu.autoconfigure.properties.QiNiuProperties;
import com.qiniu.autoconfigure.response.CustomPutRet;
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
     * Upload file to Qiniu server.
     *
     * @param file File that need to upload.
     * @return File url after upload.
     */
    public CustomPutRet upload(MultipartFile file) {
        String key = file.getOriginalFilename();
        String uploadToken = uploadToken(key);
        byte[] bytes = transferFileToByteArray(file);
        try {
            Response response = uploadManager.put(bytes, key, uploadToken);
            if (response.isOK()) {
                CustomPutRet customPutRet = response.jsonToObject(CustomPutRet.class);
                String domain = qiNiuProperties.getDomain();
                validNull(domain);
                String domainPrefix = qiNiuProperties.getDomainPrefix();
                validNull(domain);
                boolean endsWith = domain.endsWith("/");
                if (!endsWith) {
                    domain = domain + "/";
                }
                String imageUrl = domainPrefix + domain + customPutRet.getKey();
                customPutRet.setImageUrl(imageUrl);
                return customPutRet;
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
    @SuppressWarnings("JsonStandardCompliance")
    private String uploadToken(String key) {
        String bucket = qiNiuProperties.getBucket();
        validNull(bucket);
        String accessKey = qiNiuProperties.getAccessKey();
        validNull(accessKey);
        String secretKey = qiNiuProperties.getSecretKey();
        validNull(secretKey);
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\"," +
                "\"fsize\":$(fsize),\"fname\":\"$(fname)\",\"ext\":\"$(ext)\",\"mimeType\":\"$(mimeType)\"," +
                "\"imageInfo\":$(imageInfo)}");
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
            // inputStream = new FileInputStream(file);
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

}
