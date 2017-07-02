package com.example.narcis.zvonne.fragSecundare;





import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.adaptori.adaptoristoric;
import com.example.narcis.zvonne.obiecte.coman;
import com.example.narcis.zvonne.obiecte.pizza;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class istoriccomenzi extends Fragment {
    private static istoriccomenzi instance;
    View myView;
    private GridView lista;
    private List<coman> comanList=new ArrayList<>();
    private adaptoristoric a;

    public static istoriccomenzi getInstance(){
        if (instance==null)
            instance=new istoriccomenzi();
        return instance;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView=inflater.inflate(R.layout.istoriccomenzi,container,false);
        lista=(GridView)myView.findViewById(R.id.listacomenzi);

         a=new adaptoristoric(myView.getContext(),R.layout.adaptoristoric,comanList);
        lista.setAdapter(a);

        DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("Zvonne").child("comenzi");
        Query query=db.orderByChild("nume").equalTo(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comanList.clear();
                for (DataSnapshot msgSnapshot: dataSnapshot.getChildren()) {
                    coman msg = msgSnapshot.getValue(coman.class);

                    comanList.add(msg);


                }
                Collections.reverse(comanList);
                a.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return myView;
    }

}
