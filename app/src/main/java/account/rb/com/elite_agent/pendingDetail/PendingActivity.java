package account.rb.com.elite_agent.pendingDetail;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import account.rb.com.elite_agent.BaseActivity;
import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.IResponseSubcriber;
import account.rb.com.elite_agent.core.controller.product.ProductController;
import account.rb.com.elite_agent.core.model.TaskEntity;
import account.rb.com.elite_agent.core.model.UserEntity;
import account.rb.com.elite_agent.core.response.AgentCommonResponse;
import account.rb.com.elite_agent.core.response.TaskDetailResponse;
import account.rb.com.elite_agent.database.DataBaseController;
import account.rb.com.elite_agent.document.DocUploadActivity;
import account.rb.com.elite_agent.splash.PrefManager;
import account.rb.com.elite_agent.utility.Constants;

public class PendingActivity extends BaseActivity implements IResponseSubcriber,BaseActivity.CustomPopUpListener  {

    UserEntity loginEntity;
    DataBaseController dataBaseController;
    PrefManager prefManager;

    RecyclerView rvOrderDtl;
    List<TaskEntity> lsTaskDetail;
    PendingTaskDetailAdapter mAdapter;
    List<String> statuslist;

    TaskEntity taskEntityMain;
    int acceptStausMain = 0 ;
    int TASK_TYPE  = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initilize();
        dataBaseController = new DataBaseController(this);
        prefManager = new PrefManager(this);
        loginEntity = prefManager.getUserData();

        statuslist = dataBaseController.getOrderStatusList();
        statuslist.add(0, "Select");

        registerCustomPopUp(this);

        if (getIntent().hasExtra(Constants.TASK_TYPE)) {
            TASK_TYPE = getIntent().getExtras().getInt(Constants.TASK_TYPE,0);
            if(TASK_TYPE == 0) {
                getSupportActionBar().setTitle(" Pending Order");
            }else if(TASK_TYPE == 1){
                getSupportActionBar().setTitle(" Complete Order");
            }else if(TASK_TYPE == 2){
                getSupportActionBar().setTitle(" Loss Order");
            }

            showDialog();
            new ProductController(PendingActivity.this).pendingTaskDetail(loginEntity.getUser_id(),TASK_TYPE, this);


        }
    }

    private void initilize( ) {
        rvOrderDtl = (RecyclerView)findViewById(R.id.rvOrderDtl);
        rvOrderDtl.setHasFixedSize(true);
        taskEntityMain = new TaskEntity();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(PendingActivity.this);
        rvOrderDtl.setLayoutManager(mLayoutManager);

        lsTaskDetail = new ArrayList<TaskEntity>();
    }

    public void redirectToPendingTask(TaskEntity taskEntity ,int acceptStaus) {

        taskEntityMain = taskEntity;
        acceptStausMain =  acceptStaus ;
        if(acceptStaus == 1)
        {
            openPopUp(rvOrderDtl, "Confirmation",getResources().getString(R.string.accept_msg), "YES", "NO", true, false);
        }
        else if( acceptStaus == 2){
            openPopUp(rvOrderDtl, "Confirmation",getResources().getString(R.string.reject_msg), "YES", "NO", true, false);
        }

    }



    private void updatePendingTask(TaskEntity taskEntity, int acceptStaus) {
        showDialog();
        new ProductController(PendingActivity.this).updatePendongTask(taskEntity.getId(), loginEntity.getUser_id(), acceptStaus, this);
    }
    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof TaskDetailResponse) {
            if (response.getStatus_code() == 0) {

                //Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                lsTaskDetail = ((TaskDetailResponse) response).getData();
                mAdapter = new PendingTaskDetailAdapter(PendingActivity.this, lsTaskDetail ,TASK_TYPE);
                rvOrderDtl.setAdapter(mAdapter);

            } else {
                rvOrderDtl.setAdapter(null);
            }
        }else if(response instanceof AgentCommonResponse)
        {
            taskEntityMain = null;
            acceptStausMain = 0;
           // Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
            getCustomToast(response.getMessage());

            showDialog();
            new ProductController(this).pendingTaskDetail(loginEntity.getUser_id(),0, this);

        }
    }





    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        showDialog();
//        new ProductController(PendingActivity.this).pendingTaskDetail(loginEntity.getUser_id(),TASK_TYPE, this);
//
//    }

    @Override
    public void onPositiveButtonClick(Dialog dialog, View view) {

        dialog.cancel();
        if(taskEntityMain != null) {
            updatePendingTask(taskEntityMain, acceptStausMain);
        }
    }

    @Override
    public void onCancelButtonClick(Dialog dialog, View view) {

        dialog.cancel();
    }

    public void getOrderId(int orderId) {


        startActivity(new Intent(this, DocUploadActivity.class)
                .putExtra("ORDER_ID",orderId));


    }
}
