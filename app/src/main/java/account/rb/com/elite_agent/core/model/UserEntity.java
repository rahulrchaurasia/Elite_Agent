package account.rb.com.elite_agent.core.model;

public  class UserEntity  {


    /**
     * user_id : 1
     * name : durga
     * email : dp@gmail.com
     * mobile : 0900000001
     * user_type_id : 2
     */

    private int user_id;
    private String name;
    private String email;
    private String mobile;
    private int user_type_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

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

    public int getUser_type_id() {
        return user_type_id;
    }

    public void setUser_type_id(int user_type_id) {
        this.user_type_id = user_type_id;
    }
}