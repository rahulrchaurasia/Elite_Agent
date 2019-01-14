package account.rb.com.elite_agent.core.requestbuilder;

import account.rb.com.elite_agent.core.RetroRequestBuilder;
import account.rb.com.elite_agent.core.response.AgentCommonResponse;
import account.rb.com.elite_agent.core.response.ChatResponse;
import account.rb.com.elite_agent.core.response.CommonResponse;
import account.rb.com.elite_agent.core.response.DocumentViewResponse;
import account.rb.com.elite_agent.core.response.NotificationResponse;
import account.rb.com.elite_agent.core.response.OrderResponse;

import java.util.HashMap;

import account.rb.com.elite_agent.core.response.OrderSummaryResponse;
import account.rb.com.elite_agent.core.response.TaskDetailResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

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

    }
}