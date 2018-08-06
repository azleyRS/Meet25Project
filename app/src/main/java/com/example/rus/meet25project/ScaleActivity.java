package com.example.rus.meet25project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ScaleActivity extends AppCompatActivity {
    String uri;
    ImageView imageView;

    private ScaleGestureDetector mScaleGestureDetector;

    private float mScaleFactor = 1.0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);
        uri = getIntent().getStringExtra("uri");
        imageView = findViewById(R.id.scale_image_view);
        Glide.with(this).load(uri).into(imageView);
        setTitle(getResources().getString(R.string.scale_view_title));
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
    }

    public static Intent newIntent(Context packageContext, String uri) {
        Intent intent = new Intent(packageContext, ScaleActivity.class);
        intent.putExtra("uri", uri);
        return intent;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {

        mScaleGestureDetector.onTouchEvent(motionEvent);

        return true;

    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){

            mScaleFactor *= scaleGestureDetector.getScaleFactor();

            mScaleFactor = Math.max(0.1f,

                    Math.min(mScaleFactor, 10.0f));

            imageView.setScaleX(mScaleFactor);

            imageView.setScaleY(mScaleFactor);

            return true;

        }

    }
}
