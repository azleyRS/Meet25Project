package com.example.rus.meet25project;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1001;
    RecyclerView recyclerView;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //permission check
        permissionCheck();
    }

    private void permissionCheck() {
        if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
        } else {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    } else {
        // Permission has already been granted
            setAdapter();
    }
    }

    private void setAdapter() {
        Log.v("tag", "here");

        myAdapter = new MyAdapter(getGalleryImages(), this, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, String uri) {
                Intent intent = ScaleActivity.newIntent(getApplication(),uri);
                getApplication().startActivity(intent);
            }
        });
        recyclerView.setAdapter(myAdapter);
    }

    //get String list of images' uri
    private List<String> getGalleryImages() {
        Uri uri;
        Cursor cursor;
        int column_index_data;
        List<String> listOfAllImages = new ArrayList<>();
        String absolutePathOfImage;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        cursor.close();
        return listOfAllImages;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    setAdapter();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.change_to_page_view:
                Toast.makeText(this,"test",Toast.LENGTH_LONG).show();
                Intent intent = PageActivity.newIntent(this);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, MainActivity.class);
    }
}
