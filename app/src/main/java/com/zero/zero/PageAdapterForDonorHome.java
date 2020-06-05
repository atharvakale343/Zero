package com.zero.zero;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class PagerAdapterForDonorHome extends FragmentPagerAdapter {
    int numberOfTabs;
    public PagerAdapterForDonorHome(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.numberOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Donate tab1 = new Donate();
                return tab1;
            case 1:
                MyDonations tab2 = new MyDonations();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}