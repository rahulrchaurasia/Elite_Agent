package account.rb.com.elite_agent.Dashboard;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import account.rb.com.elite_agent.BaseFragment;
import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.IResponseSubcriber;
import account.rb.com.elite_agent.core.controller.product.ProductController;
import account.rb.com.elite_agent.core.model.LoginEntity;
import account.rb.com.elite_agent.core.model.OrderSummaryEntity;
import account.rb.com.elite_agent.core.model.UserEntity;
import account.rb.com.elite_agent.core.response.OrderSummaryResponse;
import account.rb.com.elite_agent.database.DataBaseController;
import account.rb.com.elite_agent.pendingDetail.PendingTaskDetailFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends BaseFragment implements  View.OnClickListener,  IResponseSubcriber {

    TextView txtPending, txtCompleted, txtWallet, txtLoss;
    LinearLayout lyPending;

    DataBaseController dataBaseController;
    UserEntity loginEntity;

    public DashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        initilize(view);
        dataBaseController = new DataBaseController(getActivity());
        loginEntity = dataBaseController.getUserData();

        showDialog();
        new ProductController(getActivity()).orderSummary(loginEntity.getUser_id(), this);

        return view;
    }

    private void initilize(View view) {

        txtPending = (TextView) view.findViewById(R.id.txtPending);
        txtCompleted = (TextView) view.findViewById(R.id.txtCompleted);
        txtWallet = (TextView) view.findViewById(R.id.txtWallet);
        txtLoss = (TextView) view.findViewById(R.id.txtLoss);

        lyPending = (LinearLayout) view.findViewById(R.id.lyPending);

        lyPending.setOnClickListener(this);

    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof OrderSummaryResponse) {
            if (response.getStatus_code() == 0) {

                OrderSummaryEntity orderSummaryEntity =((OrderSummaryResponse) response).getData().get(0);


                txtPending.setText("" + orderSummaryEntity.getPending() );
                txtCompleted.setText(" "+orderSummaryEntity.getComplete() );
                txtWallet.setText("" +  "\u20B9" +" " +orderSummaryEntity.getWallet() );
                txtLoss.setText("" +  "\u20B9"  +orderSummaryEntity.getLost() );
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {

    }

//   fragment.getSupportActionBar().setTitle(title);

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.lyPending )
        {

            PendingTaskDetailFragment fragment = new PendingTaskDetailFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment, "Dashboard");

            fragmentTransaction.commit();
        }
    }
}
