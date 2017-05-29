package com.example.narcis.zvonne.fragSecundare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.narcis.zvonne.R;

import java.util.ArrayList;
import java.util.List;


public class desprefragment extends Fragment {

    View myView;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        myView=inflater.inflate(R.layout.despre,container,false);
        Button f=(Button)myView.findViewById(R.id.facebook);
        Button navigare=(Button)myView.findViewById(R.id.navigheaza);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/cafeneaua.zvonne.3";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });




        return myView;
    }


}