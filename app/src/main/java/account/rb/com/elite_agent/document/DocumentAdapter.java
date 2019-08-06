package account.rb.com.elite_agent.document;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;



import java.util.List;

import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.model.DocumentViewEntity;

/**
 * Created by IN-RB on 09-08-2018.
 */

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocItem> {

    Activity mContext;
    List<DocumentViewEntity> lstDoc;

    public DocumentAdapter(Activity mContext, List<DocumentViewEntity> lstDoc) {
        this.mContext = mContext;
        this.lstDoc = lstDoc;
    }


    public class DocItem extends RecyclerView.ViewHolder {
        LinearLayout llDocumentUpload;
        TextView txtDOC, txtViewDoc;


        public DocItem(View itemView) {
            super(itemView);

            llDocumentUpload = (LinearLayout) itemView.findViewById(R.id.llDocumentUpload);

            txtDOC = (TextView) itemView.findViewById(R.id.txtDOC);


        }
    }

    @Override
    public DocumentAdapter.DocItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_doc_order_item, parent, false);

        return new DocumentAdapter.DocItem(itemView);
    }

    @Override
    public void onBindViewHolder(DocItem holder, int position) {

        final DocumentViewEntity entity = lstDoc.get(position);

        holder.txtDOC.setText("" + entity.getDocument_name());




        holder.llDocumentUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((DocUploadActivity) mContext).getActionView(entity);
            }
        });

    }


    @Override
    public int getItemCount() {
        return lstDoc.size();
    }


    public void updateList(DocumentViewEntity curEntity) {

        for (int pos = 0; pos < lstDoc.size(); pos++) {
            if (lstDoc.get(pos).getDoc_id() == (curEntity.getDoc_id())) {

                lstDoc.set(pos, curEntity);

            }
        }

        notifyDataSetChanged();

        //  refreshAdapter(lstSpecial);
    }


}
