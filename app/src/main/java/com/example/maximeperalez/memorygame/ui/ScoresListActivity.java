package com.example.maximeperalez.memorygame.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.maximeperalez.memorygame.R;
import com.example.maximeperalez.memorygame.adapters.ScoresListAdapter;
import com.example.maximeperalez.memorygame.model.Score;

import java.util.ArrayList;

public class ScoresListActivity extends AppCompatActivity {

    // Outlets
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // View model
    ScoresListViewModel mViewModel;

    // MARK: - Life cycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores_list);

        // Set up view model and observers
        mViewModel = ViewModelProviders.of(this).get(ScoresListViewModel.class);
        mViewModel.scoresLiveData.observe(this, this::updateList);

        // Set up recycler view
        mRecyclerView = findViewById(R.id.scores_list_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Fetch scores
        mViewModel.fetchScores();
    }

    // MARK: - Private

    private void updateList(ArrayList<Score> newScores) {
        mAdapter = new ScoresListAdapter(newScores);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
