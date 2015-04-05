package com.example.derekyu.fridgegrabber.Controller;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.derekyu.fridgegrabber.R;


public class FirstFragment extends Fragment {
    public static final String ARG_PAGE ="ARG_PAGE";

    private int mPage;

    public static FirstFragment newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FirstFragment fragment = new FirstFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        TextView textView = (TextView) view.findViewById(R.id.textForFrag);
        textView.setText("Fragment #" + mPage);
        return view;

    }
}