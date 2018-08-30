package account.rb.com.elite_agent.core.model;


public class PincodeEntity  {

    /**
     * pincode : 431402
     * postname : Marathwada A Uni Parbhani
     * districtname : Parbhani
     * cityname : Parbhani
     * state_name : MAHARASHTRA
     * city_id : 1292
     * state_id : 21
     */

    private String pincode;
    private String postname;
    private String districtname;
    private String cityname;
    private String state_name;
    private int city_id;
    private int state_id;

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPostname() {
        return postname;
    }

    public void setPostname(String postname) {
        this.postname = postname;
    }

    public String getDistrictname() {
        return districtname;
    }

    public void setDistrictname(String districtname) {
        this.districtname = districtname;
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

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
    }
}