package org.insurance.contract;

public  class Policy {
    private String policyId;
    private String userId;
    private String coverageDetails;
    private String validUntil;
    private Enums.PolicyStatus policyStatus;
    
    public Policy(String policyId, String userId, String coverageDetails, String validUntil) {
        this.policyId = policyId;
        this.userId = userId;
        this.coverageDetails = coverageDetails;
        this.validUntil = validUntil;
    }

    public String getPolicyId() { return policyId; }
    public String getUserId() { return userId; }
    public String getCoverageDetails() { return coverageDetails; }
    public String getValidUntil() { return validUntil; }

    public void setPolicyId(String policyId) { this.policyId = policyId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setCoverageDetails(String coverageDetails) { this.coverageDetails = coverageDetails; }
    public void setValidUntil(String validUntil) { this.validUntil = validUntil; }
    public void setPolicyStatus(String policyStatus) { this.policyStatus = Policy.this.policyStatus; }
}
