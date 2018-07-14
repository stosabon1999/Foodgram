package ru.production.ssobolevsky.foodgram.models;

import android.net.Uri;

/**
 * Created by pro on 25.06.2018.
 */

public class Recipe {

    private String mTitle;
    private String mSubtitle;
    private String mIngredients;
    private Uri mImage;

    public Recipe(String title, String subtitle, String ingredients, Uri image) {
        mTitle = title;
        mSubtitle = subtitle;
        mIngredients = ingredients;
        mImage = image;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String subtitle) {
        mSubtitle = subtitle;
    }

    public String getIngredients() {
        return mIngredients;
    }

    public void setIngredients(String ingredients) {
        mIngredients = ingredients;
    }

    public Uri getImage() {
        return mImage;
    }

    public void setImage(Uri image) {
        mImage = image;
    }
}
