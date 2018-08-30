package account.rb.com.elite_agent.taskDetail;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.model.TaskEntity;

/**
 * Created by IN-RB on 06-02-2018.
 */

public class TaskDetailAdapter extends RecyclerView.Adapter<TaskDetailAdapter.TaskDetailItem> {

    Fragment mContext;
    List<TaskEntity> lsTaskDetail;

    public TaskDetailAdapter(Fragment mContext, List<TaskEntity> lsTaskDetail) {
        this.mContext = mContext;
        this.lsTaskDetail = lsTaskDetail;
    }

    public class TaskDetailItem extends RecyclerView.ViewHolder {

        TextView txtCustName, txtProduct, txtOrderID,
                txtQty, txtRate, txtAmount,
                txtBank, txtMobile, txtPaymentStatus, txtOrderStatus,
                txtEmail, txtDate, txtRemark;
        Button btnUpdate;
        LinearLayout lyParent;

        public TaskDetailItem(View itemView) {
            super(itemView);

            lyParent = (LinearLayout) itemView.findViewById(R.id.lyParent);
            btnUpdate = (Button) itemView.findViewById(R.id.btnUpdate);
            txtCustName = (TextView) itemView.findViewById(R.id.txtCustName);
            txtProduct = (TextView) itemView.findViewById(R.id.txtProduct);
            txtOrderID = (TextView) itemView.findViewById(R.id.txtOrderID);


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
        holder.txtOrderID.setText("" + taskEntity.getId());

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

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((TaskDetailFragment) mContext).redirectToTask(taskEntity);
            }
        });




    }


    @Override
    public int getItemCount() {
        return lsTaskDetail.size();
    }
}
