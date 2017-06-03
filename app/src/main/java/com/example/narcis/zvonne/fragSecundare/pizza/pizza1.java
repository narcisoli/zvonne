package com.example.narcis.zvonne.fragSecundare.pizza;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.fragPrincipale.meniu;
import com.example.narcis.zvonne.fragSecundare.istoriccomenzi;
import com.example.narcis.zvonne.obiecte.pizza;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;

import de.hdodenhof.circleimageview.CircleImageView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;


/**
 * Created by Narcis on 11/8/2016.
 */

public class pizza1 extends Fragment {

    private static pizza1 instance;
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
    private RelativeLayout evalueaza;
    private MaterialRatingBar ratingBar;

    public static pizza1 getInstance() {
        if (instance == null)
            instance = new pizza1();
        return instance;
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

    private void init() {
         ratingBar = (MaterialRatingBar) myView.findViewById(R.id.rating_bar);
        evalueaza=(RelativeLayout)myView.findViewById(R.id.evalueaza);
        evalueaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] items = {"Foarte buna","Buna","Ok","Rea","Foarte rea"};
                new LovelyChoiceDialog(myView.getContext())
                        .setTopColorRes(R.color.fundaldark)
                        .setTopTitle("Evalueaza")
                        .setTopTitleColor(Color.WHITE)
                        .setItems(items, new LovelyChoiceDialog.OnItemSelectedListener<String>() {
                            @Override
                            public void onItemSelected(int position1, String item) {
                                float nota=(pizza.getNota()*pizza.getNrvoturi()+(5-position1))/(pizza.getNrvoturi()+1);
                                FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Pizza").child(pizza.getTip()).child("nota").setValue(nota);
                                FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Pizza").child(pizza.getTip()).child("nrvoturi").setValue(pizza.getNrvoturi()+1);
                                meniu.newInstance().refresh();
                                Toast.makeText(myView.getContext(), "Va multumim pentru vot", Toast.LENGTH_SHORT).show();
                                getActivity().getSupportFragmentManager().popBackStackImmediate();
                            }
                        })
                        .show();
            }
        });

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        pretpizza=(TextView)myView.findViewById(R.id.pretpizza);
        fab = (FloatingActionButton) myView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.trimitemesaj(pizza);
            }
        });
        notapizza = (TextView) myView.findViewById(R.id.notapizza);
        numepizza = (TextView) myView.findViewById(R.id.numepizza);
        ingredientepizza = (TextView) myView.findViewById(R.id.ingredientepizza);
        gramajpizza = (TextView) myView.findViewById(R.id.gramajpizza);
        ratingBar.setRating(pizza.getNota());
        imageView = (CircleImageView) myView.findViewById(R.id.imaginepizza);

        storageReference = FirebaseStorage.getInstance().getReference().child("Imagini").child("Pizza").child(pizza.getTip() + ".jpg");
        Glide.with(myView.getContext())
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(imageView);
        numepizza.setText(pizza.getTip());
        notapizza.setText("( nota "+String.format("%.2f",pizza.getNota())+" din "+pizza.getNrvoturi()+" voturi )");
        Toast.makeText(myView.getContext(),pizza.getNota()+"", Toast.LENGTH_SHORT).show();
        gramajpizza.setText(pizza.getGramaj() + "");
        pretpizza.setText(pizza.getPret()+" lei");
        ingredientepizza.setText(pizza.getIngrediente());

    }

    public void setPizza(pizza n) {
        this.pizza = n;
    }

    public interface comunicare {
        public void trimitemesaj(pizza pizza);
    }

}
