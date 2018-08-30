package account.rb.com.elite_agent.core.model;

public  class OrderEntity {
        /**
         * id : 22
         * prod_id : 1
         * user_id : 2
         * mobile : 9999999999
         * email : joel@jangam.com
         * name : joel
         */

        private int id;
        private int prod_id;
        private int user_id;
        private String mobile;
        private String email;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProd_id() {
            return prod_id;
        }

        public void setProd_id(int prod_id) {
            this.prod_id = prod_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }