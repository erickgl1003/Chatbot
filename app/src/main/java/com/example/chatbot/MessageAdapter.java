package com.example.chatbot;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Objects;

public class MessageAdapter extends RecyclerView.Adapter{
    private ArrayList<Message> messageArrayList;
    private Context context;

    public MessageAdapter(ArrayList<Message> messageArrayList, Context context){
        this.messageArrayList = messageArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message message = messageArrayList.get(position);

        ((LinearLayout.LayoutParams) ((ViewHolder) holder).tvSender.getLayoutParams()).gravity = Gravity.END;
        ((LinearLayout.LayoutParams) ((ViewHolder) holder).cvMessageLayout.getLayoutParams()).gravity = Gravity.END;

        ((ViewHolder) holder).tvMessage.setText(message.getMessage());
        ((ViewHolder) holder).tvSender.setText(message.getSender());
        if(!Objects.equals(message.getSender(), "user")){
            ((LinearLayout.LayoutParams) ((ViewHolder) holder).tvSender.getLayoutParams()).gravity = Gravity.START;
            ((LinearLayout.LayoutParams) ((ViewHolder) holder).cvMessageLayout.getLayoutParams()).gravity = Gravity.START;
        }
    }

    @Override
    public int getItemCount(){
        return messageArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvMessage, tvSender;
        MaterialCardView cvMessageLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvSender = itemView.findViewById(R.id.tvSender);
            cvMessageLayout = itemView.findViewById(R.id.cvMessageLayout);

        }
    }

}
