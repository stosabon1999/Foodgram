package ru.production.ssobolevsky.foodgram.presentation.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import ru.production.ssobolevsky.foodgram.R;
import ru.production.ssobolevsky.foodgram.presentation.activities.RecipesActivity;

/**
 * Created by pro on 27.06.2018.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    /**
     * List of categories of recipes.
     */
    private List<String> mData;

    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Button view = (Button) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoriesViewHolder holder, int position) {
        holder.mButtonCategory.setText(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), RecipesActivity.class);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * Set categories and update adapter.
     * @param list - list of categories.
     */
    public void setData(List<String> list) {
        mData = list;
        notifyDataSetChanged();
    }

     class CategoriesViewHolder extends RecyclerView.ViewHolder {
         /**
          * Name of category.
          */
        private Button mButtonCategory;

        CategoriesViewHolder(View itemView) {
            super(itemView);
            mButtonCategory = itemView.findViewById(R.id.btn_category);
        }
    }
}
