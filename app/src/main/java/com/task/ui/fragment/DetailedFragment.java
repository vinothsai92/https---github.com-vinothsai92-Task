package com.task.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.task.R;
import com.task.model.ListResponse;

import androidx.annotation.NonNull;

public class DetailedFragment extends BaseFragment {
    private ListResponse listResponse;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detailed_view, container, false);
        if (getArguments() != null) {
            listResponse = getArguments().getParcelable("selectedData");
        }
        init(root);
        return root;
    }

    @SuppressLint("SetTextI18n")
    private void init(View root) {
        TextView mCommentBtn = (TextView) root.findViewById(R.id.comment);
        TextView mData = (TextView) root.findViewById(R.id.data_);
        TextView mData_ = (TextView) root.findViewById(R.id.data_2);
        TextView mData_3 = (TextView) root.findViewById(R.id.data_3);
        TextView mData_4 = (TextView) root.findViewById(R.id.data_4);
        TextView mData_5 = (TextView) root.findViewById(R.id.data_5);
        TextView mData_6 = (TextView) root.findViewById(R.id.data_6);
        ImageView image_view = (ImageView) root.findViewById(R.id.image_view);
        String comment="-";
        if (listResponse.getComments() != null) {
            comment=listResponse.getComments();
        }
        mCommentBtn.setText("Comments: " + comment);
        mData.setText("FullName : " + listResponse.getFullName());
        mData_.setText("Name : " + listResponse.getName());
        mData_3.setText("Description : " + listResponse.getDescription());
        mData_4.setText("Node ID : " + listResponse.getNodeId());
        mData_5.setText("User ID : " + listResponse.getId());
        mData_6.setText("HTML URL : " + listResponse.getHtmlUrl());
    }
}
