package com.kartik.callrecorder.utils;

public class ScrollLoadMore extends RecyclerView.OnScrollListener {
    public static final int SCROLL_OFFSET = 5;
    private boolean isLoading = false;
    private boolean hasMore = true;

    private final WeakReference<ArrayList<RecordModel>> recordsref;

    public ScrollLoadMore(ArrayList<RecordModel> records) {
        this.recordsref = new WeakReference<> (records);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        ArrayList<RecordModel> records = recordsref.get();
        if(records!=null) {
            final int pos = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            final int pos2 = records.size() - SCROLL_OFFSET;
            if (pos > pos2) {
                if (!isLoading && hasMore) {
                    isLoading = true;
                    SqlUtils.loadRecords(records.size());
                }
            }
        }
        super.onScrollStateChanged(recyclerView, newState);
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

}