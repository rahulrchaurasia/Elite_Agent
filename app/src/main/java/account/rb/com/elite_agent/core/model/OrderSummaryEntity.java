package account.rb.com.elite_agent.core.model;

public class OrderSummaryEntity {
    /**
     * Pending : 0
     * Complete : 0
     * Current : 0
     * Loss : 0
     */

    /**
     * Status : Pending
     * count : 0.00
     */


    private String Pending;
    private String Complete;
    private String Current;
    private String Loss;

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

    public String getCurrent() {
        return Current;
    }

    public void setCurrent(String Current) {
        this.Current = Current;
    }

    public String getLoss() {
        return Loss;
    }

    public void setLoss(String Loss) {
        this.Loss = Loss;
    }



}