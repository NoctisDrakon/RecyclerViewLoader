package view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.noctisdrakon.recyclerviewloader.R;

import interfaces.OnRefreshEventListener;
import listener.EndlessScrollListener;

import static interfaces.OnRefreshEventListener.RELOAD;

/**
 * RecyclerViewLoader it's class extends FrameLayout
 * and allows you to create a RecyclerView with, or without
 * a SwipeRefreshLayout. Also allows you to handle async calls
 * to get the data that your RecyclerView will show later
 */

public class RecyclerViewLoader extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {
    private Context context;
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private SwipeRefreshLayout refreshLayout;
    private OnRefreshEventListener refreshEvent;
    private EndlessScrollListener scrollListener;

    public RecyclerViewLoader(Context context) {
        super(context);
    }

    public RecyclerViewLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * This method creates initializes the context for
     * all subsecuent operations
     *
     * @return
     */
    public RecyclerViewLoader initialize() {
        this.context = getContext();
        return this;
    }

    public RecyclerViewLoader initialize(@NonNull OnRefreshEventListener refreshListener) {
        this.context = getContext();
        this.refreshEvent = refreshListener;
        return this;
    }

    /**
     * This method return a basic RV structure
     * with no refresh layout. You can either pass your own
     * custom LayoutParams, or just pass Null to set them as
     * default
     *
     * @param llm
     * @return
     */
    public RecyclerViewLoader setNormalStructure(@Nullable LinearLayoutManager llm) {
        initRecyclerView(llm);
        addView(recyclerView);
        return this;
    }

    /**
     * This method returns RV embedded within
     * a SwipeRefreshLayout along with its callback interfaces.
     * You can either pass your custom LayoutManager, or pass Null
     * to set it to default
     *
     * @param llm
     * @return
     */
    public RecyclerViewLoader setRefreshStructure(@Nullable LinearLayoutManager llm) {
        refreshLayout = new SwipeRefreshLayout(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        refreshLayout.setLayoutParams(params);
        initRecyclerView(llm);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.addView(recyclerView);
        addView(refreshLayout);
        refreshLayout.setRefreshing(false);
        return this;
    }

    /**
     * This method allows to implement an endless scroll listener
     * in the recycler view specifying a visible element treshold
     * to trigger the refresh event
     *
     * @param visibleTreshold
     * @return
     */
    public RecyclerViewLoader withEndlessScroll(int visibleTreshold) {
        scrollListener = new EndlessScrollListener(visibleTreshold, llm, refreshEvent);
        recyclerView.addOnScrollListener(scrollListener);
        return this;
    }

    /**
     * Sets the adapter for the Recycler View
     *
     * @param adapter
     */
    public void setRVAdapter(RecyclerView.Adapter adapter) {
        if (recyclerView == null)
            throw new NullPointerException("You haven't called init() yet");
        recyclerView.setAdapter(adapter);
    }

    /**
     * If not null, sets the loading value for the refresh layout
     *
     * @param loading
     */
    public void setRefeshLayoutLoading(boolean loading) {
        if (refreshLayout != null)
            refreshLayout.setRefreshing(loading);
    }

    //init llm private method
    private void initRecyclerView(LinearLayoutManager llm) {
        recyclerView = (RecyclerView) LayoutInflater.from(context).inflate(R.layout.recycler_view_base, this, false);
        this.llm = llm == null ? new LinearLayoutManager(context) : llm;
        recyclerView.setLayoutManager(this.llm);
    }

    //On Refresh Listener Methods
    @Override
    public void onRefresh() {
        if (refreshEvent == null)
            throw new IllegalArgumentException("If you choose to use RecyclerViewLoader with Refresh Structure, " +
                    "you must init the view passing OnRefreshEventListener as argument");
        refreshEvent.onRefreshEvent(RELOAD);
    }
}
