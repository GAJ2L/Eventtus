package com.gaj2l.eventtus.view.adapters;
/**
 * Created by Shade on 5/9/2016.
 */

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.models.Event;
import com.gaj2l.eventtus.view.fragments.ActivityFragment;
import com.gaj2l.eventtus.view.fragments.DetailEventFragment;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private List<Event> list;

    public EventAdapter(List<Event> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.setItemTitle(getEvent(i).getName());
        viewHolder.setItemDetail(getEvent(i).getDescription());
        viewHolder.setEvent(i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public Event getEvent(int i) {
        return this.list.get(i);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTitle;
        public TextView itemDetail;
        public TextView btnRemove;
        public int event;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.item_title);
            itemDetail = (TextView) itemView.findViewById(R.id.item_detail);
            btnRemove = (TextView) itemView.findViewById(R.id.btnRemove);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickCard(v);
                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRemove(v);
                }
            });
        }

        public void setEvent(int event) {
            this.event = event;
        }

        public void setItemTitle(String title) {
            itemTitle.setText(title);
        }

        public void setItemDetail(String detail) {
            itemDetail.setText(detail);
        }

        public void onClickCard(View v) {
            DetailEventFragment fragment = new DetailEventFragment();
            fragment.setEvent(list.get(getAdapterPosition()));
            ((Activity) v.getContext()).getFragmentManager().beginTransaction().replace(R.id.fragment, fragment ).addToBackStack("DetailEventFragment").commit();
        }

        public void onRemove(View v) {
            Snackbar.make(v, "Evento: " + event + " removido!", Snackbar.LENGTH_SHORT).show();
        }
    }
}
