package com.gaj2l.eventtus.view.adapters;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.models.Attachment;

import java.util.List;

/**
 * Created by lucas.tomasi on 29/04/17.
 */

public class AttachmentAdapter extends RecyclerView.Adapter<AttachmentAdapter.ViewHolder> {
    private List<Attachment> attachments;

    public AttachmentAdapter(List<Attachment> list) {
        this.attachments = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_attachment, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.setItemTitle(getAttachment(i).getName());
        viewHolder.setItemSize(getAttachment(i).getSize());
        viewHolder.setItemType(getAttachment(i).getType());
    }

    @Override
    public int getItemCount() {
        if (this.attachments == null) {
            return 0;
        }
        return attachments.size();
    }


    public Attachment getAttachment(int i) {
        return this.attachments.get(i);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView itemSize;
        public TextView itemType;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.txtNameFile);
            itemSize = (TextView) itemView.findViewById(R.id.txtSize);
            itemType = (TextView) itemView.findViewById(R.id.icType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickCard(v);
                }
            });
        }

        public void setItemTitle(String title) {
            itemName.setText(title);
        }

        public void setItemSize(String size) {
            itemSize.setText(size);
        }

        public void setItemType(int type) {
            itemType.setCompoundDrawablesWithIntrinsicBounds(Attachment.TYPES_DRAWABLES[type], 0, 0, 0);
        }

        public void onClickCard(View v) {
            Snackbar.make(v, "Baixando...", Snackbar.LENGTH_LONG).show();
        }
    }
}