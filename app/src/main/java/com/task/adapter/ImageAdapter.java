package com.task.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.task.R;
import com.task.interFace.FragmentChangelistner;
import com.task.model.ListResponse;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ListResponse> templistResponse;
    private FragmentChangelistner fragmentChangelistner;

    public ImageAdapter(Context context, ArrayList<ListResponse> mTemplistResponse, FragmentChangelistner fragmentChangelistner) {
        mContext = context;
        templistResponse = mTemplistResponse;
        this.fragmentChangelistner = fragmentChangelistner;
    }

    public void datachange(ArrayList<ListResponse> mTemplistResponse) {
        templistResponse = mTemplistResponse;
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
        ViewHolder viewHolder;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.get().load(templistResponse.get(position).getOwner().getAvatarUrl()).into(viewHolder.imageView);
        viewHolder.txtImgID.setPadding(10, 0, 0, 0);
        viewHolder.txtImgID.setText(templistResponse.get(position).getName());
        viewHolder.txtItemID.setPadding(30, 0, 0, 0);
        viewHolder.txtItemID.setText(templistResponse.get(position).getDescription());
        viewHolder.editTextComment.setText(templistResponse.get(position).getComments());

        viewHolder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentChangelistner.parsingData(templistResponse.get(position), templistResponse.get(position).getId());
            }
        });

        viewHolder.editTextComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                fragmentChangelistner.textChangeListner(charSequence.toString(), templistResponse.get(position).getId());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return convertView;

    }

    static class ViewHolder {
        ImageView imageView;
        TextView txtImgID, txtItemID, viewMore;
        EditText editTextComment;

        ViewHolder(View v) {
            imageView = (ImageView) v.findViewById(R.id.list_image);
            viewMore = (TextView) v.findViewById(R.id.view_more);
            txtImgID = (TextView) v.findViewById(R.id.main_title);
            txtItemID = (TextView) v.findViewById(R.id.sub_title);
            editTextComment = (EditText) v.findViewById(R.id.comment);
        }
    }
}