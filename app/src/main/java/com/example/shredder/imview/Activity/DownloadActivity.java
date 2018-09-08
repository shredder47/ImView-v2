package com.example.shredder.imview.Activity;

import android.Manifest;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shredder.imview.R;
import com.example.shredder.imview.Utils.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DownloadActivity extends AppCompatActivity {


    @BindView(R.id.imageView_fullScreen)
    ImageView imageView;


    DownloadManager downloadManager ;
    private static final String[] PERMISSIONS = { Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_NETWORK_STATE};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        ButterKnife.bind(this);

        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);


        //getting image url from activity
        if (getIntent().getExtras() != null)
            Picasso.get().load(getIntent().getStringExtra(Util.IMAGE_URL)).into(imageView);

        //listening to image download complete
        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @OnClick(R.id.floatingActionButton)
    void download(){


        int permissionExternalMemory = ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permissionExternalMemory != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(DownloadActivity.this, PERMISSIONS,1);
        else
            initDownload();

    }

    @OnClick(R.id.btn_setWallpaper)
        void setWallpaper(){

        Toast.makeText(this, "Zzz", Toast.LENGTH_SHORT).show();

        /**
         * 
         * Need a way to convert image from net to Bitmap
         */

//        WallpaperManager wallpaperManager = WallpaperManager.getInstance(DownloadActivity.this);
//        try {
//            wallpaperManager.setBitmap(aBitmap);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
    }

    void initDownload()
    {
        Uri url = Uri.parse(getIntent().getStringExtra(Util.IMAGE_URL));

        DownloadManager.Request imageDownloadRequest = new DownloadManager.Request(url);
        imageDownloadRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        imageDownloadRequest.setAllowedOverRoaming(false);
        imageDownloadRequest.setTitle("Downloading");
        imageDownloadRequest.setDescription("Downloading Image");
        imageDownloadRequest.setVisibleInDownloadsUi(true);
        imageDownloadRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/imView/" + System.currentTimeMillis()+".jpg");

        downloadManager.enqueue(imageDownloadRequest);
    }

    BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Download Complete", Toast.LENGTH_SHORT).show();
        }
    };



}

