package com.example.shredder.imview.Activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shredder.imview.Interfaces.OnImageFetchCompleteListener;
import com.example.shredder.imview.Model.PexelsStruct;
import com.example.shredder.imview.R;
import com.example.shredder.imview.Adapter.DashboardAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {


    RequestQueue requestQueue;
    RecyclerView recyclerView;
    DashboardAdapter dashboardAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    Menu menu;
    boolean isVertical=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestQueue = Volley.newRequestQueue(this);

        String URL = "https://api.pexels.com/v1/curated?per_page=15&page="+new Random().nextInt(20);


       populateImages(new OnImageFetchCompleteListener() {
           @Override
           public void onFetchCompleted(ArrayList<PexelsStruct> allPhotoList) {

               buildRecyclerView(allPhotoList);
           }
       },URL);

    }


    public void populateImages(final OnImageFetchCompleteListener onImageFetchCompleteListener,String URL) {

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<PexelsStruct> allPhotoArray = new ArrayList<>();

                try {
                    JSONArray photoArray = response.getJSONArray("photos");
                    for( int i = 0 ;  i< photoArray.length(); i++)
                    {
                        JSONObject eachObject = photoArray.getJSONObject(i);
                        JSONObject srcObject = eachObject.getJSONObject("src");

                         String original = srcObject.getString("original");
                         String large = srcObject.getString("large");
                         String large2x = srcObject.getString("large2x");
                         String medium = srcObject.getString("medium");
                         String small = srcObject.getString("small");
                         String portrait  = srcObject.getString("portrait");

                         allPhotoArray.add (new PexelsStruct(original,large,large2x,medium,small,portrait));
                    }

                    if (onImageFetchCompleteListener !=null)
                    {
                        onImageFetchCompleteListener.onFetchCompleted(allPhotoArray);
                    }
                }
                catch (JSONException e) {}

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "563492ad6f91700001000001153b2c669b2748e5ba2444cd3af97f60");
                return params;
            }
        };
        requestQueue.add(objectRequest);
    }

    private void buildRecyclerView(ArrayList<PexelsStruct> allPhotoArray) {

        recyclerView = findViewById(R.id.recyclerView);
        dashboardAdapter = new DashboardAdapter( this, allPhotoArray);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(dashboardAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.search_menu:

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                View view = getLayoutInflater().inflate(R.layout.popup_menu, null);
                final EditText searchEditText = (EditText) view.findViewById(R.id.search_editText_popup);
                Button searchButton = (Button) view.findViewById(R.id.search_btn_popup);
                dialogBuilder.setView(view);
                final AlertDialog dialog = dialogBuilder.create();
                dialog.show();

                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String searchString = searchEditText.getText().toString().trim();

                        String URL = "https://api.pexels.com/v1/search?query="+searchString+"&per_page=25&page="+ new Random().nextInt(30);

                        populateImages(new OnImageFetchCompleteListener() {
                            @Override
                            public void onFetchCompleted(ArrayList<PexelsStruct> allPhotoList) {
                                buildRecyclerView(allPhotoList);
                            }
                        },URL);

                        dialog.dismiss();
                    }

                });

                return true;
            case R.id.logout_menu:
                return true;

            case R.id.viewType:

                toggle();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toggle() {

        if (isVertical)
            horizontal();
        else
            vertical();

    }

    private  void vertical(){

        staggeredGridLayoutManager.setSpanCount(2);
        staggeredGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        MenuItem item = menu.findItem(R.id.viewType);
        item.setIcon(R.drawable.ic_scroll_horiz);

        isVertical=true;

    }

    private  void horizontal(){

        staggeredGridLayoutManager.setSpanCount(3);
        staggeredGridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        MenuItem item = menu.findItem(R.id.viewType);
        item.setIcon(R.drawable.ic_scroll_vertical);

        isVertical=false;

    }



}
