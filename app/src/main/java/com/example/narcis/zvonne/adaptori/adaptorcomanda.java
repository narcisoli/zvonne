package com.example.narcis.zvonne.adaptori;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.obiecte.pizza;

import java.util.List;


/**
 * Created by Narcis on 9/7/2016.
 */
public class adaptorcomanda extends ArrayAdapter<pizza>  {


    private int layoutResource;
    private TextView nume;
    private TextView pret;
    private TextView nrbucati;
    private Button minus;
    private Button plus;
    private Button elimina;
    private pizza loc;


    public adaptorcomanda(Context context, int layoutResource, List<pizza> pizzalist) {
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
        nume=(TextView)view.findViewById(R.id.adaptorcomandanume);
        pret=(TextView)view.findViewById(R.id.adaptorcomandapret);
        nrbucati=(TextView)view.findViewById(R.id.nrbucati);




        loc=getItem(position);

        if (loc != null) {
            nume.setText(loc.getTip());
            pret.setText(loc.getPret()+ " lei");
            nrbucati.setText(loc.getNrbucati()+" buc.");
        }

        return view;
    }



}