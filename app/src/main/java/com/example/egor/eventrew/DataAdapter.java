package com.example.egor.eventrew;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.egor.eventrew.model.Event;

import java.util.List;

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Event> events;
    private OnItemClickListener listener;

    DataAdapter(Context context, List<Event> events, OnItemClickListener listener) {
        this.events = events;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.event_item, parent, false);
        return new ViewHolder(view);
    }

    public void setData(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        final Event event = events.get(position);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(event);
            }
        });
        holder.nameView.setText(event.getName());
        holder.companyView.setText(event.getInformation());
    }

    @Override
    public int getItemCount() {
        return events != null? events.size(): 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView nameView, companyView;
        ViewHolder(View view){
            super(view);
            this.view = view;
            nameView = (TextView) view.findViewById(R.id.nameEvent);
            companyView = (TextView) view.findViewById(R.id.eventInformation);
        }
    }

    public interface OnItemClickListener {
        void onClick(Event event);
    }
}