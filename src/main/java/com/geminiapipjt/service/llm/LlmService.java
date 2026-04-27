package com.geminiapipjt.service.llm;

import reactor.core.publisher.Flux;

/**
 * LLM 호출 추상화 인터페이스.
 *
 * 왜 인터페이스로 분리허는가?
 *   1. 나중에 OpenAI, Claude 등 다른 LLM 추가헐 때 구현체만 추가허민 끝
 *   2. 컨트롤러는 인터페이스에만 의존 → 구현체 교체 자유로움 (DIP 원칙)
 *   3. 테스트 시 Mock 구현체 만들기 쉬움
 *
 * 확장 시나리오 예:
 *   - OpenAiServiceImpl implements LlmService
 *   - ClaudeServiceImpl implements LlmService
 *   - @Primary 어노테이션 또는 @Qualifier로 선택 주입
 */
public interface LlmService {

    /**
     * 일반 응답: 한 번에 전체 답변을 받는다.
     * 응답 다 올 때꼬지 블로킹됨.
     */
    String generate(String prompt);

    /**
     * 스트리밍 응답: 토큰 단위로 흘러나오는 Flux.
     * SSE(Server-Sent Events)로 프론트에 흘려보낼 때 사용.
     */
    Flux<String> generateStream(String prompt);
}