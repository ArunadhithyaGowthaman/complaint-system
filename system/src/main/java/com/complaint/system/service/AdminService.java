package com.complaint.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint.system.dto.AssignRequest;
import com.complaint.system.enums.ComplaintStatus;
import com.complaint.system.enums.Role;
import com.complaint.system.model.Complaint;
import com.complaint.system.model.User;
import com.complaint.system.repository.ComplaintRepository;
import com.complaint.system.repository.UserRepository;

@Service
public class AdminService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    // Assign complaint to staff
    public String assignComplaint(Long complaintId, AssignRequest request) {

        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        User staff = userRepository.findById(request.getStaffId())
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        if (staff.getRole() != Role.STAFF) {
            throw new RuntimeException("User is not STAFF");
        }

        complaint.setAssignedStaff(staff);
        complaint.setStatus(ComplaintStatus.ASSIGNED);

        complaintRepository.save(complaint);

        return "Complaint assigned successfully";
    }

    public List<ComplaintResponse> getAllComplaints() {
    return complaintRepository.findAll()
        .stream()
        .map(c -> ComplaintResponse.builder()
            .id(c.getId())
            .title(c.getTitle())
            .description(c.getDescription())
            .category(c.getCategory())
            .status(c.getStatus().name())
            .userName(c.getUser().getName())
            .assignedStaffName(c.getAssignedStaff() != null ? c.getAssignedStaff().getName() : null)
            .createdAt(c.getCreatedAt())
            .build()
        ).toList();
   }
}
