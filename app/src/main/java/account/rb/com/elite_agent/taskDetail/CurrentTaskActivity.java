package account.rb.com.elite_agent.taskDetail;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import account.rb.com.elite_agent.BaseActivity;
import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.chat.ChatActivity;
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
import account.rb.com.elite_agent.taskDetail.docUpload.CurrentDocUploadActivity;

public class CurrentTaskActivity extends BaseActivity implements IResponseSubcriber {

    UserEntity loginEntity;
    DataBaseController dataBaseController;
    PrefManager prefManager;

    RecyclerView rvOrderDtl;
    List<TaskEntity> lsTaskDetail;
    TaskDetailAdapter mAdapter;
    List<String> statuslist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_task);
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

        showDialog();
        new ProductController(CurrentTaskActivity.this).taskDetail(loginEntity.getUser_id(), this);


    }

    private void initilize() {
        rvOrderDtl = (RecyclerView) findViewById(R.id.rvOrderDtl);
        rvOrderDtl.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(CurrentTaskActivity.this);
        rvOrderDtl.setLayoutManager(mLayoutManager);

        lsTaskDetail = new ArrayList<TaskEntity>();
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof TaskDetailResponse) {
            if (response.getStatus_code() == 0) {

                //Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                lsTaskDetail = ((TaskDetailResponse) response).getData();
                mAdapter = new TaskDetailAdapter(this, lsTaskDetail);
                rvOrderDtl.setAdapter(mAdapter);

            } else {
                rvOrderDtl.setAdapter(null);
            }
        } else if (response instanceof AgentCommonResponse) {
            if (response.getStatus_code() == 0) {

                //Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
                getCustomToast(response.getMessage());
                showDialog();
                new ProductController(this).taskDetail(loginEntity.getUser_id(), this);


            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(CurrentTaskActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

    }



    public void redirectToDocUpload(int orderId) {

        startActivity(new Intent(CurrentTaskActivity.this, CurrentDocUploadActivity.class)
                .putExtra("ORDER_ID",String.valueOf(orderId)));

        // temp 05
//
//        startActivity(new Intent(CurrentTaskActivity.this, CurrentDocUploadActivity.class)
//                .putExtra("ORDER_ID",String.valueOf(1)));
    }





    public void getOrderId(int orderId) {

        startActivity(new Intent(this, DocUploadActivity.class)
                .putExtra("ORDER_ID", orderId));

    }

    public void getOrderIdForComment(int orderId) {

        startActivity(new Intent(this, ChatActivity.class)
                .putExtra("ORDER_ID", orderId));


    }

    //region Not in Used
    private void uPdatePopUp(final TaskEntity taskEntity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CurrentTaskActivity.this);

        final EditText etRemark;
        final Spinner spStatus;
        Button btnProceed, btnClose;
        ArrayAdapter<String> statusAdapter;

        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.layout_task_update, null);

        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        btnProceed = (Button) dialogView.findViewById(R.id.btnProceed);
        btnClose = (Button) dialogView.findViewById(R.id.btnClose);
        etRemark = (EditText) dialogView.findViewById(R.id.etRemark);
        spStatus = (Spinner) dialogView.findViewById(R.id.spStatus);

        statusAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, statuslist);
        spStatus.setAdapter(statusAdapter);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = dataBaseController.getOrderStattusId(spStatus.getSelectedItem().toString());

                if (id == 0) {
                    Toast.makeText(CurrentTaskActivity.this, "Please Select Status", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etRemark.getText().toString().equals("")) {
                    Toast.makeText(CurrentTaskActivity.this, "Please Enter Remark", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    updateTask(taskEntity, id, etRemark.getText().toString());
                    alertDialog.dismiss();
                }

            }
        });
        alertDialog.setCancelable(true);
        alertDialog.show();
        //   alertDialog.getWindow().setLayout(900, 600);

        // for user define height and width..
    }


    private void updateTask(TaskEntity taskEntity, int Staus, String Remark) {
        showDialog();
        new ProductController(this).updateTask(taskEntity.getId(), loginEntity.getUser_id(), Staus, Remark, this);
    }

    //endregion



}
