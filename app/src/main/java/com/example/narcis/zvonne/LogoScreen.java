package com.example.narcis.zvonne;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.tv.TvContract;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.narcis.zvonne.fragPrincipale.eventfragment;
import com.example.narcis.zvonne.fragPrincipale.meniu;
import com.example.narcis.zvonne.fragPrincipale.menufragment;
import com.example.narcis.zvonne.obiecte.eveniment;
import com.example.narcis.zvonne.obiecte.pizza;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;


public class LogoScreen extends AppCompatActivity {


    private List pizzalist = new ArrayList();
    private List<eveniment> evenimentList = new ArrayList<>();
    private DatabaseReference zvonne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);
        Task<?>[] tasks = new Task[]{
                salveazaoferta(),
                salveazadescrierea(),
                saveevent(),
                savemeniu()
        };


        //   new LoadFirebaseData().execute();


        if (!isNetworkAvailable()) {
            Toast.makeText(this, "No network connection",
                    Toast.LENGTH_LONG).show();
        }


    }

    private Task<String> salveazaoferta() {
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();

        zvonne = FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Oferte");
        zvonne.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                String post = snapshot.getValue(String.class);


                tcs.setResult("dsa");
                menufragment.newInstance().setOferta(post);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        return tcs.getTask();
    }

    private Task<String> salveazadescrierea() {
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();

        zvonne = FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Descriere");
        zvonne.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                String post = snapshot.getValue(String.class);
                if (null == post) {
                    tcs.setResult(null);
                }

                tcs.setResult("dsa");
                menufragment.newInstance().setDescriere(post);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        return tcs.getTask();
    }

    private Task<String> saveevent() {
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();

        zvonne = FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Evenimente");
        zvonne.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                evenimentList.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {


                    eveniment post = postSnapshot.getValue(eveniment.class);
                    if (null == post) {
                        tcs.setResult(null);
                    }
                    evenimentList.add(post);


                }
                tcs.setResult("dsa");
                eventfragment.newInstance().setList(evenimentList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        return tcs.getTask();
    }

    private Task<String> savemeniu() {
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();

        zvonne = FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Pizza");
        zvonne.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                pizzalist.clear();
                for (DataSnapshot msgSnapshot : snapshot.getChildren()) {
                    pizza msg = msgSnapshot.getValue(pizza.class);
                    if (null == msg) {
                        tcs.setResult(null);
                    }
                    pizzalist.add(msg);


                }
                tcs.setResult("dsa");
                meniu.newInstance().seteazaList(pizzalist);
                startActivity(new Intent(LogoScreen.this, login.class));

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
            }
        });


        return tcs.getTask();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


}
