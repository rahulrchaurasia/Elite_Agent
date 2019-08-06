package account.rb.com.elite_agent.taskDetail;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import account.rb.com.elite_agent.BaseFragment;
import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.IResponseSubcriber;
import account.rb.com.elite_agent.core.controller.product.ProductController;
import account.rb.com.elite_agent.core.model.TaskEntity;
import account.rb.com.elite_agent.core.model.UserEntity;
import account.rb.com.elite_agent.core.response.AgentCommonResponse;
import account.rb.com.elite_agent.core.response.TaskDetailResponse;
import account.rb.com.elite_agent.database.DataBaseController;
import account.rb.com.elite_agent.splash.PrefManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends BaseFragment implements IResponseSubcriber {


    UserEntity loginEntity;
    DataBaseController dataBaseController;
    PrefManager prefManager;

    RecyclerView rvOrderDtl;
    List<TaskEntity> lsTaskDetail;
    TaskDetailAdapter mAdapter;
    List<String> statuslist;

    public TaskDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_task_detailragment, container, false);
        initilize(view);
        dataBaseController = new DataBaseController(getActivity());
        prefManager = new PrefManager(getActivity());
        loginEntity = prefManager.getUserData();

        loginEntity = prefManager.getUserData();

        statuslist = dataBaseController.getOrderStatusList();
        statuslist.add(0, "Select");

        showDialog();
        new ProductController(getContext()).taskDetail(loginEntity.getUser_id(),  this);

        return view;
    }

    private void initilize(View view) {
        rvOrderDtl = (RecyclerView) view.findViewById(R.id.rvOrderDtl);
        rvOrderDtl.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
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
               // mAdapter = new TaskDetailAdapter(TaskDetailFragment.this, lsTaskDetail);
                rvOrderDtl.setAdapter(mAdapter);

            } else {
                rvOrderDtl.setAdapter(null);
            }
        } else if (response instanceof AgentCommonResponse) {
            if (response.getStatus_code() == 0) {

                Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();

                showDialog();
                new ProductController(getContext()).taskDetail(loginEntity.getUser_id(),  this);



            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }


    public void redirectToTask(TaskEntity taskEntity) {
        uPdatePopUp(taskEntity);
    }

    private void uPdatePopUp(final TaskEntity taskEntity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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

        statusAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, statuslist);
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

                if(id == 0)
                {
                    Toast.makeText(getActivity(), "Please Select Status", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if( etRemark.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Please Enter Remark", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

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
        new ProductController(getContext()).updateTask(taskEntity.getId(), loginEntity.getUser_id(), Staus,Remark, this);
    }

}
