package com.example.rudy.sensors;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rudy on 10.04.2018.
 */

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<Song> itemList;
    private LayoutInflater inflater;

    public ListViewAdapter(Context context, ArrayList<Song> itemList){
        this.itemList = itemList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflater = LayoutInflater.from(context); //tu moze byc problem
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.single_list_item, null);
            holder = new ViewHolder();
            setHolderViews(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setHolderFields(holder, position);

        return convertView;
    }

    public void setHolderViews(ViewHolder holder, View convertView){
        holder.holderImageView = convertView.findViewById(R.id.imageViewListItem);
        holder.sampleTitle = convertView.findViewById(R.id.nameTextView);
        holder.sampleDescription = convertView.findViewById(R.id.speciesTextView);

    }

    public void setHolderFields(ViewHolder holder, int position){
        holder.holderImageView.setImageResource(itemList.get(position).getIconId());
        holder.sampleTitle.setText(itemList.get(position).getSongName());
        holder.sampleDescription.setText(itemList.get(position).getSongSpecies());

    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    static class ViewHolder{
        ImageView holderImageView;
        TextView sampleTitle, sampleDescription;
    }
}
