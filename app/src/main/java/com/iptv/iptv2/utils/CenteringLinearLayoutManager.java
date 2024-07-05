package com.iptv.iptv2.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CenteringLinearLayoutManager extends LinearLayoutManager {

    public CenteringLinearLayoutManager(Context context) {
        super(context);
    }

    public CenteringLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CenteringLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        centerItemsHorizontally();
    }

    private void centerItemsHorizontally() {
        RecyclerView parent = (RecyclerView) getChildAt(0).getParent();
        int totalWidth = parent.getWidth();
        int totalItemsWidth = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child != null) {
                totalItemsWidth += child.getWidth();
            }
        }

        int padding = (totalWidth - totalItemsWidth) / 2;
        parent.setPadding(padding, parent.getPaddingTop(), padding, parent.getPaddingBottom());
    }
}
