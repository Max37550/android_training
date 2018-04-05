package com.example.maximeperalez.memorygame.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.maximeperalez.memorygame.R;
import com.example.maximeperalez.memorygame.model.Board;
import com.example.maximeperalez.memorygame.model.Card;

/**
 * Created by maxime.peralez on 03/04/2018.
 */

public class ImageAdapter extends BaseAdapter {
    // Context
    private Context mContext;

    // Dimensions
    private int mItemWidth;
    private int mItemHeight;

    // Model
    private Board mBoard;

    // MARK: - Initialization

    public ImageAdapter(Context c, Board board, int itemWidth, int itemHeight) {
        mContext = c;
        mItemHeight = itemHeight;
        mItemWidth = itemWidth;
        mBoard = board;
    }

    // MARK: - Public

    public int getCount() {
        return mBoard.getCards().size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // Creates a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(mItemWidth, mItemHeight));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        Card card = mBoard.getCards().get(position);
        Bitmap bitmap = card.getBitmap();

        if (card.isHidden()) {
            imageView.setImageResource(R.drawable.card_overlay);
        } else {
            imageView.setImageBitmap(bitmap);
        }

        imageView.setTag(card.getId());

        if (!card.isStillInGame()) {
            imageView.setVisibility(View.INVISIBLE);
        }

        return imageView;
    }
}