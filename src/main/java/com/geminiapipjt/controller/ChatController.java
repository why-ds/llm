package com.geminiapipjt.controller;

import com.geminiapipjt.dto.ChatRequest;
import com.geminiapipjt.dto.ChatResponse;
import com.geminiapipjt.service.llm.LlmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * 채팅 REST 컨트롤러.
 *
 * 엔드포인트:
 *   - POST /api/chat        : 일반 응답 (한번에 받음)
 *   - POST /api/chat/stream : 스트리밍 응답 (SSE)
 *
 * ⚠️ LlmService 인터페이스에만 의존! GeminiServiceImpl 직접 의존 X
 *    → 나중에 다른 LLM 추가/교체 시 컨트롤러 수정 불필요
 */
@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor    // Lombok: final 필드 생성자 자동 생성
public class ChatController {

    private final LlmService llmService;

    /**
     * 일반 채팅 응답.
     * 클라이언트가 답변 다 받을 때꼬지 기다림.
     */
    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request) {
        log.info("일반 채팅 요청 수신");
        String content = llmService.generate(request.prompt());
        return ChatResponse.of(content);
    }

    /**
     * 스트리밍 채팅 응답 (SSE).
     *
     * produces = TEXT_EVENT_STREAM_VALUE 지정 필수.
     * → 브라우저/클라이언트가 SSE로 인식헌다.
     *
     * 응답 형태:
     *   data: 안녕\n\n
     *   data: 하세요\n\n
     *   data: ~ \n\n
     */
    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(@RequestBody ChatRequest request) {
        log.info("스트리밍 채팅 요청 수신");
        return llmService.generateStream(request.prompt());
    }
}