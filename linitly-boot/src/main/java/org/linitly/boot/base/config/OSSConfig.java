package org.linitly.boot.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: linxiunan
 * @date: 2020/12/10 11:56
 * @descrption:
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.oss")
public class OSSConfig {
    private String endpoint; // 连接区域地址
    private String accessKeyId; // 连接keyId
    private String accessKeySecret; // 连接秘钥
    private String bucketName; // 需要存储的bucketName
    private String storePath; // 图片保存路径
}