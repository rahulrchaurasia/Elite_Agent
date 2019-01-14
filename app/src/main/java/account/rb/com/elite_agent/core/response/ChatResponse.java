package account.rb.com.elite_agent.core.response;

import java.util.List;

import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.model.ChatEntity;

/**
 * Created by Rajeev Ranjan on 11/01/2019.
 */

public class ChatResponse extends APIResponse {


    private List<ChatEntity> Data;

    public List<ChatEntity> getData() {
        return Data;
    }

    public void setData(List<ChatEntity> Data) {
        this.Data = Data;
    }


}
