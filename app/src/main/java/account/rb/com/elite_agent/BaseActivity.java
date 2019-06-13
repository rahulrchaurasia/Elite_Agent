package account.rb.com.elite_agent;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import account.rb.com.elite_agent.utility.Utility;


/**
 * Created by Rohit on 12/12/15.
 */
public class BaseActivity extends AppCompatActivity {

    //public Realm realm;
    ProgressDialog dialog;
    CustomPopUpListener customPopUpListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Realm
        //Realm.init(this);
        // Get a Realm instance for this thread
       // realm = Realm.getDefaultInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void cancelDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    protected void showDialog() {
//        dialog = ProgressDialog.show(BaseActivity.this, "", "Loading...", true);
        showDialog("Loading...");
    }

    protected void showDialog(String msg) {
        dialog = ProgressDialog.show(BaseActivity.this, "", msg, true);
    }

    public static boolean isValidePhoneNumber(EditText editText) {
        String phoneNumberPattern = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
        String phoneNumberEntered = editText.getText().toString().trim();
        return !(phoneNumberEntered.isEmpty() || !phoneNumberEntered.matches(phoneNumberPattern));
    }

    public static boolean isValideEmailID(EditText editText) {
        String emailEntered = editText.getText().toString().trim();
        return !(emailEntered.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailEntered).matches());
    }

    public static boolean isEmpty(EditText editText) {
        String text = editText.getText().toString().trim();
        return !(text.isEmpty());
    }

    public void getCustomToast(String strMessage) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.layout_custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));


        TextView text = (TextView) layout.findViewById(R.id.txtMessage);
        text.setText("" + strMessage);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void openPopUp(final View view, String title, String desc, String positiveButtonName, String negativeButtonName, boolean isNegativeVisible, boolean isCancelable) {
        try {
            final Dialog dialog;
            dialog = new Dialog(BaseActivity.this, R.style.CustomDialog);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_common_popup);

            TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
            tvTitle.setText(title);
            TextView tvOk = (TextView) dialog.findViewById(R.id.tvOk);
            tvOk.setText(positiveButtonName);

            TextView tvCancel = (TextView) dialog.findViewById(R.id.tvCancel);
            tvCancel.setText(negativeButtonName);
            if (isNegativeVisible) {
                tvCancel.setVisibility(View.VISIBLE);
            } else {
                tvCancel.setVisibility(View.GONE);
            }

            TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
            txtMessage.setText(desc);
            ImageView ivCross = (ImageView) dialog.findViewById(R.id.ivCross);

            dialog.setCancelable(isCancelable);
            dialog.setCanceledOnTouchOutside(isCancelable);

            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = lp.MATCH_PARENT;  // Width
            lp.height = lp.WRAP_CONTENT; // Height
            dialogWindow.setAttributes(lp);

            dialog.show();
            tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog
                    if (customPopUpListener != null)
                        customPopUpListener.onPositiveButtonClick(dialog, view);
                }
            });

            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog
                    if (customPopUpListener != null)
                        customPopUpListener.onCancelButtonClick(dialog, view);
                }
            });
            ivCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog
                    if (customPopUpListener != null)
                        customPopUpListener.onCancelButtonClick(dialog, view);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // region CustomPopup

    public interface CustomPopUpListener {

        void onPositiveButtonClick(Dialog dialog, View view);

        void onCancelButtonClick(Dialog dialog, View view);

    }

    public void registerCustomPopUp(CustomPopUpListener customPopUpListener) {
        if (customPopUpListener != null)
            this.customPopUpListener = customPopUpListener;
    }

    //endregion

    public void composeEmail(String addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{addresses});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        startActivity(Intent.createChooser(intent, "Email via..."));
    }

    public File createFile(String name) {
        FileOutputStream outStream = null;

        File dir = Utility.createDirIfNotExists();
        String fileName = name + ".jpg";
        fileName = fileName.replaceAll("\\s+", "");
        File outFile = new File(dir, fileName);

        return outFile;
    }

    public File saveImageToStorage(Bitmap bitmap, String name) {
        FileOutputStream outStream = null;

        File dir = Utility.createDirIfNotExists();
        String fileName = name + ".jpg";
        fileName = fileName.replaceAll("\\s+", "");
        File outFile = new File(dir, fileName);
        try {
            outStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outFile;
    }

    public File savePdfToStorage(Bitmap bitmap, String name) {
        FileOutputStream outStream = null;

        File dir = Utility.createDirIfNotExists();
        String fileName = name + ".pdf";
        fileName = fileName.replaceAll("\\s+", "");
        File outFile = new File(dir, fileName);
        try {
            outStream = new FileOutputStream(outFile);

            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outFile;
    }

    public void DownloadFile(String fileURL, File directory) {
        try {

            FileOutputStream f = new FileOutputStream(directory);
            URL u = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            InputStream in = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showPdf(File file) {
        try {
//            Uri selectedUri = FileProvider.getUriForFile(this,
//                    this.getString(R.string.file_provider_authority),
//                    new File(Environment.getExternalStorageDirectory() + "/MTC Report/" + FileName + ".pdf"));

            Uri selectedUri = FileProvider.getUriForFile(this,
                    this.getString(R.string.file_provider_authority), file);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(selectedUri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            this.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class DownloadFromUrl extends AsyncTask<Void, Void, Void> {


        File dir;
        File outFile;
        String fileURL;
        String fileName;

        public DownloadFromUrl(String imgURL, String tempfileName) {
            showDialog();
            fileURL = imgURL;
            dir = Utility.createDirIfNotExists();
            fileName = tempfileName + ".pdf";
            fileName = fileName.replaceAll("\\s+", "");

        }


        @Override
        protected Void doInBackground(Void... voids) {

            outFile = new File(dir, fileName);

            try {
                outFile.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            DownloadFile(fileURL, outFile);
            return null;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            cancelDialog();
            showPdf(outFile);
        }
    }



}
