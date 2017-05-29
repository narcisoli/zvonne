package com.example.narcis.zvonne.fragSecundare;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.narcis.zvonne.MainActivity;
import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.fragSecundare.istoriccomenzi;


public class multumimfragment extends Fragment {

    View myView;

private static multumimfragment instance;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        myView=inflater.inflate(R.layout.multumim,container,false);
        Button m=(Button)myView.findViewById(R.id.multumimcomandabuton);
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getFragmentManager().popBackStack();
               getFragmentManager().popBackStack();
               getFragmentManager().beginTransaction().replace(R.id.container,istoriccomenzi.getInstance()).addToBackStack("").commit();
            }
        });



        return myView;
    }
public static multumimfragment getInstance(){
        if (instance==null)
            instance=new multumimfragment();
        return instance;
}

}
