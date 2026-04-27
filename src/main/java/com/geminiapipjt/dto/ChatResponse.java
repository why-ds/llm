package com.geminiapipjt.dto;

/**
 * 채팅 응답 DTO.
 * 단순 문자열만 반환해도 되는디, 나중에 메타 정보(토큰 수, 모델명 등)
 * 추가될 거 대비해서 record로 감싸둠 → 확장성 확보.
 */
public record ChatResponse(String content) {

    /** 정적 팩토리 메서드. 호출부에서 ChatResponse.of("...") 형태로 깔끔허게 생성 */
    public static ChatResponse of(String content) {
        return new ChatResponse(content);
    }
}