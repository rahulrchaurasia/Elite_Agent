package account.rb.com.elite_agent.core.controller.product;

import android.content.Context;

import account.rb.com.elite_agent.core.IResponseSubcriber;
import account.rb.com.elite_agent.core.requestbuilder.ProductRequestBuilder;
import account.rb.com.elite_agent.core.response.AgentCommonResponse;
import account.rb.com.elite_agent.core.response.CommonResponse;
import account.rb.com.elite_agent.core.response.OrderResponse;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;

import account.rb.com.elite_agent.core.response.OrderSummaryResponse;
import account.rb.com.elite_agent.core.response.TaskDetailResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rajeev Ranjan on 02/02/2018.
 */

public class ProductController implements IProduct {

    ProductRequestBuilder.ProductNetworkService productNetworkService;
    Context mContext;
    IResponseSubcriber iResponseSubcriber;

    public ProductController(Context context) {
        productNetworkService = new ProductRequestBuilder().getService();
        mContext = context;
    }


    @Override
    public void inserOrderData(int prodid, int userid, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("prodid", String.valueOf(prodid));
        body.put("userid", String.valueOf(userid));

        productNetworkService.insertOrder(body).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.body() != null) {

                    if (response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }
                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });

    }

    @Override
    public void taskDetail(int user_id, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("userid", String.valueOf(user_id));

        productNetworkService.taskDetail(body).enqueue(new Callback<TaskDetailResponse>() {
            @Override
            public void onResponse(Call<TaskDetailResponse> call, Response<TaskDetailResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<TaskDetailResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });

    }  //taskDetail

    @Override
    public void pendingTaskDetail(int agent_id, int status_id, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("agent_id", String.valueOf(agent_id));
        body.put("status_id", String.valueOf(status_id));

        productNetworkService.pendingtaskDetail(body).enqueue(new Callback<TaskDetailResponse>() {
            @Override
            public void onResponse(Call<TaskDetailResponse> call, Response<TaskDetailResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<TaskDetailResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });

    } // pendingTaskDetail

    @Override
    public void updateTask(int orderID, int agent_id, int order_status, String Remark, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("order_id", String.valueOf(orderID));
        body.put("agent_id", String.valueOf(agent_id));
        body.put("order_status", String.valueOf(order_status));
        body.put("order_remark", String.valueOf(Remark));


        productNetworkService.updateTask(body).enqueue(new Callback<AgentCommonResponse>() {
            @Override
            public void onResponse(Call<AgentCommonResponse> call, Response<AgentCommonResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<AgentCommonResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void updatePendongTask(int orderID, int agent_id, int accept_status, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("order_id", String.valueOf(orderID));
        body.put("agent_id", String.valueOf(agent_id));
        body.put("agent_accept_status", String.valueOf(accept_status));


        productNetworkService.updatePendingTask(body).enqueue(new Callback<AgentCommonResponse>() {
            @Override
            public void onResponse(Call<AgentCommonResponse> call, Response<AgentCommonResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<AgentCommonResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void orderSummary(int agent_id, final IResponseSubcriber iResponseSubcriber) {

        HashMap<String, String> body = new HashMap<>();

        body.put("agent_id", String.valueOf(agent_id));

        productNetworkService.orderSummary(body).enqueue(new Callback<OrderSummaryResponse>() {
            @Override
            public void onResponse(Call<OrderSummaryResponse> call, Response<OrderSummaryResponse> response) {
                if (response.body() != null) {

                    if (response.body().getStatus_code() == 0) {
                        //callback of data
                        iResponseSubcriber.OnSuccess(response.body(), response.body().getMessage());
                    } else {
                        //failure
                        iResponseSubcriber.OnFailure(new RuntimeException(response.body().getMessage()));
                    }

                } else {
                    //failure
                    iResponseSubcriber.OnFailure(new RuntimeException("Enable to reach server, Try again later"));
                }
            }

            @Override
            public void onFailure(Call<OrderSummaryResponse> call, Throwable t) {
                if (t instanceof ConnectException) {
                    iResponseSubcriber.OnFailure(t);
                } else if (t instanceof SocketTimeoutException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof UnknownHostException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Check your internet connection"));
                } else if (t instanceof NumberFormatException) {
                    iResponseSubcriber.OnFailure(new RuntimeException("Unexpected server response"));
                } else {
                    iResponseSubcriber.OnFailure(new RuntimeException(t.getMessage()));
                }
            }
        });
    }

    //insertOrder
}
