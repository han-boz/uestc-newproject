package com.healthinnova.portal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * JWT 配置属性
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    /** JWT 签名密钥（Base64 编码） */
    private String secret = "aGVhbHRoLWlubm92YS1wb3J0YWwtc2VjcmV0LWtleS1mb3Itand0LXRva2VuLWdlbmVyYXRpb24=";

    /** Token 过期时间（秒），默认 24 小时 */
    private Long expiration = 86400L;

    /** Token 请求头名称 */
    private String header = "Authorization";

    /** Token 类型前缀 */
    private String tokenPrefix = "Bearer ";

}
