package com.complaint.system.service;

import org.springframework.stereotype.Service;
import com.complaint.system.dto.StatusUpdateRequest;
import com.complaint.system.enums.ComplaintStatus;
import com.complaint.system.enums.ComplaintStatusTransitionValidator;
import com.complaint.system.model.Complaint;
import com.complaint.system.model.User;
import com.complaint.system.repository.ComplaintRepository;
import com.complaint.system.repository.UserRepository;

@Service
public class StaffService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    public StaffService(ComplaintRepository complaintRepository,
                        UserRepository userRepository) {
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
    }

    public String updateComplaintStatus(Long complaintId,
                                        StatusUpdateRequest request,
                                        String staffEmail) {

        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        User staff = userRepository.findByEmail(staffEmail)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        if (complaint.getAssignedStaff() == null ||
            !complaint.getAssignedStaff().getId().equals(staff.getId())) {
            throw new RuntimeException("You are not assigned to this complaint");
        }

        ComplaintStatus newStatus = ComplaintStatus.valueOf(request.getStatus().toUpperCase());

        // ✅ Centralized validation replaces scattered if-else
        ComplaintStatusTransitionValidator.validate(complaint.getStatus(), newStatus);

        complaint.setStatus(newStatus);
        complaintRepository.save(complaint);
        return "Complaint status updated to " + newStatus;
    }
}