package com.complaint.system.controller;

import java.security.Principal;

import jakarta.validation.Valid;

import com.complaint.system.dto.ComplaintResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.complaint.system.dto.StatusUpdateRequest;
import com.complaint.system.service.StaffService;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @PutMapping("/update-status/{complaintId}")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long complaintId,
            @Valid @RequestBody StatusUpdateRequest request,
            Principal principal) {

        String staffEmail = principal.getName();

        return ResponseEntity.ok(
                staffService.updateComplaintStatus(
                        complaintId,
                        request,
                        staffEmail
                )
        );
    }
    @GetMapping("/complaints")
    public ResponseEntity<List<ComplaintResponse>> getAssignedComplaints(Principal principal) {
         return ResponseEntity.ok(
        staffService.getAssignedComplaints(principal.getName())
          );
    }
}
