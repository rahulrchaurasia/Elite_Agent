package account.rb.com.elite_agent.utility;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import account.rb.com.elite_agent.splash.PrefManager;

/**
 * Created by IN-RB on 09-02-2018.
 */

public class FirebaseIDService   extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Refreshed token: " + refreshedToken);
        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }


    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.


        sharedPreferences = getSharedPreferences("ELITE_AGENT", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Constants.DEVICE_TOKEN, token);
        editor.commit();

    }
}
