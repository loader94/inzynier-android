package ladowski.serwishandlowy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                tab_1 tab1 = new tab_1();
                return tab1;
            case 1:
                tab_2 tab2 = new tab_2();
                return tab2;
            case 2:
                tab_4 tab4 = new tab_4();
                return tab4;

            case 3:
                tab_3 tab3 = new tab_3();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
