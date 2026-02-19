package com.complaint.system.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.complaint.system.dto.ComplaintRequest;
import com.complaint.system.dto.ComplaintResponse;
import com.complaint.system.dto.FeedbackRequest;
import com.complaint.system.service.ComplaintService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    // Submit complaint (USER only)
    @PostMapping
    public ResponseEntity<String> submitComplaint(
            @Valid @RequestBody ComplaintRequest request,
            Principal principal) {

        String email = principal.getName();

        return ResponseEntity.status(201).body(
                complaintService.submitComplaint(request, email)
        );
    }

    // Get my complaints (USER only)
    @GetMapping("/my")
    public ResponseEntity<List<ComplaintResponse>> getMyComplaints(Principal principal) {

        String email = principal.getName();

        return ResponseEntity.ok(
                complaintService.getUserComplaints(email)
        );
    }
    
    @PutMapping("/feedback/{complaintId}")
    public ResponseEntity<String> submitFeedback(
            @PathVariable Long complaintId,
            @Valid @RequestBody FeedbackRequest request,
            Principal principal) {

        String userEmail = principal.getName();

        return ResponseEntity.ok(
                complaintService.submitFeedback(
                        complaintId,
                        request.getFeedback(),
                        userEmail
                )
        );
    }
}
