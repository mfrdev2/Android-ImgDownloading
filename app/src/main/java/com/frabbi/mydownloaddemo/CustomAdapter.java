package com.frabbi.mydownloaddemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<InFo> {


    private final List<InFo> list;
    private final Context context;

    public CustomAdapter(@NonNull Context context, int resource, List<InFo> list) {
        super(context, resource, list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      //  super.getView(position, convertView, parent);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view,parent,false);
        }
        InFo inFo = list.get(position);
        TextView id = convertView.findViewById(R.id.Id);
        TextView imgName = convertView.findViewById(R.id.nameId);
        ImageView img = convertView.findViewById(R.id.imageId);
        id.setText(inFo.getId()+".");
        imgName.setText(inFo.getImgName());
        img.setImageResource(inFo.getImg());
        return convertView;
    }
}
