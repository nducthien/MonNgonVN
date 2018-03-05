package com.example.dell.monngonviet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dell.monngonviet.R;
import com.example.dell.monngonviet.model.player1;

import java.util.ArrayList;

public class PlayerAdapter1 extends ArrayAdapter<player1> {
    public PlayerAdapter1(Context context, ArrayList<player1> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup patert) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layoutmenu, patert, false);
        }
        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        TextView txtGioiThieu = (TextView) convertView.findViewById(R.id.txtGioiThieu);

        player1 pl = getItem(position);
        if (pl != null) {
            txtName.setText(pl.getName().toString());
            txtGioiThieu.setText(pl.getGioithieu().toString());
        }
        return convertView;
    }

}