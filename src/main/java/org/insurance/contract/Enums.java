package org.insurance.contract;


public class Enums {

    public enum ClaimStatus {
        CREATED,
        EVIDENCE_UPLOADED,
        APPROVED,
        COST_ESTIMATED,
        GARAGE_ASSIGNED,
        REPAIRED,
        PAYMENT_RELEASED,
        DISCREPANCY_FLAGGED,
        REJECTED
    }

    public enum PolicyStatus {
        ACTIVE,
        EXPIRED,
        CANCELLED
    }
}
