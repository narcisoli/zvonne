package com.example.narcis.zvonne.fragPrincipale;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.fragSecundare.desprefragment;
import com.example.narcis.zvonne.fragSecundare.istoriccomenzi;
import com.example.narcis.zvonne.login;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class profilfragment extends Fragment {

    private static profilfragment instance;
    private View myView;
    private Button deconectare;
    private FirebaseUser user;
    private TextView username;
    private FirebaseAuth mAuth;
    private TextView numecont;
    private Button istoriccomen;
    private Button despre;
    private FragmentManager f;




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        myView=inflater.inflate(R.layout.profilfragment,container,false);
        init();
        istoriccomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, istoriccomenzi.getInstance()).addToBackStack("").commit();
            }
        });



        despre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,new desprefragment()).addToBackStack("").commit();
            }
        });

        deconectare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(getActivity(), "Deconectarea a avut loc cu success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), login.class);
                LoginManager.getInstance().logOut();
                startActivity(intent);
                getActivity().finish();


            }
        });

        if (user != null) {
            username.setText(user.getEmail());
            numecont.setText(user.getDisplayName());


        }

        return myView;
    }

    private void init() {
        f=getActivity().getFragmentManager();
        deconectare=(Button)myView.findViewById(R.id.deconectare);
        user = FirebaseAuth.getInstance().getCurrentUser();
        username = (TextView) myView.findViewById(R.id.emailcont);
        mAuth=FirebaseAuth.getInstance();
        numecont=(TextView)myView.findViewById(R.id.numecont);
        istoriccomen = (Button) myView.findViewById(R.id.istoriccomenzi);
        despre = (Button) myView.findViewById(R.id.despre);
    }

    public static profilfragment newInstance() {
        if (instance==null)
            instance = new profilfragment();
        return instance;
    }


}
