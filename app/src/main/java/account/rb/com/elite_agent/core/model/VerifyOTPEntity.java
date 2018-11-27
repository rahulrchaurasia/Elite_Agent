package account.rb.com.elite_agent.core.model;

public  class VerifyOTPEntity {


    /**
     * SavedStatus : 2
     * OTP :
     */

    private int SavedStatus;
    private String OTP;

    public int getSavedStatus() {
        return SavedStatus;
    }

    public void setSavedStatus(int SavedStatus) {
        this.SavedStatus = SavedStatus;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }
}