package ru.production.ssobolevsky.foodgram.presentation.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.production.ssobolevsky.foodgram.R;
import ru.production.ssobolevsky.foodgram.presentation.model.RecipeCard;
import ru.production.ssobolevsky.foodgram.presentation.adapters.RecipesAdapter;

public class RecipesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mManager;
    private RecipesAdapter mAdapter;
    private List<RecipeCard> mRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        init();
    }

    private void init() {
        mRecyclerView = findViewById(R.id.rv_recipes);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
        mRecipes = new ArrayList<>();
        mRecipes.add(new RecipeCard("https://img01.rl0.ru/eda/c620x415i/s2.eda.ru/StaticContent/Photos/120214155832/120214160646/p_O.jpg",
                "Американские блины",
                "Яйца взбить с сахаром и солью до появления пены."));
        mRecipes.add(new RecipeCard("https://img01.rl0.ru/eda/c620x415i/s2.eda.ru/StaticContent/Photos/120214155832/120214160646/p_O.jpg",
                "Американские блины",
                "Яйца взбить с сахаром и солью до появления пены."));
        mRecipes.add(new RecipeCard("https://img01.rl0.ru/eda/c620x415i/s2.eda.ru/StaticContent/Photos/120214155832/120214160646/p_O.jpg",
                "Американские блины",
                "Яйца взбить с сахаром и солью до появления пены."));
        mAdapter = new RecipesAdapter();
        mAdapter.setData(mRecipes);
        mRecyclerView.setAdapter(mAdapter);
    }
}
