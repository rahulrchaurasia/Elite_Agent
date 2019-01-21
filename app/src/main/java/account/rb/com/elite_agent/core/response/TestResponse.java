package account.rb.com.elite_agent.core.response;

import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.TestAPIResponse;

/**
 * Created by Rajeev Ranjan on 15/01/2019.
 */

public class TestResponse extends APIResponse {
    /**
     * result : {"id":"88","email":"prashantgiri@gmail.com","empcode":"BD00068","type":"0","Username":"Prashant"}
     */

    private TestEntity result;

    public TestEntity getResult() {
        return result;
    }

    public void setResult(TestEntity result) {
        this.result = result;
    }

    public static class TestEntity {
        /**
         * id : 88
         * email : prashantgiri@gmail.com
         * empcode : BD00068
         * type : 0
         * Username : Prashant
         */

        private String id;
        private String email;
        private String empcode;
        private String type;
        private String Username;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEmpcode() {
            return empcode;
        }

        public void setEmpcode(String empcode) {
            this.empcode = empcode;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUsername() {
            return Username;
        }

        public void setUsername(String Username) {
            this.Username = Username;
        }
    }


    /**
     * message : Success
     * result : {"id":"88","email":"prashantgiri@gmail.com","empcode":"BD00068","type":"0","Username":"Prashant"}
     * status : Success
     * statusId : 1
     */


}
