package account.rb.com.elite_agent.utility;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by IN-RB on 02-02-2018.
 */

public class Constants {

    public static int SPLASH_DISPLAY_LENGTH = 2000;

    public static String DEVICE_TOKEN = "devicetoken";
    public static String PUSH_NOTIFY = "pushNotify";


    public static void hideKeyBoard(View view, Context context) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
