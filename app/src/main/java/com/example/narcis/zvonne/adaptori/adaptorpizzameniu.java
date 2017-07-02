package com.example.narcis.zvonne.adaptori;


import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.fragPrincipale.meniu;
import com.example.narcis.zvonne.fragSecundare.pizza2;
import com.example.narcis.zvonne.obiecte.pizza;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Narcis on 9/7/2016.
 */
public class adaptorpizzameniu extends ArrayAdapter<pizza> {

    private final FragmentManager supportFragmentManager;
    public List<pizza> pizzaList = new ArrayList<>();
    adaptorCallBack adaptorCallBack;
    private int layoutResource;
    private pizza loc;
    private View view;
    private RelativeLayout relativeLayout;
    private RelativeLayout rel3;
    private RelativeLayout rel2;


    public adaptorpizzameniu(Context context, int layoutResource, List<pizza> pizzalist, adaptorCallBack adaptorCallBack, FragmentManager supportFragmentManager) {
        super(context, layoutResource, pizzalist);
        this.layoutResource = layoutResource;
        this.adaptorCallBack = adaptorCallBack;
        this.pizzaList = pizzalist;
        this.supportFragmentManager=supportFragmentManager;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);
        }
        loc = getItem(position);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.rel1);
        relativeLayout.setTag(position);
        rel2 = (RelativeLayout) view.findViewById(R.id.rel2);

        rel3 = (RelativeLayout) view.findViewById(R.id.rel3);
        if (loc != null) {




            TextView tip = (TextView) view.findViewById(R.id.pizzanume);
            TextView ingrediente = (TextView) view.findViewById(R.id.pizzaingrediente);


            final ImageView imageView = (ImageView) view.findViewById(R.id.imaginepizza);
            star(loc);


            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Imagini").child("Pizza").child(loc.getTip() + ".jpg");
            /*    final Transformation blurTransformation = new Transformation() {
                @Override
                public Bitmap transform(Bitmap source) {
                    Bitmap blurred = blur.fastblur(view.getContext(), source, 10);
                    source.recycle();
                    return blurred;
                }

                @Override
                public String key() {
                    return "blur()";
                }
            };
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(final Uri uri) {

                    Picasso.with(view.getContext())
                            .load(uri) // thumnail url goes here
                            .transform(blurTransformation)
                            .into(imageView, new Callback() {
                                @Override
                                public void onSuccess() {
                                    Picasso.with(view.getContext())
                                            .load(uri) // original image url goes here
                                            .placeholder(imageView.getDrawable())
                                            .into(imageView);

                                }

                                @Override
                                public void onError() {
                                }
                            });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
            */

            Glide.with(view.getContext())
                    .using(new FirebaseImageLoader())
                    .load(storageReference)
                    .placeholder(R.drawable.zvonneicon)
                    .into(imageView);
            tip.setText(loc.getTip());
            ingrediente.setText(loc.getIngrediente());
            TextView b1 = (TextView) view.findViewById(R.id.butonpizza);
            b1.setText(loc.getPret() + " lei");

        }
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adaptorCallBack.adauga(pizzaList.get(position));
            }
        });
        rel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               pizza2.getInstance().setPizza(pizzaList.get(position));
               supportFragmentManager.beginTransaction().replace(R.id.container,pizza2.getInstance()).addToBackStack("").commit();
            }
        });
        rel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String[] items = {"Foarte buna","Buna","Ok","Rea","Foarte rea"};
                new LovelyChoiceDialog(view.getContext())
                        .setTopColorRes(R.color.fundaldark)
                        .setTopTitle("Evalueaza")
                        .setTopTitleColor(Color.WHITE)
                        .setItems(items, new LovelyChoiceDialog.OnItemSelectedListener<String>() {
                            @Override
                            public void onItemSelected(int position1, String item) {
                                pizza pizza=pizzaList.get(position);
                                float nota=(pizza.getNota()*pizza.getNrvoturi()+(float)(5-position1))/(float)(pizza.getNrvoturi()+1);
                                pizza.setNota(nota);
                                pizza.setNrvoturi(pizza.getNrvoturi()+1);
                                Log.i("numar",pizza.toString());
                                FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Pizza").child(pizza.getTip()).child("nota").setValue(nota);
                                FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Pizza").child(pizza.getTip()).child("nrvoturi").setValue(pizza.getNrvoturi());
                                adaptorCallBack.refresh1();

                                Snackbar snackbar = Snackbar
                                        .make(view, "Va multumim pentru vot", Snackbar.LENGTH_SHORT);

                                snackbar.show();

                            }
                        })
                        .show();
            }
        });

        return view;
    }

    private void star(pizza loc) {
        ImageView star1 = (ImageView) view.findViewById(R.id.star1);
        ImageView star2 = (ImageView) view.findViewById(R.id.star2);
        ImageView star3 = (ImageView) view.findViewById(R.id.star3);
        ImageView star4 = (ImageView) view.findViewById(R.id.star4);
        ImageView star5 = (ImageView) view.findViewById(R.id.star5);
        float vot = loc.getNota();
        Log.i("nota", vot + "");
        if (vot < 1) {
            star1.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
            star2.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
            star3.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
            star4.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
            star5.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
        } else if (vot >= 1 && vot < 1.25) {
            star1.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star2.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
            star3.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
            star4.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
            star5.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
        } else if (vot >= 1.25 && vot < 1.75) {
            star1.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star2.setImageDrawable(view.getResources().getDrawable(R.drawable.starjum));
            star3.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
            star4.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
            star5.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
        } else if (vot >= 1.75 && vot < 2.25) {
            star1.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star2.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star3.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
            star4.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
            star5.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
        } else if (vot >= 2.25 && vot < 2.75) {
            star1.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star2.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star3.setImageDrawable(view.getResources().getDrawable(R.drawable.starjum));
            star4.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
            star5.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
        } else if (vot >= 2.75 && vot < 3.25) {
            star1.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star2.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star3.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star4.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
            star5.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
        } else if (vot >= 3.25 && vot < 3.75) {
            star1.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star2.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star3.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star4.setImageDrawable(view.getResources().getDrawable(R.drawable.starjum));
            star5.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
        } else if (vot >= 3.75 && vot < 4.25) {
            star1.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star2.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star3.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star4.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star5.setImageDrawable(view.getResources().getDrawable(R.drawable.star0));
        } else if (vot >= 4.25 && vot < 4.75) {
            star1.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star2.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star3.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star4.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star5.setImageDrawable(view.getResources().getDrawable(R.drawable.starjum));
        } else if (vot >= 4.75 && vot < 5.75) {
            star1.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star2.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star3.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star4.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
            star5.setImageDrawable(view.getResources().getDrawable(R.drawable.star1));
        }

    }

    public interface adaptorCallBack {
        void adauga(pizza pizza);
        void refresh1();
    }


}