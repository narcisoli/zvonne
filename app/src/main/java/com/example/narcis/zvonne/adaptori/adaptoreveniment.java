package com.example.narcis.zvonne.adaptori;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.obiecte.blur;
import com.example.narcis.zvonne.obiecte.eveniment;
import com.example.narcis.zvonne.obiecte.mesaj;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Narcis on 9/7/2016.
 */
public class adaptoreveniment extends ArrayAdapter<eveniment> {

    private int layoutResource;
    private View view;
    private TextView eventdescriere;
    private TextView eventdata;
    private TextView eventnume;


    public adaptoreveniment(Context context, int layoutResource, List<eveniment> pizzalist) {
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

        eveniment loc = getItem(position);


        if (loc != null) {


            eventdata = (TextView) view.findViewById(R.id.eventdata);
            eventnume = (TextView) view.findViewById(R.id.eventnume);
            eventdata.setText(loc.getData());

            eventnume.setText(loc.getNume());



        }


        return view;
    }


}