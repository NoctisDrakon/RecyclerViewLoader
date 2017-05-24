package listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import interfaces.OnRefreshEventListener;

import static interfaces.OnRefreshEventListener.REFRESH;

/**
 * Created by NoctisDrakon on 24/05/2017.
 * This is a custom implementation of RecyclerView OnScrollListener
 * based on StackOverflow's most up voted answer for question number 26543131
 */

public class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private final LinearLayoutManager mLayoutManager;
    private final int visibleThreshold;
    private OnRefreshEventListener listener;
    private boolean loading = false;

    /**
     * This constructor receives the linear layout manager of the recycler view,
     * the visible item's threshold and the interface to be triggered
     *
     * @param visibleThreshold
     * @param mLayoutManager
     * @param listener
     */
    public EndlessScrollListener(int visibleThreshold, LinearLayoutManager mLayoutManager, OnRefreshEventListener listener) {
        this.visibleThreshold = visibleThreshold;
        this.mLayoutManager = mLayoutManager;
        this.listener = listener;
    }

    /**
     * When RecyclerView is scrilled, this calculates how many elements are visible and how many are hidden
     * according to the visible threshold. If remaining elements before showing the last item count is smaller
     * than the threshold value, OnRefreshEvent is triggered with REFRESH parameter.
     *
     * @param recyclerView
     * @param dx
     * @param dy
     */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (!loading && dy > 0) {
            int totalItemCount = mLayoutManager.getItemCount();
            int itemThreshold = totalItemCount - visibleThreshold;
            int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();

            if (lastVisibleItem >= itemThreshold) {
                loading = true;

                if (listener == null)
                    throw new IllegalArgumentException("If you choose to use RecyclerViewLoader with Endless Scroll, " +
                            "you must init the view passing OnRefreshEventListener as argument");
                listener.onRefreshEvent(REFRESH);
            }
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView view, int scrollState) {
    }

    public void setLoading(boolean isLoading) {
        loading = isLoading;
    }

}
