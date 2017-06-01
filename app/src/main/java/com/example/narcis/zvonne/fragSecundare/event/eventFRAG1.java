package com.example.narcis.zvonne.fragSecundare.event;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.narcis.zvonne.obiecte.blur;
import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.obiecte.eveniment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


public class eventFRAG1 extends Fragment {

    private static eventFRAG1 instance;
    private View view;
    private ImageView circleimage;
    private TextView detalii;
    private static eveniment event;
    private TextView nume;
    private TextView data;
    private StorageReference storageReference;
    private ProgressBar progres;


    public static eventFRAG1 newInstance(eveniment eveniment) {

        if (instance == null)
            instance = new eventFRAG1();
        event=eveniment;
        return instance;
    }
    public static eventFRAG1 newInstance( ) {

        if (instance == null)
            instance = new eventFRAG1();

        return instance;
    }



    @Override
    public void onStop() {
        super.onStop();
        circleimage.setImageDrawable(null);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.eventfrag1, container, false);
        detalii=(TextView)view.findViewById(R.id.eventfrag1text);
        nume=(TextView)view.findViewById(R.id.eventnume);
        data=(TextView)view.findViewById(R.id.eventdata);
        detalii.setText(event.getDetalii());
        progres=(ProgressBar)view.findViewById(R.id.progres);
        progres.setVisibility(View.VISIBLE);

        data.setText("-"+event.getData()+"-");
        nume.setText(event.getNume());
        storageReference= FirebaseStorage.getInstance().getReference().child("Imagini").child("Evenimente").child(event.getId()+".jpg");
        final Transformation blurTransformation = new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                Bitmap blurred = blur.fastblur(view.getContext(), source, 10);
                source.recycle();
                return blurred;
            }

            @Override
            public String key() {
                return "blur()";
            }
        };
        circleimage = (ImageView) view.findViewById(R.id.circleimage);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(final Uri uri) {
                progres.setVisibility(View.GONE);
                Picasso.with(view.getContext())
                        .load(uri) // thumnail url goes here
                        .transform(blurTransformation)
                        .into(circleimage, new Callback() {
                            @Override
                            public void onSuccess() {
                                Picasso.with(view.getContext())
                                        .load(uri) // original image url goes here
                                        .placeholder(circleimage.getDrawable())
                                        .into(circleimage);

                            }

                            @Override
                            public void onError() {
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });





        return view;
    }


}
