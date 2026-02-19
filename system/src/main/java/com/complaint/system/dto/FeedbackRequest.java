package com.complaint.system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FeedbackRequest {

    @NotBlank(message = "Feedback is required")
    private String feedback;
}
