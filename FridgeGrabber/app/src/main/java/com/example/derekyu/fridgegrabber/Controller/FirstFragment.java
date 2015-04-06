package com.example.derekyu.fridgegrabber.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.DialogFragment;

import com.example.derekyu.fridgegrabber.R;
import com.example.derekyu.fridgegrabber.tools.PredicateLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class FirstFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //mPage = getArguments().getInt(ARG_PAGE);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        Button addButton = (Button) view.findViewById(R.id.addIngredients);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newPass = new Intent(getActivity(), ModifyIngredientsActivity.class);
                startActivity(newPass);
            }
        });
        return view;

    }



}