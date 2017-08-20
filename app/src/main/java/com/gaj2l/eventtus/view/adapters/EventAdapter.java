package com.gaj2l.eventtus.view.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Util;
import com.gaj2l.eventtus.models.Activity;
import com.gaj2l.eventtus.models.Event;
import com.gaj2l.eventtus.view.activities.BaseActivity;
import com.gaj2l.eventtus.view.fragments.DetailEventFragment;

import java.util.Collections;
import java.util.List;

/**
 * Created by Jackson Majolo on 01/05/17.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>
{
    private List<Event> events;

    public EventAdapter(List<Event> list) {
        this.events = list;

        Collections.sort( events );
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_event_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i)
    {
        Event event = getEvent(i);
        viewHolder.setItemTitle(event.getName());
        viewHolder.setItemDate(Util.getAllDateFomatted(event.getDtStart()),Util.getAllDateFomatted(event.getDtEnd()));
        viewHolder.setItemState(Event.STATE_TITLE[event.getState()],Event.STATE_DRAWABLES[event.getState()],Event.STATE_COLORS[event.getState()]);

        if( event.getLogo() == null )
        {
            viewHolder.setTextLogo(String.valueOf(event.getContactName().toUpperCase().charAt(0)),event.getCor());
        }
        else
        {
            viewHolder.setImageLogo(event.getLogo());
        }
    }

    @Override
    public int getItemCount()
    {
        if (this.events == null)
        {
            return 0;
        }
        return events.size();
    }

    public Event getEvent(int i) {
        return this.events.get(i);
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView  itemTitle;
        private TextView  itemDtInicial;
        private TextView  itemDtFinal;
        private TextView  txtLogo;
        private ImageView imgLogo;
        private TextView  itemState;
        private View      view;
        private Context   context;

        public ViewHolder(View itemView)
        {
            super(itemView);
            itemTitle     = (TextView) itemView.findViewById(R.id.txtNameFile);
            itemDtFinal   = (TextView) itemView.findViewById(R.id.txtDtFinal);
            itemDtInicial = (TextView) itemView.findViewById(R.id.txtDtInicial);
            itemState     = (TextView) itemView.findViewById(R.id.txtState);
            txtLogo       = (TextView) itemView.findViewById(R.id.logo);
            imgLogo       = (ImageView) itemView.findViewById(R.id.imgLogo);
            view          = itemView;
            context       = itemView.getContext();
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

        public void setItemDate(String inicio, String fim)
        {
            itemDtInicial.setText(inicio);
            itemDtFinal.setText(fim);
        }

        public void setItemState(int name,int drawable, int color)
        {
            itemState.setCompoundDrawablesWithIntrinsicBounds(drawable,0,0,0);
            itemState.setText(view.getResources().getString(name));
            itemState.setTextColor(view.getResources().getColor(color,null));
        }

        public void setImageLogo(String imageLogo)
        {
            imgLogo.setImageBitmap(Util.base642bitmap(imageLogo));
            txtLogo.setVisibility(View.INVISIBLE);
        }

        public void setTextLogo(String textLogo, String color)
        {
            txtLogo.setText(textLogo);
            if( color != null )
                txtLogo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
        }

        public void onClickCard(View v)
        {
            DetailEventFragment fragment = new DetailEventFragment();
            fragment.setEvent(events.get(getAdapterPosition()));
            ((BaseActivity) v.getContext()).getFragmentManager().beginTransaction().replace(R.id.fragment, fragment).addToBackStack("DetailEventFragment").commit();
        }
    }
}