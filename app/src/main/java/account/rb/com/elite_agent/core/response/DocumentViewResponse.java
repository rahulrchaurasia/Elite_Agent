package account.rb.com.elite_agent.core.response;



import java.util.List;

import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.model.DocumentViewEntity;

/**
 * Created by IN-RB on 14-08-2018.
 */

public class DocumentViewResponse extends APIResponse {


    private List<DocumentViewEntity> Data;

    public List<DocumentViewEntity> getData() {
        return Data;
    }

    public void setData(List<DocumentViewEntity> Data) {
        this.Data = Data;
    }


}
