package com.example.dongwoo.produnt;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class sellerList_Adapter extends BaseAdapter {
    Context context;
    List<sellerData> rowItems;
    sellerList_Adapter(Context context, List<sellerData> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }
    @Override
    public int getCount() {
        return rowItems.size();
    }
    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }
    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
    /* private view holder class */
    private class ViewHolder {
        TextView seller_name;
        TextView good_rate;
        TextView bad_rate;
        TextView data_volume;
        TextView price;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.seller_row_layout, null);
            holder = new ViewHolder();
            holder.seller_name = (TextView) convertView.findViewById(R.id.seller_name);
            holder.good_rate = (TextView) convertView.findViewById(R.id.good_rate);
            holder.bad_rate = (TextView) convertView.findViewById(R.id.bad_rate);
            holder.data_volume = (TextView) convertView.findViewById(R.id.data_volume);
            holder.price = (TextView)convertView.findViewById(R.id.cash);
//            holder.contactType = (TextView) convertView
//                    .findViewById(R.id.contact_type);
            sellerData row_pos = rowItems.get(position);
            //holder.profile_pic.setImageResource(row_pos.getProfile_pic_id());
            holder.seller_name.setText(row_pos.getSeller_name());
            holder.good_rate.setText(row_pos.getGood_rate());
            holder.bad_rate.setText(row_pos.getBad_rate());
            holder.data_volume.setText(row_pos.getData_volume());
            holder.price.setText(row_pos.getPrice());
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}
