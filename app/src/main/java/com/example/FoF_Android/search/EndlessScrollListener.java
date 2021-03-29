package com.example.FoF_Android.search;

import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private boolean isLoading;
    private boolean hasMorePages;
    private int pageNumber = 0;
    private RefreshList refreshList;
    private boolean isRefreshing;
    private int pastVisibleItems;



    public EndlessScrollListener(RefreshList refreshList) {
        this.isLoading = false;
        this.hasMorePages = true;
        this.refreshList = refreshList;
    }

    @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        StaggeredGridLayoutManager manager =
                (StaggeredGridLayoutManager) recyclerView.getLayoutManager();

        int visibleItemCount = manager.getChildCount();
        int totalItemCount = manager.getItemCount();
        int[] firstVisibleItems = manager.findFirstVisibleItemPositions(null);
        if (firstVisibleItems != null && firstVisibleItems.length > 0) {
            pastVisibleItems = firstVisibleItems[0];
        }

        if ((visibleItemCount + pastVisibleItems) >= totalItemCount && !isLoading) {
            isLoading = true;
            if (hasMorePages && !isRefreshing) {
                isRefreshing = true;
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        refreshList.onRefresh(pageNumber);
                    }
                }, 200);
            }
        } else {
            isLoading = false;
        }
    }

    public void noMorePages() {
        this.hasMorePages = false;
    }

    public void notifyMorePages() {
        isRefreshing = false;
        pageNumber = pageNumber + 1;
    }



    public interface RefreshList {
        void onRefresh(int pageNumber);
    }
}
