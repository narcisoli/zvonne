package com.example.narcis.zvonne.fragPrincipale;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.adaptori.adaptoreveniment;
import com.example.narcis.zvonne.fragSecundare.event.eventFRAG1;
import com.example.narcis.zvonne.fragSecundare.event.eventFRAG2;
import com.example.narcis.zvonne.fragSecundare.event.eventdetalii;
import com.example.narcis.zvonne.obiecte.eveniment;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;


public class eventfragment extends Fragment {

    private static eventfragment instance;
    View myView;
    List<eveniment> evenimentList = new ArrayList<>();
    private DatabaseReference myRef;
    private adaptoreveniment a;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        myView = inflater.inflate(R.layout.eventfragment, container, false);
        ListView lista = (ListView) myView.findViewById(R.id.eventlist);
        a = new adaptoreveniment(myView.getContext(), R.layout.eventadaptor, evenimentList);
        lista.setAdapter(a);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                eventFRAG1.newInstance(evenimentList.get(i));
                eventFRAG2.newInstance(evenimentList.get(i));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,eventdetalii.newInstance()).addToBackStack("").commit();
            }
        });


        return myView;
    }

    public void setList(List<eveniment> evenimentList) {
        this.evenimentList = evenimentList;

    }

    public static eventfragment newInstance() {
        if (instance == null)
            instance = new eventfragment();
        return instance;
    }


}