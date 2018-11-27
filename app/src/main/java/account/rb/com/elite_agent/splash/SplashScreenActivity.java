package account.rb.com.elite_agent.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import account.rb.com.elite_agent.BaseActivity;
import account.rb.com.elite_agent.HomeActivity;
import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.IResponseSubcriber;
import account.rb.com.elite_agent.database.DataBaseController;
import account.rb.com.elite_agent.login.LoginActivity;
import account.rb.com.elite_agent.utility.Constants;

public class SplashScreenActivity extends BaseActivity implements IResponseSubcriber {

    PrefManager prefManager;
    DataBaseController dataBaseController;

    TextView txtGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prefManager = new PrefManager(this);



        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(prefManager.getUserData() != null)
                {
                    startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                }else{
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                }

            }
        }, Constants.SPLASH_DISPLAY_LENGTH);

    }

    @Override
    public void OnSuccess(APIResponse response, String message) {
    //    Toast.makeText(this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void OnFailure(Throwable t) {
        Toast.makeText(this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

    }
}
