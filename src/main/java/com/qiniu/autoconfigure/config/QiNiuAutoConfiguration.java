package com.qiniu.autoconfigure.config;

import com.qiniu.autoconfigure.properties.QiNiuProperties;
import com.qiniu.autoconfigure.service.QiNiuService;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Config qiniu.
 *
 * @author xzmeasy
 */
@Configuration
@ConditionalOnClass(QiNiuService.class)
@EnableConfigurationProperties(QiNiuProperties.class)
public class QiNiuAutoConfiguration {

    private final QiNiuProperties qiNiuProperties;

    public QiNiuAutoConfiguration(QiNiuProperties qiNiuProperties) {
        this.qiNiuProperties = qiNiuProperties;
    }

    /**
     * Config {@link UploadManager} object used to upload resource.
     *
     * @return {@link UploadManager} bean.
     */
    @Bean
    @ConditionalOnMissingBean(UploadManager.class)
    public UploadManager uploadManager() {
        Region region = Region.autoRegion();
        com.qiniu.storage.Configuration configuration = new com.qiniu.storage.Configuration(region);
        return new UploadManager(configuration);
    }

    @Bean
    @ConditionalOnMissingBean(QiNiuService.class)
    public QiNiuService qiNiuService() {
        return new QiNiuService(uploadManager(), qiNiuProperties);
    }

}
