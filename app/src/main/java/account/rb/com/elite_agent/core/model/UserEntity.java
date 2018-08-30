package account.rb.com.elite_agent.core.model;

import io.realm.RealmObject;

public  class UserEntity extends RealmObject {
            /**
             * user_id : 7
             * email : govind.dharne4@rupeeboss.com
             * mobile : 9876543210
             * name : Govind V Dharn
             * card_no : 000000000007
             * policy_no : 90890
             * pincode : 400070
             * area : A H Wadia Marg
             * address : MAHARASHTRA
             * cityname : Mumbai
             * state_name : MAHARASHTRA
             * city_id : 1276
             * state_id : 21
             * user_type_id : 2
             * user_type : Agent
             */

            private int user_id;
            private String email;
            private String mobile;
            private String name;
            private String card_no;
            private String policy_no;
            private String pincode;
            private String area;
            private String address;
            private String cityname;
            private String state_name;
            private String city_id;
            private String state_id;
            private int user_type_id;
            private String user_type;

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCard_no() {
                return card_no;
            }

            public void setCard_no(String card_no) {
                this.card_no = card_no;
            }

            public String getPolicy_no() {
                return policy_no;
            }

            public void setPolicy_no(String policy_no) {
                this.policy_no = policy_no;
            }

            public String getPincode() {
                return pincode;
            }

            public void setPincode(String pincode) {
                this.pincode = pincode;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getCityname() {
                return cityname;
            }

            public void setCityname(String cityname) {
                this.cityname = cityname;
            }

            public String getState_name() {
                return state_name;
            }

            public void setState_name(String state_name) {
                this.state_name = state_name;
            }

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getState_id() {
                return state_id;
            }

            public void setState_id(String state_id) {
                this.state_id = state_id;
            }

            public int getUser_type_id() {
                return user_type_id;
            }

            public void setUser_type_id(int user_type_id) {
                this.user_type_id = user_type_id;
            }

            public String getUser_type() {
                return user_type;
            }

            public void setUser_type(String user_type) {
                this.user_type = user_type;
            }
        }