package ru.production.ssobolevsky.foodgram.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import ru.production.ssobolevsky.foodgram.R;

/**
 * Created by pro on 27.06.2018.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    private List<String> mData;

    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Button view = (Button) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoriesViewHolder holder, int position) {
        holder.mButtonCategory.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<String> list) {
        mData = list;
        notifyDataSetChanged();
    }

     class CategoriesViewHolder extends RecyclerView.ViewHolder {

        private Button mButtonCategory;

        CategoriesViewHolder(View itemView) {
            super(itemView);
            mButtonCategory = itemView.findViewById(R.id.btn_category);
        }
    }
}
