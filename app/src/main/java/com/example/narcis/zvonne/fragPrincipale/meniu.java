package com.example.narcis.zvonne.fragPrincipale;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.adaptori.adaptorpizzameniu;
import com.example.narcis.zvonne.fragSecundare.pizza.pizza1;
import com.example.narcis.zvonne.fragSecundare.pizza.pizza2;
import com.example.narcis.zvonne.fragSecundare.pizza.pizzadetalii;
import com.example.narcis.zvonne.obiecte.pizza;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.song.refresh_view.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

public class meniu extends Fragment {
    View myView;
    private adaptorpizzameniu adaptor;
    private List<pizza> pizzalist = new ArrayList<>();
    private DatabaseReference zvonne;
    private ListView lista;
    private static meniu instance;

    private PullToRefreshView refreshview;
    private int nrvoturi = 0;
    private int nota = 0;

    public void seteazaList(List eazaList) {
        this.pizzalist = eazaList;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.meniupizza, container, false);
        init();


        listener();
        return myView;
    }


    private void listener() {
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int i, long l) {
                pizza1.getInstance().setPizza(pizzalist.get(i));
                pizza2.getInstance().setPizza(pizzalist.get(i));
                getFragmentManager().beginTransaction().addToBackStack("").replace(R.id.container, pizzadetalii.newInstance()).commit();
            }
        });
    }


    private void init() {

        lista = (ListView) myView.findViewById(R.id.lista);
        refreshview = (PullToRefreshView) myView.findViewById(R.id.refreshView);
        adaptor = new adaptorpizzameniu(myView.getContext(), R.layout.adaptorpizza, pizzalist);
        lista.setAdapter(adaptor);

        refreshview.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pizzalist.clear();
                zvonne = FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Pizza");
                zvonne.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        pizzalist.clear();
                        for (DataSnapshot msgSnapshot : snapshot.getChildren()) {
                            pizza msg = msgSnapshot.getValue(pizza.class);

                            pizzalist.add(msg);


                        }
                        adaptor.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(DatabaseError firebaseError) {
                    }
                });
                refreshview.setRefreshing(false);

            }
        });


    }


    public static meniu newInstance() {
        if (instance == null)
            instance = new meniu();
        return instance;
    }


}
