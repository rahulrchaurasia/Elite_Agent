package account.rb.com.elite_agent.Test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import account.rb.com.elite_agent.BaseActivity;
import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.IResponseSubcriber;
import account.rb.com.elite_agent.core.controller.test.TestController;
import account.rb.com.elite_agent.core.response.TestResponse;

public class TestActivity extends BaseActivity implements IResponseSubcriber {

    Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();

                new TestController(TestActivity.this).getLogin("9767701709","123456","2","0","0",TestActivity.this);
            }
        });

    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof TestResponse) {
            if (response.getStatus_code() == 0) {

                TestResponse.TestEntity testEntity = ((TestResponse) response).getResult();
                getCustomToast( "Name: " +testEntity.getUsername() + "\n Email ID: "+ testEntity.getEmail());
            }
        }
    }


    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
    }
}
