package account.rb.com.elite_agent.core.model;

public class OrderSummaryEntity {
    /**
     * Status : Pending
     * count : 0.00
     */

    private String Pending;
    private String Complete;
    private String Wallet;
    private String Status;


    private String Lost;

    public String getPending() {
        return Pending;
    }

    public void setPending(String Pending) {
        this.Pending = Pending;
    }

    public String getComplete() {
        return Complete;
    }

    public void setComplete(String Complete) {
        this.Complete = Complete;
    }

    public String getWallet() {
        return Wallet;
    }

    public void setWallet(String Wallet) {
        this.Wallet = Wallet;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getLost() {
        return Lost;
    }

    public void setLost(String lost) {
        Lost = lost;
    }



}