package account.rb.com.elite_agent.taskDetail.docUpload;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

import account.rb.com.elite_agent.BaseActivity;
import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.APIResponse;
import account.rb.com.elite_agent.core.IResponseSubcriber;
import account.rb.com.elite_agent.core.controller.product.ProductController;
import account.rb.com.elite_agent.core.model.DocUploadEntity;
import account.rb.com.elite_agent.core.model.UserEntity;
import account.rb.com.elite_agent.core.response.CommonResponse;
import account.rb.com.elite_agent.core.response.UploadDocResponse;
import account.rb.com.elite_agent.splash.PrefManager;
import account.rb.com.elite_agent.utility.CircleTransform;
import account.rb.com.elite_agent.utility.Constants;
import account.rb.com.elite_agent.utility.Utility;
import okhttp3.MultipartBody;

public class UploadPopUpActivity extends BaseActivity implements IResponseSubcriber, View.OnClickListener, BaseActivity.CustomPopUpListener {


    private static final int CAMERA_REQUEST = 1888;
    private static final int SELECT_PICTURE = 1800;
    private int PICK_PDF_REQUEST = 1805;

    File Docfile;
    File file;
    Uri imageUri;
    private Uri cropImageUri;    // for Crop
    Uri selectedImageUri;      // for File and Gallery
    InputStream inputStream;
    ExifInterface ei;
    Bitmap bitmapPhoto = null;
    private String PHOTO_File = "EliteDoc";
    MultipartBody.Part part;
    HashMap<String, Integer> body;
    private int PROFILE = 1;
    int RequestID = 0;
    String DOC_PATH = "";


    ///////////

    UserEntity loginEntity;
    PrefManager prefManager;

    Button btnSubmit;
    EditText etComment;
    ImageView ivUser, ivCross, ivProfile;
    String ORDER_ID = "";
    DocUploadEntity docUploadEntity;
    String[] perms = {
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pop_up);
        this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFinishOnTouchOutside(false);


        registerCustomPopUp(this);
        prefManager = new PrefManager(this);
        loginEntity = prefManager.getUserData();
        initilize();
        setOnClickListener();

        if (getIntent().getStringExtra("ORDER_ID") != null) {
            ORDER_ID = getIntent().getStringExtra("ORDER_ID");
        } else {

            Toast.makeText(this, "Something went wrong on Server Side", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    private void initilize() {


        btnSubmit = findViewById(R.id.btn_submit);
        ivProfile = findViewById(R.id.ivProfile);
        ivUser = findViewById(R.id.ivUser);
        ivCross = findViewById(R.id.ivCross);
        etComment = findViewById(R.id.et_Comment);

    }

    private void setOnClickListener() {
        btnSubmit.setOnClickListener(this);
        ivProfile.setOnClickListener(this);
        ivCross.setOnClickListener(this);
    }

    private void galleryCamPopUp() {

        if (!checkPermission()) {

            if (checkRationalePermission()) {
                //Show Information about why you need the permission
                requestPermission();

            } else {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission

                //  permissionAlert(navigationView,"Need Call Permission","This app needs Call permission.");
                openPopUp(ivUser, "Need  Permission", "This app needs all permissions.", "GRANT", "", false, true);


            }
        } else {

            showCamerGalleryPopUp();
        }
    }

    // region permission
    private boolean checkPermission() {

        int camera = ActivityCompat.checkSelfPermission(getApplicationContext(), perms[0]);

        int WRITE_EXTERNAL = ActivityCompat.checkSelfPermission(getApplicationContext(), perms[1]);
        int READ_EXTERNAL = ActivityCompat.checkSelfPermission(getApplicationContext(), perms[2]);

        return camera == PackageManager.PERMISSION_GRANTED
                && WRITE_EXTERNAL == PackageManager.PERMISSION_GRANTED
                && READ_EXTERNAL == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkRationalePermission() {

        boolean camera = ActivityCompat.shouldShowRequestPermissionRationale(UploadPopUpActivity.this, perms[0]);

        boolean write_external = ActivityCompat.shouldShowRequestPermissionRationale(UploadPopUpActivity.this, perms[1]);
        boolean read_external = ActivityCompat.shouldShowRequestPermissionRationale(UploadPopUpActivity.this, perms[2]);

        return camera || write_external || read_external;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, perms, Constants.PERMISSION_CAMERA_STORACGE_CONSTANT);
    }

    private void setDocumentUpload(DocUploadEntity docUploadEntity) {

        DOC_PATH = docUploadEntity.getPath();

        if (docUploadEntity.getType().toLowerCase().equals("pdf")) {

            ivUser.setBackground(ContextCompat.getDrawable(UploadPopUpActivity.this, R.drawable.pdf_icon_red_bg));

        } else {
            Glide.with(UploadPopUpActivity.this)
                    .load(docUploadEntity.getPath())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.circle_placeholder)
                    .skipMemoryCache(true)
                    .override(120, 120)
                    .transform(new CircleTransform(UploadPopUpActivity.this)) // applying the image transformer
                    .into(ivUser);
        }


    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof UploadDocResponse) {
            if (response.getStatus_code() == 0) {

                Toast.makeText(this, response.getMessage(), Toast.LENGTH_LONG).show();
                docUploadEntity = ((UploadDocResponse) response).getData().get(0);
                setDocumentUpload(docUploadEntity);

            }
        } else if (response instanceof CommonResponse) {
            Toast.makeText(this, response.getMessage(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.putExtra("MESSAGE", "REFERECE");
            setResult(CurrentDocUploadActivity.REQUEST_CODE, intent);
            finish();
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        DOC_PATH = "";
        cancelDialog();
        Toast.makeText(this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_submit:

                if(DOC_PATH =="")
                {
                    Toast.makeText(this,"Please Upload the Document",Toast.LENGTH_SHORT).show();
                    return;
                }
               else if (!isEmpty(etComment)) {
                    etComment.requestFocus();
                    etComment.setError("Enter Comment");
                    return;
                }
                showDialog();
                new ProductController(this).saveDocComment(docUploadEntity.getId(), etComment.getText().toString(), this);


                break;

            case R.id.ivProfile:

                galleryCamPopUp();
                break;

            case R.id.ivCross:
                Intent intent = new Intent();
                intent.putExtra("MESSAGE", "REFERECE");
                setResult(CurrentDocUploadActivity.REQUEST_CODE, intent);
                finish();
                break;
        }
    }

    @Override
    public void onPositiveButtonClick(Dialog dialog, View view) {

        dialog.cancel();
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, Constants.REQUEST_PERMISSION_SETTING);
    }

    @Override
    public void onCancelButtonClick(Dialog dialog, View view) {

        dialog.cancel();
    }
    //endregion

    private void showCamerGalleryPopUp() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this, R.style.CustomDialog);

        LinearLayout lyCamera, lyGallery, lyPdf;
        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.layout_cam_gallery, null);

