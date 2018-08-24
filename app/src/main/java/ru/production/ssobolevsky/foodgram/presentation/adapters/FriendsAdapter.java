package ru.production.ssobolevsky.foodgram.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.production.ssobolevsky.foodgram.R;
import ru.production.ssobolevsky.foodgram.data.models.UserEntity;
import ru.production.ssobolevsky.foodgram.domain.models.User;
import ru.production.ssobolevsky.foodgram.presentation.activities.MainActivity;

/**
 * Created by pro on 03.07.2018.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.UsersViewHolder> {
    /**
     * List of friends of selected user.
     */
    private List<User> mData;

    public FriendsAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_searched_user, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsersViewHolder holder, int position) {
        final User user = mData.get(position);
        holder.mUserName.setText(user.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.itemView.getContext().startActivity(MainActivity.newIntent(holder.itemView.getContext(), user.getUid()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {
        /**
         * Name of friend.
         */
        private TextView mUserName;

        public UsersViewHolder(View itemView) {
            super(itemView);
            mUserName = itemView.findViewById(R.id.tv_user_searched_name);
        }
    }

    public void setData(List<User> data) {
        mData = data;
        notifyDataSetChanged();
    }

}
