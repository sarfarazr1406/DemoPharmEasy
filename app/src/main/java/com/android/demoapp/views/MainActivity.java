package com.android.demoapp.views;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.demoapp.R;
import com.android.demoapp.VolleyParser;
import com.android.demoapp.db.MedicineData;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MySingleton single = null;
    MyPageAdapter pageAdapter;
    public static Handler handler;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        single = new MySingleton(getApplicationContext());
        final RequestQueue r = single.getRequestQueue();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView splashView = (ImageView)findViewById(R.id.splashImg);
        final NavigationView nView = (NavigationView) findViewById(R.id.nav_view);
        final ProgressBar pBar = (ProgressBar) findViewById(R.id.progress);
        nView.setVisibility(View.INVISIBLE);
        final View v = (View) findViewById(R.id.appMain);
        v.setVisibility(View.INVISIBLE);
        splashView.setVisibility(View.VISIBLE);
        pBar.setVisibility(View.VISIBLE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        final JSONObject jObjSearch = null;
        final VolleyParser vp = new VolleyParser();
        String url = "https://www.1mg.com/api/v1/search/autocomplete?name=bar&pageSize=10000000&_=1435404923427";
        if (savedInstanceState == null) {
            JsonObjectRequest jReq = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    Log.i("saz", "received");
                    v.setVisibility(View.VISIBLE);
                    nView.setVisibility(View.VISIBLE);
                    splashView.setVisibility(View.INVISIBLE);
                    pBar.setVisibility(View.INVISIBLE);
                    vp.parseJSONObject(jsonObject, getBaseContext());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.i("saz", "Search " + volleyError.toString());
                }
            });
            jReq.setRetryPolicy(new DefaultRetryPolicy(
                    60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            single.addToRequestQueue(jReq);

        }
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Log.i("saz", "Handler receicved message");
                List<android.support.v4.app.Fragment> fragments = getFragments();

                v.setVisibility(View.VISIBLE);
                nView.setVisibility(View.VISIBLE);
                splashView.setVisibility(View.INVISIBLE);
                pBar.setVisibility(View.INVISIBLE);
                pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
                ViewPager pager =
                        (ViewPager) findViewById(R.id.pager);
                pager.setAdapter(pageAdapter);

            }
        };


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private List<android.support.v4.app.Fragment> getFragments() {
        List<android.support.v4.app.Fragment> fList = new ArrayList<android.support.v4.app.Fragment>();
        Cursor cursor = new MedicineData().getTaskCount();

        if (cursor != null) {

            while (cursor.moveToNext()) {
                fList.add(Fragment_Main.getInstance(cursor.getString(cursor.getColumnIndex(MedicineData.NAME)),
                        cursor.getString(cursor.getColumnIndex(MedicineData.MANUFACTURER)),
                        cursor.getDouble(cursor.getColumnIndex(MedicineData.OPRICE))));
            }
            cursor.close();
        }
        return fList;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        handler.sendMessage(new Message());
        super.onRestoreInstanceState(savedInstanceState);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

class MyPageAdapter extends FragmentPagerAdapter {
    private List<android.support.v4.app.Fragment> fragments;
    public MyPageAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }
    private Context ctx;
    public MyPageAdapter(android.support.v4.app.FragmentManager fm, List<android.support.v4.app.Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}


class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    MySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag("saz");
        req.setShouldCache(false);
        getRequestQueue().add(req);
    }
}