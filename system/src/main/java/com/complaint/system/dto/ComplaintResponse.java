package com.complaint.system.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComplaintResponse {

    private Long id;
    private String title;
    private String description;
    private String category;
    private String status;
    private String userName;
    private String assignedStaffName;
    private LocalDateTime createdAt;
}
