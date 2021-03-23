package com.task;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog _ProgressDialog;
    private FrameLayout mFlContentLayout;

    public static Object getString(Class<?> cls, String response) {
        Gson gsonObj = new Gson();
        return gsonObj.fromJson(response, cls);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        _ProgressDialog = new ProgressDialog(BaseActivity.this);
        mFlContentLayout = (FrameLayout) findViewById(R.id.base_FlContent);

    }

    /**
     * Sets the content view for this activity. Since we are using navigation
     * bar, content view cannot be used as before.
     *
     * @param layout Layout to be set for the fragment or layout that is to be shown
     *               as the main content layout.
     */
    protected final void setMainContentView(int layout) {
        try {
            if (mFlContentLayout != null) {
                mFlContentLayout.addView(getLayoutInflater().inflate(layout, null));
            }
        } catch (Exception ignored) {
            //Log.d(getLocalClassName(), e.getMessage());
        }
    }


    public void showProgressDialog(boolean isShow) {
        if (isShow) {
            _ProgressDialog.setMessage("Please Wait....");
            _ProgressDialog.show();
        } else {
            _ProgressDialog.dismiss();
        }
    }

    public void showToast(String msg) {
        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

}
