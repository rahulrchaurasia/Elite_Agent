package account.rb.com.elite_agent.core.response;

import java.util.List;

import account.rb.com.elite_agent.core.APIResponse;

/**
 * Created by IN-RB on 08-02-2018.
 */

public class AgentCommonResponse extends APIResponse {


    private List<Object> Data;

    public List<Object> getData() {
        return Data;
    }

    public void setData(List<Object> Data) {
        this.Data = Data;
    }
}
