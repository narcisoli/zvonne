package com.example.narcis.zvonne.fragSecundare.pizza;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.obiecte.pizza;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;



/**
 * Created by Narcis on 11/8/2016.
 */

public class pizza1 extends Fragment {

    private View myView;
    private pizza pizza;
    private TextView numepizza;
    private TextView ingredientepizza;
    private TextView pretpizza;
    private CircleImageView imageView;
    private StorageReference storageReference;

    private TextView totalpizza;
    private TextView gramajpizza;
    private TextView notapizza;
    private FloatingActionButton fab;
    private comunicare mCallback;

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.comandapizza, container, false);
        init();
        return myView;
    }

    private static pizza1 instance;

    public static pizza1 getInstance() {
        if (instance == null)
            instance = new pizza1();
        return instance;
    }

    private void init() {

        pretpizza=(TextView)myView.findViewById(R.id.pretpizza);
        fab = (FloatingActionButton) myView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.trimitemesaj(pizza);
            }
        });
        numepizza = (TextView) myView.findViewById(R.id.numepizza);
        ingredientepizza = (TextView) myView.findViewById(R.id.ingredientepizza);
        gramajpizza = (TextView) myView.findViewById(R.id.gramajpizza);

        imageView = (CircleImageView) myView.findViewById(R.id.imaginepizza);

        storageReference = FirebaseStorage.getInstance().getReference().child("Imagini").child("Pizza").child(pizza.getTip() + ".jpg");
        Glide.with(myView.getContext())
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(imageView);

        gramajpizza.setText(pizza.getGramaj() + "");
        pretpizza.setText(pizza.getPret()+" lei");
        ingredientepizza.setText(pizza.getIngrediente());

    }

    public void setPizza(pizza n) {
        this.pizza = n;
    }

}
