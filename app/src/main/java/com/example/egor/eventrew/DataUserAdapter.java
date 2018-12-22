package com.example.egor.eventrew;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.egor.eventrew.model.Event;
import com.example.egor.eventrew.model.User;

import java.util.List;

class DataUserAdapter extends RecyclerView.Adapter<DataUserAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<User> users;
    //private OnItemClickListener listener;

    DataUserAdapter(Context context, List<User> users, OnItemClickListener listener) {
        this.users = users;
        this.inflater = LayoutInflater.from(context);
        //this.listener = listener;
    }
    @Override
    public DataUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    public void setData(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull DataUserAdapter.ViewHolder holder, int position) {
        final User user = users.get(position);
        holder.nameView.setText(user.getFirstname());
        holder.companyView.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return users != null? users.size(): 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView nameView, companyView;
        ViewHolder(View view){
            super(view);
            this.view = view;
            nameView = (TextView) view.findViewById(R.id.userName);
            companyView = (TextView) view.findViewById(R.id.userEmail);
        }
    }

    public interface OnItemClickListener {
        void onClick(User user);
    }
}