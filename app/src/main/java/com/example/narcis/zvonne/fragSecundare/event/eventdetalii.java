package com.example.narcis.zvonne.fragSecundare.event;


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


public class eventdetalii extends Fragment {

    private static eventdetalii instance;
    View myView;

    private ViewPager viewPAger;

    private PagerTabStrip pagerTabStrip;
    public static eventdetalii newInstance() {

        if (instance == null)
            instance = new eventdetalii();
        return instance;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.eventdetalii, container, false);
        pagerTabStrip=(PagerTabStrip)myView.findViewById(R.id.pagertab);
        pagerTabStrip.setTextColor(getResources().getColor(R.color.white));
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.white));
        viewPAger = (ViewPager) myView.findViewById(R.id.viewpagerdetalii);
        viewPAger.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        return myView;

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return  eventFRAG1.newInstance();
                case 1:
                    return  eventFRAG2.newInstance();
                default:
                    return new eventFRAG1();
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
