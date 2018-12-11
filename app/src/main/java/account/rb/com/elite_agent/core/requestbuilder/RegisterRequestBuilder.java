package account.rb.com.elite_agent.core.requestbuilder;

import account.rb.com.elite_agent.core.RetroRequestBuilder;
import account.rb.com.elite_agent.core.requestmodel.AddUserRequestEntity;
import account.rb.com.elite_agent.core.requestmodel.RegisterRequest;
import account.rb.com.elite_agent.core.requestmodel.UpdateUserRequestEntity;
import account.rb.com.elite_agent.core.response.AddUserResponse;
import account.rb.com.elite_agent.core.response.CommonResponse;
import account.rb.com.elite_agent.core.response.GetOtpResponse;
import account.rb.com.elite_agent.core.response.IfscCodeResponse;
import account.rb.com.elite_agent.core.response.LoginResponse;
import account.rb.com.elite_agent.core.response.PincodeResponse;
import account.rb.com.elite_agent.core.response.UpdateUserResponse;

import java.util.HashMap;

import account.rb.com.elite_agent.core.response.UserRegistrationResponse;
import account.rb.com.elite_agent.core.response.VerifyUserRegisterResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class RegisterRequestBuilder extends RetroRequestBuilder {

    public RegisterRequestBuilder.RegisterQuotesNetworkService getService() {

        return super.build().create(RegisterRequestBuilder.RegisterQuotesNetworkService.class);
    }

    public interface RegisterQuotesNetworkService {

        @POST("/api/otp-insert")
        Call<GetOtpResponse> getOtp(@Body HashMap<String, String> body);

        @POST("/api/add-user")
        Call<AddUserResponse> addUser(@Body AddUserRequestEntity body);

        @POST("/api/login")
        Call<LoginResponse> getLogin(@Body HashMap<String, String> body);

        @POST("/api/add-by-pincode")
        Call<PincodeResponse> getCity(@Body HashMap<String, String> body);

        @POST("/api/update-user")
        Call<UpdateUserResponse> updateUser(@Body UpdateUserRequestEntity updateUserRequestEntity);

        @POST("/api/change-password")
        Call<CommonResponse> changePassword(@Body HashMap<String, String> body);

        @POST("/api/forgot-password")
        Call<CommonResponse> forgotPassword(@Body HashMap<String, String> body);


        @POST("/api/agent-otp-verify")
        Call<UserRegistrationResponse> userRegistration(@Body RegisterRequest registerRequest);

        @POST("/api/get-ifsc-code")
        Call<IfscCodeResponse> getIfscCode(@Body HashMap<String, String> body);

        @POST("/api/check-agent-registration")
        Call<VerifyUserRegisterResponse> verifyUserRegistration(@Body HashMap<String, String> body);




    }
}