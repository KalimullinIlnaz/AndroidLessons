package com.a65apps.kalimullinilnazrafilovich.myapplication;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDecoration extends RecyclerView.ItemDecoration {
    private int offsetLeft;
    private int offsetRight;
    private int offsetTop;
    private int offsetBottom;

    public ItemDecoration(int offsetLeft, int offsetRight, int offsetTop, int offsetBottom) {
        this.offsetLeft =offsetLeft;
        this.offsetRight = offsetRight;
        this.offsetTop = offsetTop;
        this.offsetBottom = offsetBottom;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.right = offsetRight;
        outRect.left = offsetLeft;
        outRect.top = offsetTop;
        outRect.bottom = offsetBottom;

    }
}

