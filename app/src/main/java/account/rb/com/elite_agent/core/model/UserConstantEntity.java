package account.rb.com.elite_agent.core.model;

public class UserConstantEntity {
    /**
     * contactno : 02266048200
     * emailid : delight@rupeeboss.com
     */

    private String contactno;
    private String emailid;
    private String IsForceUpdate;

    public String getIsForceUpdate() {
        return IsForceUpdate;
    }

    public void setIsForceUpdate(String isForceUpdate) {
        IsForceUpdate = isForceUpdate;
    }

    public String getVersionCode() {
        return VersionCode;
    }

    public void setVersionCode(String versionCode) {
        VersionCode = versionCode;
    }

    private String VersionCode;

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }
}