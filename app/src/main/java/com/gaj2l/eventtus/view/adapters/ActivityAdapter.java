package com.gaj2l.eventtus.view.adapters;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.view.activities.BaseActivity;
import com.gaj2l.eventtus.view.fragments.ActivityFragment;
import com.gaj2l.eventtus.view.fragments.DetailActivityFragment;

import java.util.List;

/**
 * Created by lucas on 14/04/17.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    private List<Activity> list;

    public ActivityAdapter(List<Activity> list) {
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
        viewHolder.setItemTitle(getActivity(i).getName());
        viewHolder.setItemDetail(getActivity(i).getLocalName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public Activity getActivity(int i) {
        return this.list.get(i);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTitle;
        public TextView itemDetail;
        public TextView btnRemove;

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

        public void setItemTitle(String title) {
            itemTitle.setText(title);
        }

        public void setItemDetail(String detail) {
            itemDetail.setText(detail);
        }

        public void onClickCard(View v) {
            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setActivity(list.get(getAdapterPosition()));
            ((BaseActivity) v.getContext()).getFragmentManager().beginTransaction().replace(R.id.fragment, fragment).addToBackStack("DetailActivityFragment").commit();
        }

        public void onRemove(View v) {
            Snackbar.make(v, "Atividade: " + getAdapterPosition() + " removido!", Snackbar.LENGTH_SHORT).show();
        }
    }
}

