package com.naztech.nsmssync;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private ArrayList<MessageItem> mMessageItem;
    //private AdapterView.OnItemClickListener mListener;
    //private MessageViewHolder.OnItemClickListener mmListener;
    private  OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MessageAdapter(ArrayList<MessageItem> messageList) {
        mMessageItem = messageList;
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        MessageViewHolder mvh = new MessageViewHolder(v, mListener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {

        MessageItem currentItem = mMessageItem.get(position);
        holder.TVtext.setText(currentItem.getMessage());
        holder.TVphoneNo.setText(currentItem.getNumber());
        holder.TVstatus.setText(currentItem.getStatus());
        holder.TVdate.setText(currentItem.getDate());

    }

    @Override
    public int getItemCount() {
        return mMessageItem.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView TVtext;
        public TextView TVdate;
        public TextView TVphoneNo;
        public TextView TVstatus;


        public MessageViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            TVtext = itemView.findViewById(R.id.TVtext);
            TVdate = itemView.findViewById(R.id.TVdate);
            TVphoneNo = itemView.findViewById(R.id.TVnumber);
            TVstatus = itemView.findViewById(R.id.TVstatus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }

                }
            });
        }
    }
}
