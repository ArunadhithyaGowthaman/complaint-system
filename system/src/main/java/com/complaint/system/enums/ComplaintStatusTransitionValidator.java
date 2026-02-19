package com.complaint.system.enums;

import java.util.Map;
import java.util.Set;

public class ComplaintStatusTransitionValidator {

    // Defines all legal transitions
    private static final Map<ComplaintStatus, Set<ComplaintStatus>> ALLOWED =
        Map.of(
            ComplaintStatus.OPEN,        Set.of(ComplaintStatus.ASSIGNED),
            ComplaintStatus.ASSIGNED,    Set.of(ComplaintStatus.IN_PROGRESS),
            ComplaintStatus.IN_PROGRESS, Set.of(ComplaintStatus.RESOLVED),
            ComplaintStatus.RESOLVED,    Set.of(ComplaintStatus.CLOSED),
            ComplaintStatus.CLOSED,      Set.of()  // terminal state
        );

    public static void validate(ComplaintStatus current, ComplaintStatus next) {
        Set<ComplaintStatus> allowed = ALLOWED.getOrDefault(current, Set.of());
        if (!allowed.contains(next)) {
            throw new IllegalStateException(
                "Invalid transition: " + current + " → " + next
            );
        }
    }
}