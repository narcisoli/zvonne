package com.example.narcis.zvonne.fragSecundare;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.fragPrincipale.comandafragment;
import com.example.narcis.zvonne.obiecte.coman;
import com.example.narcis.zvonne.obiecte.pizza;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Narcis on 12/5/2016.
 */

public class confirmafragment extends Fragment {


    View myView;
    private Button b;
    private static confirmafragment instance;
    private List<pizza> pizzaList;
    private EditText detalii;
    private EditText adresa;
    private Integer i=0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.confirmafragment, container, false);
        b=(Button)myView.findViewById(R.id.comandac2);
        detalii=(EditText)myView.findViewById(R.id.detaliic2);
        adresa=(EditText)myView.findViewById(R.id.editTextadresa);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(adresa.getText().toString()))
                    Toast.makeText(myView.getContext(), "Adresa nu poate fi goala", Toast.LENGTH_SHORT).show();
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Esti sigur ca vrei sa comanzi?");
                    builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {


                        public void onClick(DialogInterface dialog, int id) {
                            String a="";
                            for (int i=0;i<pizzaList.size();i++)
                                a+=pizzaList.get(i).getNrbucati()+"x "+pizzaList.get(i).getTip()+"\n";

                            a+="Adresa: "+adresa.getText().toString();
                            if (detalii.getText().toString().trim()!="")
                                a+="\nDetalii: "+detalii.getText().toString();
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd");
                            String formattedDate = df.format(c.getTime());
                            DatabaseReference zv=FirebaseDatabase.getInstance().getReference().child("Zvonne").child("totalcomenzi");

                            zv.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    i=dataSnapshot.getValue(Integer.class);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            i++;
                            coman coman=new coman(a,FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),formattedDate,1,i);
                            zv.setValue(i);
                            FirebaseDatabase.getInstance().getReference().child("Zvonne").child("comenzi").child(i+"").setValue(coman);
                            getFragmentManager().popBackStack();
                            getFragmentManager().beginTransaction().replace(R.id.container,multumimfragment.getInstance()).addToBackStack("").commit();

                        }
                    });
                    builder.setNegativeButton("Nu",null);

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });
        return myView;
    }
    public static confirmafragment getInstance(){
        if (instance==null)
            instance=new confirmafragment();
        return instance;
    }
    public void setpizzalist(List<pizza> pizzaLista){
        this.pizzaList=pizzaLista;
    }
}