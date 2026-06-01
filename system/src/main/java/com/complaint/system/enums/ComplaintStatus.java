package com.complaint.system.enums;
import java.util.Set;

public enum ComplaintStatus {
    OPEN(ASSIGNED),
    ASSIGNED(IN_PROGRESS), 
    IN_PROGRESS(RESOLVED), 
    RESOLVED(CLOSED), 
    CLOSED();

    private final Set<ComplaintStatus> valid;

    ComplaintStatus(ComplaintStatus...nextStates){
        this.valid=Set.of(nextStates);
    }

   public boolean canTransitionto(ComplaintStatus next){
        return valid.contains(next);
    }

    public static void validate(ComplaintStatus current, ComplaintStatus next) {
        if (!current.canTransitionto(next)) {
            throw new IllegalStateException(
                "Invalid transition: " + current + " → " + next
            );
        }
    }
}
