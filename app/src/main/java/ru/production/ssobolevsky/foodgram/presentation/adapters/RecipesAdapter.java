package ru.production.ssobolevsky.foodgram.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.production.ssobolevsky.foodgram.R;
import ru.production.ssobolevsky.foodgram.presentation.model.RecipeCard;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeCardViewHolder> {
    /**
     * List of recipes.
     */
    private List<RecipeCard> mData;

    @Override
    public RecipesAdapter.RecipeCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card, parent, false);
        return new RecipesAdapter.RecipeCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipesAdapter.RecipeCardViewHolder holder, int position) {
        RecipeCard card = mData.get(position);
        holder.mTitle.setText(card.getTitle());
        holder.mSubtitle.setText(card.getSubtitle());
        Picasso.get()
                .load(card.getUrl())
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<RecipeCard> list) {
        mData = list;
        notifyDataSetChanged();
    }

    class RecipeCardViewHolder extends RecyclerView.ViewHolder {
        /**
         * Image of recipe.
         */
        private ImageView mImageView;
        /**
         * Title of recipe.
         */
        private TextView mTitle;
        /**
         * Subtitle of recipe.
         */
        private TextView mSubtitle;

        RecipeCardViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_recipe_screen);
            mTitle = itemView.findViewById(R.id.tv_title_recipe);
            mSubtitle = itemView.findViewById(R.id.tv_subtitle_recipe);
        }
    }
}
