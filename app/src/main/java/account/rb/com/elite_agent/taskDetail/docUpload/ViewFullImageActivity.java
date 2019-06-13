package account.rb.com.elite_agent.taskDetail.docUpload;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import account.rb.com.elite_agent.R;

public class ViewFullImageActivity extends AppCompatActivity {

    private  String DOC_PATH = "";
    ImageView ivUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full_image);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black_dark)));
        ivUser = findViewById(R.id.ivUser);

        if( getIntent().getStringExtra("DOC_PATH") != null)
        {
            DOC_PATH = getIntent().getStringExtra("DOC_PATH");
            bindImage();
        }
    }

    private void  bindImage(){
        Glide.with(ViewFullImageActivity .this)
                .load(DOC_PATH)
                .into(ivUser);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // finish();
        supportFinishAfterTransition();
        super.onBackPressed();
    }
}
