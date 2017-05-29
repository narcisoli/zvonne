package com.example.narcis.zvonne.adaptori;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.narcis.zvonne.MainActivity;
import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.fragSecundare.comandapizza;
import com.example.narcis.zvonne.obiecte.pizza;
import com.kcode.bottomlib.BottomDialog;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by Narcis on 9/7/2016.
 */
public class adaptorpizzameniu extends ArrayAdapter<pizza>  {

    private int layoutResource;
    public static List<pizza> pizzaList = new ArrayList<>();
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
            tip.setText(loc.getTip());
            ingrediente.setText(loc.getIngrediente());
            TextView b1=(TextView) view.findViewById(R.id.butonpizza);
            b1.setText(loc.getPret()+" lei");

        }

        return view;
    }



}