        builder.setView(dialogView);
        final android.support.v7.app.AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        lyCamera = (LinearLayout) dialogView.findViewById(R.id.lyCamera);
        lyGallery = (LinearLayout) dialogView.findViewById(R.id.lyGallery);
        lyPdf = (LinearLayout) dialogView.findViewById(R.id.lyPdf);

        lyCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
                alertDialog.dismiss();

            }
        });

        lyGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                alertDialog.dismiss();

            }
        });
        lyPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
                alertDialog.dismiss();

            }
        });
        alertDialog.setCancelable(true);
        alertDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);

        // for user define height and width..
    }


    private void launchCamera() {


        String FileName = "PHOTO_File";


        Docfile = createFile(FileName);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            imageUri = Uri.fromFile(Docfile);
        } else {
            imageUri = FileProvider.getUriForFile(UploadPopUpActivity.this,
                    getString(R.string.file_provider_authority), Docfile);
        }


        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                imageUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }


    private void openGallery() {

        String FileName = "PHOTO_File";


        Docfile = createFile(FileName);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    private void showFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("application/pdf");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);

        // Create the ACTION_GET_CONTENT Intent
        Intent getContentIntent = FileUtils.createGetContentIntent();    // Only For PDF Pls check createGetContentIntent() method

        Intent intent = Intent.createChooser(getContentIntent, "Select a file");
        startActivityForResult(intent, PICK_PDF_REQUEST);


        // Intent intent = Intent.createChooser(FileUtils)

//        new MaterialFilePicker()
//                .withActivity(this)
//                .withRequestCode(1)
//                .withFilter(Pattern.compile(".*\\.pdf$")) // Filtering files and directories by file name using regexp
//                .withFilterDirectories(true) // Set directories filterable (false by default)
//                .withHiddenFiles(true) // Show hidden files and folders
//                .start();
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }


    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Below For Cropping The Camera Image
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            //extractTextFromImage();
            startCropImageActivity(imageUri);
        }
        // Below For Cropping The Gallery Image
        else if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            selectedImageUri = data.getData();
            startCropImageActivity(selectedImageUri);
        } else if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            //  else if (requestCode == 1 && resultCode == RESULT_OK) {
            //  String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            // Do anything with file
            //  file = new File(data.getData().getPath());

            final Uri uri = data.getData();

            // Get the File path from the Uri
            String path = FileUtils.getPath(this, uri);
            // Alternatively, use FileUtils.getFile(Context, Uri)
            if (path != null && FileUtils.isLocal(path)) {
                file = new File(path);

                part = Utility.getMultipartFile(file);
                body = Utility.getBody(this, Integer.valueOf(ORDER_ID));

                showDialog();
                new ProductController(this).uploadDocument(part, body, this);
            } else {
                Toast.makeText(this, "File Path Not Found", Toast.LENGTH_SHORT).show();
            }


        }

        //region Below  handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);


            if (resultCode == RESULT_OK) {
                try {
                    cropImageUri = result.getUri();
                    Bitmap mphoto = null;
                    try {
                        mphoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), cropImageUri);
                        //  mphoto = getResizedBitmap(mphoto, 800);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    file = saveImageToStorage(mphoto, PHOTO_File);

                    part = Utility.getMultipartImage(file);
                    body = Utility.getBody(this, Integer.valueOf(ORDER_ID));

                    showDialog();
                    new ProductController(this).uploadDocument(part, body, this);

                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }

        //endregion


    }


}
