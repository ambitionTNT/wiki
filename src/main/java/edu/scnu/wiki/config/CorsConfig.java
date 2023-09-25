package edu.scnu.wiki.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author long
 * @version 1.0
 * @ClassName CorsConfig
 * @description: TODO
 * @date 2023/9/25 16:12
 */

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 解决前后端跨域问题
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//对于所有的请求地址
                .allowedOriginPatterns("*")
                .allowedHeaders(CorsConfiguration.ALL)//对所有的请求头
                .allowedMethods(CorsConfiguration.ALL)//对所有的Method 如 GET,POST
                .allowCredentials(true)//允许带上凭证 如token，session
                .maxAge(3600);
    }
}
