package com.fetchhiring;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter for managing and displaying items in a RecyclerView.
 * Displays the header along with its items
 */
public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ItemModel> items;
    private Context context;


    public ItemAdapter(Context context, List<ItemModel> items) {
        this.items = items;
        this.context = context;
    }

    public void updateItems(List<ItemModel> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new ItemViewHolder(view);
        }
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemModel item = items.get(position);
        if (item.isHeader()) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.headerTextView.setText("List ID: " + item.getListId());
        } else {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            itemHolder.nameTextView.setText(item.getName());
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        ItemModel item = items.get(position);
        if (item.isHeader()) {
            return 0;  // 0 for header type
        } else {
            return 1;  // 1 for item type
        }
    }


    //ViewHolder for the individual items
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
        }
    }

    //ViewHolder for the header items
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTextView;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            headerTextView = itemView.findViewById(R.id.headerTextView);
        }
    }

}

