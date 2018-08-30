package account.rb.com.elite_agent.core.requestmodel;

/**
 * Created by Rajeev Ranjan on 02/02/2018.
 */

public class AddUserRequestEntity {

    /**
     * elite_card_no : 123456
     * policy_no : 90890
     * mobile_no : 9876543212
     * email_address : govind.dharne7@rupeeboss.com
     * password : Admin@123
     */

    private String elite_card_no;
    private String policy_no;
    private String mobile_no;
    private String email_address;
    private String password;

    public String getElite_card_no() {
        return elite_card_no;
    }

    public void setElite_card_no(String elite_card_no) {
        this.elite_card_no = elite_card_no;
    }

    public String getPolicy_no() {
        return policy_no;
    }

    public void setPolicy_no(String policy_no) {
        this.policy_no = policy_no;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
