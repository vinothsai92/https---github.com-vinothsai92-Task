package com.task.interFace;

import com.task.model.ListResponse;

import androidx.fragment.app.Fragment;

public interface FragmentChangelistner {


    void parsingData(ListResponse listResponse,int id);

    void textChangeListner(String text,int id);
}
