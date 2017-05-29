package com.example.narcis.zvonne.fragPrincipale;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.adaptori.adaptoreveniment;
import com.example.narcis.zvonne.obiecte.eveniment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class eventfragment extends Fragment {

    private static eventfragment instance;
    View myView;
        List<eveniment> ev=new ArrayList<>();
    private DatabaseReference myRef;
    private adaptoreveniment a;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        myView=inflater.inflate(R.layout.eventfragment,container,false);
        ListView lista=(ListView)myView.findViewById(R.id.eventlist);
          a=new adaptoreveniment(myView.getContext(),R.layout.eventadaptor,ev);
        lista.setAdapter(a);


        return myView;
    }
    public void setList(List<eveniment> evenimentList){
        this.ev=evenimentList;

    }
    public static eventfragment newInstance() {
        if (instance==null)
            instance = new eventfragment();
        return instance;
    }


}