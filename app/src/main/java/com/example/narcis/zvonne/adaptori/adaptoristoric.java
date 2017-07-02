package com.example.narcis.zvonne.adaptori;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.obiecte.coman;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Narcis on 9/7/2016.
 */
public class adaptoristoric extends ArrayAdapter<coman> {


    private int layoutResource;
    private coman loc;


    public adaptoristoric(Context context, int layoutResource, List<coman> pizzalist) {
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
        TextView data = (TextView) view.findViewById(R.id.adaptorrezervaredata);
        TextView detalii = (TextView) view.findViewById(R.id.adaptorrezervaredetalii);
        TextView status = (TextView) view.findViewById(R.id.adaptoristoricstatus);


        loc = getItem(position);

        if (loc != null) {
            long timestamp = loc.getId();
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(timestamp);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String dateStr1 = sdf.format(cal.getTime());
            data.setText(dateStr1);
            detalii.setText(loc.getText());
            if (loc.getStatus() == 1)
                status.setText("Neconfirmata");
            if (loc.getStatus() == 2) {
                status.setText("Confirmata");

            }
            if (loc.getStatus() == 3) {
                status.setText("La cuptor");

            }
            if (loc.getStatus() == 4) {
                status.setText("Pe drum");

            }
            if (loc.getStatus() == 5) {
                status.setText("Finalizata");

            }
            if (loc.getStatus() == 0) {
                status.setText("Anulata");

            }

        }

        return view;
    }


}
