package com.battery.models;


import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Battery {
    private UUID id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Postcode is required")
    private String postcode;

    @Min(value = 0, message = "Capacity must be a non-negative value")
    @Max(value = 85000, message = "Capacity cannot exceed 85000")
    private int capacity;

    private List<String> errors = new CopyOnWriteArrayList<>();
}
