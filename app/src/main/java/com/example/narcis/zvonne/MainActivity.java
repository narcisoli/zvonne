package com.example.narcis.zvonne;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.narcis.zvonne.fragPrincipale.comandafragment;
import com.example.narcis.zvonne.fragPrincipale.eventfragment;
import com.example.narcis.zvonne.fragPrincipale.meniu;
import com.example.narcis.zvonne.fragPrincipale.menufragment;
import com.example.narcis.zvonne.fragPrincipale.profilfragment;
import com.example.narcis.zvonne.fragSecundare.pizza.pizza1;
import com.example.narcis.zvonne.obiecte.pizza;
import com.google.firebase.database.FirebaseDatabase;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;



import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements pizza1.comunicare,comandafragment.trimitebadge {

    android.app.FragmentManager fragmentManager = getFragmentManager();

    public static android.app.FragmentManager f;
    public static BottomBar bottomBar;
    private ViewPager viewPager;
    public static FragmentManager fram;
    private List<pizza> pizzaList=new ArrayList<>();
    private BottomBarTab nearby;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Notif","pornim service");
        startService(new Intent(this,MyService.class));




        f=fragmentManager;
        init();
        nearby= bottomBar.getTabWithId(R.id.tab_comanda);
        ascultabutoane();
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        fram=getSupportFragmentManager();
        bottomBar.selectTabAtPosition(2,true);

    }



    private void ascultabutoane() {

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if(getSupportFragmentManager().getBackStackEntryCount()!=0)
                    getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.container)).commit();
                switch(tabId){
                    case R.id.tab_meniu:viewPager.setCurrentItem(0);break;
                    case R.id.tab_evenimente:viewPager.setCurrentItem(1);break;
                    case R.id.tab_acasa:viewPager.setCurrentItem(2);break;
                    case R.id.tab_comanda:viewPager.setCurrentItem(3);break;
                    case R.id.tab_profil:viewPager.setCurrentItem(4);break;
                }
            }
        });

    }
    private void init() {
        FirebaseDatabase.getInstance().getReference().child("Zvonne").child("totalcomenzi").setValue(0);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        viewPager=(ViewPager)findViewById(R.id.viewPager) ;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomBar.selectTabAtPosition(position,true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
    }

    @Override
    public void trimitemesaj(pizza pizza) {
        boolean ok=true;
        for(int i=0;i<pizzaList.size();i++){
            if(pizza.getTip()==pizzaList.get(i).getTip()){
                ok=false;
            }
        }
        if(ok){
            pizzaList.add(pizza);
        }

            for(int i=0;i<pizzaList.size();i++){
                if(pizza.getTip()==pizzaList.get(i).getTip()){
                  int nr= pizzaList.get(i).getNrbucati();
                    pizzaList.get(i).setNrbucati(nr+1);
                }
            }
        int nr=0;
        for(int i=0;i<pizzaList.size();i++){
           nr+=pizzaList.get(i).getNrbucati();
        }
        nearby.setBadgeCount(nr);
        comandafragment.newInstance().setPizzaList(pizzaList);



    }

    @Override
    public void send(int i) {
        nearby.setBadgeCount(i);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0:return meniu.newInstance();
                case 1:return eventfragment.newInstance();
                case 2: return menufragment.newInstance();
                case 3: return comandafragment.newInstance();
                case 4: return profilfragment.newInstance();
                default: return menufragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}




