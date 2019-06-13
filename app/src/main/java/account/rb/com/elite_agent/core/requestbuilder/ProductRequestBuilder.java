package account.rb.com.elite_agent.core.requestbuilder;

import account.rb.com.elite_agent.core.RetroRequestBuilder;
import account.rb.com.elite_agent.core.response.AgentCommonResponse;
import account.rb.com.elite_agent.core.response.ChatResponse;
import account.rb.com.elite_agent.core.response.CommonResponse;
import account.rb.com.elite_agent.core.response.DocumentViewResponse;
import account.rb.com.elite_agent.core.response.NotificationResponse;
import account.rb.com.elite_agent.core.response.OrderResponse;

import java.util.HashMap;
import java.util.Map;

import account.rb.com.elite_agent.core.response.OrderSummaryResponse;
import account.rb.com.elite_agent.core.response.TaskDetailResponse;
import account.rb.com.elite_agent.core.response.UploadDocResponse;
import account.rb.com.elite_agent.core.response.ViewDocCommentResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public class ProductRequestBuilder extends RetroRequestBuilder {

    public ProductRequestBuilder.ProductNetworkService getService() {

        return super.build().create(ProductRequestBuilder.ProductNetworkService.class);
    }

    public interface ProductNetworkService {

        @POST("/api/insertorder")
        Call<OrderResponse> insertOrder(@Body HashMap<String, String> body);

        @POST("/api/get-tasklist")
        Call<TaskDetailResponse> taskDetail(@Body HashMap<String, String> body);

        @POST("/api/agent-order-detail")
        Call<TaskDetailResponse> pendingtaskDetail(@Body HashMap<String, String> body);

        @POST("/api/update-order-status")
        Call<AgentCommonResponse> updateTask(@Body HashMap<String, String> body);

        @POST("/api/agentaccept-status-update")
        Call<AgentCommonResponse> updatePendingTask(@Body HashMap<String, String> body);

        @POST("/api/getincome")
        Call<OrderSummaryResponse> orderSummary(@Body HashMap<String, String> body);


        @POST("/api/get-notification")
        Call<NotificationResponse> getNotification(@Body HashMap<String, String> body);   //used

        @POST("/api/get-order-document")
        Call<DocumentViewResponse> getDocumentView(@Body HashMap<String, String> body);   //used

        @POST("/api/save-request-comments")
        Call<ChatResponse> saveAgentChat(@Body HashMap<String, String> body);

        @POST("/api/get-request-comments")
        Call<ChatResponse> displayAgentChat(@Body HashMap<String, String> body);


  // Current Order : Upload
        @POST("/api/view-doc-comment")
        Call<ViewDocCommentResponse> viewDocComment(@Body HashMap<String, String> body);

        @POST("/api/save-doc-comment")
        Call<CommonResponse> saveDocComment(@Body HashMap<String, String> body);

        @Multipart
        @POST("/api/comment-doc-upload")
        Call<UploadDocResponse> uploadDocument(@Part() MultipartBody.Part doc, @PartMap() Map<String, Integer> partMap);  //used


    }
}