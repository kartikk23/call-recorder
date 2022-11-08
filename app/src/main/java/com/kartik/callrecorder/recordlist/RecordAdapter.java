package com.kartik.callrecorder.recordlist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.kartik.callrecorder.R;
import com.kartik.callrecorder.model.RecordModel;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int FAVORITES = 1;
    public static final int INCOMING = 1 << 1;
    public static final int OUTGOING = 1 << 2;

    private int mode = INCOMING | OUTGOING;

    private ArrayList<RecordModel> records = new ArrayList<>();

    public RecordAdapter(ArrayList<RecordModel> records) {
        this.records = records;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecordController(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.record_item, parent, false),this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof RecordController) {
            ((RecordController) viewHolder).bindModel(records.get(position), mode);
        }
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public void addMode(int mode_flag){
        mode = mode | mode_flag;
        notifyDataSetChanged();
    }

    public void removeMode(int mode_flag){
        mode = mode & ~mode_flag;
        notifyDataSetChanged();
    }
}
