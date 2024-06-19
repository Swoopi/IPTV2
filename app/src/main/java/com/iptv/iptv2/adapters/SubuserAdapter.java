package com.iptv.iptv2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iptv.iptv2.R;

import java.util.List;

public class SubuserAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> subuserNames;

    public SubuserAdapter(Context context, List<String> subuserNames) {
        super(context, R.layout.item_subuser, subuserNames);
        this.context = context;
        this.subuserNames = subuserNames;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_subuser, parent, false);
        }

        TextView subuserNameTextView = convertView.findViewById(R.id.subuserNameTextView);
        subuserNameTextView.setText(subuserNames.get(position));

        return convertView;
    }
}
