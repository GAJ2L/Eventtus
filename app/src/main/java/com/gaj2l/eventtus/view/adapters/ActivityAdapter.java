package com.gaj2l.eventtus.view.adapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by lucas on 14/04/17.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder>
{
    private List<Activity> activities;
    private String color;

    public ActivityAdapter(List<Activity> list,String color)
    {
        this.activities = list;
        this.color = color;

        if ( this.activities != null )
            Collections.sort( activities );
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_activity_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i)
    {
        viewHolder.setItemTitle(getActivity(i).getName());
        viewHolder.setItemDateStart(getActivity(i).getDtStart().format(DateTimeFormatter.ofPattern("dd-MM-y HH:mm")));
        viewHolder.setItemLogo(String.valueOf(getActivity(i).getName().charAt(0)),color);
    }

    @Override
    public int getItemCount()
    {
        if (this.activities == null)
        {
            return 0;
        }
        return activities.size();
    }

    public Activity getActivity(int i) {
        return this.activities.get(i);
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView itemTitle;
        public TextView itemDateStart;
        public TextView itemLogo;

        public ViewHolder(View itemView)
        {
            super(itemView);
            itemTitle     = (TextView) itemView.findViewById(R.id.txtNameFile);
            itemDateStart = (TextView) itemView.findViewById(R.id.txtState);
            itemLogo      = (TextView) itemView.findViewById(R.id.logo);
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

        public void setItemLogo(String letter, String color)
        {
            itemLogo.setText(letter);
            if( color != null )
                itemLogo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
        }

        public void onClickCard(View v)
        {
            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setActivity(activities.get(getAdapterPosition()));
            ((BaseActivity) v.getContext()).getFragmentManager().beginTransaction().replace(R.id.fragment, fragment).addToBackStack("DetailActivityFragment").commit();
            ((BaseActivity) v.getContext()).getFragmentManager().beginTransaction().setBreadCrumbTitle("OI");
        }
    }
}