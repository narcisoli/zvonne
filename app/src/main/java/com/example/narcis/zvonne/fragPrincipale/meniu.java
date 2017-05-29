package com.example.narcis.zvonne.fragPrincipale;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.narcis.zvonne.MainActivity;
import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.adaptori.adaptorpizzameniu;
import com.example.narcis.zvonne.fragSecundare.comandapizza;
import com.example.narcis.zvonne.obiecte.pizza;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kcode.bottomlib.BottomDialog;

import com.song.refresh_view.PullToRefreshView;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;

import java.util.ArrayList;
import java.util.List;

public class meniu extends Fragment {
    View myView;
    private adaptorpizzameniu adaptor;
    private List<pizza> pizzalist = new ArrayList<>();
    private DatabaseReference zvonne;
    private ListView lista;
    private static meniu instance;
    comunicare mCallback;
    private PullToRefreshView refreshview;
    private int nrvoturi = 0;
    private int nota = 0;

    public void seteazaList(List eazaList) {
        this.pizzalist = eazaList;
    }


    public interface comunicare {
        public void trimitemesaj(pizza pizza);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (comunicare) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
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
                BottomDialog dialog = BottomDialog.newInstance(pizzalist.get(i).getTip(), new String[]{"Adauga in cos", "Vezi detalii", "Evalueaza"});
                dialog.show(MainActivity.fram, "dialog");
                dialog.setListener(new BottomDialog.OnClickListener() {
                    @Override
                    public void click(int position) {
                        if (position == 0) {

                            mCallback.trimitemesaj(pizzalist.get(i));
                        } else if (position == 1) {
                            comandapizza.getInstance().setPizza(pizzalist.get(i));
                            getFragmentManager().beginTransaction().addToBackStack("").replace(R.id.container, comandapizza.getInstance()).commit();
                        } else if (position == 2) {
                            List<String> lis = new ArrayList<String>();
                            lis.add("Foarte buna");
                            lis.add("Buna");
                            lis.add("Ok");
                            lis.add("Rea");
                            lis.add("Foarte Rea");
                            new LovelyChoiceDialog(myView.getContext())
                                    .setIcon(R.drawable.zvoneeeeeee)
                                    .setTopColorRes(R.color.guillotine_background_dark)
                                    .setItems(lis, new LovelyChoiceDialog.OnItemSelectedListener<String>() {
                                        @Override
                                        public void onItemSelected(int position, String item) {
                                            DatabaseReference zv = FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Pizza").child(pizzalist.get(i).getTip());

                                            zv.child("nrvoturi").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    try {
                                                        nrvoturi = dataSnapshot.getValue(Integer.class);
                                                    } catch (NullPointerException ex) {
                                                        nrvoturi = 0;
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                            zv.child("nota").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    try {
                                                        nota = dataSnapshot.getValue(int.class);
                                                    } catch (NullPointerException ex) {
                                                        nota = 0;
                                                    }

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                            int notanoua = (nota * nrvoturi + 5 - position) / (nrvoturi + 1);
                                            nrvoturi++;
                                            zv.child("nrvoturi").setValue(nrvoturi);
                                            zv.child("nota").setValue(notanoua);


                                        }
                                    })
                                    .show();
                        }
                    }
                });
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
