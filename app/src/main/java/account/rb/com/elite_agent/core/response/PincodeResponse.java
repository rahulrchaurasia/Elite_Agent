package account.rb.com.elite_agent.core.response;

import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.model.PincodeEntity;

import java.util.List;

/**
 * Created by Rajeev Ranjan on 03/02/2018.
 */

public class PincodeResponse extends APIResponse {

    private List<PincodeEntity> Data;

    public List<PincodeEntity> getData() {
        return Data;
    }

    public void setData(List<PincodeEntity> Data) {
        this.Data = Data;
    }
}
