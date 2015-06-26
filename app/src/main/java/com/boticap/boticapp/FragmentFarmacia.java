package com.boticap.boticapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentFarmacia extends Fragment {

    View rootView;
    TextView farmaciasCercanas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fm_farmacia, container, false);

        farmaciasCercanas = (TextView) rootView.findViewById(R.id.text_farmacias_cercanas);
        farmaciasCercanas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent miIntent = new Intent(getActivity(), MapsActivity.class);
                startActivity(miIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
