package account.rb.com.elite_agent.product;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import account.rb.com.elite_agent.BaseFragment;
import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.IResponseSubcriber;
import account.rb.com.elite_agent.core.model.UserEntity;
import account.rb.com.elite_agent.core.response.OrderResponse;
import account.rb.com.elite_agent.database.DataBaseController;
import account.rb.com.elite_agent.splash.PrefManager;


import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends BaseFragment implements View.OnClickListener, IResponseSubcriber {

    PrefManager prefManager;
   // Spinner spCategory, spSubCategory, spProduct;
    List<String> CategorylList, SubCategoryList, ProductList;
    ArrayAdapter<String> categoryAdapter, subCategoryAdapter, ProductAdapter;
    DataBaseController dataBaseController;
    UserEntity loginEntity;
    Button btnBooked;

    LinearLayout lvLogo;
    ImageView ivLogo,ivClientLogo;
    TextView txtpending,txtcompleted,txtwallet ,txtLossMoney;;
    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_product, container, false);

        View view = inflater.inflate(R.layout.content_product, container, false);
        initialize_Widgets(view);

        dataBaseController = new DataBaseController(getActivity());
        prefManager = new PrefManager(getActivity());
        loginEntity = prefManager.getUserData();


        return view;
    }

    private void initialize_Widgets(View view) {

     //   btnBooked = (Button) view.findViewById(R.id.btnBooked);


        txtpending = (TextView)view.findViewById(R.id.txtpending);
        txtcompleted  = (TextView)view.findViewById(R.id.txtcompleted);
        txtwallet = (TextView)view.findViewById(R.id.txtwallet);
        txtLossMoney =  (TextView)view.findViewById(R.id.txtLossMoney);

      //  lvLogo = (LinearLayout) view.findViewById(R.id.lvLogo);
     //   ivLogo  = (ImageView) view.findViewById(R.id.ivLogo );
     //   ivClientLogo  = (ImageView) view.findViewById(R.id.ivClientLogo );

        txtcompleted.setOnClickListener(this);
        txtpending.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.txtpending)
        {
            reqDocPopUp();
        }

        else  if (v.getId() == R.id.txtcompleted) {

            int CategoryID,SubCategoryID,prdID = 0;



            showDialog();
        //    new ProductController(getActivity()).inserOrderData(prdID, loginEntity.getUser_id(), this);




        }

    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof OrderResponse) {
            if (response.getStatus_code() == 0) {

                Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
            //    startActivity(new  Intent(getActivity(), PaymentActivity.class));
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void reqDocPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Button btnClose;

        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.layout_req_doc, null);

        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        btnClose = (Button) dialogView.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        alertDialog.setCancelable(false);
        alertDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);

    }
}
