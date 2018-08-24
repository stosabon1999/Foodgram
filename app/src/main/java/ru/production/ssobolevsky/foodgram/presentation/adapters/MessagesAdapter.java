package ru.production.ssobolevsky.foodgram.presentation.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.production.ssobolevsky.foodgram.R;
import ru.production.ssobolevsky.foodgram.data.datasources.MyFirebaseData;
import ru.production.ssobolevsky.foodgram.domain.models.Message;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {
    /**
     * Item of current user message.
     */
    private static final int ITEM_TYPE_LEFT = 1;
    /**
     * Item of selected user message.
     */
    private static final int ITEM_TYPE_RIGHT = 0;
    /**
     * List of messages with selected user.
     */
    private List<Message> mData;

    public MessagesAdapter() {
        mData = new ArrayList<>();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = 0;
        switch (viewType) {
            case ITEM_TYPE_LEFT:
                layout = R.layout.item_message_left;
                break;
            case ITEM_TYPE_RIGHT:
                layout = R.layout.item_message_right;
                break;
        }
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = mData.get(position);
        holder.mTime.setText(message.getTime());
        holder.mText.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Message> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getSenderUid().equals(MyFirebaseData.getFirebaseUserUid())) {
            position = 0;
        } else {
            position = 1;
        }
        return position;
    }

    /**
     * Get timestamp of first item for identify message on the top.
     * @return timestamp of top message.
     */
    public Long getFirstItemTimestamp() {
        return mData.get(0).getTimestamp();
    }

    public void addData(List<Message> list) {
        mData.addAll(0, list);
        notifyDataSetChanged();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        /**
         * Time of message.
         */
        private TextView mTime;
        /**
         * Text of message.
         */
        private TextView mText;

        public MessageViewHolder(View itemView) {
            super(itemView);

            mText = itemView.findViewById(R.id.tv_text_message);
            mTime = itemView.findViewById(R.id.tv_time_message);
        }
    }

}
