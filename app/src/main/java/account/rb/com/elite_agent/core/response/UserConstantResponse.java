package account.rb.com.elite_agent.core.response;

import java.util.List;

import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.model.UserConstantEntity;

/**
 * Created by Rajeev Ranjan on 11/01/2019.
 */

public class UserConstantResponse extends APIResponse {


    private List<UserConstantEntity> Data;

    public List<UserConstantEntity> getData() {
        return Data;
    }

    public void setData(List<UserConstantEntity> Data) {
        this.Data = Data;
    }


}
