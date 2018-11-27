package account.rb.com.elite_agent.core.response;



import java.util.List;

import account.rb.com.elite_agent.core.APIResponse;

/**
 * Created by IN-RB on 03-11-2018.
 */

public class UserRegistrationResponse extends APIResponse {


    private List<RegistrationResponseEntity> Data;

    public List<RegistrationResponseEntity> getData() {
        return Data;
    }

    public void setData(List<RegistrationResponseEntity> Data) {
        this.Data = Data;
    }

    public static class RegistrationResponseEntity {
        /**
         * SavedStatus : 0
         * Message : Record Saved Successfully
         */

        private int SavedStatus;
        private String Message;

        public int getSavedStatus() {
            return SavedStatus;
        }

        public void setSavedStatus(int SavedStatus) {
            this.SavedStatus = SavedStatus;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }
    }
}
