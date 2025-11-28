package edu.sjsu.android.cs175finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> implements Filterable {
    private List<Friend> friends;
    private List<Friend> friendsFull;

    public FriendsAdapter(List<Friend> friends) {
        this.friends = friends;
        friendsFull = new ArrayList<>(friends);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Friend friend = friends.get(position);
        holder.name.setText(friend.getName());
        holder.username.setText(friend.getUsername());
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    @Override
    public Filter getFilter() {
        return friendFilter;
    }

    private Filter friendFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Friend> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(friendsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Friend friend : friendsFull) {
                    if (friend.getName().toLowerCase().startsWith(filterPattern)) {
                        filteredList.add(friend);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            friends.clear();
            friends.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView username;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.friendName);
            username = itemView.findViewById(R.id.friendUsername);
        }
    }
}
