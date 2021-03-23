package com.task.ui.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.task.DetailedView;
import com.task.R;
import com.task.adapter.ImageAdapter;
import com.task.model.ListResponse;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class HomeFragment extends Fragment {

    public static ArrayList<ListResponse> listResponse;
    public static ListResponse mSelectedData;
    public int currentPage = 1;
    public int TotalPage = 0;
    public Button btnNext;
    public Button btnPre;
    //    ArrayList<HashMap<String, Object>> MyArrList = new ArrayList<HashMap<String, Object>>();
    private HomeViewModel homeViewModel;
    private ListView lstView;
    private ImageAdapter imageAdapter;
    private ArrayList<ListResponse> templistResponse;
    private ProgressDialog _ProgressDialog;
    private RequestQueue requestQueue;
    private String BASE_URL = "https://api.github.com/repositories";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        lstView = (ListView) root.findViewById(R.id.listView1);
        // Next
        btnNext = (Button) root.findViewById(R.id.btnNext);
        // Previous
        btnPre = (Button) root.findViewById(R.id.btnPre);
        lstView.setClipToPadding(false);
        // Perform action on click
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentPage = currentPage + 1;
                ShowData();
            }
        });

        // Perform action on click
        btnPre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentPage = currentPage - 1;
                ShowData();
            }
        });
        templistResponse = new ArrayList<>();
        imageAdapter = new ImageAdapter(getActivity(), templistResponse);
        lstView.setAdapter(imageAdapter);
        _ProgressDialog = new ProgressDialog(getActivity());
        if (isOnline()) {
            serviceCall();
        }

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), DetailedView.class);
                mSelectedData=templistResponse.get(position);
                getActivity().startActivity(i);
            }
        });
        return root;
    }

    private void ShowData() {
        new LoadContentFromServer().execute();
    }

    private void serviceCall() {
        requestQueue = Volley.newRequestQueue(getActivity());
        showProgressDialog(true);
        JsonArrayRequest
                JsonArrayRequest
                = new JsonArrayRequest(
                Request.Method.GET,
                BASE_URL,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                showProgressDialog(false);
                dataparsing(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showProgressDialog(false);
                        showToast(error.networkResponse.toString());
                    }
                });
        requestQueue.add(JsonArrayRequest);
    }

    private void dataparsing(Object response) {
        listResponse = new ArrayList<>();
        Type listType = new TypeToken<ArrayList<ListResponse>>() {
        }.getType();
        Gson gsonObj = new Gson();
        listResponse = gsonObj.fromJson(response.toString(), listType);
        TotalPage = (listResponse.size() / 10);
        for (int i = 0; i < 10; i++) {
            templistResponse.add(listResponse.get(i));
        }
        imageAdapter.datachange(templistResponse);

    }

    private void showProgressDialog(boolean isShow) {
        if (isShow) {
            _ProgressDialog.setMessage("Please Wait....");
            _ProgressDialog.show();
        } else {
            _ProgressDialog.dismiss();
        }
    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            Toast.makeText(getActivity(), "Please check internet connection", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    class LoadContentFromServer extends AsyncTask<Object, Integer, Object> {

        @Override
        protected Object doInBackground(Object... params) {

            try {

                int displayPerPage = 10;   // Per Page
                int TotalRows = listResponse.size();
                int indexRowStart = ((displayPerPage * currentPage) - displayPerPage);

                if (TotalRows <= displayPerPage) {
                    TotalPage = 1;
                } else if ((TotalRows % displayPerPage) == 0) {
                    TotalPage = (TotalRows / displayPerPage);
                } else {
                    TotalPage = (TotalRows / displayPerPage) + 1;
                    TotalPage = (int) TotalPage;
                }
                int indexRowEnd = displayPerPage * currentPage;
                if (indexRowEnd > TotalRows) {
                    indexRowEnd = TotalRows;
                }

                for (int i = indexRowStart; i < indexRowEnd; i++) {
                    templistResponse.add(listResponse.get(i));
                    publishProgress(i);
                }


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onProgressUpdate(Integer... progress) {
            imageAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Object result) {

            // Disabled Button Next
            if (currentPage >= TotalPage) {
                btnNext.setEnabled(false);
            } else {
                btnNext.setEnabled(true);
            }

            // Disabled Button Previos
            if (currentPage <= 1) {
                btnPre.setEnabled(false);
            } else {
                btnPre.setEnabled(true);
            }

        }
    }
}
