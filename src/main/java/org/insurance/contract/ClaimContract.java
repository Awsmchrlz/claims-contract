package org.insurance.contract;

import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.shim.ChaincodeException;

import java.util.Objects;

@Contract(name = "ClaimContract", info = @Info(title = "Insurance Claim Contract", version = "1.0"))
@Default
public class ClaimContract implements ContractInterface {

    private final Genson genson = new Genson();

    // === Utility Key Prefixing ===
    private String getClaimKey(String claimId) {
        return "CLAIM_" + claimId;
    }

    private String getPolicyKey(String policyId) {
        return "POLICY_" + policyId;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void init(Context ctx) {
        System.out.println("Chaincode initialized.");
    }


    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean policyExists(Context ctx, String policyId) {
        String policyJSON = ctx.getStub().getStringState(getPolicyKey(policyId));
        return policyJSON != null && !policyJSON.isEmpty();
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Claim createClaim(Context ctx, String claimId, String policyId, String nrcNumber) {
        if (claimExists(ctx, claimId)) {
            throw new ChaincodeException("Claim with ID " + claimId + " already exists.");
        }

        if (!policyExists(ctx, policyId)) {
            throw new ChaincodeException("Associated policy does not exist.");
        }

        Claim claim = new Claim(claimId, policyId, nrcNumber);
        ctx.getStub().putStringState(getClaimKey(claimId), genson.serialize(claim));
        return claim;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public Claim readClaim(Context ctx, String claimId) {
        String claimJSON = ctx.getStub().getStringState(getClaimKey(claimId));
        if (claimJSON == null || claimJSON.isEmpty()) {
            throw new ChaincodeException("Claim not found.");
        }
        return genson.deserialize(claimJSON, Claim.class);
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean claimExists(Context ctx, String claimId) {
        String claimJSON = ctx.getStub().getStringState(getClaimKey(claimId));
        return claimJSON != null && !claimJSON.isEmpty();
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Claim uploadDamageEvidence(Context ctx, String claimId, String damageEvidenceUrl) {
        Claim claim = readClaim(ctx, claimId);
        claim.setDamageEvidence(damageEvidenceUrl);
        claim.setStatus(Enums.ClaimStatus.EVIDENCE_UPLOADED);
        ctx.getStub().putStringState(getClaimKey(claimId), genson.serialize(claim));
        return claim;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Claim approveClaim(Context ctx, String claimId) {
        Claim claim = readClaim(ctx, claimId);
        claim.setStatus(Enums.ClaimStatus.APPROVED);
        ctx.getStub().putStringState(getClaimKey(claimId), genson.serialize(claim));
        return claim;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Claim estimateCost(Context ctx, String claimId, String costEstimation) {
        Claim claim = readClaim(ctx, claimId);
        claim.setCostEstimation(costEstimation);
        claim.setStatus(Enums.ClaimStatus.COST_ESTIMATED);
        ctx.getStub().putStringState(getClaimKey(claimId), genson.serialize(claim));
        return claim;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Claim assignGarage(Context ctx, String claimId, String garageId) {
        Claim claim = readClaim(ctx, claimId);
        claim.setGarageId(garageId);
        claim.setStatus(Enums.ClaimStatus.GARAGE_ASSIGNED);
        ctx.getStub().putStringState(getClaimKey(claimId), genson.serialize(claim));
        return claim;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Claim markRepaired(Context ctx, String claimId) {
        Claim claim = readClaim(ctx, claimId);
        claim.setRepaired(true);
        claim.setStatus(Enums.ClaimStatus.REPAIRED);
        ctx.getStub().putStringState(getClaimKey(claimId), genson.serialize(claim));
        return claim;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Claim releasePayment(Context ctx, String claimId) {
        Claim claim = readClaim(ctx, claimId);

        if (!claim.isRepaired()) {
            throw new ChaincodeException("Cannot release payment before repair is confirmed.");
        }
        claim.setPaymentReleased(true);
        claim.setStatus(Enums.ClaimStatus.PAYMENT_RELEASED);
        ctx.getStub().putStringState(getClaimKey(claimId), genson.serialize(claim));
        return claim;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Claim flagDiscrepancy(Context ctx, String claimId, String reason) {
        Claim claim = readClaim(ctx, claimId);
        claim.setDiscrepancyReason(reason);
        claim.setStatus(Enums.ClaimStatus.DISCREPANCY_FLAGGED);
        ctx.getStub().putStringState(getClaimKey(claimId), genson.serialize(claim));
        return claim;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Claim rejectClaim(Context ctx, String claimId, String reason) {
        Claim claim = readClaim(ctx, claimId);
        claim.setDiscrepancyReason(reason);
        claim.setStatus(Enums.ClaimStatus.REJECTED);
        ctx.getStub().putStringState(getClaimKey(claimId), genson.serialize(claim));
        return claim;
    }


}
