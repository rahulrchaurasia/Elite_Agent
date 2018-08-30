package account.rb.com.elite_agent.core.response;

import java.util.List;

import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.model.OrderSummaryEntity;

/**
 * Created by IN-RB on 07-02-2018.
 */

public class OrderSummaryResponse extends APIResponse {
    private List<OrderSummaryEntity> Data;

    public List<OrderSummaryEntity> getData() {
        return Data;
    }

    public void setData(List<OrderSummaryEntity> Data) {
        this.Data = Data;
    }






}
