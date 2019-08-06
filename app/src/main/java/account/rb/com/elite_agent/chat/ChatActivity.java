package account.rb.com.elite_agent.chat;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import account.rb.com.elite_agent.BaseActivity;
import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.IResponseSubcriber;
import account.rb.com.elite_agent.core.controller.product.ProductController;
import account.rb.com.elite_agent.core.model.ChatEntity;
import account.rb.com.elite_agent.core.model.UserEntity;
import account.rb.com.elite_agent.core.response.ChatResponse;
import account.rb.com.elite_agent.splash.PrefManager;
import account.rb.com.elite_agent.utility.Constants;

public class ChatActivity extends BaseActivity implements IResponseSubcriber ,OnClickListener {


    UserEntity loginEntity;
    PrefManager prefManager;
    ImageButton imgbtnSend;
    EditText etComment;

    RecyclerView rvChat;
    List<ChatEntity> chatEntityList;
   // ChatAdapter mAdapter;
    int OrderID;

    TextWatcher commentTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (s.toString().trim().length() == 0) {
              imgbtnSend.setImageDrawable(null);

            }else{
                imgbtnSend.setImageDrawable(ContextCompat.getDrawable(ChatActivity.this, R.drawable.ic_send));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent().getExtras() != null) {
            OrderID = getIntent().getIntExtra("ORDER_ID", 0);
            showDialog();
            new ProductController(ChatActivity.this).displayAgentChat(OrderID, this);


        }
        prefManager = new PrefManager(this);
        loginEntity = prefManager.getUserData();
        prefManager.setNotificationCounter(0);
        initialize();

        setListener();


    }

    private void initialize() {

        imgbtnSend = findViewById(R.id.imgbtnSend);
        etComment =  findViewById(R.id.etComment);
        chatEntityList = new ArrayList<ChatEntity>();

        rvChat = (RecyclerView) findViewById(R.id.rvChat);
        rvChat.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChatActivity.this);
        rvChat.setLayoutManager(layoutManager);



    }
    private void setListener(){
        imgbtnSend.setOnClickListener(this);
        etComment.addTextChangedListener(commentTextWatcher);
    }



        @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof ChatResponse) {
            if (response.getStatus_code() == 0) {

               etComment.setText("");
                chatEntityList = ((ChatResponse) response).getData();
               // mAdapter = new ChatAdapter(ChatActivity.this, chatEntityList);
              //  rvChat.setAdapter(mAdapter);
                rvChat.scrollToPosition(chatEntityList.size() - 1);

            } else {
                rvChat.setAdapter(null);

                getCustomToast("No Data Available");
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        Constants.hideKeyBoard(view, ChatActivity.this);
        if(view.getId() == R.id.imgbtnSend)
        {
            if(etComment.getText().toString().trim().length()>0) {
                showDialog();
                new ProductController(this).saveAgentChat(OrderID, etComment.getText().toString(), this);
            }
        }
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
        finish();
        super.onBackPressed();
    }

}
