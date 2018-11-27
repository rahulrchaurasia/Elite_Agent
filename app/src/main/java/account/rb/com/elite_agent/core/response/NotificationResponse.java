package account.rb.com.elite_agent.core.response;


import java.util.List;

import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.model.NotificationEntity;

/**
 * Created by IN-RB on 20-11-2018.
 */

public class NotificationResponse extends APIResponse {


    private List<NotificationEntity> Data;

    public List<NotificationEntity> getData() {
        return Data;
    }

    public void setData(List<NotificationEntity> Data) {
        this.Data = Data;
    }


}
