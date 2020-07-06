package com.storelogflog.uk.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.storelogflog.uk.R;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.apputil.Utility;
import com.storelogflog.uk.bean.messageBean.Message;
import com.storelogflog.uk.bean.offerListBean.Offer;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ActiveListHolder> {

   private FragmentActivity activity;
   private List<Message>messageList;



    public ChatAdapter(FragmentActivity activity,List<Message>messageList) {
        this.activity = activity;
        this.messageList=messageList;

    }

    @NonNull
    @Override
    public ActiveListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.item_chat,parent,false);
        return new ActiveListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ActiveListHolder holder, final int position) {

        Message message=messageList.get(position);

        if(message.getType().equals("C"))
        {
           holder.llMsgOutgoing.setVisibility(View.VISIBLE);
            holder.llMsgIncoming.setVisibility(View.GONE);
           holder.txtmsgOugoing.setText(""+message.getMessage());

           if (message.getDate()!=null)
           {
               holder.txtTimeOutgoing.setText(""+ Utility.getTime(message.getDate()));
           }

        }
        else if(message.getType().equals("O"))
        {
            holder.llMsgIncoming.setVisibility(View.VISIBLE);
            holder.llMsgOutgoing.setVisibility(View.GONE);
            holder.txtmsgIncoming.setText(""+message.getMessage());

            if (message.getDate()!=null)
            {
                holder.txtTimeIncoming.setText(""+ Utility.getTime(message.getDate()));
            }

        }


    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class ActiveListHolder extends RecyclerView.ViewHolder
    {

       private AppCompatTextView txtDateTime;
       private RadioButton radioButton;
       private LinearLayout llMsgIncoming;
       private LinearLayout llMsgOutgoing;
       private AppCompatTextView txtmsgOugoing;
       private AppCompatTextView txtmsgIncoming;
       private AppCompatTextView txtTimeIncoming;
       private AppCompatTextView txtTimeOutgoing;


        public ActiveListHolder(@NonNull View itemView) {
            super(itemView);
            llMsgIncoming=itemView.findViewById(R.id.ll_msg_incoming);
            llMsgOutgoing=itemView.findViewById(R.id.ll_msg_outgoing);
            txtmsgIncoming=itemView.findViewById(R.id.text_message_incoming);
            txtmsgOugoing=itemView.findViewById(R.id.text_message_outgoing);
            txtTimeIncoming=itemView.findViewById(R.id.txt_time_incoming);
            txtTimeOutgoing=itemView.findViewById(R.id.txt_time_outgoing);

        }
    }


}
