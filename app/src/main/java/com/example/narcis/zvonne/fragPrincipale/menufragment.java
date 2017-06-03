package com.example.narcis.zvonne.fragPrincipale;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.narcis.zvonne.MainActivity;
import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.obiecte.ora;
import com.example.narcis.zvonne.obiecte.otf;
import com.facebook.FacebookButtonBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

/**
 * Created by Narcis on 12/5/2016.
 */

public class menufragment extends Fragment {


    private static menufragment instance;
    boolean aBoolean = false;
    FloatingActionButton call;
    FloatingActionButton browser;
    FloatingActionButton gps;
    FloatingActionButton fab;
    String of = "";
    String desc = "";
    private View myView;
    private TextView oferta;
    private TextView descriere;

    public static menufragment newInstance() {
        if (instance == null)
            instance = new menufragment();
        return instance;
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.home, container, false);
        oferta = (otf) myView.findViewById(R.id.ofertazilei);
        descriere = (otf) myView.findViewById(R.id.descriere);

        ImageView image = (ImageView) myView.findViewById(R.id.imageView25);
        Drawable myDrawable = getResources().getDrawable(R.drawable.zvonne);
        call = (FloatingActionButton) myView.findViewById(R.id.fab4);
        browser = (FloatingActionButton) myView.findViewById(R.id.fab2);
        gps = (FloatingActionButton) myView.findViewById(R.id.fab3);
        fab = (FloatingActionButton) myView.findViewById(R.id.fab1);


        browser.setVisibility(View.GONE);
        gps.setVisibility(View.GONE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aBoolean) {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.add));
                    browser.setVisibility(View.GONE);
                    gps.setVisibility(View.GONE);
                    aBoolean = false;
                } else {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.back));
                    browser.setVisibility(View.VISIBLE);
                    gps.setVisibility(View.VISIBLE);
                    aBoolean = true;
                }
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri number = Uri.parse("tel:0740187671");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });
        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + 46.320186 + "," + 24.296343));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myView.getContext().startActivity(intent);
            }
        });
        browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.facebook.com/cafeneaua.zvonne.3";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
        image.setImageBitmap(myLogo);
        oferta.setText(of);
        descriere.setText(desc);



        return myView;
    }

    public void setDescriere(String descriere) {
        this.desc = descriere;
    }

    public void setOferta(String oferta) {
        this.of = oferta;
    }


}

