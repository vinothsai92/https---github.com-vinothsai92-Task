package com.task;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.task.model.ListResponse;

import androidx.appcompat.app.AppCompatActivity;

import static com.task.ui.home.HomeFragment.mSelectedData;

public class DetailedView extends AppCompatActivity {
    TextView mCommentBtn, mData, mData_;
    ImageView image_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
        init();
    }

    private void init() {
        mCommentBtn = (TextView) findViewById(R.id.comment);
        mData = (TextView) findViewById(R.id.data_);
        mData_ = (TextView) findViewById(R.id.data_2);
        image_view = (ImageView) findViewById(R.id.image_view);

        mCommentBtn.setText(mSelectedData.getOwner().getGravatarId());
        mData.setText(mSelectedData.getFullName());
        mData_.setText(mSelectedData.getName());
        Picasso.get().load(mSelectedData.getOwner().getAvatarUrl()).into(image_view);
    }
}
