package account.rb.com.elite_agent.core.response;

import java.util.List;

import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.model.IfscEntity;

/**
 * Created by IN-RB on 24-11-2018.
 */

public class IfscCodeResponse extends APIResponse {

    private List<IfscEntity> MasterData;

    public List<IfscEntity> getMasterData() {
        return MasterData;
    }

    public void setMasterData(List<IfscEntity> MasterData) {
        this.MasterData = MasterData;
    }

}
