package account.rb.com.elite_agent.Dashboard;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import account.rb.com.elite_agent.BaseFragment;
import account.rb.com.elite_agent.EmailUs.EmailUsActivity;
import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.IResponseSubcriber;
import account.rb.com.elite_agent.core.controller.product.ProductController;
import account.rb.com.elite_agent.core.controller.register.RegisterController;
import account.rb.com.elite_agent.core.model.OrderSummaryEntity;
import account.rb.com.elite_agent.core.model.UserConstantEntity;
import account.rb.com.elite_agent.core.model.UserEntity;
import account.rb.com.elite_agent.core.response.OrderSummaryResponse;
import account.rb.com.elite_agent.core.response.UserConstantResponse;
import account.rb.com.elite_agent.database.DataBaseController;
import account.rb.com.elite_agent.pendingDetail.PendingActivity;
import account.rb.com.elite_agent.splash.PrefManager;
import account.rb.com.elite_agent.taskDetail.CurrentTaskActivity;
import account.rb.com.elite_agent.utility.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends BaseFragment implements View.OnClickListener, IResponseSubcriber, BaseFragment.CustomPopUpListener {

    PrefManager prefManager;
    TextView txtCurrentCount, txtPendingCount, txtCompletetCount, txtLossCount;
    LinearLayout lyCurrent, lyPending, lyComplete, lyLoss, lyCall, lyEmail;

    DataBaseController dataBaseController;
    UserEntity loginEntity;
    UserConstantEntity userConstatntEntity;
    String[] permissionsRequired = new String[]{Manifest.permission.CALL_PHONE};
    PackageInfo pinfo;
    String versionNAme;

    public DashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        initilize(view);
        setOnClickListner();
        dataBaseController = new DataBaseController(getActivity());
        prefManager = new PrefManager(getActivity());
        loginEntity = prefManager.getUserData();
        userConstatntEntity = prefManager.getUserConstatnt();


        registerCustomPopUp(this);

        try {
            pinfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            versionNAme = pinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        new RegisterController(getActivity()).getUserConstatnt(loginEntity.getUser_id(), DashBoardFragment.this);

        return view;
    }

    private void initilize(View view) {


        txtCurrentCount = (TextView) view.findViewById(R.id.txtCurrentCount);
        txtPendingCount = (TextView) view.findViewById(R.id.txtPendingCount);
        txtCompletetCount = (TextView) view.findViewById(R.id.txtCompletetCount);
        txtLossCount = (TextView) view.findViewById(R.id.txtLossCount);

        lyCurrent = (LinearLayout) view.findViewById(R.id.lyCurrent);
        lyPending = (LinearLayout) view.findViewById(R.id.lyPending);
        lyComplete = (LinearLayout) view.findViewById(R.id.lyComplete);
        lyLoss = (LinearLayout) view.findViewById(R.id.lyLoss);

        lyCall = (LinearLayout) view.findViewById(R.id.lyCall);
        lyEmail = (LinearLayout) view.findViewById(R.id.lyEmail);


    }

    private void setOnClickListner() {
        lyCurrent.setOnClickListener(this);
        lyPending.setOnClickListener(this);
        lyComplete.setOnClickListener(this);
        lyLoss.setOnClickListener(this);

        lyCall.setOnClickListener(this);
        lyEmail.setOnClickListener(this);
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof OrderSummaryResponse) {
            if (response.getStatus_code() == 0) {

                OrderSummaryEntity orderSummaryEntity = ((OrderSummaryResponse) response).getData().get(0);

                txtPendingCount.setText("" + orderSummaryEntity.getPending());
                txtCompletetCount.setText(" " + orderSummaryEntity.getComplete());
                txtCurrentCount.setText("" + orderSummaryEntity.getCurrent());
                //  txtLossCount.setText("" + "\u20B9" + orderSummaryEntity.getLost());
                txtLossCount.setText("" + orderSummaryEntity.getLoss());
            }
        } else if (response instanceof UserConstantResponse) {

            if (response.getStatus_code() == 0) {

                userConstatntEntity = ((UserConstantResponse) response).getData().get(0);

                if (userConstatntEntity.getVersionCode() != null) {
                    int serverVersionCode = Integer.parseInt((userConstatntEntity.getVersionCode()));
                    if (pinfo != null && pinfo.versionCode < serverVersionCode) {

                        int forceUpdate = Integer.parseInt(userConstatntEntity.getIsForceUpdate());
                        if (forceUpdate == 1) {
                            // forced update app
                            showPlaystoreDialog("New version available on play store!!!! Please update.");
                        }
                    }
                }

            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {

    }

//   fragment.getSupportActionBar().setTitle(tit`le);

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.lyCurrent:

                startActivity(new Intent(getActivity(), CurrentTaskActivity.class));
                break;
            case R.id.lyPending:

                startActivity(new Intent(getActivity(), PendingActivity.class).putExtra(Constants.TASK_TYPE, 0));
                break;


            case R.id.lyComplete:


                startActivity(new Intent(getActivity(), PendingActivity.class).putExtra(Constants.TASK_TYPE, 1));
                break;

            case R.id.lyLoss:


                startActivity(new Intent(getActivity(), PendingActivity.class).putExtra(Constants.TASK_TYPE, 2));

                break;

            case R.id.lyCall:

                if(userConstatntEntity != null) {
                    ConfirmAlert("Calling", getResources().getString(R.string.supp_Calling) + " ", userConstatntEntity.getContactno());
                }

                break;

            case R.id.lyEmail:

                startActivity(new Intent(getActivity(), EmailUsActivity.class));
                break;
        }


    }


    public void ConfirmAlert(String Title, String strBody, final String strMobile) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);


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

                Intent intentCalling = new Intent(Intent.ACTION_DIAL);
                intentCalling.setData(Uri.parse("tel:" + strMobile));
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
    public void onPositiveButtonClick(Dialog dialog, View view) {
        dialog.cancel();
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, Constants.REQUEST_PERMISSION_SETTING);

    }

    @Override
    public void onCancelButtonClick(Dialog dialog, View view) {
        dialog.cancel();
    }


    @Override
    public void onResume() {
        super.onResume();

        if (loginEntity != null) {

            new ProductController(getActivity()).orderSummary(loginEntity.getUser_id(), this);
        }
    }

    //region PlayStore

    public void showPlaystoreDialog(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);

        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        // dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        TextView txtMsg = (TextView) dialogView.findViewById(R.id.txtMsg);
        txtMsg.setText(msg);

        LinearLayout lyUpdate = (LinearLayout) dialogView.findViewById(R.id.lyUpdate);
        //TextView btnOk = (TextView) dialog.findViewById(R.id.tvOk);
        // if button is clicked, close the custom dialog
        lyUpdate.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            alertDialog.dismiss();
                                            getActivity().finish();
                                            openAppMarketPlace();
                                        }
                                    }
        );
        alertDialog.setCancelable(false);
        alertDialog.show();


    }

    private void openAppMarketPlace() {

        final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }

    }

    //endregion

}
