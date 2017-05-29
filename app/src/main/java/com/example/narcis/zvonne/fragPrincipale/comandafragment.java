package com.example.narcis.zvonne.fragPrincipale;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.narcis.zvonne.MainActivity;
import com.example.narcis.zvonne.R;
import com.example.narcis.zvonne.adaptori.adaptorcomanda;
import com.example.narcis.zvonne.adaptori.adaptorpizzameniu;
import com.example.narcis.zvonne.fragSecundare.confirmafragment;
import com.example.narcis.zvonne.obiecte.pizza;
import com.kcode.bottomlib.BottomDialog;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;

import java.util.ArrayList;
import java.util.List;
public class comandafragment extends Fragment{
    private static comandafragment instance;
    View myView;
    private ListView listView;
    public  TextView pret1;
    private adaptorcomanda adaptor;
    public static int pret;
    private List<pizza> pizzaList=new ArrayList<>();
    private FragmentManager f;
    private trimitebadge trim;
    private Button buton;
    private TextView butoncomanda;
    private TextView butonsterge;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.comandafragment, container, false);
        init();
        ascultabunoane();


        return myView;
    }
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            trim = (trimitebadge) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    public interface trimitebadge{
        public void send(int i);
    }

    private void ascultabunoane() {
      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
              BottomDialog dialog = BottomDialog.newInstance("",new String[]{"1 bucata","2 bucati","3 bucati","4 bucati","5 bucati","6 bucati","7 bucati","8 bucati","9 bucati","Elimina"});
              dialog.show(MainActivity.fram,"dialog");
              dialog.setListener(new BottomDialog.OnClickListener() {
                  @Override
                  public void click(int position) {
                      if(position!=9){
                    pizzaList.get(i).setNrbucati(position+1);
                      }
                      else pizzaList.remove(i);
                      adaptor.notifyDataSetChanged();
                      calculeazapret();
                      trim.send(numara());
                  }
              });
          }
      });
      butoncomanda.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if (numara()==0){
                  Toast.makeText(myView.getContext(), "Trebuie sa ai ceva in cos !", Toast.LENGTH_SHORT).show();
              }
              else {
              confirmafragment.getInstance().setpizzalist(pizzaList);
              getFragmentManager().beginTransaction().replace(R.id.container, confirmafragment.getInstance()).addToBackStack("").commit();}
          }
      });
      butonsterge.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              pizzaList.clear();
              calculeazapret();
              adaptor.notifyDataSetChanged();
              trim.send(numara());
          }
      });

    }

    private int numara() {
        int nr=0;
        for (int i=0;i<pizzaList.size();i++){
            nr+=pizzaList.get(i).getNrbucati();
        }
        return nr;
    }

    private void calculeazapret() {
        pret=0;
        for (int i=0;i<pizzaList.size();i++){
            pizza p =pizzaList.get(i);
            pret+=p.getPret()*p.getNrbucati();}
        pret1.setText(pret+" lei");
    }

    private void init() {
        f=getActivity().getFragmentManager();
        butonsterge=(TextView)myView.findViewById(R.id.butonsterge);
        butoncomanda=(TextView)myView.findViewById(R.id.butoncomanda);
        listView =(ListView) myView.findViewById(R.id.listacomanda);

        adaptor=new adaptorcomanda(myView.getContext(),R.layout.adaptorcomanda,pizzaList);
        listView.setAdapter(adaptor);
        pret1=(TextView)myView.findViewById(R.id.pret1);
        calculeazapret();
    }

    public static comandafragment newInstance() {
        if (instance==null)
            instance = new comandafragment();
        return instance;
    }
    public void setPizzaList(List<pizza> list){
        this.pizzaList=list;
    }

}
