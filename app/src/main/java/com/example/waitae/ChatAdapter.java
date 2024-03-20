package com.example.waitae;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    private ArrayList<ChatModel> chatsList;
    private Context context;

    public ChatAdapter(ArrayList<ChatModel> chatsList, Context context) {
        this.chatsList = chatsList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_chat_view, parent, false);
                return new UserViewHolder(view);

            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ai_response, parent, false);
                return new AIChatViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModel chatModal = chatsList.get(position);
        switch (chatModal.getSender()){
            case "user":
                ((UserViewHolder)holder).user_msg.setText(chatModal.getMessage());
                break;
            case "bot":
                ((AIChatViewHolder)holder).ai_chat_box.setText(chatModal.getMessage());
                break;
        }
    }

    public int getItemViewType(int position){
        switch (chatsList.get(position).getSender()){
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return chatsList.size();
    }

    public static class UserViewHolder extends  RecyclerView.ViewHolder{

        TextView user_msg;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            user_msg = itemView.findViewById(R.id.user_msg);
        }
    }

    public static class AIChatViewHolder extends RecyclerView.ViewHolder{

        TextView ai_chat_box;
        public AIChatViewHolder(@NonNull View itemView) {
            super(itemView);
            ai_chat_box = itemView.findViewById(R.id.ai_chat_box);
        }
    }
}
