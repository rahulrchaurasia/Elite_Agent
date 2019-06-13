package account.rb.com.elite_agent.core.response;

import java.util.List;

import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.model.DocViewEntity;

/**
 * Created by Rajeev Ranjan on 29/05/2019.
 */
public class ViewDocCommentResponse extends APIResponse {


    private List<DocViewEntity> Data;

    public List<DocViewEntity> getData() {
        return Data;
    }

    public void setData(List<DocViewEntity> Data) {
        this.Data = Data;
    }


}
