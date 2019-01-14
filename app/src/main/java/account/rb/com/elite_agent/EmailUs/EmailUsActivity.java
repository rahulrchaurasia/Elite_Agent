package account.rb.com.elite_agent.EmailUs;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import account.rb.com.elite_agent.BaseActivity;
import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.IResponseSubcriber;
import account.rb.com.elite_agent.core.controller.register.RegisterController;
import account.rb.com.elite_agent.core.model.LoginEntity;
import account.rb.com.elite_agent.core.model.UserConstantEntity;
import account.rb.com.elite_agent.core.model.UserEntity;
import account.rb.com.elite_agent.core.response.UserConstantResponse;
import account.rb.com.elite_agent.splash.PrefManager;
import account.rb.com.elite_agent.utility.Constants;

public class EmailUsActivity extends BaseActivity implements View.OnClickListener ,IResponseSubcriber {

    LinearLayout lyCalling, lyEmail;
    TextView txtCall ,txtEmail;
    String[] permissionsRequired = new String[]{Manifest.permission.CALL_PHONE};
    UserConstantEntity userConstatntEntity;
    UserEntity loginEntity;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        prefManager = new PrefManager(this);
        loginEntity = prefManager.getUserData();
        userConstatntEntity = prefManager.getUserConstatnt();
        initialize();
        setListner();
        setCallUI();
        if (userConstatntEntity == null) {

            if (prefManager.getUserConstatnt() == null) {
                new RegisterController(this).getUserConstatnt(loginEntity.getUser_id(),this);
            }
        }
    }

    private void initialize() {
        lyCalling = (LinearLayout) findViewById(R.id.lyCalling);
        lyEmail = (LinearLayout) findViewById(R.id.lyEmail);
        txtCall =  findViewById(R.id.txtCall);
        txtEmail =  findViewById(R.id.txtEmail);

    }


    private void setListner() {
        lyCalling.setOnClickListener(this);
        lyEmail.setOnClickListener(this);
    }

    private void setCallUI()
    {
        if(userConstatntEntity != null) {
            txtCall.setText(userConstatntEntity.getContactno());
            txtEmail.setText(userConstatntEntity.getEmailid());
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.lyCalling:

                if (ActivityCompat.checkSelfPermission(this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[0])) {
                        //Show Information about why you need the permission
                        ActivityCompat.requestPermissions(this, permissionsRequired, Constants.PERMISSION_CALLBACK_CONSTANT);

                    } else {

                        // openPopUp(lyCall, "Need  Permission", "This app needs all permissions.", "GRANT", true);
                        openPopUp(lyCalling, "Need Call Permission", "Required call permissions.", "GRANT", "DENNY", false, true);

                    }
                } else {

                    ConfirmAlert("Calling", getResources().getString(R.string.supp_Calling) + " ", userConstatntEntity.getContactno());
                }

                break;


            case R.id.lyEmail:

                if (userConstatntEntity != null) {
                    composeEmail(userConstatntEntity.getEmailid(), "Elite Support Team");
                }
                break;


        }


    }

    public void ConfirmAlert(String Title, String strBody, final String strMobile) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);


        Button btnSubmit;
        TextView txtTile, txtBody, txtMob;
        ImageView ivCross;

        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.layout_confirm_popup, null);

        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        txtTile = (TextView) dialogView.findViewById(R.id.txtTile);
        txtBody = (TextView) dialogView.findViewById(R.id.txtMessage);
        txtMob = (TextView) dialogView.findViewById(R.id.txtOther);
        ivCross = (ImageView) dialogView.findViewById(R.id.ivCross);

        btnSubmit = (Button) dialogView.findViewById(R.id.btnSubmit);

        txtTile.setText(Title);
        txtBody.setText(strBody);
        txtMob.setText(strMobile);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                Intent intentCalling = new Intent(Intent.ACTION_CALL);
                intentCalling.setData(Uri.parse("tel:" + strMobile));
                if (ActivityCompat.checkSelfPermission(EmailUsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intentCalling);

            }
        });

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case Constants.PERMISSION_CALLBACK_CONSTANT:
                if (grantResults.length > 0) {

                    //boolean writeExternal = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean call_phone = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (call_phone) {

                        ConfirmAlert("Calling", getResources().getString(R.string.supp_Calling) + " ", userConstatntEntity.getContactno());
                    }

                }

                break;


        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        if (response instanceof UserConstantResponse) {

            if (response.getStatus_code() == 0) {

                userConstatntEntity = ((UserConstantResponse) response).getData().get(0);
                if (userConstatntEntity != null) {
                    setCallUI();
                }
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {

    }
}
