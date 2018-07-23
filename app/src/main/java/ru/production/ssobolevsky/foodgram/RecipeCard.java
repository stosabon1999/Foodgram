package ru.production.ssobolevsky.foodgram;

import android.widget.ImageView;

/**
 * Created by pro on 17.06.2018.
 */

public class RecipeCard {

    private ImageView mScreen;

    private String mTitle;

    private String mSubtitle;

    public RecipeCard(ImageView screen, String title, String subtitle) {
        mScreen = screen;
        mTitle = title;
        mSubtitle = subtitle;
    }

    public ImageView getScreen() {
        return mScreen;
    }

    public void setScreen(ImageView screen) {
        mScreen = screen;
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
