package com.task;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.task.model.ListResponse;
import com.task.ui.fragment.DetailedFragment;
import com.task.ui.fragment.dashboard.DashboardFragment;
import com.task.ui.fragment.home.HomeFragment;
import com.task.ui.fragment.notifications.NotificationsFragment;

import org.json.JSONArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG_FRAGMENT_HOME = "tag_frag_home";
    private static final String TAG_FRAGMENT_DASHBOARD = "tag_frag_dashboard";
    private static final String TAG_FRAGMENT_NOTIFICATION = "tag_frag_notification";
    private static final String TAG_FRAGMENT_DETAIL = "tag_frag_detail";
    private ListResponse listResponse;
    private String bundlevalue = null;
    private String BASE_URL = "https://api.github.com/repositories";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if (intent != null) {
            listResponse = (ListResponse) intent.getParcelableExtra("myData");
        }
        init();
    }

    private void init() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
        if (listResponse != null) {
            Bundle args = new Bundle();
            args.putParcelable("selectedData", listResponse);
            DetailedFragment detailedFragment = new DetailedFragment();
            detailedFragment.setArguments(args);
            switchFragment(detailedFragment, TAG_FRAGMENT_DETAIL);
        } else {
            serviceCall();
        }


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_home:
//                switchFragment(new HomeFragment(), TAG_FRAGMENT_HOME);
                serviceCall();
                break;
            case R.id.navigation_dashboard:
                switchFragment(new DashboardFragment(), TAG_FRAGMENT_DASHBOARD);
                break;
            case R.id.navigation_notifications:
                switchFragment(new NotificationsFragment(), TAG_FRAGMENT_NOTIFICATION);
                break;
        }
        return false;
    }

    private void switchFragment(Fragment fragment, String tag) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment, tag)
                    .commit();
        }
    }

    private void serviceCall() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
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
                callHomeFragmant(response);

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

    private void callHomeFragmant(JSONArray response) {
        Bundle args = new Bundle();
        args.putString("selectedData", String.valueOf(response));
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(args);
        switchFragment(homeFragment, TAG_FRAGMENT_DETAIL);
    }

}
