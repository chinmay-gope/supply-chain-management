package com.supplychainmanagement.controller;


import com.supplychainmanagement.dto.request.ChatRequest;
import com.supplychainmanagement.dto.response.ChatResponse;
import com.supplychainmanagement.service.AIChatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai/chat")
public class AIChatController {

    private final AIChatService aiChatService;

    public AIChatController(AIChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chat(
            @Valid @RequestBody ChatRequest request) {

        return ResponseEntity.ok(aiChatService.chat(request));
    }
}
