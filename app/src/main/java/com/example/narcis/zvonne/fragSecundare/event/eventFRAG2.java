package com.example.narcis.zvonne.fragSecundare.event;

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
import com.example.narcis.zvonne.obiecte.eveniment;
import com.example.narcis.zvonne.obiecte.mesaj;
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


public class eventFRAG2 extends Fragment {


    private static eventFRAG2 instance;
    private static eveniment event;
    private View view;
    private ListView listView;
    private DatabaseReference db;
    private List<mesaj> mesajList=new ArrayList<>();
    private adaptormesaj adaptor;
    private ImageView imagine;
    private EditText editeText;

    public static eventFRAG2 newInstance(eveniment eveniment) {

        if (instance == null)
            instance = new eventFRAG2();
        event = eveniment;
        return instance;
    }
    public static eventFRAG2 newInstance() {

        if (instance == null)
            instance = new eventFRAG2();
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.evenfrag2, container, false);
        listView = (ListView) view.findViewById(R.id.listamesjae1);
        mesajList.clear();
        adaptor=new adaptormesaj(view.getContext(),R.layout.adaptormesaj,mesajList);
        listView.setAdapter(adaptor);
        editeText=(EditText)view.findViewById(R.id.edittext1);
        imagine=(ImageView)view.findViewById(R.id.imaginetrimite1);
        db = FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Evenimente").child(event.getId() + "").child("Mesaje");
        imagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editeText.getText().toString()))
                    Toast.makeText(view.getContext(), "Trebuie sa introduceti ceva", Toast.LENGTH_SHORT).show();
                else {

                    db.push().setValue(new mesaj(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),editeText.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString()));
                    editeText.setText("");
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    editeText.clearFocus();
                }
            }
        });

        db.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mesajList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    mesaj m=data.getValue(mesaj.class);
                    mesajList.add(m);
                }
                Collections.reverse(mesajList);
                adaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return view;
    }


}
