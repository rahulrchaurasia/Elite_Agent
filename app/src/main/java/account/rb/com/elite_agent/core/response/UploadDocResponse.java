package account.rb.com.elite_agent.core.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.model.DocUploadEntity;

/**
 * Created by Rajeev Ranjan on 29/05/2019.
 */
public class UploadDocResponse extends APIResponse {


    private List<DocUploadEntity> Data;

    public List<DocUploadEntity> getData() {
        return Data;
    }

    public void setData(List<DocUploadEntity> Data) {
        this.Data = Data;
    }


}
