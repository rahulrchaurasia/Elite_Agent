package account.rb.com.elite_agent.chat;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.util.List;

import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.core.model.ChatEntity;


/**
 * Created by IN-RB on 22-02-2018.
 */


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatItem> {

    Activity mContext;
    List<ChatEntity> chatEntityList;

    public ChatAdapter(Activity mContext, List<ChatEntity> chatList) {
        this.mContext = mContext;
        this.chatEntityList = chatList;
    }

    public class ChatItem extends RecyclerView.ViewHolder
    {
        public TextView txtAdmin ,txtAgent;

        public RelativeLayout rlAdmin , rlAgent;
        public ChatItem(View itemView) {
            super(itemView);
            txtAdmin = itemView.findViewById(R.id.txtAdmin);
            txtAgent = itemView.findViewById(R.id.txtAgent);
            rlAdmin  = itemView.findViewById(R.id.rlAdmin);
            rlAgent  = itemView.findViewById(R.id.rlAgent);

        }
    }


    @Override
    public ChatAdapter.ChatItem onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_chat_item, parent, false);

        return new ChatAdapter.ChatItem(itemView);

    }

    @Override
    public void onBindViewHolder(ChatItem holder, int position) {

        final ChatEntity chatEntity = chatEntityList.get(position);
        holder.txtAdmin.setText( "" +chatEntity.getComments());
        holder.txtAgent.setText( "" +chatEntity.getComments());

        if(chatEntity.getComments_by().toUpperCase().equalsIgnoreCase("ADMIN"))
        {
            holder.rlAdmin.setVisibility(View.VISIBLE);
            holder.rlAgent.setVisibility(View.GONE);
        }else{
            holder.rlAgent.setVisibility(View.VISIBLE);
            holder.rlAdmin.setVisibility(View.GONE);

        }
    }


    @Override
    public int getItemCount() {
        return chatEntityList.size();
    }
}
