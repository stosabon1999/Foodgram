package ru.production.ssobolevsky.foodgram;

import android.widget.ImageView;

/**
 * Created by pro on 17.06.2018.
 */

public class RecipeCard {

    private String mUrl;

    private String mTitle;

    private String mSubtitle;

    public RecipeCard(String url, String title, String subtitle) {
        mUrl = url;
        mTitle = title;
        mSubtitle = subtitle;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
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
}
