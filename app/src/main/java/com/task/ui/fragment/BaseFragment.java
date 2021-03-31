package com.task.ui.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


public class BaseFragment extends Fragment {
    private AlertDialog.Builder builder = null;
    private AlertDialog alertDialog = null;
    private ProgressDialog _ProgressDialog=null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return null;
    }


    public boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            interNetAlert();
            return false;
        }
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            interNetAlert();
            return false;
        }
    }

    public boolean isNetworkAvailableWithoutAlert() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            //interNetAlert();
            return false;
        }
    }



    /**
     * Convert object to json
     *
     * @param object
     * @return json
     */
    public String Stringparams(Object object) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(object);
    }

    public Object getString(Class<?> cls, String response) {
        Gson gsonObj = new Gson();
        return gsonObj.fromJson(response, cls);
    }

    public void interNetAlert() {
        new AlertDialog.Builder(getActivity())
                .setTitle("No Internet Connection")
                .setMessage("Please turn on internet connection and then try again")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog(boolean isShow){
        if(isShow){
            if(_ProgressDialog==null){
                _ProgressDialog = new ProgressDialog(getActivity());
                _ProgressDialog.setMessage("Please Wait....");
                _ProgressDialog.show();
            }
        }else{
            _ProgressDialog.dismiss();
            _ProgressDialog=null;
        }
    }


}



