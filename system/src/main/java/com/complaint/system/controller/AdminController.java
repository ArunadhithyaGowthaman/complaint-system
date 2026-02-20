package com.complaint.system.controller;

import jakarta.validation.Valid;

import com.complaint.system.dto.ComplaintResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.complaint.system.dto.AssignRequest;
import com.complaint.system.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Assign complaint to staff
    @PutMapping("/assign/{complaintId}")
    public ResponseEntity<String> assignComplaint(
            @PathVariable Long complaintId,
            @Valid @RequestBody AssignRequest request) {

        return ResponseEntity.ok(
                adminService.assignComplaint(complaintId, request)
        );
    }

    @GetMapping("/complaints")
     public ResponseEntity<List<ComplaintResponse>> getAllComplaints() {
           return ResponseEntity.ok(adminService.getAllComplaints());
   }
}
