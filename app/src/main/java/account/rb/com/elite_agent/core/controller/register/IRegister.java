package account.rb.com.elite_agent.core.controller.register;

import account.rb.com.elite_agent.core.IResponseSubcriber;
import account.rb.com.elite_agent.core.requestmodel.AddUserRequestEntity;
import account.rb.com.elite_agent.core.requestmodel.RegisterRequest;
import account.rb.com.elite_agent.core.requestmodel.UpdateUserRequestEntity;

/**
 * Created by Rajeev Ranjan on 02/02/2018.
 */

public interface IRegister {
    void getOtp(String email, String mobile, String ip, IResponseSubcriber iResponseSubcriber);

    void addUser(AddUserRequestEntity addUserRequestEntity, IResponseSubcriber iResponseSubcriber);

    void getCityState(String pincode, IResponseSubcriber iResponseSubcriber);

    void updateUser(UpdateUserRequestEntity updateUserRequestEntity, IResponseSubcriber iResponseSubcriber);

    void getLogin(String mobile, String password, String device_token,String device_id, IResponseSubcriber iResponseSubcriber);

    void changePassword(String mobile, String curr_password, String new_password,  IResponseSubcriber iResponseSubcriber);

    void forgotPassword(String mobile, IResponseSubcriber iResponseSubcriber);

    void saveUserRegistration(RegisterRequest registerRequest, IResponseSubcriber iResponseSubcriber);

    void verifyOTPTegistration(String email, String mobile, String ip, IResponseSubcriber iResponseSubcriber);

    void getIFSC(String IfscCode, IResponseSubcriber iResponseSubcriber);

}
