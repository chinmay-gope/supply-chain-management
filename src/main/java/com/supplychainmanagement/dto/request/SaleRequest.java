package com.supplychainmanagement.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record SaleRequest(

        @NotNull(message = "User ID is required")
        Long soldById,

        @NotEmpty(message = "Sale must contain at least one product")
        List<@Valid SaleItemRequest> items

) {}
