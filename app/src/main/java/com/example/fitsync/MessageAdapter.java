package com.example.fitsync;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private List<Message> messageList; // Assuming Message is your data model

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item, parent, false);
        return new MyViewHolder(chatView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message = messageList.get(position);
        if (message.sentBy.equals(Message.SENT_BY_ME)) {
            holder.rightChat.setVisibility(View.VISIBLE);
            holder.leftChat.setVisibility(View.GONE);
            holder.rightTextChat.setText(message.getMessage());
        } else {
            holder.leftChat.setVisibility(View.VISIBLE);
            holder.rightChat.setVisibility(View.GONE);
            holder.leftTextChat.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftChat, rightChat;
        TextView leftTextChat, rightTextChat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leftChat = itemView.findViewById(R.id.leftChat);
            rightChat = itemView.findViewById(R.id.rightChat);
            leftTextChat = itemView.findViewById(R.id.leftTextChat);
            rightTextChat = itemView.findViewById(R.id.rightTextChat);
        }
    }
}
