package com.example.ensamarketplace;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ensamarketplace.model.Announcement;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> implements Filterable{
    private final List<Announcement> allAnnouncements;
    private final List<Announcement> filteredAnnouncements;

    RecycleViewAdapter(List<Announcement> announcements) {
        this.allAnnouncements = announcements;
        filteredAnnouncements = new ArrayList<>(announcements);
    }

    @NonNull
    @Override
    public RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_announcement, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycleViewAdapter.MyViewHolder holder, final int position) {
        final Announcement announcement = filteredAnnouncements.get(position);
        holder.title.setText(announcement.getTitre());
        holder.price.setText(announcement.getPrice() + "DHS");
        holder.image.setBackgroundResource(R.drawable.no_image);
        holder.cardView.setId(announcement.getId());
    }

    @Override
    public int getItemCount() {
        return filteredAnnouncements.size();
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    private final Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Announcement> filteredList = new ArrayList<>();
            if (constraint == null || constraint.toString().length() == 0) {
                filteredList.addAll(allAnnouncements);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Announcement item : allAnnouncements) {
                    if (item.getTitre().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredAnnouncements.clear();
            filteredAnnouncements.addAll((ArrayList<Announcement>) results.values);
            notifyDataSetChanged();
        }
    };

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView price;
        private final ImageView image;
        private final CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.carView);
        }
    }
}