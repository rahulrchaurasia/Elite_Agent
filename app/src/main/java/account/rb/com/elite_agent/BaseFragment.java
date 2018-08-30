package account.rb.com.elite_agent;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rohit on 17/01/16.
 */
public class BaseFragment extends Fragment {

    ProgressDialog dialog;

    public BaseFragment() {

    }

    protected void cancelDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    protected void showDialog() {
        showDialog("Loading...");
    }

    protected void showDialog(String msg) {
        dialog = ProgressDialog.show(getActivity(), "", msg, true);
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

    public static boolean validatePhoneNumber(EditText editText) {
        String phoneNumberPattern = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
        String phoneNumberEntered = editText.getText().toString().trim();
        if (phoneNumberEntered.isEmpty() || !phoneNumberEntered.matches(phoneNumberPattern)) {
            return false;
        }
        return true;
    }

    public static boolean isValidPan(String Pan) {
//        String rx = "/[A-Z]{5}[0-9]{4}[A-Z]{1}$/";
        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
        Matcher matcher = pattern.matcher(Pan);
        if (matcher.matches()) {
            return true;

        } else {
            return false;
        }
    }


}
