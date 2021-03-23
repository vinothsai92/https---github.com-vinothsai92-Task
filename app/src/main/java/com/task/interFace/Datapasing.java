package com.task.interFace;

import android.content.Context;

import com.task.model.ListResponse;

import java.util.ArrayList;

public interface Datapasing {
    void passData(ArrayList<ListResponse> listResponse, Context context);

}