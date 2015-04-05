package com.example.derekyu.fridgegrabber.Controller;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by derekyu on 4/4/15.
 */
public class OptionsFragAdapter extends FragmentPagerAdapter{
    final int PAGE_COUNT =3;
    private String tabTitle[] = new String[] {"Tab1", "Tab2", "Tab3"};
    private Context context;

    public OptionsFragAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount(){
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position){
        return FirstFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tabTitle[position];
    }
}
