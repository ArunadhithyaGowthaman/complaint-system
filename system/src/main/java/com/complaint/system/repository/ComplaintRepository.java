package com.complaint.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.complaint.system.model.Complaint;
import com.complaint.system.model.User;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findByUser(User user);

    List<Complaint> findByAssignedStaff(User staff);
}
