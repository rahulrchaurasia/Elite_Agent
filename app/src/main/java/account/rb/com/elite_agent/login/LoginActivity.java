package account.rb.com.elite_agent.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import account.rb.com.elite_agent.BaseActivity;
import account.rb.com.elite_agent.HomeActivity;
import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.IResponseSubcriber;
import account.rb.com.elite_agent.core.controller.register.RegisterController;
import account.rb.com.elite_agent.core.response.LoginResponse;
import account.rb.com.elite_agent.register.SignUpActivity;
import account.rb.com.elite_agent.splash.PrefManager;
import account.rb.com.elite_agent.utility.ReadDeviceID;

public class LoginActivity extends BaseActivity implements View.OnClickListener, IResponseSubcriber {


    TextView tvRegistration, tvForgotPassword;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 1111;
    EditText etPassword, etMobile;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String mobile, password;
    Button btnSignIn;
    PrefManager prefManager;
    String deviceId ="";
    String refreshedToken = "";
    String[] perms = {
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE",

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        initialize();

        prefManager = new PrefManager(this);

        String mob = prefManager.getMobile();
        String pwd = prefManager.getPassword();

        etMobile.setText(mob);
        etPassword.setText(pwd);

        try {
            deviceId = prefManager.getDeviceID();
            if (deviceId == null || deviceId.matches("")) {
                deviceId = new ReadDeviceID(this).getAndroidID();
                prefManager.setDeviceID(deviceId);
            }
            refreshedToken = prefManager.getDeviceToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!checkPermission()) {
            requestPermission();
        }
    }

    private void initialize() {

        etPassword = (EditText) findViewById(R.id.etPassword);
        etMobile = (EditText) findViewById(R.id.etMobile);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        tvRegistration = (TextView) findViewById(R.id.tvRegistration);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(this);
        tvRegistration.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvForgotPassword:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                break;

            case R.id.tvRegistration:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));

                break;

            case R.id.btnSignIn:
                if (!isEmpty(etMobile)) {
                    etMobile.requestFocus();
                    etMobile.setError("Enter Mobile");
                    return;
                }
                if (!isEmpty(etPassword)) {
                    etPassword.requestFocus();
                    etPassword.setError("Enter Password");
                    return;
                }

                showDialog("Please Wait...");

                new RegisterController(LoginActivity.this).getLogin(etMobile.getText().toString(), etPassword.getText().toString(),refreshedToken,deviceId, this);


                break;
        }
    }

    //region permission

    private boolean checkPermission() {

        int camera = ContextCompat.checkSelfPermission(getApplicationContext(), perms[0]);

        int write_external = ContextCompat.checkSelfPermission(getApplicationContext(), perms[1]);
        int read_external = ContextCompat.checkSelfPermission(getApplicationContext(), perms[2]);

        return camera == PackageManager.PERMISSION_GRANTED


                && write_external == PackageManager.PERMISSION_GRANTED
                && read_external == PackageManager.PERMISSION_GRANTED;

    }


    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof LoginResponse) {
            if (response.getStatus_code() == 0) {

                //store user credentials


                prefManager.setMobile(etMobile.getText().toString());
                prefManager.setPassword(etPassword.getText().toString());
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(this, perms, REQUEST_CODE_ASK_PERMISSIONS);
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Retry")
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                //.setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults.length > 0) {

                    //boolean writeExternal = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean camera = grantResults[0] == PackageManager.PERMISSION_GRANTED;


                    boolean writeExternal = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternal = grantResults[2] == PackageManager.PERMISSION_GRANTED;



                    if (camera && writeExternal && readExternal ) {
                        // you can do all necessary steps
                        // new Dialer().getObject().getLeadData(String.valueOf(Utility.EmpCode), this, this);
                        // Toast.makeText(this, "All permission granted", Toast.LENGTH_SHORT).show();
                    } else {

                        //Permission Denied, You cannot access location data and camera
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            showMessageOKCancel("Required permissions to proceed Elite Customer..!",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            // finish();
                                            requestPermission();
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }


    //endregion
}
