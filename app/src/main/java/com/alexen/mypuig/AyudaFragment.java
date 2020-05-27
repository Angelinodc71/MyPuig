package com.alexen.mypuig;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class AyudaFragment extends Fragment {


    public AyudaFragment() {
        // Required empty public constructor
    }
    ExpandableRelativeLayout expandableLayout1, expandableLayout2, expandableLayout3, expandableLayout4;
    Button buttonLayout1, buttonLayout2, buttonLayout3, buttonLayout4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ayuda, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonLayout1 = view.findViewById(R.id.expandableButton1);
        buttonLayout2 = view.findViewById(R.id.expandableButton2);
        buttonLayout3 = view.findViewById(R.id.expandableButton3);
        buttonLayout4 = view.findViewById(R.id.expandableButton4);
        expandableLayout1 = (ExpandableRelativeLayout) view.findViewById(R.id.expandableLayout1);
        expandableLayout2 = (ExpandableRelativeLayout) view.findViewById(R.id.expandableLayout2);
        expandableLayout3 = (ExpandableRelativeLayout) view.findViewById(R.id.expandableLayout3);
        expandableLayout4 = (ExpandableRelativeLayout) view.findViewById(R.id.expandableLayout4);

        expandableLayout1.toggle(); // toggle expand and collapse
        expandableLayout2.toggle(); // toggle expand and collapse
        expandableLayout3.toggle(); // toggle expand and collapse
        expandableLayout4.toggle(); // toggle expand and collapse

        buttonLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout1.toggle(); // toggle expand and collapse
            }
        });

        buttonLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout2.toggle(); // toggle expand and collapse
            }
        });

        buttonLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout3.toggle(); // toggle expand and collapse
            }
        });

        buttonLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout4.toggle(); // toggle expand and collapse
            }
        });
    }
}
