package com.example.narcis.zvonne.fragSecundare.pizza;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.narcis.zvonne.R;


public class pizzadetalii extends Fragment {

    private static pizzadetalii instance;
    View myView;

    private ViewPager viewPAger;

    private PagerTabStrip pagerTabStrip;

    public static pizzadetalii newInstance() {

        if (instance == null)
            instance = new pizzadetalii();
        return instance;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.pizzadetalii, container, false);
        pagerTabStrip = (PagerTabStrip) myView.findViewById(R.id.pagertab2);
        pagerTabStrip.setTextColor(getResources().getColor(R.color.white));
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.white));
        viewPAger = (ViewPager) myView.findViewById(R.id.viewpagerdetalii2);
        viewPAger.setAdapter(new MyPagerAdapter2(getChildFragmentManager()));
        return myView;

    }

    private class MyPagerAdapter2 extends FragmentPagerAdapter {

        public MyPagerAdapter2(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return pizza1.getInstance();
                case 1:
                    return pizza2.getInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 1)
                return "Comentarii";
            else return "Detalii";
        }


    }


}
