package account.rb.com.elite_agent.core.requestmodel;

/**
 * Created by Rajeev Ranjan on 03/02/2018.
 */

public class UpdateUserRequestEntity {
    /**
     * name : Govind V Dharne
     * email : govind.dharne4@rupeeboss.com
     * mobile : 9876543210
     * rto : 1
     * expirydate : 2018-10-10
     * address : PBN
     * area : Kurla
     * pincode : 400070
     * city : 12
     * state : 1
     * otp : 498121
     */

    private String name;
    private String email;
    private String mobile;
    private String rto;
    private String expirydate;
    private String address;
    private String area;
    private String pincode;
    private String city;
    private String state;
    private int otp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRto() {
        return rto;
    }

    public void setRto(String rto) {
        this.rto = rto;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }
}
