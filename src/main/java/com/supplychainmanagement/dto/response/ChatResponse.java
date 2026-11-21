package com.supplychainmanagement.dto.response;

import java.time.LocalDateTime;

public record ChatResponse(

        String question,
        String answer,
        LocalDateTime timestamp

) {}
