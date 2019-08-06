package account.rb.com.elite_agent.pendingDetail;


import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class PendingTaskDetailFragment extends BaseFragment implements IResponseSubcriber ,BaseFragment.CustomPopUpListener {


     // ********* NOT IN USED **********/////////
    UserEntity loginEntity;
    DataBaseController dataBaseController;

    RecyclerView rvOrderDtl;
    List<TaskEntity> lsTaskDetail;
    PendingTaskDetailAdapter mAdapter;
    List<String> statuslist;
    PrefManager prefManager;
    TaskEntity taskEntityMain;
    int acceptStausMain = 0 ;


    public PendingTaskDetailFragment() {
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

        statuslist = dataBaseController.getOrderStatusList();
        statuslist.add(0, "Select");

        registerCustomPopUp(this);
        return view;
    }

    private void initilize(View view) {
        rvOrderDtl = (RecyclerView) view.findViewById(R.id.rvOrderDtl);
        rvOrderDtl.setHasFixedSize(true);
        taskEntityMain = new TaskEntity();
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
               // mAdapter = new PendingTaskDetailAdapter(PendingTaskDetailFragment.this, lsTaskDetail);
                rvOrderDtl.setAdapter(mAdapter);

            } else {
                rvOrderDtl.setAdapter(null);
            }
        }else if(response instanceof AgentCommonResponse )
        {
            taskEntityMain = null;
            acceptStausMain = 0;
            Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();

            showDialog();
            new ProductController(getContext()).pendingTaskDetail(loginEntity.getUser_id(),0, this);

        }
    }

    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
        new ProductController(getContext()).updatePendongTask(taskEntity.getId(), loginEntity.getUser_id(), acceptStaus, this);
    }


    @Override
    public void onResume() {
        super.onResume();

        showDialog();
        new ProductController(getContext()).pendingTaskDetail(loginEntity.getUser_id(),0, this);

    }


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
}
