package com.example.song.demo.dragsort;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.song.R;
import com.widget.dragsort.dsgv.ChannelModel;
import com.widget.dragsort.dsgv.DragGridBaseAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Desc:频道编辑
 * Author：LiZhimin
 * Date：2016/6/30 17:53
 */
public class ChannelEditAdapter extends BaseAdapter implements DragGridBaseAdapter {
    private List<ChannelModel> list;
    private Context context;
    private int mHidePosition = -1;


    public ChannelEditAdapter(Context context, List<ChannelModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 由于复用convertView导致某些item消失了，所以这里不复用item，
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.item_channel_edit, null);
        ImageView mImageView = (ImageView) convertView.findViewById(R.id.iv_channel_icon);
        TextView mTextView = (TextView) convertView.findViewById(R.id.tv_channel_name);
        ChannelModel model = list.get(position);
        Glide.with(context).load(model.getImg()).into(mImageView);
        mTextView.setText(model.getName());
        if (position == mHidePosition) {
            convertView.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }


    @Override
    public void reorderItems(int oldPosition, int newPosition) {
        ChannelModel model = list.get(oldPosition);
        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                Collections.swap(list, i, i + 1);
            }
        } else if (oldPosition > newPosition) {
            for (int i = oldPosition; i > newPosition; i--) {
                Collections.swap(list, i, i - 1);
            }
        }
        list.set(newPosition, model);
    }

    @Override
    public void setHideItem(int hidePosition) {
        this.mHidePosition = hidePosition;
        notifyDataSetChanged();
    }

    @Override
    public void removeItem(int removePosition) {
        list.remove(removePosition);
        notifyDataSetChanged();

    }


}
