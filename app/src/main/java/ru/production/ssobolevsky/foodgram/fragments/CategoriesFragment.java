package ru.production.ssobolevsky.foodgram.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import ru.production.ssobolevsky.foodgram.adapters.CategoriesAdapter;
import ru.production.ssobolevsky.foodgram.utils.CategoriesRepository;
import ru.production.ssobolevsky.foodgram.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mManager;
    private CategoriesAdapter mAdapter;
    private List<String> mCategories;

    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    public static final CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.rv_categories);
        init();
    }

    private void init() {
        mManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
        mCategories = Arrays.asList(CategoriesRepository.getCategories());
        mAdapter = new CategoriesAdapter();
        mAdapter.setData(mCategories);
        mRecyclerView.setAdapter(mAdapter);
    }
}
