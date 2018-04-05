package com.example.maximeperalez.memorygame.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maximeperalez.memorygame.R;
import com.example.maximeperalez.memorygame.model.Score;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by maxime.peralez on 05/04/2018.
 */

public class ScoresListAdapter extends RecyclerView.Adapter<ScoresListAdapter.ViewHolder> {

    // Model
    private ArrayList<Score> mScores;

    // Holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mScoreTextView;
        TextView mPositionTextView;
        TextView mRankingValueTextView;

        ViewHolder(View itemView) {
            super(itemView);
            mScoreTextView = itemView.findViewById(R.id.score_text_view);
            mPositionTextView = itemView.findViewById(R.id.score_position_text_view);
            mRankingValueTextView = itemView.findViewById(R.id.ranking_value_text_view);
        }
    }

    // MARK: - Initialization

    public ScoresListAdapter(ArrayList<Score> scores) {
        mScores = scores;
        sortScores();
    }

    // MARK: - Private

    private void sortScores() {
        Collections.sort(mScores, Score::compareTo);
    }

    // MARK: - Overridden methods

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View scoreView = layoutInflater.inflate(R.layout.item_score, parent, false);

        return new ViewHolder(scoreView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Score score = mScores.get(position);
        holder.mScoreTextView.setText(score.toString());
        holder.mPositionTextView.setText(String.valueOf(position + 1));
        holder.mRankingValueTextView.setText(String.valueOf(score.getRankingValue()));
    }

    @Override
    public int getItemCount() {
        return mScores.size();
    }
}
