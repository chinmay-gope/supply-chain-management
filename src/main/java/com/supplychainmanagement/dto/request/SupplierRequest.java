package com.supplychainmanagement.dto.request;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierRequest {

    @NotBlank
    @Size(max = 150)
    private String name;

    @Email
    @Size(max = 150)
    private String email;

    @Size(max = 20)
    private String phone;

    @Size(max = 255)
    private String address;
}