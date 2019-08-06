package account.rb.com.elite_agent.document;

import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import account.rb.com.elite_agent.BaseActivity;
import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.IResponseSubcriber;
import account.rb.com.elite_agent.core.controller.product.ProductController;
import account.rb.com.elite_agent.core.model.DocumentViewEntity;
import account.rb.com.elite_agent.core.model.UserEntity;
import account.rb.com.elite_agent.core.response.DocumentViewResponse;
import account.rb.com.elite_agent.database.DataBaseController;
import account.rb.com.elite_agent.splash.PrefManager;

public class DocUploadActivity extends BaseActivity implements IResponseSubcriber {



    int OrderID;


    private String DOC1 = "ELITE_DOC";

    ///////////
    DataBaseController dataBaseController;
    UserEntity loginEntity;

    TextView txtDocVerify;
    RecyclerView rvProduct;
    DocumentAdapter mAdapter;
    PrefManager prefManager;
    List<DocumentViewEntity> lstDoc;
    DocumentViewEntity documentViewEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (getIntent().getExtras() != null) {
            OrderID = getIntent().getIntExtra("ORDER_ID", 0);

            if (OrderID != 0) {
                showDialog();
                new ProductController(this).getDocumentView(String.valueOf(OrderID), DocUploadActivity.this);
            }
        }

        prefManager = new PrefManager(this);
        loginEntity = prefManager.getUserData();
        initialize();
    }



    private void initialize() {


        rvProduct = (RecyclerView) findViewById(R.id.rvProduct);
        rvProduct.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DocUploadActivity.this);
        rvProduct.setLayoutManager(layoutManager);

    }



    private void viewUploadFile(String url) {

        if (url.equals("")) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(DocUploadActivity.this);

        // TouchImageView ivDocFile;
        ImageView ivDocFile;
        Button btnClose;
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView;

        dialogView = inflater.inflate(R.layout.layout_view_doc, null);


        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        btnClose = (Button) dialogView.findViewById(R.id.btnClose);
//        ivDocFile = (TouchImageView) dialogView.findViewById(R.id.ivDocFile);
        ivDocFile = (ImageView) dialogView.findViewById(R.id.ivDocFile);

        Glide.with(this)
                .load(url)

//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ivDocFile);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        alertDialog.setCancelable(false);
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation;
        alertDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);


    }




    public void getActionView(DocumentViewEntity entity) {
        documentViewEntity = entity;

        viewUploadFile(entity.getPath());

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


    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof DocumentViewResponse) {
            if (response.getStatus_code() == 0) {

                lstDoc = ((DocumentViewResponse) response).getData();
                mAdapter = new DocumentAdapter(DocUploadActivity.this, lstDoc);
                rvProduct.setAdapter(mAdapter);



            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
