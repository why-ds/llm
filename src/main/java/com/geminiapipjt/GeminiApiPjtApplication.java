package com.geminiapipjt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 메인 어플리케이션 클래스.
 *
 * 1단계에선 DB 없이 Gemini 호출만 먼저 검증헐 거라
 * DataSourceAutoConfiguration을 일단 제외해둔다.
 * 나중에 DB 붙일 때 exclude 부분 지우민 됨.
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class GeminiApiPjtApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeminiApiPjtApplication.class, args);
    }
}