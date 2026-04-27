package com.geminiapipjt.service.llm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Spring AI ChatClient를 활용한 Gemini 서비스 구현체.
 *
 * ChatClient는 Spring AI가 제공허는 통합 API로,
 * 모델별 차이(Gemini, OpenAI, Claude 등)를 추상화해준다.
 * → 우리는 비즈니스 로직에만 집중허민 됨.
 */
@Slf4j
@Service
public class GeminiServiceImpl implements LlmService {

    private final ChatClient chatClient;

    /**
     * 생성자 주입 방식.
     *
     * ChatClient.Builder는 Spring AI starter가 자동으로 빈 등록해주는 거여.
     * 여기서 .defaultSystem()으로 모든 호출에 적용될 시스템 프롬프트 지정 가능.
     *
     * ⚠️ 시스템 프롬프트 운영 시 고려사항:
     *   - 이런 식으로 하드코딩 말고 application.yml이나 DB에서 관리허는 게 좋음
     *   - 프롬프트 변경할 때마다 재배포 필요 없게 → 추후 개선 포인트
     */
    public GeminiServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("""
                        너는 친절하고 정확한 한국어 AI 어시스턴트여.
                        모르는 건 모른다고 솔직하게 답해사 헌다.
                        답변은 명확하고 간결하게.
                        """)
                .build();
    }

    /**
     * 일반 응답 - 동기 호출.
     * .call()은 응답이 다 올 때꼬지 블로킹된다.
     */
    @Override
    public String generate(String prompt) {
        log.info("Gemini 일반 호출 - prompt: {}", prompt);
        try {
            String result = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();   // 응답 텍스트만 추출
            log.info("Gemini 응답 길이: {} chars", result != null ? result.length() : 0);
            return result;
        } catch (Exception e) {
            log.error("Gemini 호출 실패: {}", e.getMessage(), e);
            throw new RuntimeException("LLM 호출 실패: " + e.getMessage(), e);
        }
    }

    /**
     * 스트리밍 응답 - Flux로 토큰 단위 전송.
     * Spring AI가 Reactor Flux를 네이티브 지원허기 때문에
     * .stream().content() 한 줄로 끝.
     */
    @Override
    public Flux<String> generateStream(String prompt) {
        log.info("Gemini 스트리밍 호출 - prompt: {}", prompt);
        return chatClient.prompt()
                .user(prompt)
                .stream()
                .content()
                .doOnError(e -> log.error("스트리밍 실패: {}", e.getMessage(), e));
    }
}