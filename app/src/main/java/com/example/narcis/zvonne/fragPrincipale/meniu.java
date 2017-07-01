package com.example.narcis.zvonne.fragPrincipale;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.narcis.zvonne.MainActivity;
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

public class meniu extends Fragment implements adaptorpizzameniu.adaptorCallBack {
    private static meniu instance;
    View myView;
    OnHeadlineSelectedListener mCallback;
    private adaptorpizzameniu adaptor;
    private List<pizza> pizzalist = new ArrayList<>();
    private DatabaseReference zvonne;
    private ListView lista;
    private PullToRefreshView refreshview;

    public static meniu newInstance() {
        if (instance == null)
            instance = new meniu();
        return instance;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public void refresh(){
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

    }

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

    }

    private void init() {

        lista = (ListView) myView.findViewById(R.id.lista);
        refreshview = (PullToRefreshView) myView.findViewById(R.id.refreshView);
        adaptor = new adaptorpizzameniu(myView.getContext(), R.layout.adaptorpizza, pizzalist,this,getActivity().getSupportFragmentManager() );
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

    @Override
    public void adauga(pizza pizza) {
        mCallback.mesajpizza(pizza);
    }


    public interface OnHeadlineSelectedListener {
        public void mesajpizza(pizza pizza);
    }
}
