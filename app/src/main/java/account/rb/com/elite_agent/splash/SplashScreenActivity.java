package account.rb.com.elite_agent.splash;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import account.rb.com.elite_agent.BaseActivity;
import account.rb.com.elite_agent.HomeActivity;
import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.IResponseSubcriber;
import account.rb.com.elite_agent.core.controller.register.RegisterController;
import account.rb.com.elite_agent.core.model.UserEntity;
import account.rb.com.elite_agent.login.LoginActivity;
import account.rb.com.elite_agent.utility.Constants;

import static account.rb.com.elite_agent.utility.Constants.SPLASH_DISPLAY_LENGTH;

public class SplashScreenActivity extends BaseActivity implements IResponseSubcriber {

    PrefManager prefManager;
    UserEntity loginEntity;

    TextView txtGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        txtGroup = (TextView) findViewById(R.id.txtGroup);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prefManager = new PrefManager(this);
        loginEntity = prefManager.getUserData();


        verify();
    }

    private void fetchUserConstatnt() {

        if (loginEntity != null) {
            new RegisterController(this).getUserConstatnt(loginEntity.getUser_id(), null);
        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {
        //    Toast.makeText(this, "" + response.getMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void OnFailure(Throwable t) {
        Toast.makeText(this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchUserConstatnt();
    }

    public void verify() {
        if (!Constants.checkInternetStatus(SplashScreenActivity.this)) {

            Snackbar snackbar = Snackbar.make(txtGroup, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            verify();
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.CYAN);

            snackbar.show();
        } else {

            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (prefManager.getUserData() != null) {
                        startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                    } else {
                        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    }

                }
            }, SPLASH_DISPLAY_LENGTH);

        }
    }


}

