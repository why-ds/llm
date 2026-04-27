package com.geminiapipjt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS(Cross-Origin Resource Sharing) 설정.
 *
 * 브라우저는 보안상 다른 출처(origin)로 요청 보내는 거 기본 차단헌다.
 * 프론트(5173)에서 백엔드(8080)로 호출허려민 백엔드가 명시적으로 허용해줘사 함.
 *
 * ⚠️ 운영 환경에선 실제 도메인만 허용허도록 좁혀사 헌다.
 *    개발 단계라 application.yml에서 주입받아 쓰는 구조로 잡음.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")             // /api/ 하위 경로만 CORS 허용
                .allowedOrigins(allowedOrigins)     // 허용할 출처 (yml에서 주입)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)             // 쿠키 등 인증정보 허용
                .maxAge(3600);                      // preflight 캐시 시간(초)
    }
}