package com.complaint.system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusUpdateRequest {

    @NotNull(message = "Status is required")
    private String status;
}
