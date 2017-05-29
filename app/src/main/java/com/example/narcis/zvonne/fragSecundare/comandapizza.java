package com.example.narcis.zvonne.fragSecundare;



import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

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


/**
 * Created by Narcis on 11/8/2016.
 */

public class comandapizza extends Fragment {

    private View myView;
    private pizza pizza;
    private TextView numepizza;
    private TextView ingredientepizza;
    private TextView pretpizza;
    private ImageView imageView;
    private StorageReference storageReference;
    private RatingBar ratingbar;
    private TextView totalpizza;
    private TextView gramajpizza;
    private TextView notapizza;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView=inflater.inflate(R.layout.comandapizza,container,false);
        init();
        return myView;
    }
    private static comandapizza instance;
    public static comandapizza getInstance(){
        if (instance==null)
            instance=new comandapizza();
        return instance;
    }
    private void init() {
        ratingbar=(RatingBar)myView.findViewById(R.id.rating);
        LayerDrawable stars = (LayerDrawable) ratingbar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        numepizza=(TextView)myView.findViewById(R.id.numepizza);
        ingredientepizza=(TextView)myView.findViewById(R.id.ingredientepizza);
        gramajpizza=(TextView)myView.findViewById(R.id.gramajpizza);
        pretpizza=(TextView)myView.findViewById(R.id.pretpizza);
        notapizza=(TextView)myView.findViewById(R.id.notapizza);
        imageView=(ImageView)myView.findViewById(R.id.imaginepizza);
        storageReference=FirebaseStorage.getInstance().getReference().child("Imagini").child("Pizza").child(pizza.getTip()+".jpg");
        Glide.with(myView.getContext())
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(imageView);

        ratingbar.setRating(pizza.getNota());
        gramajpizza.setText(pizza.getGramaj()+"");
        notapizza.setText("( "+pizza.getNota()+"/5 din "+pizza.getNrvoturi()+" voturi )");
        numepizza.setText(pizza.getTip());
        ingredientepizza.setText(pizza.getIngrediente());
        pretpizza.setText(pizza.getPret()+"");
    }

    public void setPizza(pizza n){
        this.pizza=n;
    }

}
