package com.geminiapipjt.dto;

/**
 * 채팅 요청 DTO.
 * Java 17의 record로 불변(immutable) DTO 깔끔허게 정의.
 */
public record ChatRequest(String prompt) {}