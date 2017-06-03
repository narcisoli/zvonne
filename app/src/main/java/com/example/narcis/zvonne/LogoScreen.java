package com.example.narcis.zvonne;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.tv.TvContract;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.narcis.zvonne.fragPrincipale.eventfragment;
import com.example.narcis.zvonne.fragPrincipale.meniu;
import com.example.narcis.zvonne.fragPrincipale.menufragment;
import com.example.narcis.zvonne.obiecte.eveniment;
import com.example.narcis.zvonne.obiecte.pizza;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;


public class LogoScreen extends AppCompatActivity {


    private List pizzalist = new ArrayList();
    private List<eveniment> evenimentList = new ArrayList<>();
    private DatabaseReference zvonne;
    private boolean bool1 = false;
    private boolean bool2 = false;
    private boolean bool3 = false;
    private boolean bool4 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);

       Thread th= new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (bool1 && bool2 && bool3 && bool4)
                        break;
                }
                startActivity(new Intent(LogoScreen.this, login.class));
                finish();

            }
        });
       th.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


            }
        }, 5000);
        salveazaoferta();
        salveazadescrierea();
        saveevent();
        savemeniu();


        if (!isNetworkAvailable()) {
            Toast.makeText(this, "No network connection",
                    Toast.LENGTH_LONG).show();
        }


    }

    private void salveazaoferta() {

        zvonne = FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Oferte");
        zvonne.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String post = snapshot.getValue(String.class);
                menufragment.newInstance().setOferta(post);
                bool1 = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    private void salveazadescrierea() {


        zvonne = FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Descriere");
        zvonne.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                String post = snapshot.getValue(String.class);
                menufragment.newInstance().setDescriere(post);
                bool2 = true;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    private void saveevent() {
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();

        zvonne = FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Evenimente");
        zvonne.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                evenimentList.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {


                    eveniment post = postSnapshot.getValue(eveniment.class);
                    evenimentList.add(post);


                }
                eventfragment.newInstance().setList(evenimentList);
                bool3 = true;


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    private void savemeniu() {


        zvonne = FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Pizza");
        zvonne.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                pizzalist.clear();
                for (DataSnapshot msgSnapshot : snapshot.getChildren()) {
                    pizza msg = msgSnapshot.getValue(pizza.class);
                    pizzalist.add(msg);


                }

                meniu.newInstance().seteazaList(pizzalist);
                bool4 = true;


            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
            }
        });


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


}
