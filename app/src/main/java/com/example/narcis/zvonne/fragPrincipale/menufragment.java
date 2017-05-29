package com.example.narcis.zvonne.fragPrincipale;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

import com.example.narcis.zvonne.MainActivity;
import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.obiecte.otf;
import com.facebook.FacebookButtonBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Narcis on 12/5/2016.
 */

public class menufragment extends Fragment {


    private static menufragment instance;
    private View myView;
    private TextView oferta;
    private TextView descriere;
    String of="";
    String desc="";
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.home, container, false);
        oferta=(otf)myView.findViewById(R.id.ofertazilei);
        descriere=(otf)myView.findViewById(R.id.descriere);
        ImageView image = (ImageView) myView.findViewById(R.id.imageView25);
        Drawable myDrawable = getResources().getDrawable(R.drawable.zvonne);
        Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
        image.setImageBitmap(myLogo);
        oferta.setText(of);
        descriere.setText(desc);
        TextView navigare=(otf)myView.findViewById(R.id.eventname);
        navigare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" +46.320186+","+   24.296343));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myView.getContext().startActivity(intent);
            }
        });

        return myView;
    }



    public static menufragment newInstance() {
        if (instance==null)
            instance = new menufragment();
        return instance;
    }

    public void setDescriere(String descriere) {
        this.desc = descriere;
    }

    public void setOferta(String oferta) {
        this.of = oferta;
    }
}

