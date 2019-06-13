package account.rb.com.elite_agent.utility;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by IN-RB on 04-06-2018.
 */

public class Utility {


    public static String PUSH_BROADCAST_ACTION = "Finmart_Push_BroadCast_Action";
    public static String PUSH_NOTIFY = "notifyFlag";
    public static String PUSH_LOGIN_PAGE = "pushloginPage";


    public static MultipartBody.Part getMultipartImage(File file) {
        RequestBody imgBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part imgFile = MultipartBody.Part.createFormData("docfile", file.getName(), imgBody);
        return imgFile;
    }


    public static MultipartBody.Part getMultipartFile(File file) {
        RequestBody imgBody = RequestBody.create(MediaType.parse("file/*"), file);
        MultipartBody.Part imgFile = MultipartBody.Part.createFormData("docfile", file.getName(), imgBody);
        return imgFile;
    }


    public static HashMap<String, Integer> getBody(Context context, int orderid) {
        HashMap<String, Integer> body = new HashMap<String, Integer>();
        body.put("orderid", (orderid));


        return body;
    }

    public static File createDirIfNotExists() {

        File file = new File(Environment.getExternalStorageDirectory(), "/Elite-Agent");
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("TravellerLog :: ", "Problem creating Image folder");

            }
        }
        return file;
    }

}
