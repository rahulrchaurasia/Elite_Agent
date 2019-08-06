package account.rb.com.elite_agent.taskDetail.docUpload;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;

import java.util.List;

import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.model.DocViewEntity;
import account.rb.com.elite_agent.utility.CircleTransform;

/**
 * Created by Rajeev Ranjan on 29/05/2019.
 */
public class DocViewAdapter extends RecyclerView.Adapter<DocViewAdapter.ViewItem> implements View.OnClickListener {

    Context context;
    List<DocViewEntity> viewEntityList;

    public DocViewAdapter(Context context, List<DocViewEntity> viewEntityList) {
        this.context = context;
        this.viewEntityList = viewEntityList;
    }

    @Override
    public void onClick(View v) {

    }

    public class ViewItem extends RecyclerView.ViewHolder {

        public ImageView ivUser;
        public ReadMoreTextView txtComment;
        public LinearLayout lyParent;

        public ViewItem(View itemView) {
            super(itemView);
            txtComment = itemView.findViewById(R.id.txtComment);
            lyParent = itemView.findViewById(R.id.lyParent);
            ivUser = itemView.findViewById(R.id.ivUser);
        }
    }

    @Override
    public DocViewAdapter.ViewItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_doc_item_view, parent, false);
        return new DocViewAdapter.ViewItem(itemView);

    }

    @Override
    public void onBindViewHolder(ViewItem holder, int position) {

        if (holder instanceof DocViewAdapter.ViewItem) {
            final DocViewEntity entity = viewEntityList.get(position);

            holder.txtComment.setText("" + entity.getComment());


            if (entity.getType().toLowerCase().trim().equalsIgnoreCase("pdf")) {


                holder.ivUser.setImageDrawable(context.getResources().getDrawable(R.drawable.pdf_icon_red_bg));


            } else {
                Glide.with(context)
                        .load(entity.getImagePath())
                        // .override(60, 60)
                        .transform(new CircleTransform(context))
                        .into(holder.ivUser);

            }


            holder.ivUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ((CurrentDocUploadActivity) context).redirectToUploadDoc(view, entity);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return viewEntityList.size();
    }
}
