package ru.clevertec.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


import java.math.BigDecimal;

public record CarDto(
        @NotBlank(message = "Name cannot be null or empty.")
        @Size(min = 5, max = 10, message = "Name must be between 5 and 10 characters.")
        @Pattern(regexp = "[А-Яа-я ]+", message = "Name must contain only Russian letters or spaces.")
        @JsonProperty(required = true)
        String name,

        @Size (min = 10, max = 30, message = "Description must be between 10 and 30 characters.")
        @Pattern (regexp = "[А-Яа-я ]*", message = "Description must contain only Russian letters or spaces.")
        @JsonProperty(required = true)
        String description,

        @NotNull(message = "Price cannot be null.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive.")
        @JsonProperty(required = true)
        BigDecimal price) {
}