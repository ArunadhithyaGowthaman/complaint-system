package com.complaint.system.enums;

import java.util.Map;
import java.util.Set;

public class ComplaintStatusTransitionValidator {
    public static void validate(ComplaintStatus current, ComplaintStatus next) {
        if(!current.canTranstionto(next)){
            throw new IllegalStateException("Invalid transition"+current+ "->" +next);
        }
    }
}
