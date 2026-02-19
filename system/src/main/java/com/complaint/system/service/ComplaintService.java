package com.complaint.system.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.complaint.system.dto.ComplaintRequest;
import com.complaint.system.dto.ComplaintResponse;
import com.complaint.system.enums.ComplaintStatus;
import com.complaint.system.enums.ComplaintStatusTransitionValidator;
import com.complaint.system.model.Complaint;
import com.complaint.system.model.User;
import com.complaint.system.repository.ComplaintRepository;
import com.complaint.system.repository.UserRepository;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    // ✅ Constructor injection (industry standard)
    public ComplaintService(ComplaintRepository complaintRepository,
                            UserRepository userRepository) {
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
    }

    public String submitComplaint(ComplaintRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Complaint complaint = Complaint.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .status(ComplaintStatus.OPEN)
                .user(user)
                .build();

        Complaint saved = complaintRepository.save(complaint);
        return "Complaint submitted successfully. ID: " + saved.getId();
    }
    
    public List<ComplaintResponse> getUserComplaints(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return complaintRepository.findByUser(user)
                .stream()
                .map(c -> ComplaintResponse.builder()
                        .id(c.getId())
                        .title(c.getTitle())
                        .description(c.getDescription())
                        .category(c.getCategory())
                        .status(c.getStatus().name())
                        .userName(c.getUser().getName())
                        .assignedStaffName(
                            c.getAssignedStaff() != null ?
                            c.getAssignedStaff().getName() : null
                        )
                        .createdAt(c.getCreatedAt())
                        .build()
                )
                .toList();
    }

    public String submitFeedback(Long complaintId, String feedbackText, String userEmail) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        if (!complaint.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Access denied: complaint does not belong to you");
        }

        // ✅ Centralized transition validation
        ComplaintStatusTransitionValidator.validate(
            complaint.getStatus(), ComplaintStatus.CLOSED
        );

        complaint.setFeedback(feedbackText);
        complaint.setStatus(ComplaintStatus.CLOSED); // ✅ Now works — field name is fixed
        complaintRepository.save(complaint);
        return "Feedback submitted. Complaint closed.";
    }
}