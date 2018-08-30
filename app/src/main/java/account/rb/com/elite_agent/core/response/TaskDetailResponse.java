package account.rb.com.elite_agent.core.response;

import java.util.List;

import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.model.TaskEntity;

/**
 * Created by IN-RB on 06-02-2018.
 */

public class TaskDetailResponse extends APIResponse {
    private List<TaskEntity> Data;

    public List<TaskEntity> getData() {
        return Data;
    }

    public void setData(List<TaskEntity> Data) {
        this.Data = Data;
    }




}
