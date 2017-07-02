package com.example.narcis.zvonne.adaptori;

/**
 * Created by Narcis on 12/21/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.narcis.zvonne.MainActivity;
import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.obiecte.mesaj;
import com.firebase.ui.storage.images.FirebaseImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Narcis on 9/7/2016.
 */
public class adaptormesaj extends ArrayAdapter<mesaj> {

    private int layoutResource;


    public adaptormesaj(Context context, int layoutResource, List<mesaj> pizzalist) {
        super(context, layoutResource, pizzalist);
        this.layoutResource = layoutResource;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);


        }

        final mesaj loc = getItem(position);



        if (loc != null) {

            TextView id=(TextView)view.findViewById(R.id.numemesaj);
            CircleImageView poza=(CircleImageView)view.findViewById(R.id.poza);
            TextView detalii=(TextView)view.findViewById(R.id.mesajtext);
            Glide.with(view.getContext())
                    .load(loc.getPoza())
                    .placeholder(R.drawable.zvonneicon)
                    .into(poza);
            id.setText(loc.getUser());
            detalii.setText(loc.getText());


        }

        return view;
    }



}
