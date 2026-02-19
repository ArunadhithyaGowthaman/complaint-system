package com.complaint.system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignRequest {

    @NotNull(message = "Staff ID is required")
    private Long staffId;
}
