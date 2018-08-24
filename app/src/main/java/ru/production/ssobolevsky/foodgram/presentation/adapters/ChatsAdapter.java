package ru.production.ssobolevsky.foodgram.presentation.adapters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.production.ssobolevsky.foodgram.R;
import ru.production.ssobolevsky.foodgram.domain.models.Chat;
import ru.production.ssobolevsky.foodgram.presentation.activities.DialogActivity;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatViewHolder> {
    /**
     * List of chats of current user.
     */
    private List<Chat> mData;

    public ChatsAdapter() {
        mData = new ArrayList<>();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = mData.get(position);
        holder.mName.setText(chat.getName());
        holder.mTime.setText(String.valueOf(chat.getDate()));
        holder.mLastMessage.setText(chat.getLastMessage());
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(DialogActivity.CHAT_UID, chat.getUid());
            bundle.putString(DialogActivity.USER_NAME, chat.getName());
            holder.itemView.getContext().startActivity(DialogActivity.newInstance(holder.itemView.getContext(), bundle));
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * Set and update chats.
     * @param data - list of chats.
     */
    public void setData(List<Chat> data) {
        mData = data;
        notifyDataSetChanged();
    }


    class ChatViewHolder extends RecyclerView.ViewHolder {
        /**
         * Image of user.
         */
        private ImageView mUserImage;
        /**
         * Name of user.
         */
        private TextView mName;
        /**
         * Last message in chat.
         */
        private TextView mLastMessage;
        /**
         * Time of last message in chat.
         */
        private TextView mTime;

        public ChatViewHolder(View itemView) {
            super(itemView);
            mUserImage = itemView.findViewById(R.id.chat_user_image);
            mName = itemView.findViewById(R.id.chat_user_name);
            mLastMessage = itemView.findViewById(R.id.chat_last_message);
            mTime = itemView.findViewById(R.id.chat_time);
        }
    }

}
