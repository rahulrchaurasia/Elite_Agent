package account.rb.com.elite_agent.core.model;


import java.util.List;

import io.realm.RealmObject;

public class LoginEntity   {

    /**
     * user_id : 7
     * email : govind.dharne4@rupeeboss.com
     * mobile : 9876543210
     * name : Govind V Dharne
     * card_no : 123456
     * policy_no : 90890
     * pincode : 400070
     * area : Kurla
     * address : PBN
     * cityname : MEDAK
     * state_name : ANDAMAN-NICOBAR
     * city_id : 12
     * state_id : 1
     */
    private List<UserEntity> userdetails;
    private List<OrderStatusEntity> orderstatuslist;

    public List<UserEntity> getUserdetails() {
        return userdetails;
    }

    public void setUserdetails(List<UserEntity> userdetails) {
        this.userdetails = userdetails;
    }

    public List<OrderStatusEntity> getOrderstatuslist() {
        return orderstatuslist;
    }

    public void setOrderstatuslist(List<OrderStatusEntity> orderstatuslist) {
        this.orderstatuslist = orderstatuslist;
    }
}