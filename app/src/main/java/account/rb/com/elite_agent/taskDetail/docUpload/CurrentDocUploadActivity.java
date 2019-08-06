package account.rb.com.elite_agent.taskDetail.docUpload;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Pair;
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
import account.rb.com.elite_agent.core.model.DocViewEntity;
import account.rb.com.elite_agent.core.model.UserEntity;
import account.rb.com.elite_agent.core.response.ViewDocCommentResponse;
import account.rb.com.elite_agent.database.DataBaseController;
import account.rb.com.elite_agent.splash.PrefManager;

public class CurrentDocUploadActivity extends BaseActivity implements View.OnClickListener, IResponseSubcriber {


    FloatingActionButton btnAddFile;
    RecyclerView rvDocList;
    FloatingActionButton fab;
    List<DocViewEntity> viewEntityList;
    DocViewAdapter mAdapter;

    UserEntity loginEntity;
    DataBaseController dataBaseController;
    PrefManager prefManager;
    String ORDER_ID = "";
    static final int REQUEST_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_doc_upload);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getStringExtra("ORDER_ID") != null) {
            ORDER_ID = getIntent().getStringExtra("ORDER_ID");
        } else {

            Toast.makeText(this, "Something went wrong on Server Side", Toast.LENGTH_SHORT).show();
            finish();
        }

        viewEntityList = new ArrayList<>();
        initView();
        dataBaseController = new DataBaseController(this);
        prefManager = new PrefManager(this);
        loginEntity = prefManager.getUserData();


        FloatingActionButton fab = findViewById(R.id.btnAddFile);

        fab.setOnClickListener(this);


        initView();
        bindData();
    }

    private void initView() {


        btnAddFile = (FloatingActionButton) findViewById(R.id.btnAddFile);
        rvDocList = (RecyclerView) findViewById(R.id.rvDocList);
        rvDocList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvDocList.setLayoutManager(layoutManager);
        btnAddFile.setOnClickListener(this);

    }


    private void bindData() {
        showDialog();
        new ProductController(this).viewDocComment(ORDER_ID, this);

    }

    public void redirectToUploadDoc(View view, DocViewEntity entity) {

        if (entity.getType().toLowerCase().equals("pdf")) {
            new DownloadFromUrl(entity.getImagePath(), "EliteDoc").execute();

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Intent shareIntent = new Intent(CurrentDocUploadActivity.this, ViewFullImageActivity.class)
                        .putExtra("DOC_PATH", entity.getImagePath());
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(view, "profileTransition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(CurrentDocUploadActivity.this, pairs);

                startActivity(shareIntent, options.toBundle());
            } else {
                startActivity(new Intent(CurrentDocUploadActivity.this, ViewFullImageActivity.class)
                        .putExtra("DOC_PATH", entity.getImagePath()));
            }
        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnAddFile) {

            startActivityForResult(new Intent(CurrentDocUploadActivity.this, UploadPopUpActivity.class)
                    .putExtra("ORDER_ID", ORDER_ID), REQUEST_CODE);
        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof ViewDocCommentResponse) {
            if (response.getStatus_code() == 0) {

                //Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                viewEntityList = ((ViewDocCommentResponse) response).getData();
                mAdapter = new DocViewAdapter(this, viewEntityList);
                rvDocList.setAdapter(mAdapter);

            } else {
                rvDocList.setAdapter(null);
            }
        }

    }

    @Override
    public void OnFailure(Throwable t) {

        cancelDialog();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (data != null) {
                if (data.hasExtra("MESSAGE")) {
                    bindData();
                }
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
