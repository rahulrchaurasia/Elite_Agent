package account.rb.com.elite_agent.core.controller.product;

import java.util.HashMap;

import account.rb.com.elite_agent.core.IResponseSubcriber;
import okhttp3.MultipartBody;

/**
 * Created by Rajeev Ranjan on 02/02/2018.
 */

public interface IProduct {


    void inserOrderData(int prodid, int userid, IResponseSubcriber iResponseSubcriber);

    void taskDetail(int user_id, IResponseSubcriber iResponseSubcriber);

    void pendingTaskDetail(int agent_id, int status_id, IResponseSubcriber iResponseSubcriber);

    void updateTask(int orderID ,int agent_id, int order_status, String Remark, IResponseSubcriber iResponseSubcriber);

    void updatePendongTask(int orderID ,int agent_id, int accept_status,  IResponseSubcriber iResponseSubcriber);

    void orderSummary( int agent_id, IResponseSubcriber iResponseSubcriber);

    void getNotifcation(int userid,String count, IResponseSubcriber iResponseSubcriber);

    void getDocumentView(String order_id, IResponseSubcriber iResponseSubcriber);

    void saveAgentChat( int request_id,String comments, IResponseSubcriber iResponseSubcriber);

    void displayAgentChat( int request_id, IResponseSubcriber iResponseSubcriber);

    void viewDocComment( String orderid, IResponseSubcriber iResponseSubcriber);

   void saveDocComment( int id, String comment, IResponseSubcriber iResponseSubcriber);

    void uploadDocument(MultipartBody.Part document, HashMap<String, Integer> body, final IResponseSubcriber iResponseSubcriber);

}
