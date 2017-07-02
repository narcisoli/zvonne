package com.example.narcis.zvonne.fragSecundare.pizza;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.adaptori.adaptormesaj;
import com.example.narcis.zvonne.obiecte.mesaj;
import com.example.narcis.zvonne.obiecte.pizza;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * Created by Narcis on 11/8/2016.
 */

public class pizza2 extends Fragment {

    private static pizza2 instance;
    private View myView;
    private pizza pizza;
    private ImageView trimite;
    private ListView listView;
    private EditText editText;
    private List<mesaj> mesajeList=new ArrayList<>();
    private adaptormesaj adaptor;
    private DatabaseReference db;

    public static pizza2 getInstance(){
        if (instance==null)
            instance=new pizza2();
        return instance;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView=inflater.inflate(R.layout.evenfrag2,container,false);
        init();
        return myView;
    }

    private void init() {
        editText=(EditText)myView.findViewById(R.id.edittext1);
        listView=(ListView)myView.findViewById(R.id.listamesjae1);
        trimite=(ImageView)myView.findViewById(R.id.imaginetrimite1);
        mesajeList.clear();
        adaptor=new adaptormesaj(myView.getContext(),R.layout.adaptormesaj,mesajeList);
        listView.setAdapter(adaptor);
        db = FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Pizza").child(pizza.getTip()).child("Mesaje");
        trimite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editText.getText().toString()))
                    Toast.makeText(view.getContext(), "Trebuie sa introduceti ceva", Toast.LENGTH_SHORT).show();
                else {

                    db.push().setValue(new mesaj(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),editText.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString()));
                    editText.setText("");
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    editText.clearFocus();
                }
            }
        });

        db.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mesajeList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    mesaj m=data.getValue(mesaj.class);
                    mesajeList.add(m);
                }
                Collections.reverse(mesajeList);
                adaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void setPizza(pizza n){
        this.pizza=n;
    }

}
