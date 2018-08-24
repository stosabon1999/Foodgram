package ru.production.ssobolevsky.foodgram.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import ru.production.ssobolevsky.foodgram.R;
import ru.production.ssobolevsky.foodgram.data.repositories.ChatRepositoryImpl;
import ru.production.ssobolevsky.foodgram.domain.models.Chat;
import ru.production.ssobolevsky.foodgram.domain.usecases.impl.ChatUseCaseImpl;
import ru.production.ssobolevsky.foodgram.presentation.adapters.ChatsAdapter;
import ru.production.ssobolevsky.foodgram.presentation.presenters.ChatsPresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatsFragment extends Fragment implements ChatsView {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mManager;
    private ChatsAdapter mAdapter;
    private ProgressBar mProgressBar;
    private TextView mNoChatsTextView;

    private ChatsPresenter mPresenter;

    public ChatsFragment() {
        // Required empty public constructor
    }
    public static ChatsFragment newInstance() {
        ChatsFragment fragment = new ChatsFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        mToolbar = view.findViewById(R.id.chats_toolbar);
        mProgressBar = view.findViewById(R.id.pb_chats);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        mNoChatsTextView = view.findViewById(R.id.tv_no_chats);
        mRecyclerView = view.findViewById(R.id.rv_chats);
        mManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
        mAdapter = new ChatsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new ChatsPresenter(new ChatUseCaseImpl(new ChatRepositoryImpl()));
        mPresenter.attachView(this);
        mPresenter.getDialogs();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showDialogs(List<Chat> chats) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mNoChatsTextView.setVisibility(View.GONE);
        mAdapter.setData(chats);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mNoChatsTextView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyDialogs() {
        mRecyclerView.setVisibility(View.GONE);
        mNoChatsTextView.setVisibility(View.VISIBLE);
    }
}
