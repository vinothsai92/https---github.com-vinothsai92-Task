package com.task.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.task.DetailedView;
import com.task.R;
import com.task.model.ListResponse;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ListResponse>  templistResponse;

    public ImageAdapter(Context context, ArrayList<ListResponse> mTemplistResponse) {
        mContext = context;
        templistResponse=mTemplistResponse;
    }

    public void datachange(ArrayList<ListResponse> mTemplistResponse) {
        templistResponse=mTemplistResponse;
        notifyDataSetChanged();
    }

    public int getCount() {
        return templistResponse.size();
    }

    public Object getItem(int position) {
        return templistResponse.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row, null);
        }

        // ColImagePath
        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_image);
        imageView.getLayoutParams().height = 60;
        imageView.getLayoutParams().width = 60;
        imageView.setPadding(5, 5, 5, 5);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        try {
            Picasso.get().load(templistResponse.get(position).getOwner().getAvatarUrl()).into(imageView);

        } catch (Exception e) {
            // When Error
            imageView.setImageResource(android.R.drawable.ic_menu_report_image);
        }

        // ColImageID
        TextView txtImgID = (TextView) convertView.findViewById(R.id.main_title);
        txtImgID.setPadding(10, 0, 0, 0);
        txtImgID.setText(templistResponse.get(position).getName());

        // ColItemID
        TextView txtItemID = (TextView) convertView.findViewById(R.id.sub_title);
        txtItemID.setPadding(50, 0, 0, 0);
        txtItemID.setText(templistResponse.get(position).getDescription());



        return convertView;

    }
}