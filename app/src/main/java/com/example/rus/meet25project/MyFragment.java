package com.example.rus.meet25project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MyFragment extends Fragment {
    private ImageView imageView;
    private String imageUri;

    public static Fragment newInstance(String s) {
        Bundle args = new Bundle();
        args.putString("image uri",s);
        MyFragment myFragment = new MyFragment();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item,container,false);
        if (getArguments() != null) {
            imageUri = getArguments().getString("image uri");
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        imageView = view.findViewById(R.id.fragment_image_view);
        Glide.with(this).load(imageUri).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ScaleActivity.newIntent(getActivity(), imageUri);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_page, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.change_to_recycler_view:
                Toast.makeText(getContext(),"to recycler view", Toast.LENGTH_LONG).show();
                Intent intent = MainActivity.newIntent(getContext());
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
