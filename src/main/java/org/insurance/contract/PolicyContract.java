package org.insurance.contract;

import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.shim.ChaincodeException;
@Contract(name = "PolicyContract")
@Default
public class PolicyContract {

    private final Genson genson = new Genson();

    private String getPolicyKey(String policyId) {
        return "POLICY_" + policyId;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void init(final Context ctx) {
        System.out.println("PolicyContract initialized");
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Policy createPolicy(Context ctx, String policyId, String userId, String vehicleInfo, String coverageDetails) {
        String key = getPolicyKey(policyId);

        if (ctx.getStub().getStringState(key) != null && !ctx.getStub().getStringState(key).isEmpty()) {
            throw new ChaincodeException("Policy already exists");
        }

        Policy policy = new Policy(policyId, userId, vehicleInfo, coverageDetails);
        ctx.getStub().putStringState(key, genson.serialize(policy));
        return policy;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public Policy readPolicy(Context ctx, String policyId) {
        String key = getPolicyKey(policyId);
        String data = ctx.getStub().getStringState(key);
        if (data == null || data.isEmpty()) {
            throw new ChaincodeException("Policy not found");
        }
        return genson.deserialize(data, Policy.class);
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean policyExists(Context ctx, String policyId) {
        return ctx.getStub().getStringState(getPolicyKey(policyId)) != null;
    }

}
