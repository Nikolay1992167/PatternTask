package ru.clevertec.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    /**
     * ID (generated by the database)
     */
    private UUID id;

    /**
     * Name (cannot be null or empty, contains 5-10 characters (Russian or spaces))
     */
    private String name;

    /**
     * Description (can be null or 10-30 characters (Russian and spaces))
     */
    private String description;

    /**
     * Cannot be null and must be positive
     */
    private BigDecimal price;

    /**
     * Creation time, cannot be null (set before saving and not updated)
     */
    private LocalDateTime created;
}