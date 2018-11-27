package account.rb.com.elite_agent.notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import account.rb.com.elite_agent.BaseActivity;
import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.IResponseSubcriber;
import account.rb.com.elite_agent.core.controller.product.ProductController;
import account.rb.com.elite_agent.core.model.NotificationEntity;
import account.rb.com.elite_agent.core.model.UserEntity;
import account.rb.com.elite_agent.core.response.NotificationResponse;
import account.rb.com.elite_agent.database.DataBaseController;
import account.rb.com.elite_agent.splash.PrefManager;
import account.rb.com.elite_agent.utility.Constants;

public class NotificationActivity extends BaseActivity implements IResponseSubcriber {

    DataBaseController dataBaseController;
    UserEntity loginEntity;
    PrefManager prefManager;

    RecyclerView rvNotify;
    List<NotificationEntity> NotificationLst;
    NotificationAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefManager = new PrefManager(this);
        dataBaseController = new DataBaseController(NotificationActivity.this);
        loginEntity = prefManager.getUserData();
        prefManager.setNotificationCounter(0);

        initialize();

        showDialog();
        new ProductController(NotificationActivity.this).getNotifcation(loginEntity.getUser_id(),"1", this);
    }

    private void initialize() {

        prefManager = new PrefManager(NotificationActivity.this);
        NotificationLst = new ArrayList<NotificationEntity>();

        prefManager.setNotificationCounter(0);

        rvNotify = (RecyclerView) findViewById(R.id.rvNotify);
        rvNotify.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NotificationActivity.this);
        rvNotify.setLayoutManager(layoutManager);



    }
    public void redirectToMain(NotificationEntity notifyEntity) {

//        if(notifyEntity.getNotifyflag().toUpperCase().equals("DR")) {
//
//            startActivity(new Intent(this, DocUploadActivity.class));
//            finish();
//        }
//        else if(notifyEntity.getNotifyflag().toUpperCase().equals("NA")) {
//
//            startActivity(new Intent(this, OrderActivity.class));
//            finish();
//        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof NotificationResponse) {
            if (response.getStatus_code() == 0) {

                //Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                NotificationLst = ((NotificationResponse) response).getData();
                mAdapter = new NotificationAdapter(NotificationActivity.this, NotificationLst);
                rvNotify.setAdapter(mAdapter);

            } else {
                rvNotify.setAdapter(null);
                Snackbar.make(rvNotify, "No Notification  Data Available", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();

        // intent.putExtra("COUNTER", "0");
        setResult(Constants.REQUEST_CODE, intent);
        finish();
        super.onBackPressed();
    }

}
