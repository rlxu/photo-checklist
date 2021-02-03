package com.example.photochecklist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photochecklist.models.TaskItem;

import org.parceler.Parcels;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    static final int DETAIL_ACTIVITY_CODE = 20;
    List<TaskItem> mItems;
    Context context;

    // pass in items array into constructor
    public ItemAdapter(List<TaskItem> items) {
        mItems = items;
    }

    // for each row, inflate layout and cache references into ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.todo_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    // bind values based on position of element
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // get data according to position
        final TaskItem item = mItems.get(position);
        // populate the views according to this data
        holder.itemTextView.setText(item.title);
        // Set icon and cross out text if completed
        if (item.isComplete) {
            holder.itemTextView.setPaintFlags(holder.itemTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.completionIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);
        } else {
            holder.itemTextView.setPaintFlags(holder.itemTextView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            holder.completionIcon.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24);
        }
    }

    @Override
    public int getItemCount() { return mItems.size(); }

    // create ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView itemTextView;
        public CardView itemCardView;
        public ImageView completionIcon;

        public ViewHolder(View itemView) {
            super(itemView);

            // perform findViewById assignments
            itemTextView = itemView.findViewById(R.id.itemTextView);
            itemCardView = itemView.findViewById(R.id.itemCardView);
            completionIcon = itemView.findViewById(R.id.completionIcon);

            itemCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                TaskItem item = mItems.get(pos);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(TaskItem.class.getSimpleName(), Parcels.wrap(item));
                intent.putExtra("position", pos);
                ((Activity) context).startActivityForResult(intent, DETAIL_ACTIVITY_CODE);
            }
        }
    }
}
