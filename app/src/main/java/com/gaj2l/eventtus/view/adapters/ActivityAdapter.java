package com.gaj2l.eventtus.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.view.activities.BaseActivity;
import com.gaj2l.eventtus.view.fragments.DetailActivityFragment;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

/**
 * Created by lucas on 14/04/17.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    private List<Activity> activities;

    public ActivityAdapter(List<Activity> list) {
        this.activities = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_activity_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.setItemTitle(getActivity(i).getName());
        viewHolder.setItemDateStart(getActivity(i).getDtStart().format(DateTimeFormatter.ofPattern("dd-MM-y HH:mm")));
    }

    @Override
    public int getItemCount() {
        if (this.activities == null) {
            return 0;
        }
        return activities.size();
    }


    public Activity getActivity(int i) {
        return this.activities.get(i);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTitle;
        public TextView itemDateStart;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.txtNameFile);
            itemDateStart = (TextView) itemView.findViewById(R.id.txtState);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickCard(v);
                }
            });
        }

        public void setItemTitle(String title)
        {
            itemTitle.setText(title);
        }

        public void setItemDateStart(String date) {
            itemDateStart.setText(date);
        }

        public void onClickCard(View v) {
            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setActivity(activities.get(getAdapterPosition()));
            ((BaseActivity) v.getContext()).getFragmentManager().beginTransaction().replace(R.id.fragment, fragment).addToBackStack("DetailActivityFragment").commit();
            ((BaseActivity) v.getContext()).getFragmentManager().beginTransaction().setBreadCrumbTitle("OI");
        }
    }
}

