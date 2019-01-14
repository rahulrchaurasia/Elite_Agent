package account.rb.com.elite_agent.register;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import account.rb.com.elite_agent.BaseActivity;
import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.IResponseSubcriber;
import account.rb.com.elite_agent.core.controller.register.RegisterController;
import account.rb.com.elite_agent.core.model.IfscEntity;
import account.rb.com.elite_agent.core.model.PincodeEntity;
import account.rb.com.elite_agent.core.model.VerifyOTPEntity;
import account.rb.com.elite_agent.core.requestmodel.AddUserRequestEntity;
import account.rb.com.elite_agent.core.requestmodel.RegisterRequest;
import account.rb.com.elite_agent.core.requestmodel.UpdateUserRequestEntity;
import account.rb.com.elite_agent.core.response.AddUserResponse;
import account.rb.com.elite_agent.core.response.GetOtpResponse;
import account.rb.com.elite_agent.core.response.IfscCodeResponse;
import account.rb.com.elite_agent.core.response.PincodeResponse;
import account.rb.com.elite_agent.core.response.UpdateUserResponse;
import account.rb.com.elite_agent.core.response.UserRegistrationResponse;
import account.rb.com.elite_agent.core.response.VerifyUserRegisterResponse;
import account.rb.com.elite_agent.utility.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends BaseActivity implements IResponseSubcriber, View.OnClickListener, View.OnFocusChangeListener {

    EditText etFullName, etMobile, etPincode, etArea, etCity, etState, etEmail, etPassword, etconfirmPassword;
    //region bank details
    EditText etBankAcNo, etAccountType, etIfscCode, erMicrCode, etBankBranch, etBankCity, etBankName;
    TextView txtSaving, txtCurrent;
    IfscEntity ifscEntity;
    public String ACCOUNT_TYPE = "SAVING";

    //endregion

    Button btnSubmit;
    TextView tvOk;
    EditText etOtp;
    Dialog dialog;
    AddUserRequestEntity addUserRequestEntity;
    PincodeEntity pincodeEntity;
    UpdateUserRequestEntity updateUserRequestEntity;
    String OTP = "0000";
    LinearLayout llOtherInfo, llBankDetail;
    RelativeLayout rlBankDetail;
    ImageView ivBankDetail;
    RegisterRequest registerRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        init_widets();
        setListener();
        registerRequest = new RegisterRequest();

    }

    //region Method

    //region Broadcast Receiver
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                String message = intent.getStringExtra("message");
                String strmessage = extractDigitFromMessage(message);
                if (!strmessage.equals("")) {
                    etOtp.setText(strmessage);

                }
            }
        }
    };

    //endregion

    private String extractDigitFromMessage(String message) {
        //---This will match any 6 digit number in the message, can use "|" to lookup more possible combinations
        Pattern p = Pattern.compile("(|^)\\d{6}");
        try {
            if (message != null) {
                Matcher m = p.matcher(message);
                if (m.find()) {
                    return m.group(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void setListener() {
        btnSubmit.setOnClickListener(this);
        etPincode.addTextChangedListener(pincodeTextWatcher);
        etIfscCode.setOnFocusChangeListener(this);
        txtSaving.setOnClickListener(this);
        txtCurrent.setOnClickListener(this);

        rlBankDetail.setOnClickListener(this);
        ivBankDetail.setOnClickListener(this);

    }

    private void init_widets() {

        llBankDetail = (LinearLayout) findViewById(R.id.llBankDetail);
        rlBankDetail = (RelativeLayout) findViewById(R.id.rlBankDetail);
        llOtherInfo = (LinearLayout) findViewById(R.id.llOtherInfo);
        etFullName = (EditText) findViewById(R.id.etFullName);
        etMobile = (EditText) findViewById(R.id.etMobile);
        etPincode = (EditText) findViewById(R.id.etPincode);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etconfirmPassword = (EditText) findViewById(R.id.etconfirmPassword);


        etArea = (EditText) findViewById(R.id.etArea);
        etCity = (EditText) findViewById(R.id.etCity);
        etState = (EditText) findViewById(R.id.etState);

        //region bank details

        etBankAcNo = (EditText) findViewById(R.id.etBankAcNo);
        etAccountType = (EditText) findViewById(R.id.etAccountType);
        etIfscCode = (EditText) findViewById(R.id.etIfscCode);
        etIfscCode.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(15)});

        erMicrCode = (EditText) findViewById(R.id.erMicrCode);
        etBankBranch = (EditText) findViewById(R.id.etBankBranch);
        etBankCity = (EditText) findViewById(R.id.etBankCity);
        etBankName = (EditText) findViewById(R.id.etBankName);
        txtSaving = (TextView) findViewById(R.id.txtSaving);
        txtCurrent = (TextView) findViewById(R.id.txtCurrent);

        ivBankDetail = (ImageView) findViewById(R.id.ivBankDetail);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        //endregion
    }

    private boolean validateRegistration() {
        if (!isEmpty(etFullName)) {
            etFullName.requestFocus();
            etFullName.setError("Enter Name");
            return false;
        }

        if (!isEmpty(etMobile)) {
            etMobile.requestFocus();
            etMobile.setError("Enter Mobile");
            return false;
        }
        if (!isValidePhoneNumber(etMobile)) {
            etMobile.requestFocus();
            etMobile.setError("Enter Valid Mobile");
            return false;
        }
        if (!isEmpty(etEmail)) {
            etEmail.requestFocus();
            etEmail.setError("Enter Email");
            return false;
        }
        if (!isValideEmailID(etEmail)) {
            etEmail.requestFocus();
            etEmail.setError("Enter Valid Email");
            return false;
        }
        if (!isEmpty(etPincode) && etPincode.getText().toString().length() != 6) {
            etPincode.requestFocus();
            etPincode.setError("Enter Pincode");
            return false;
        }
        if (!isEmpty(etPassword)) {
            etPassword.requestFocus();
            etPassword.setError("Enter Password");
            return false;
        }
        if (etPassword.getText().toString().trim().length() < 3) {
            etPassword.requestFocus();
            etPassword.setError("Minimum length should be 3");
            return false;
        }
        if (!isEmpty(etconfirmPassword)) {
            etconfirmPassword.requestFocus();
            etconfirmPassword.setError("Confirm Password");
            return false;
        }
        if (!etPassword.getText().toString().equals(etconfirmPassword.getText().toString())) {
            etconfirmPassword.requestFocus();
            etconfirmPassword.setError("Password Mismatch");
            return false;
        }

        if (etBankAcNo.getText().toString().isEmpty()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etBankAcNo.requestFocus();
                etBankAcNo.setError("Enter Bank Account No");
                etBankAcNo.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                return false;
            } else {
                etBankAcNo.requestFocus();
                etBankAcNo.setError("Enter Bank Account No");
                return false;
            }
        }
        if (etIfscCode.getText().toString().isEmpty()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                etIfscCode.requestFocus();
                etIfscCode.setError("Enter Bank IFSC");
                etIfscCode.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                return false;
            } else {
                etIfscCode.requestFocus();
                etIfscCode.setError("Enter Bank IFSC");
                return false;
            }
        }
        if (erMicrCode.getText().toString().isEmpty()) {


            erMicrCode.requestFocus();
            erMicrCode.setError("Enter Bank MICR");
            return false;

        }

        if (etBankName.getText().toString().isEmpty()) {


            etBankName.requestFocus();
            etBankName.setError("Enter Bank Name");
            return false;

        }
        if (etBankBranch.getText().toString().isEmpty()) {


            etBankBranch.requestFocus();
            etBankBranch.setError("Enter Bank Branck");
            return false;

        }
        if (etBankCity.getText().toString().isEmpty()) {


            etBankCity.requestFocus();
            etBankCity.setError("Enter Bank City");
            return false;
        }


        return true;
    }

    private void showOtpAlert() {

        try {

            dialog = new Dialog(SignUpActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.otp_dialog);
            TextView tvOk = (TextView) dialog.findViewById(R.id.tvOk);
            final TextView txtOTPMessage = (TextView) dialog.findViewById(R.id.txtOTPMessage);
            final TextView tvTime = (TextView) dialog.findViewById(R.id.tvTime);
            TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
            tvTitle.setText("Enter OTP sent on : " + etMobile.getText().toString());
            TextView resend = (TextView) dialog.findViewById(R.id.tvResend);
            etOtp = (EditText) dialog.findViewById(R.id.etOtp);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = lp.MATCH_PARENT;

            lp.height = lp.WRAP_CONTENT; // Height
            dialogWindow.setAttributes(lp);

            txtOTPMessage.setText("");
            txtOTPMessage.setVisibility(View.GONE);

            dialog.show();

            etOtp.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    txtOTPMessage.setText("");
                    txtOTPMessage.setVisibility(View.GONE);
                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });
            tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Close dialog
                    if (etOtp.getText().toString().equals("0000") || etOtp.getText().toString().equals(OTP)) {

                        etMobile.setText(etMobile.getText().toString());
                        dialog.dismiss();
                        setRegisterRequest(OTP);

                    } else {
                        Toast.makeText(SignUpActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                        txtOTPMessage.setText("Invalid OTP");
                        txtOTPMessage.setVisibility(View.VISIBLE);
                    }


                }
            });

            resend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    etOtp.setText("");
                    OTP = "";
                    showDialog("Re-sending otp...");
                    new RegisterController(SignUpActivity.this).verifyOTPTegistration(etEmail.getText().toString(), etMobile.getText().toString(), "", SignUpActivity.this);
                }
            });

            new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvTime.setText((millisUntilFinished / 1000) + " seconds remaining");
                }

                @Override
                public void onFinish() {
                    tvTime.setText("");
                    // dialog.dismiss();
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setRegisterRequest(String strOTP) {


        registerRequest.setOtp("" + strOTP);
        registerRequest.setAgent_name("" + etFullName.getText());
        registerRequest.setEmailid("" + etEmail.getText());
        registerRequest.setMobile("" + etMobile.getText());

        registerRequest.setPincode("" + etPincode.getText());
        registerRequest.setState("" + etState.getText());
        registerRequest.setArea("" + etArea.getText());
        registerRequest.setCity("" + etCity.getText());


        showDialog();
        new RegisterController(this).saveUserRegistration(registerRequest, SignUpActivity.this);
    }

    private void setSavingAcc() {
        ACCOUNT_TYPE = "SAVING";
        txtSaving.setBackgroundResource(R.drawable.customeborder_blue);
        txtSaving.setTextColor(ContextCompat.getColor(SignUpActivity.this, R.color.colorPrimary));

        txtCurrent.setBackgroundResource(R.drawable.customeborder);
        txtCurrent.setTextColor(ContextCompat.getColor(SignUpActivity.this, R.color.description_text));


    }

    private void setCurrentAcc() {
        ACCOUNT_TYPE = "CURRENT";
        txtCurrent.setBackgroundResource(R.drawable.customeborder_blue);
        txtCurrent.setTextColor(ContextCompat.getColor(SignUpActivity.this, R.color.colorPrimary));

        txtSaving.setBackgroundResource(R.drawable.customeborder);
        txtSaving.setTextColor(ContextCompat.getColor(SignUpActivity.this, R.color.description_text));


    }

    //endregion


    //region event


    @Override
    public void onClick(View view) {

        Constants.hideKeyBoard(view, SignUpActivity.this);
        switch (view.getId()) {

            case R.id.txtSaving:
                setSavingAcc();
                break;
            case R.id.txtCurrent:
                setCurrentAcc();
                break;

            case R.id.ivBankDetail:
            case R.id.rlBankDetail:

                if (llBankDetail.getVisibility() == View.GONE) {
                    llBankDetail.setVisibility(View.VISIBLE);
                    ivBankDetail.setImageDrawable(getResources().getDrawable(R.drawable.up_arrow));

                } else {
                    llBankDetail.setVisibility(View.GONE);
                    ivBankDetail.setImageDrawable(getResources().getDrawable(R.drawable.down_arrow));
                }

                break;
            case R.id.btnSubmit:

                if (validateRegistration() == true) {
                    showDialog();
                    new RegisterController(SignUpActivity.this).verifyOTPTegistration(etEmail.getText().toString(), etMobile.getText().toString(), "", SignUpActivity.this);

                }
                break;


        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof PincodeResponse) {
            if (response.getStatus_code() == 0) {
                pincodeEntity = ((PincodeResponse) response).getData().get(0);
                if (pincodeEntity != null) {
//                 etArea.setText("" + pincodeEntity.getPostname());
                    etCity.setText("" + pincodeEntity.getCityname());
                    etState.setText("" + pincodeEntity.getState_name());
                }
            } else {
                etCity.setText("");
                etState.setText("");
            }
        } else if (response instanceof VerifyUserRegisterResponse) {
            if (response.getStatus_code() == 0) {
                VerifyOTPEntity verifyOTPEntity = ((VerifyUserRegisterResponse) response).getData();
                if (verifyOTPEntity.getSavedStatus() == 1) {
                    showOtpAlert();
                } else if (verifyOTPEntity.getSavedStatus() == 2) {
                    getCustomToast(response.getMessage());
                }
            }
        } else if (response instanceof IfscCodeResponse) {
            if (response.getStatus_code() == 0) {
                Constants.hideKeyBoard(etPincode, this);
                if (((IfscCodeResponse) response).getMasterData() != null) {
                    if (((IfscCodeResponse) response).getMasterData().size() > 0) {
                        ifscEntity = ((IfscCodeResponse) response).getMasterData().get(0);

                        etIfscCode.setText("" + ifscEntity.getIFSCCode());
                        if (ifscEntity.getMICRCode() != null)
                            erMicrCode.setText("" + ifscEntity.getMICRCode());
                        etBankName.setText("" + ifscEntity.getBankName());
                        etBankBranch.setText("" + ifscEntity.getBankBran());
                        etBankCity.setText("" + ifscEntity.getCityName());

                        if (!erMicrCode.getText().toString().isEmpty()) {


                            registerRequest.setMICR_code(erMicrCode.getText().toString());
                            registerRequest.setBank_name(etBankName.getText().toString());
                            registerRequest.setBank_branch_name(etBankBranch.getText().toString());
                            registerRequest.setCity(etBankCity.getText().toString());
                        }
                    } else {
                        etIfscCode.setText("" + ifscEntity.getIFSCCode());
                        erMicrCode.setText("");
                        etBankName.setText("");
                        etBankBranch.setText("");
                        etBankCity.setText("");
                        getCustomToast("Invalid IFSC Code");
                    }
                }
            }
        } else if (response instanceof UserRegistrationResponse) {

            if (response.getStatus_code() == 0) {
                // this.finish();
                //Toast.makeText(this, "Data Save Successfully" , Toast.LENGTH_SHORT).show();
                getCustomToast("Data Save Successfully");
                this.finish();
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }


    //region textwatcher and Onfocus Listener
    TextWatcher pincodeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (start == 5) {
                etCity.setText("");
                etState.setText("");
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 6) {
                showDialog("Fetching City...");
                new RegisterController(SignUpActivity.this).getCityState(etPincode.getText().toString(), SignUpActivity.this);

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        switch (view.getId()) {
            case R.id.etIfscCode:
                if (!hasFocus) {
                    if (etIfscCode.getText().length() > 3) {
                        showDialog("Fetching Bank Details...");
                        new RegisterController(SignUpActivity.this).getIFSC(etIfscCode.getText().toString(), SignUpActivity.this);
                    }
                }
                break;
        }
    }
    //endregion

    //endregion
}
