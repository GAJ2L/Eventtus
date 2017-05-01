package com.gaj2l.eventtus.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Event;
import com.gaj2l.eventtus.view.activities.BaseActivity;
import com.gaj2l.eventtus.view.fragments.DetailEventFragment;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

/**
 * Created by Jackson Majolo on 01/05/17.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private List<Event> list;

    public EventAdapter(List<Event> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_event_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Event event = getEvent(i);
        List<Activity> activities = ComponentProvider.getServiceComponent().getActivityService().getActivitiesByEvent(event.getId());
        int size = activities != null ? activities.size() : 0;

        viewHolder.setItemTitle(event.getName());
        viewHolder.setItemAmountActivities(size + " atividades");
        viewHolder.setItemDateStart(event.getDtStart().format(DateTimeFormatter.ofPattern("dd-MM-y HH:mm")));
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
        public TextView itemLocal;
        public TextView itemDateStart;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.txtNameFile);
            itemLocal = (TextView) itemView.findViewById(R.id.txtLocal);
            itemDateStart = (TextView) itemView.findViewById(R.id.txtDateStart);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickCard(v);
                }
            });
        }

        public void setItemTitle(String title) {
            itemTitle.setText(title);
        }

        public void setItemAmountActivities(String detail) {
            itemLocal.setText(detail);
        }

        public void setItemDateStart(String date) {
            itemDateStart.setText(date);
        }

        public void onClickCard(View v) {
            DetailEventFragment fragment = new DetailEventFragment();
            fragment.setEvent(list.get(getAdapterPosition()));
            ((BaseActivity) v.getContext()).getFragmentManager().beginTransaction().replace(R.id.fragment, fragment).addToBackStack("DetailEventFragment").commit();
        }
    }
}

