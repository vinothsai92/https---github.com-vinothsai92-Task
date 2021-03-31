package com.task.ui.fragment.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.task.MainActivity;
import com.task.R;
import com.task.adapter.ImageAdapter;
import com.task.interFace.FragmentChangelistner;
import com.task.model.ListResponse;
import com.task.ui.fragment.BaseFragment;
import com.task.ui.fragment.DetailedFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class HomeFragment extends BaseFragment implements FragmentChangelistner {

    public static ListResponse mSelectedData;
    private static ArrayList<ListResponse> listResponse;
    private int TOTAL_LIST_ITEMS = 0;
    private int NUM_ITEMS_PAGE = 0;
    private HomeViewModel homeViewModel;
    private ListView lstView;
    private ImageAdapter imageAdapter;
    private ArrayList<ListResponse> templistResponse;

    private int noOfBtns;
    private Button[] btns;
    private TextView title;
    private FragmentChangelistner changeFragment;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(root);


        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void findViews(View root) {
        lstView = (ListView) root.findViewById(R.id.listView1);
        title = (TextView) root.findViewById(R.id.title);
        View empty = root.findViewById(R.id.empty);
        lstView.setClipToPadding(false);
        templistResponse = new ArrayList<>();
        imageAdapter = new ImageAdapter(getActivity(), templistResponse,this);
        lstView.setAdapter(imageAdapter);
        lstView.setEmptyView(empty);


        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                changeFragment.changeFragment(new DetailedFragment(),"");
            }
        });

        if (getArguments() != null) {
            String response = getArguments().getString("selectedData");
            assert response != null;
            try {
                JSONArray array = new JSONArray(response);
                dataparsing(array,root);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



    }




    private void dataparsing(Object response, View root) {
        listResponse = new ArrayList<>();
        Type listType = new TypeToken<ArrayList<ListResponse>>() {
        }.getType();
        Gson gsonObj = new Gson();
        listResponse = gsonObj.fromJson(response.toString(), listType);
        if (listResponse.size() > 0) {
            TOTAL_LIST_ITEMS = listResponse.size();
            NUM_ITEMS_PAGE = 10;
            footer(root);
            loadList(0);
            CheckBtnBackGroud(0);
        }
    }

    @SuppressLint("SetTextI18n")
    private void footer(View view) {
        int val = TOTAL_LIST_ITEMS % NUM_ITEMS_PAGE;
        val = val == 0 ? 0 : 1;
        noOfBtns = TOTAL_LIST_ITEMS / NUM_ITEMS_PAGE + val;

        LinearLayout ll = (LinearLayout) view.findViewById(R.id.btnLay);

        btns = new Button[noOfBtns];

        for (int i = 0; i < noOfBtns; i++) {
            btns[i] = new Button(getActivity());
            btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btns[i].setText("" + (i + 1));

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.addView(btns[i], lp);

            final int j = i;
            btns[j].setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    loadList(j);
                    CheckBtnBackGroud(j);
                }
            });
        }

    }

    private void CheckBtnBackGroud(int index) {
        title.setText(String.valueOf("Page " + (index + 1) + " of " + noOfBtns));
        for (int i = 0; i < noOfBtns; i++) {
            if (i == index) {
                btns[index].setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_icon));
                btns[i].setTextColor(getResources().getColor(android.R.color.white));
            } else {
                btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btns[i].setTextColor(getResources().getColor(android.R.color.black));
            }
        }

    }


    private void loadList(int number) {
        templistResponse.clear();
        int start = number * NUM_ITEMS_PAGE;
        for (int i = start; i < (start) + NUM_ITEMS_PAGE; i++) {
            if (i < listResponse.size()) {
                templistResponse.add(listResponse.get(i));
            } else {
                break;
            }
        }
        imageAdapter.datachange(templistResponse);
    }

    @Override
    public void parsingData(ListResponse mListResponse,int id){

        for (int i = 0; i < listResponse.size(); i++) {
            if(id==listResponse.get(i).getId() &&listResponse.get(i).getComments()!=null &&listResponse.get(i).getComments().length()>0){
                mListResponse.setComments(listResponse.get(i).getComments());
                break;
            }
        }
        Intent intent=new Intent(getActivity(), MainActivity.class);
        intent.putExtra("myData", (Parcelable) mListResponse);
        startActivity(intent);

    }

    @Override
    public void textChangeListner(String text, int id) {
        for (int i = 0; i < listResponse.size(); i++) {
            if(listResponse.get(i).getId()==id){
                listResponse.get(i).setComments(text);
            }
        }
    }
}
