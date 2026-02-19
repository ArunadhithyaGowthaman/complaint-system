package com.complaint.system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ComplaintRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private String category;
}
