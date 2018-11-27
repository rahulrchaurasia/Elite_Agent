package account.rb.com.elite_agent.core.response;


import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.model.VerifyOTPEntity;

/**
 * Created by IN-RB on 14-11-2018.
 */

public class VerifyUserRegisterResponse extends APIResponse {


    /**
     * Data : {"SavedStatus":1,"OTP":"344324"}
     */

    private VerifyOTPEntity Data;

    public VerifyOTPEntity getData() {
        return Data;
    }

    public void setData(VerifyOTPEntity Data) {
        this.Data = Data;
    }


}
