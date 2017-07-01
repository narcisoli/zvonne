package com.example.narcis.zvonne.adaptori;


import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.obiecte.blur;
import com.example.narcis.zvonne.obiecte.pizza;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Narcis on 9/7/2016.
 */
public class adaptorpizzameniu extends ArrayAdapter<pizza>  {

    public static List<pizza> pizzaList = new ArrayList<>();
    private int layoutResource;
    private pizza loc;
    private View view;

    public adaptorpizzameniu(Context context, int layoutResource, List<pizza> pizzalist) {
        super(context, layoutResource, pizzalist);
        this.layoutResource = layoutResource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);
        }
      loc = getItem(position);
        if (loc != null) {
            TextView tip=(TextView)view.findViewById(R.id.pizzanume);
            TextView ingrediente=(TextView)view.findViewById(R.id.pizzaingrediente);

            final ImageView imageView=(ImageView) view.findViewById(R.id.imaginepizza);
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Imagini").child("Pizza").child(loc.getTip() + ".jpg");
            final Transformation blurTransformation = new Transformation() {
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
            tip.setText(loc.getTip());
            ingrediente.setText(loc.getIngrediente());
            TextView b1=(TextView) view.findViewById(R.id.butonpizza);
            b1.setText(loc.getPret()+" lei");

        }

        return view;
    }



}