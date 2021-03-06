package account.rb.com.elite_agent.taskDetail;

import android.app.Activity;
import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.model.TaskEntity;

/**
 * Created by IN-RB on 06-02-2018.
 */

public class TaskDetailAdapter extends RecyclerView.Adapter<TaskDetailAdapter.TaskDetailItem> {

    Activity mContext;
    List<TaskEntity> lsTaskDetail;

    public TaskDetailAdapter(Activity mContext, List<TaskEntity> lsTaskDetail) {
        this.mContext = mContext;
        this.lsTaskDetail = lsTaskDetail;
    }

    public class TaskDetailItem extends RecyclerView.ViewHolder {

        TextView txtViewDoc, txtCustName, txtProduct, txtOrderId,txtTat,
                txtQty, txtRate, txtAmount,
                txtBank, txtMobile, txtPaymentStatus, txtOrderStatus,
                txtEmail, txtDate, txtRemark ;
        Button btnUploadFile;

        LinearLayout lyParent;
        RelativeLayout RlComment;

        public TaskDetailItem(View itemView) {
            super(itemView);
            RlComment = (RelativeLayout) itemView.findViewById(R.id.RlComment);

            lyParent = (LinearLayout) itemView.findViewById(R.id.lyParent);

            btnUploadFile = (Button) itemView.findViewById(R.id.btnUploadFile);

            txtViewDoc  = (TextView) itemView.findViewById(R.id.txtViewDoc);
            txtCustName = (TextView) itemView.findViewById(R.id.txtCustName);
            txtProduct = (TextView) itemView.findViewById(R.id.txtProduct);
            txtOrderId = (TextView) itemView.findViewById(R.id.txtOrderId);
            txtTat  = (TextView) itemView.findViewById(R.id.txtTat);

            txtQty = (TextView) itemView.findViewById(R.id.txtQty);
            txtRate = (TextView) itemView.findViewById(R.id.txtRate);
            txtAmount = (TextView) itemView.findViewById(R.id.txtAmount);

            txtBank = (TextView) itemView.findViewById(R.id.txtBank);
            txtMobile = (TextView) itemView.findViewById(R.id.txtMobile);
            txtPaymentStatus = (TextView) itemView.findViewById(R.id.txtPaymentStatus);
            txtOrderStatus = (TextView) itemView.findViewById(R.id.txtOrderStatus);

            txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtRemark = (TextView) itemView.findViewById(R.id.txtRemark);

        }
    }

    @Override
    public TaskDetailAdapter.TaskDetailItem onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_taskdtl_item, parent, false);

        return new TaskDetailAdapter.TaskDetailItem(itemView);

    }

    @Override
    public void onBindViewHolder(TaskDetailItem holder, int position) {

        final TaskEntity taskEntity = lsTaskDetail.get(position);

        holder.txtCustName.setText("" + taskEntity.getCust_name());
        holder.txtProduct.setText("" + taskEntity.getProduct_name());
        holder.txtOrderId.setText("" + taskEntity.getDisplay_request_id());
        holder.txtTat.setText("" + taskEntity.getTat());

        holder.txtQty.setText("" + taskEntity.getQuantity());
        holder.txtRate.setText("" + taskEntity.getRate());
        holder.txtAmount.setText("" + "\u20B9" + " " + taskEntity.getAmount());

        holder.txtBank.setText("" + taskEntity.getBank_name());
        holder.txtMobile.setText("" + taskEntity.getMobile());
        holder.txtPaymentStatus.setText("" + taskEntity.getPayment_status());
        holder.txtOrderStatus.setText("" + taskEntity.getOrder_status());

        holder.txtEmail.setText("" + taskEntity.getEmail());
        holder.txtDate.setText("" + taskEntity.getPayment_date());
        holder.txtRemark.setText("" + taskEntity.getPayment_remark());

        if (taskEntity.getStatus_id().equals("1")) {
            holder.txtOrderStatus.setTextColor(Color.parseColor("#009EE3"));
        } else if (taskEntity.getStatus_id().equals("2")) {
            holder.txtOrderStatus.setTextColor(Color.parseColor("#212121"));
        } else if (taskEntity.getStatus_id().equals("3")) {
            holder.txtOrderStatus.setTextColor(Color.parseColor("#42ceb2"));
        } else if (taskEntity.getStatus_id().equals("4")) {
            holder.txtOrderStatus.setTextColor(Color.parseColor("#de6d75"));
        }



        holder.btnUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((CurrentTaskActivity) mContext).redirectToDocUpload(taskEntity.getId());
            }
        });
        holder.txtViewDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CurrentTaskActivity) mContext).getOrderId(taskEntity.getId());
            }
        });

        holder.RlComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CurrentTaskActivity) mContext).getOrderIdForComment(taskEntity.getId());
            }
        });


    }


    @Override
    public int getItemCount() {
        return lsTaskDetail.size();
    }
}
