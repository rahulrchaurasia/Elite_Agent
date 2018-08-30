package account.rb.com.elite_agent.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
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
import account.rb.com.elite_agent.core.model.LoginEntity;
import account.rb.com.elite_agent.core.response.LoginResponse;
import account.rb.com.elite_agent.register.SignUpActivity;
import account.rb.com.elite_agent.splash.PrefManager;
import account.rb.com.elite_agent.utility.Constants;
import account.rb.com.elite_agent.utility.ReadDeviceID;

public class loginActivity extends BaseActivity implements View.OnClickListener, IResponseSubcriber {


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
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.SEND_SMS",
            "android.permission.READ_SMS",
            "android.permission.RECEIVE_SMS"};

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
       // tvRegistration.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvForgotPassword:
                startActivity(new Intent(loginActivity.this, ForgotPasswordActivity.class));
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
                if (refreshedToken == null) {
                    refreshedToken = "";
                }
                new RegisterController(loginActivity.this).getLogin(etMobile.getText().toString(), etPassword.getText().toString(),"","", this);


                break;
        }
    }

    //region permission

    private boolean checkPermission() {

        int camera = ContextCompat.checkSelfPermission(getApplicationContext(), perms[0]);
        int fineLocation = ContextCompat.checkSelfPermission(getApplicationContext(), perms[1]);
        int sendSms = ContextCompat.checkSelfPermission(getApplicationContext(), perms[2]);
        int readSms = ContextCompat.checkSelfPermission(getApplicationContext(), perms[3]);
        int receiveSms = ContextCompat.checkSelfPermission(getApplicationContext(), perms[4]);
        return camera == PackageManager.PERMISSION_GRANTED
                && fineLocation == PackageManager.PERMISSION_GRANTED
                && sendSms == PackageManager.PERMISSION_GRANTED
                && readSms == PackageManager.PERMISSION_GRANTED
                && receiveSms == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof LoginResponse) {
            if (response.getStatus_code() == 0) {

                //store user credentials


                prefManager.setMobile(etMobile.getText().toString());
                prefManager.setPassword(etPassword.getText().toString());
                startActivity(new Intent(loginActivity.this, HomeActivity.class));
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults.length > 0) {

                    //boolean writeExternal = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean camera = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean fineLocation = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean sendSms = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean readSms = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean receiveSms = grantResults[4] == PackageManager.PERMISSION_GRANTED;


                    if (camera && fineLocation && sendSms && readSms && receiveSms) {
                        // you can do all necessary steps
                        // new Dialer().getObject().getLeadData(String.valueOf(Utility.EmpCode), this, this);
                        Toast.makeText(this, "All permission granted", Toast.LENGTH_SHORT).show();
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

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(loginActivity.this)
                .setTitle("Retry")
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                //.setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    //endregion
}
