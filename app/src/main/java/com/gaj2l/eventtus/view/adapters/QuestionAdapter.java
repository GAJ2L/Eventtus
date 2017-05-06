package com.gaj2l.eventtus.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaj2l.eventtus.R;

import java.util.List;

/**
 * Created by artur on 5/4/17.
 */

public class QuestionAdapter
    extends
        RecyclerView.Adapter<QuestionAdapter.ViewHolder>
{
    private List<String> messages;

    public QuestionAdapter( List<String> list)
    {
        this.messages = list;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.question_list, viewGroup, false );

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i)
    {
        viewHolder.setMessage( messages.get(i) );
    }

    @Override
    public int getItemCount()
    {
        if (this.messages == null)
        {
            return 0;
        }

        return messages.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView message;

        public ViewHolder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.questionListValue);
        }

        public void setMessage( String msg ) {
        message.setText(msg);
    }
    }
}
