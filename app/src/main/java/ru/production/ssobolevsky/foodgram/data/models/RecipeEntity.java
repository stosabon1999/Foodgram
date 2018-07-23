package ru.production.ssobolevsky.foodgram.data.models;

import android.net.Uri;

/**
 * Created by pro on 25.06.2018.
 */

public class RecipeEntity {


    private String uid;
    private String mTitle;
    private String mSubtitle;
    private String mIngredients;
    private Uri mImage;

    public RecipeEntity(String uid, String title, String subtitle, String ingredients, Uri image) {
        this.uid = uid;
        mTitle = title;
        mSubtitle = subtitle;
        mIngredients = ingredients;
        mImage = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
