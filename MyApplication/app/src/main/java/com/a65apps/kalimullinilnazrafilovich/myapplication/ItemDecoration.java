package com.a65apps.kalimullinilnazrafilovich.myapplication;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDecoration extends RecyclerView.ItemDecoration {
    private int offsetLeftPx;
    private int offsetRightPx;
    private int offsetTopPx;
    private int offsetBottomPx;

    public ItemDecoration(int offsetLeftPx, int offsetRightPx, int offsetTopPx, int offsetBottomPx) {
        this.offsetLeftPx =offsetLeftPx;
        this.offsetRightPx = offsetRightPx;
        this.offsetTopPx = offsetTopPx;
        this.offsetBottomPx = offsetBottomPx;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.right = offsetRightPx;
        outRect.left = offsetLeftPx;
        outRect.top = offsetTopPx;
        outRect.bottom = offsetBottomPx;
    }

}
