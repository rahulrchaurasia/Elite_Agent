package account.rb.com.elite_agent.core.response;

import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.model.OrderEntity;

import java.util.List;

/**
 * Created by IN-RB on 03-02-2018.
 */

public class OrderResponse extends APIResponse {


    private List<OrderEntity> Data;

    public List<OrderEntity> getData() {
        return Data;
    }

    public void setData(List<OrderEntity> Data) {
        this.Data = Data;
    }


}
