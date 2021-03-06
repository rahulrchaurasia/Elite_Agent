package account.rb.com.elite_agent.login;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import account.rb.com.elite_agent.BaseFragment;
import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.IResponseSubcriber;
import account.rb.com.elite_agent.core.controller.register.RegisterController;
import account.rb.com.elite_agent.core.model.UserEntity;
import account.rb.com.elite_agent.core.response.CommonResponse;
import account.rb.com.elite_agent.database.DataBaseController;
import account.rb.com.elite_agent.splash.PrefManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends BaseFragment implements View.OnClickListener ,IResponseSubcriber {


    EditText etoldpswd  , etnewpswd, etconfirmpswd;
    Button btnSubmit;

    DataBaseController dataBaseController;
    PrefManager prefManager;
    UserEntity loginEntity;
    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        initialize_Widgets(view);

        dataBaseController = new DataBaseController(getActivity());
        prefManager = new PrefManager(getActivity());
        loginEntity = prefManager.getUserData();
        return view;
    }

    private void initialize_Widgets(View view) {

        etoldpswd = (EditText) view.findViewById(R.id.etoldpswd);
        etnewpswd = (EditText) view.findViewById(R.id.etnewpswd);
        etconfirmpswd = (EditText) view.findViewById(R.id.etconfirmpswd);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(this);


    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof CommonResponse) {
            if (response.getStatus_code() == 0) {
                Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();

                clear();

            }
        }
    }

    private void clear()
    {
        etoldpswd.setText("");
        etnewpswd.setText("");
        etconfirmpswd.setText("");

    }
    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnSubmit)
        {

            if (etoldpswd.getText().toString().matches("")) {
                // Snackbar.make(etEmpCode, "Enter valid input", Snackbar.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "ENTER PASSWORD", Toast.LENGTH_SHORT).show();
                etoldpswd.requestFocus();
                //   etoldpswd.setError("Enter Password");
                return;
            }
            if (etnewpswd.getText().toString().matches("")) {
                // Snackbar.make(etEmpCode, "Enter valid input", Snackbar.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "Enter New Password", Toast.LENGTH_SHORT).show();
                etnewpswd.requestFocus();
//                etnewpswd.setError("Enter New Password");
                return;
            }
            if (etconfirmpswd.getText().toString().matches("")) {
                // Snackbar.make(etEmpCode, "Enter valid input", Snackbar.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "RE-Enter Password", Toast.LENGTH_SHORT).show();
                etconfirmpswd.requestFocus();
//                etconfirmpswd.setError("RE-Enter Password");
                return;
            }
            if (!etconfirmpswd.getText().toString().matches(etnewpswd.getText().toString())) {

                Toast.makeText(getActivity(), "Password Not Matched", Toast.LENGTH_SHORT).show();
                etconfirmpswd.requestFocus();
//                etconfirmpswd.setError("Password Not Matched");
                return;
            }

            showDialog();
            new RegisterController(getActivity()).changePassword(loginEntity.getMobile(), etoldpswd.getText().toString(), etnewpswd.getText().toString(), this);
        }


    }
}
