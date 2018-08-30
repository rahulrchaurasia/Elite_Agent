package account.rb.com.elite_agent.splash;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME ="ELITE_AGENT";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_PRODUCT_MASTER_UPDATE = "isProductMasterUpdate";

    private static final String MOBILE = "ELITE_CUSTOMER_MOBILE";
    private static final String PASSWORD = "ELITE_CUSTOMER_PASSWORD";

    public static String DEVICE_TOKEN = "devicetoken";
    public static String DEVICE_ID = "deviceID";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    public void setIsProductMasterUpdate(boolean isFirstTime) {
        editor.putBoolean(IS_PRODUCT_MASTER_UPDATE, isFirstTime);
        editor.commit();
    }

    public boolean IsProductMasterUpdate() {
        return pref.getBoolean(IS_PRODUCT_MASTER_UPDATE, true);
    }


    public  void setMobile(String mob)
    {
        editor.putString(MOBILE, mob);

        editor.commit();
    }

    public String getMobile() {
        return pref.getString(MOBILE, "");
    }

    public  void setPassword(String pwd)
    {

        editor.putString(PASSWORD, pwd);
        editor.commit();
    }

    public String getPassword() {
        return pref.getString(PASSWORD, "");
    }



    public  String  getDeviceToken()
    {
        return pref.getString(DEVICE_TOKEN, "");
    }


    public  void setDeviceID(String deviceID)
    {
        editor.putString(DEVICE_ID, deviceID);

        editor.commit();
    }

    public String getDeviceID() {
        return pref.getString(DEVICE_ID, "");
    }

}