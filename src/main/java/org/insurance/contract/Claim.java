package org.insurance.contract;

import org.insurance.contract.Enums;

public class Claim {
    private String claimId;
    private String policyId;
    private String userId;
    private String damageEvidence;
    private String costEstimation;
    private String garageId;
    private String discrepancyReason;
    private Enums.ClaimStatus status;
    private boolean paymentReleased;
    private boolean repaired;

    public Claim() {}

    public Claim(String claimId, String policyId, String userId) {
        this.claimId = claimId;
        this.policyId = policyId;
        this.userId = userId;
        this.status = Enums.ClaimStatus.CREATED;
        this.paymentReleased = false;
        this.repaired = false;
    }

    public String getClaimId() { return claimId; }
    public String getPolicyId() { return policyId; }
    public String getUserId() { return userId; }
    public String getDamageEvidence() { return damageEvidence; }
    public String getCostEstimation() { return costEstimation; }
    public String getGarageId() { return garageId; }
    public String getDiscrepancyReason() { return discrepancyReason; }
    public Enums.ClaimStatus getStatus() { return status; }
    public boolean isPaymentReleased() { return paymentReleased; }
    public boolean isRepaired() { return repaired; }

    public void setDamageEvidence(String damageEvidence) { this.damageEvidence = damageEvidence; }
    public void setCostEstimation(String costEstimation) { this.costEstimation = costEstimation; }
    public void setGarageId(String garageId) { this.garageId = garageId; }
    public void setDiscrepancyReason(String discrepancyReason) { this.discrepancyReason = discrepancyReason; }
    public void setStatus(Enums.ClaimStatus status) { this.status = status; }
    public void setPaymentReleased(boolean paymentReleased) { this.paymentReleased = paymentReleased; }
    public void setRepaired(boolean repaired) { this.repaired = repaired; }

    public void assignGarage(String garageId) {
        this.garageId = garageId;
        this.status = Enums.ClaimStatus.GARAGE_ASSIGNED;
    }

    public void markRepaired() {
        this.repaired = true;
        this.status = Enums.ClaimStatus.REPAIRED;
    }
}
